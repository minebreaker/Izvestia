package rip.deadcode.izvestia;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static rip.deadcode.izvestia.Core.expect;

class CoreTest {

    @Test
    void test1() {

        Exception expectedException = new IllegalStateException();

        expect( () -> {
            throw expectedException;
        } ).throwsException( e -> {
            assertThat( e ).isSameAs( expectedException );
        } );
    }

    @Test
    void test2() {
        try {
            expect( () -> {} ).throwsException( e -> {} );
            fail( "Should throw AssertionError." );
        } catch ( AssertionError e ) {
            assertThat( e ).hasMessageThat().isEqualTo( "Expected that exception is thrown, but did not." );
        }
    }
}