package rip.deadcode.izvestia.parameterize;

public final class TestCase {

    private TestCase() {
        throw new AssertionError();
    }

    public static final class TestCase2<T, U> {

        private final T field1;
        private final U field2;

        TestCase2( T field1, U field2 ) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public T component1() {
            return field1;
        }

        public U component2() {
            return field2;
        }
    }

    public static final class TestCase3<T, U, V> {

        private final T field1;
        private final U field2;
        private final V field3;

        TestCase3( T field1, U field2, V field3 ) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        public T component1() {
            return field1;
        }

        public U component2() {
            return field2;
        }

        public V component3() {
            return field3;
        }
    }

    public static final class TestCase4<T, U, V, W> {

        private final T field1;
        private final U field2;
        private final V field3;
        private final W field4;

        TestCase4( T field1, U field2, V field3, W field4 ) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        public T component1() {
            return field1;
        }

        public U component2() {
            return field2;
        }

        public V component3() {
            return field3;
        }

        public W component4() {
            return field4;
        }
    }
}
