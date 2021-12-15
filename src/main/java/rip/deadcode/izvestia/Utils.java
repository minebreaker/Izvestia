package rip.deadcode.izvestia;

import rip.deadcode.izvestia.functions.Executable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;


public final class Utils {

    public static final String redirectStdout( Executable block ) {
        return redirect( System.out, System::setOut, block );
    }

    public static final String redirectStderr( Executable block ) {
        return redirect( System.err, System::setErr, block );
    }

    private static final String redirect( PrintStream current, Consumer<PrintStream> setter, Executable block ) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream( baos );
        setter.accept( ps );

        try {
            block.execute();
        } catch ( Throwable e ) {
            throw new RuntimeException( e );
        }

        setter.accept( current );

        return new String( baos.toByteArray(), StandardCharsets.UTF_8 );
    }
}
