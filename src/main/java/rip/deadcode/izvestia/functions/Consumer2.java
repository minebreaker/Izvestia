package rip.deadcode.izvestia.functions;


@FunctionalInterface
public interface Consumer2<T, U> {
    public void consume( T param1, U param2 ) throws Throwable;
}
