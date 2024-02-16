package compilers.util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamToChar {

    public CharStream FileToCharStream(String filePath) {
        try {
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);
            CharStream charStream = CharStreams.fromStream(inputStream);
            return charStream;

        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
