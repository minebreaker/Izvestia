# Izvestia - tiny library for Java/Kotlin unit testing

JUnit 5 assertion is not easy to use, so I wrote my own one.
Use with Google's [Truth](http://google.github.io/truth/).


## Usage

```java
class Test {
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
class Test {
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

## Joke

> There is no truth in the news and there is no news in the truth.
