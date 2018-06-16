package rip.deadcode.izvestia;

import java.util.function.Consumer;

import static com.google.common.truth.Truth.assertThat;

public final class Expects {

    private Expects() {}

    public static final class ExpectAssertion {

        private Executable testing;

        private ExpectAssertion( Executable testing ) {
            this.testing = testing;
        }

        /**
         * Used to assert the thrown exception.
         *
         * @param assertion Assertion function. You should assert the given exception is what is expected.
         * @throws AssertionError If test code didn't throw an exception.
         */
        public void throwsException( Consumer<Throwable> assertion ) {
            try {
                testing.execute();
            } catch ( Throwable throwable ) {
                assertion.accept( throwable );
                return;
            }

            throw new AssertionError( "Expected that exception is thrown, but did not." );
        }

        /**
         * Used to assert the thrown exception.
         *
         * @param t The class that is expected to be thrown.
         * @throws AssertionError If test code didn't throw an exception.
         */
        public void throwsException( Class<? extends Throwable> t ) {
            try {
                testing.execute();
            } catch ( Throwable throwable ) {
                assertThat( throwable ).isInstanceOf( t );
                return;
            }

            throw new AssertionError( "Expected that exception is thrown, but did not." );
        }
    }

    /**
     * Expects given test code will throw an exception.
     * If an exception was not thrown, this method throws {@link AssertionError}.
     *
     * {@code
     * class Test {
     *     {@literal @}Test
     *     void test() {
     *         expect(() -> {
     *             throw new IllegalStateException("Failure!");
     *         }).throwsException(e -> {
     *             // Use Truth or whatever to assert the exception
     *             assertThat(e).isInstanceOf(IllegalStateException.class);
     *             assertThat(e).hasMessageThat().isEqualTo("Failure!");
     *         });
     *     }
     * }
     * }
     *
     * @param testing Test code that is expected to throw an exception.
     * @return {@link ExpectAssertion} that asserts an expected exception.
     */
    public static ExpectAssertion expect( Executable testing ) {
        return new ExpectAssertion( testing );
    }
}
