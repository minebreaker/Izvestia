package rip.deadcode.izvestia.functions;


@FunctionalInterface
public interface Consumer3<T, U, V> {
    public void consume( T param1, U param2, V param3 ) throws Throwable;
}
