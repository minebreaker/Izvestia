package rip.deadcode.izvestia.functions;


@FunctionalInterface
public interface Consumer4<T, U, V, W> {
    public void consume( T param1, U param2, V param3, W param4 ) throws Throwable;
}
