package rip.deadcode.izvestia;

import com.google.common.truth.Truth;
import rip.deadcode.izvestia.functions.Executable;

import javax.annotation.Nullable;
import java.nio.file.Path;

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
}
