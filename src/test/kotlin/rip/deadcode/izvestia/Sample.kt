package rip.deadcode.izvestia

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import rip.deadcode.izvestia.Core.expect


class Sample {

    @Test
    fun sampleTest() {
        expect {
            throw IllegalStateException("Failure!")
        }.throwsException {
            assertThat(it).isInstanceOf(IllegalStateException::class.java)
            assertThat(it).hasMessageThat().isEqualTo("Failure!")
        }
    }

    @Disabled
    @Test
    fun sampleTestFailure() {
        expect {
            // NOP
        }.throwsException {
            // NOP
        }
        // This test fails because no exception was throw in `expect` block.
    }
}
