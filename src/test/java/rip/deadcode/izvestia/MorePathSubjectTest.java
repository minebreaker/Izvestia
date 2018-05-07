package rip.deadcode.izvestia;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static rip.deadcode.izvestia.Core.assertThat;

class MorePathSubjectTest {

    @Test
    void testSameFile() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );
        Files.createFile( actual );

        assertThat( actual ).isSameFile( fs.getPath( "/test" ) );
    }

    @Test
    void testPointsSamePath() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        assertThat( actual ).pointsSamePath( fs.getPath( "/etc/../test" ) );
    }

    @Test
    void testPointsSamePathFail() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        try {
            assertThat( actual ).pointsSamePath( fs.getPath( "/another" ) );
        } catch ( AssertionError e ) {
            return;
        }
        throw new RuntimeException();
    }

    @Test
    void testHasFileNameThat() {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path actual = fs.getPath( "/test" );

        assertThat( actual ).hasFileNameThat().isEqualTo( "test" );
    }
}