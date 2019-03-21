package rip.deadcode.izvestia

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
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

    @Test
    fun sampleTestFailure() {
        try {
            expect {
                // NOP
            }.throwsException {
                // NOP
            }

            fail("Unreachable")

        } catch (e: AssertionError) {
            assertThat(e).hasMessageThat().isEqualTo("Expected that exception is thrown, but did not.")
        }
    }
}
