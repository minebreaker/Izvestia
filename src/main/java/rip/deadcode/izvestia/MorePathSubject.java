package rip.deadcode.izvestia;

import com.google.common.truth.FailureMetadata;
import com.google.common.truth.PathSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Subject;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;

import static com.google.common.truth.Fact.fact;
import static com.google.common.truth.Fact.simpleFact;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;


/**
 * Assertions for {@link Path} instances.
 * Replacement for {@link PathSubject}.
 */
public final class MorePathSubject extends Subject<MorePathSubject, Path> {

    private MorePathSubject( FailureMetadata metadata, @Nullable Path actual ) {
        super( metadata, actual );
    }

    /**
     * Asserts that two paths locate the same file, using {@link Files#isSameFile(Path, Path)}.
     *
     * @param other Other
     * @throws UncheckedIOException If IO failed.
     */
    public void isSameFile( @Nullable Path other ) throws UncheckedIOException {
        if ( actual() == null || other == null ) {
            failWithoutActual( fact( "expected non null paths", actualAsString() ) );
        } else {
            try {
                if ( !Files.isSameFile( actual(), other ) ) {
                    failWithActual(
                            fact( "expected a path that is equal to", other )
                    );
                }
            } catch ( IOException e ) {
                failWithActual( fact( "IO failure occurred during the assertion", e ) );
            }
        }
    }

    /**
     * Asserts that both paths are logically same.
     * While {@link Files#isSameFile(Path, Path)} may access to the actual files,
     * this only compares the path.
     * Does not care {@link java.nio.file.FileSystem} of the paths.
     *
     * @param other Other
     */
    public void pointsSamePath( @Nullable Path other ) {
        isNotNull();
        if ( other == null ) {
            failWithActual( simpleFact( "expected non null paths" ) );
        }

        Path normalizedActual = actual().toAbsolutePath().normalize();
        //noinspection ConstantConditions
        Path normalizedExpected = other.toAbsolutePath().normalize();

        if ( normalizedActual.getNameCount() != normalizedExpected.getNameCount() ) {
            failWithActual( fact( "expected a path that is same as", other ) );
        }

        Iterator<Path> iA = normalizedActual.iterator();
        Iterator<Path> iE = normalizedExpected.iterator();

        while ( iA.hasNext() || iE.hasNext() ) {
            Path a = iA.next();
            Path e = iE.next();
            if ( !Objects.equals( a, e ) ) {
                failWithActual( fact( "expected a path that is same as", other ) );
            }
        }
    }

    /**
     * Assert the name oh the file. File has not to exist.
     *
     * @return StringSubject of the file name.
     */
    public StringSubject hasFileNameThat() {
        isNotNull();
        Path nameElement = actual().getFileName();
        if ( nameElement == null ) {
            failWithoutActual( simpleFact( "This instance has no name element." ) );
        }

        //noinspection ConstantConditions
        return check( "nameElement" ).that( nameElement.toString() );
    }

    public void isAbsolute() {
        isNotNull();
        if ( !actual().isAbsolute() ) {
            failWithActual( simpleFact( "expected to be absolute" ) );
        }
    }

    public void isRelative() {
        isNotNull();
        if ( actual().isAbsolute() ) {
            failWithActual( simpleFact( "expected to be relative" ) );
        }
    }

    static enum FileType {
        FILE, DIRECTORY, SYMBOLIC_LINK {
            @Override
            public String toString() {
                return "symbolic link";
            }
        }, UNKNOWN;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    private static FileType inferType( Path path ) {
        if ( Files.isRegularFile( path, NOFOLLOW_LINKS ) ) {
            return FileType.FILE;
        } else if ( Files.isDirectory( path, NOFOLLOW_LINKS ) ) {
            return FileType.DIRECTORY;
        } else if ( Files.isSymbolicLink( path ) ) {
            return FileType.SYMBOLIC_LINK;
        } else {
            return FileType.UNKNOWN;
        }
    }

    private void assertType( Path path, FileType expected ) {
        isNotNull();
        FileType actualType = inferType( path );
        if ( !actualType.equals( expected ) ) {
            failWithoutActual(
                    fact( "expected to be", expected ),
                    fact( "but was", actualType ),
                    fact( "actual path", actual() )
            );
        }
    }

    public void isFile() {
        assertType( actual(), FileType.FILE );
    }

    public void isDirectory() {
        assertType( actual(), FileType.DIRECTORY );
    }

    public void isSymbolicLink() {
        assertType( actual(), FileType.SYMBOLIC_LINK );
    }

    public void exists() {
        isNotNull();
        if ( !Files.exists( actual(), NOFOLLOW_LINKS ) ) {
            failWithoutActual(
                    simpleFact( "expected to exist" ),
                    fact( "actual path", actual() ) );
        }
    }

    public static Subject.Factory<MorePathSubject, Path> paths() {
        return MorePathSubject::new;
    }
}
