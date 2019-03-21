package rip.deadcode.izvestia.functions;


/**
 * Code block that can be executed, like testing code.
 *
 * @see <a href="https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/function/Executable.html"></a>
 */
@FunctionalInterface
public interface Executable {
    public void execute() throws Throwable;
}
