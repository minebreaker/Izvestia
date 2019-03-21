package rip.deadcode.izvestia.parameterize;


import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DynamicTest;
import rip.deadcode.izvestia.functions.Consumer1;
import rip.deadcode.izvestia.functions.Consumer2;
import rip.deadcode.izvestia.functions.Consumer3;
import rip.deadcode.izvestia.functions.Consumer4;
import rip.deadcode.izvestia.parameterize.TestCase.TestCase2;
import rip.deadcode.izvestia.parameterize.TestCase.TestCase3;
import rip.deadcode.izvestia.parameterize.TestCase.TestCase4;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;


public final class Parameterizers {

    public static ParameterClause test( String testName ) {
        return new ParameterClause( testName );
    }

    public static <T, U> TestCase2<T, U> testCase( T field1, U field2 ) {
        return new TestCase2<>( field1, field2 );
    }

    public static <T, U, V> TestCase3<T, U, V> testCase( T field1, U field2, V field3 ) {
        return new TestCase3<>( field1, field2, field3 );
    }

    public static <T, U, V, W> TestCase4<T, U, V, W> testCase( T field1, U field2, V field3, W field4 ) {
        return new TestCase4<>( field1, field2, field3, field4 );
    }

    @SafeVarargs
    public static <T> List<T> args( T... args ) {
        return ImmutableList.copyOf( args );
    }

    public static final class ParameterClause {

        private final String testName;

        private ParameterClause( String testName ) {
            this.testName = testName;
        }

        @SafeVarargs
        public final <T> RunClause1<T> parameterized( T... arguments ) {
            return new RunClause1<>( testName, arguments );
        }

        @SafeVarargs
        public final <T, U> RunClause2<T, U> parameterized( TestCase2<T, U>... testCase ) {
            return new RunClause2<>( testName, testCase );
        }

        @SafeVarargs
        public final <T, U, V> RunClause3<T, U, V> parameterized( TestCase3<T, U, V>... testCase ) {
            return new RunClause3<>( testName, testCase );
        }

        @SafeVarargs
        public final <T, U, V, W> RunClause4<T, U, V, W> parameterized( TestCase4<T, U, V, W>... testCase ) {
            return new RunClause4<>( testName, testCase );
        }

        @SuppressWarnings( "unchecked" )
        public final <T, U> RunClause2<T, U> parameterized( Iterable<T> arg1, Iterable<U> arg2 ) {
            return new RunClause2<>(
                    testName,
                    stream( arg1 )
                            .flatMap( e -> stream( arg2 )
                                    .map( f -> new TestCase2<>( e, f ) ) )
                            .toArray( n -> (TestCase2<T, U>[]) new TestCase2[n] )
            );
        }

        @SuppressWarnings( "unchecked" )
        public final <T, U, V> RunClause3<T, U, V> parameterized( Iterable<T> arg1, Iterable<U> arg2, Iterable<V> arg3 ) {
            return new RunClause3<>(
                    testName,
                    stream( arg1 )
                            .flatMap( e -> stream( arg2 )
                                    .flatMap( f -> stream( arg3 )
                                            .map( g -> new TestCase3( e, f, g ) ) ) )
                            .toArray( n -> (TestCase3<T, U, V>[]) new TestCase3[n] )
            );
        }
    }

    public static final class RunClause1<T> {

        private final String testName;
        private final T[] arguments;

        private RunClause1( String testName, T[] arguments ) {
            this.testName = testName;
            this.arguments = arguments;
        }

        @Nonnull
        public Stream<DynamicTest> run( Consumer1<T> runner ) {
            return Arrays.stream( arguments ).map( args -> DynamicTest.dynamicTest(
                    formatDisplayName( testName, args ),
                    () -> {
                        runner.consume( args );
                    }
            ) );
        }
    }

    public static final class RunClause2<T, U> {

        private final String testName;
        private final TestCase2<T, U>[] arguments;

        private RunClause2( String testName, TestCase2<T, U>[] arguments ) {
            this.testName = testName;
            this.arguments = arguments;
        }

        @Nonnull
        public Stream<DynamicTest> run( Consumer2<T, U> runner ) {
            return Arrays.stream( arguments ).map( args -> DynamicTest.dynamicTest(
                    formatDisplayName( testName, args.component1(), args.component2() ),
                    () -> {
                        runner.consume( args.component1(), args.component2() );
                    }
            ) );
        }
    }

    public static final class RunClause3<T, U, V> {

        private final String testName;
        private final TestCase3<T, U, V>[] arguments;

        private RunClause3( String testName, TestCase3<T, U, V>[] arguments ) {
            this.testName = testName;
            this.arguments = arguments;
        }

        @Nonnull
        public Stream<DynamicTest> run( Consumer3<T, U, V> runner ) {
            return Arrays.stream( arguments ).map( args -> DynamicTest.dynamicTest(
                    formatDisplayName( testName, args.component1(), args.component2(), args.component3() ),
                    () -> {
                        runner.consume( args.component1(), args.component2(), args.component3() );
                    }
            ) );
        }
    }

    public static final class RunClause4<T, U, V, W> {

        private final String testName;
        private final TestCase4<T, U, V, W>[] arguments;

        private RunClause4( String testName, TestCase4<T, U, V, W>[] arguments ) {
            this.testName = testName;
            this.arguments = arguments;
        }

        @Nonnull
        public Stream<DynamicTest> run( Consumer4<T, U, V, W> runner ) {
            return Arrays.stream( arguments ).map( args -> DynamicTest.dynamicTest(
                    formatDisplayName(
                            testName, args.component1(), args.component2(), args.component3(), args.component4()
                    ),
                    () -> {
                        runner.consume( args.component1(), args.component2(), args.component3(), args.component4() );
                    }
            ) );
        }
    }

    private static String formatDisplayName( String testName, Object... args ) {
        return Arrays.stream( args )
                     .map( Object::toString )
                     .collect( joining( ", ", testName + "(", ")" ) );
    }

    private static <T> Stream<T> stream( Iterable<T> iter ) {
        return StreamSupport.stream( iter.spliterator(), false );
    }
}
