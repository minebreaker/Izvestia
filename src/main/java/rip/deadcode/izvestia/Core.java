package rip.deadcode.izvestia;

import com.google.common.truth.Truth;

import javax.annotation.Nullable;
import java.nio.file.Path;

public final class Core {

    /**
     * @see Expects#expect(Executable)
     */
    public static Expects.ExpectAssertion expect( Executable testing ) {
        return Expects.expect( testing );
    }

    public static MorePathSubject assertThat( @Nullable Path actual ) {
        return Truth.assertAbout( MorePathSubject.paths() ).that( actual );
    }
}
