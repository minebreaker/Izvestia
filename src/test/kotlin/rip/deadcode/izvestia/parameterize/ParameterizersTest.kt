package rip.deadcode.izvestia.parameterize

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import rip.deadcode.izvestia.parameterize.Parameterizers.args
import rip.deadcode.izvestia.parameterize.Parameterizers.test
import rip.deadcode.izvestia.parameterize.Parameterizers.testCase
import rip.deadcode.izvestia.parameterize.TestCase.TestCase2
import rip.deadcode.izvestia.parameterize.TestCase.TestCase3
import rip.deadcode.izvestia.parameterize.TestCase.TestCase4
import kotlin.streams.toList


class ParameterizersTest {

    @Test
    fun test1() {
        val argList = mutableListOf<String>()
        val result = test("testName").parameterized("1", "2", "3").run { arg ->
            argList.add(arg)
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName })
                .containsExactly("testName(1)", "testName(2)", "testName(3)").inOrder()
        assertThat(argList).containsExactly("1", "2", "3").inOrder()
    }

    @Test
    fun test2() {
        val argList = mutableListOf<TestCase2<String, Int>>()
        val result = test("testName").parameterized(
                testCase("1", 1),
                testCase("2", 2),
                testCase("3", 3)
        ).run { arg1, arg2 ->
            argList.add(testCase(arg1, arg2))
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName })
                .containsExactly("testName(1, 1)", "testName(2, 2)", "testName(3, 3)").inOrder()
        assertThat(argList.map { it.component1() }).containsExactly("1", "2", "3").inOrder()
        assertThat(argList.map { it.component2() }).containsExactly(1, 2, 3).inOrder()
    }

    @Test
    fun test3() {
        val argList = mutableListOf<TestCase3<String, String, Int>>()
        val result = test("testName").parameterized(
                testCase("1", "a", 1),
                testCase("2", "b", 2),
                testCase("3", "c", 3)
        ).run { arg1, arg2, arg3 ->
            argList.add(testCase(arg1, arg2, arg3))
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName })
                .containsExactly("testName(1, a, 1)", "testName(2, b, 2)", "testName(3, c, 3)").inOrder()
        assertThat(argList.map { it.component1() }).containsExactly("1", "2", "3").inOrder()
        assertThat(argList.map { it.component2() }).containsExactly("a", "b", "c").inOrder()
        assertThat(argList.map { it.component3() }).containsExactly(1, 2, 3).inOrder()
    }

    @Test
    fun test4() {
        val argList = mutableListOf<TestCase4<String, String, String, Int>>()
        val result = test("testName").parameterized(
                testCase("1", "a", "A", 1),
                testCase("2", "b", "B", 2),
                testCase("3", "c", "C", 3)
        ).run { arg1, arg2, arg3, arg4 ->
            argList.add(testCase(arg1, arg2, arg3, arg4))
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName })
                .containsExactly("testName(1, a, A, 1)", "testName(2, b, B, 2)", "testName(3, c, C, 3)").inOrder()
        assertThat(argList.map { it.component1() }).containsExactly("1", "2", "3").inOrder()
        assertThat(argList.map { it.component2() }).containsExactly("a", "b", "c").inOrder()
        assertThat(argList.map { it.component3() }).containsExactly("A", "B", "C").inOrder()
        assertThat(argList.map { it.component4() }).containsExactly(1, 2, 3).inOrder()
    }

    @Test
    fun testCombination2() {
        val argList = mutableListOf<TestCase2<String, Int>>()
        val result = test("testName").parameterized(
                args("1", "2"),
                args(1, 2, 3)
        ).run { arg1, arg2 ->
            argList.add(testCase(arg1, arg2))
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName }).containsExactly(
                "testName(1, 1)", "testName(1, 2)", "testName(1, 3)",
                "testName(2, 1)", "testName(2, 2)", "testName(2, 3)"
        ).inOrder()
        assertThat(argList.map { it.component1() }).containsExactly("1", "1", "1", "2", "2", "2").inOrder()
        assertThat(argList.map { it.component2() }).containsExactly(1, 2, 3, 1, 2, 3).inOrder()
    }

    @Test
    fun testCombination3() {
        val argList = mutableListOf<TestCase3<String, String, Int>>()
        val result = test("testName").parameterized(
                args("1", "2"),
                args("a", "b"),
                args(1, 2)
        ).run { arg1, arg2, arg3 ->
            argList.add(testCase(arg1, arg2, arg3))
        }.toList()
        result.forEach {
            it.executable.execute()
        }

        assertThat(result.map { it.displayName }).containsExactly(
                "testName(1, a, 1)", "testName(1, a, 2)",
                "testName(1, b, 1)", "testName(1, b, 2)",
                "testName(2, a, 1)", "testName(2, a, 2)",
                "testName(2, b, 1)", "testName(2, b, 2)"
        ).inOrder()
        assertThat(argList.map { it.component1() }).containsExactly("1", "1", "1", "1", "2", "2", "2", "2").inOrder()
        assertThat(argList.map { it.component2() }).containsExactly("a", "a", "b", "b", "a", "a", "b", "b").inOrder()
        assertThat(argList.map { it.component3() }).containsExactly(1, 2, 1, 2, 1, 2, 1, 2).inOrder()
    }
}
