package rip.deadcode.izvestia;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.truth.ExpectFailure.assertThat;
import static rip.deadcode.izvestia.Core.assertThat;
import static rip.deadcode.izvestia.Core.expect;

class MorePathSubjectTest {

    @Test
    void testSameFile1() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createFile( actual );

        assertThat( actual ).isSameFile( fs.getPath( "/test" ) );
    }

    @Test
    void testSameFile2() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path p1 = fs.getPath( "/test1" );
        Files.createFile( p1 );
        Path p2 = fs.getPath( "/test2" );
        Files.createFile( p2 );

        expect( () -> {
            assertThat( p1 ).isSameFile( p2 );
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factValue( "expected a path that is equal to" ).isEqualTo( "/test2" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "/test1" );
        } );
    }

    @Test
    void testPointsSamePath1() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        assertThat( actual ).pointsSamePath( fs.getPath( "/etc/../test" ) );
    }

    @Test
    void testPointsSamePath2() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test1" );

        expect( () -> {
            assertThat( actual ).pointsSamePath( fs.getPath( "/test2" ) );
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factValue( "expected a path that is same as" ).isEqualTo( "/test2" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "/test1" );
        } );
    }

    @Test
    void testHasFileNameThat1() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/foo/bar/test" );

        assertThat( actual ).hasFileNameThat().isEqualTo( "test" );
    }

    @Test
    void testHasFileNameThat2() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getRootDirectories().iterator().next();

        expect( () -> {
            assertThat( actual ).hasFileNameThat().isEqualTo( "test" );
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factKeys().containsExactly( "This instance has no name element." );
        } );
    }

    @Test
    void testIsAbsolute1() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        assertThat( actual ).isAbsolute();
    }

    @Test
    void testIsAbsolute2() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "./test" );

        expect( () -> {
            assertThat( actual ).isAbsolute();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factKeys().contains( "expected to be absolute" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "./test" );
        } );
    }

    @Test
    void testIsRelative1() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "./test" );

        assertThat( actual ).isRelative();
    }

    @Test
    void testIsRelative2() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        expect( () -> {
            assertThat( actual ).isRelative();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factKeys().contains( "expected to be relative" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "/test" );
        } );
    }

    @Test
    void testIsFile1() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createFile( actual );

        assertThat( actual ).isFile();
    }

    @Test
    void testIsFile2() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createSymbolicLink( actual, fs.getPath( "/other" ) );

        expect( () -> {
            assertThat( actual ).isFile();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factValue( "expected to be" ).isEqualTo( "file" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "symbolic link" );
            assertThat( (AssertionError) t ).factValue( "actual path" ).isEqualTo( "/test" );
        } );
    }

    @Test
    void testIsDirectory() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createDirectory( actual );

        assertThat( actual ).isDirectory();
    }

    @Test
    void testIsDirectory2() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createSymbolicLink( actual, fs.getPath( "/other" ) );

        expect( () -> {
            assertThat( actual ).isDirectory();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factValue( "expected to be" ).isEqualTo( "directory" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "symbolic link" );
            assertThat( (AssertionError) t ).factValue( "actual path" ).isEqualTo( "/test" );
        } );
    }

    @Test
    void testIsSymbolicLink1() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createSymbolicLink( actual, fs.getPath( "/other" ) );

        assertThat( actual ).isSymbolicLink();
    }

    @Test
    void testIsSymbolicLink2() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createFile( actual );

        expect( () -> {
            assertThat( actual ).isSymbolicLink();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factValue( "expected to be" ).isEqualTo( "symbolic link" );
            assertThat( (AssertionError) t ).factValue( "but was" ).isEqualTo( "file" );
            assertThat( (AssertionError) t ).factValue( "actual path" ).isEqualTo( "/test" );
        } );
    }

    @Test
    void testExists1() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createFile( actual );

        assertThat( actual ).exists();
    }

    @Test
    void testExists2() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        expect( () -> {
            assertThat( actual ).exists();
        } ).throwsException( t -> {
            assertThat( (AssertionError) t ).factKeys().contains( "expected to exist" );
            assertThat( (AssertionError) t ).factValue( "actual path" ).isEqualTo( "/test" );
        } );
    }
}