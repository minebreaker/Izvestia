package rip.deadcode.izvestia;

import com.google.common.truth.Truth;
import rip.deadcode.izvestia.functions.Executable;
import rip.deadcode.izvestia.parameterize.Parameterizers;
import rip.deadcode.izvestia.parameterize.TestCase;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.List;


public final class Core {

    private Core() {}

    /**
     * @param testing Test codes.
     * @see Expects#expect(Executable)
     * @return see {@link Expects.ExpectAssertion}
     */
    public static Expects.ExpectAssertion expect( Executable testing ) {
        return Expects.expect( testing );
    }

    /**
     * {@link com.google.common.truth.Subject} for {@link Path}.
     *
     * @param actual -
     * @see com.google.common.truth.Truth8#assertThat(Path)
     * @return -
     */
    public static MorePathSubject assertThat( @Nullable Path actual ) {
        return Truth.assertAbout( MorePathSubject.paths() ).that( actual );
    }

    public static Parameterizers.ParameterClause test( String testName ) {
        return Parameterizers.test( testName );
    }

    public static <T, U> TestCase.TestCase2<T, U> testCase( T field1, U field2 ) {
        return Parameterizers.testCase( field1, field2 );
    }

    public static <T, U, V> TestCase.TestCase3<T, U, V> testCase( T field1, U field2, V field3 ) {
        return Parameterizers.testCase( field1, field2, field3 );
    }

    public static <T, U, V, W> TestCase.TestCase4<T, U, V, W> testCase( T field1, U field2, V field3, W field4 ) {
        return Parameterizers.testCase( field1, field2, field3, field4 );
    }

    @SafeVarargs
    public static <T> List<T> args( T... args ) {
        return Parameterizers.args( args );
    }
}
