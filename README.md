# Izvestia - tiny library for Java/Kotlin unit testing

JUnit 5 assertions are not easy to use, so I wrote my own one.
Use this with Google's [Truth](http://google.github.io/truth/).

[![CircleCI](https://circleci.com/gh/minebreaker/Izvestia.svg?style=svg)](https://circleci.com/gh/minebreaker/Izvestia)
[![codecov](https://codecov.io/gh/minebreaker/Izvestia/branch/master/graph/badge.svg)](https://codecov.io/gh/minebreaker/Izvestia)


## Usage

```java
class TestClass {
    @Test
    void test() {

        expect(() -> {
            throw new IllegalStateException("Failure!");
        }).throwsException(e -> {
            // Use Truth or whatever to assert the exception
            assertThat(e).isInstanceOf(IllegalStateException.class);
            assertThat(e).hasMessageThat().isEqualTo("Failure!");
        });
    }
}
```

```kotlin
class TestClass {
    @Test
    fun test() {

        expect {
            throw IllegalStateException("Failure!")
        }.thorwsException {
            assertThat(it).isInstanceOf(IllegalStateException::class.java)
            assertThat(it).hasMessageThat().isEqualTo("Failure!")
        }
    }
}
```


## Parameterized test

### Parameterized test cases

```kotlin
class Test {
    @TestFactory
    fun testMethod() = test("test-name").parameterized(
        testCase("foo", 1),
        testCase("bar", 2)
    ).run { arg1, arg2 ->
        assertions()
    }
}
```


### Combinations

```kotlin
class Test {
    @TestFactory
    fun testMethod() = test("test-name").parameterized(
        args("foo", "bar"),
        args(1, 2, 3)
    ).run { arg1, arg2 ->
        assertions()
    }
}
```


## Joke

> There is no truth in the news and there is no news in the truth.
