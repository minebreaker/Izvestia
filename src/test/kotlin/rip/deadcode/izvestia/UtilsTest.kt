package rip.deadcode.izvestia

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class UtilsTest {

    private val sep = System.lineSeparator()

    @Test
    fun testRedirectStdout() {

        val result = Utils.redirectStdout {
            println("foo")
            println("bar")
        }

        assertThat(result).isEqualTo("foo${sep}bar${sep}")
    }

    @Test
    fun testRedirectStderr() {

        val result = Utils.redirectStderr {
            System.err.println("foo")
            System.err.println("bar")
        }

        assertThat(result).isEqualTo("foo${sep}bar${sep}")
    }
}
