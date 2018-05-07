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
            failWithRawMessage( "Not true that %s is same file as <%s>", actualAsString(), other );
        } else {
            try {
                boolean result = Files.isSameFile( actual(), other );
            } catch ( IOException e ) {
                throw new UncheckedIOException( e );
            }
        }
    }

    public void pointsSamePath( @Nullable Path other ) {
        isNotNull();
        if ( other == null ) {
            failWithRawMessage( "Not true that %s has same path as %s", actualAsString(), other );
        }


        Path normalizedActual = actual().toAbsolutePath().normalize();
        //noinspection ConstantConditions
        Path normalizedExpected = other.toAbsolutePath().normalize();

        if ( normalizedActual.getNameCount() != normalizedExpected.getNameCount() ) {
            failWithRawMessage( "Not true that %s is same path as %s", actualAsString(), other );
        }

        Iterator<Path> iA = normalizedActual.iterator();
        Iterator<Path> iE = normalizedExpected.iterator();

        while ( iA.hasNext() || iE.hasNext() ) {
            Path a = iA.next();
            Path e = iE.next();
            if ( !Objects.equals( a, e ) ) {
                failWithRawMessage( "Not true that %s is same path as %s", actualAsString(), other );
            }
        }
    }

    public StringSubject hasFileNameThat() {
        isNotNull();
        Path nameElement = actual().getFileName();
        if ( nameElement == null ) {
            failWithRawMessage( "This instance has no name element." );
        }

        //noinspection ConstantConditions
        return check( "nameElement" ).that( nameElement.toString() );
    }

    public void isAbsolute() {
        isNotNull();
        if ( !actual().isAbsolute() ) {
            failWithRawMessage( "Not true that %s is absolute.", actualAsString() );
        }
    }

    public void isRelative() {
        isNotNull();
        if ( actual().isAbsolute() ) {
            failWithRawMessage( "Not true that %s is relative.", actualAsString() );
        }
    }

    public void isFile() {
        isNotNull();
        if ( !Files.isRegularFile( actual() ) ) {
            failWithRawMessage( "Not true that %s is a file.", actualAsString() );
        }
    }

    public void isDirectory() {
        isNotNull();
        if ( !Files.isDirectory( actual() ) ) {
            failWithRawMessage( "Not true that %s is a directory.", actualAsString() );
        }
    }

    public void isSymbolicLink() {
        isNotNull();
        if ( !Files.isSymbolicLink( actual() ) ) {
            failWithRawMessage( "Not true that %s is a symbolic link.", actualAsString() );
        }
    }

    public void exists() {
        isNotNull();
        if ( !Files.exists( actual() ) ) {
            failWithRawMessage( "Not true that the file %s exists.", actualAsString() );
        }
    }

    public static Subject.Factory<MorePathSubject, Path> paths() {
        return MorePathSubject::new;
    }
}
