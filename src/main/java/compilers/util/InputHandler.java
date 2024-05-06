package compilers.util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {

    private String filePath;
    private boolean useStdin;

    public InputHandler(String filePath) {
        //trim the [ ]
        if (filePath == null || filePath == "[]") useStdin = true;
        else this.filePath = filePath.substring(1, filePath.length() - 1);
    }

    public CharStream fileToCharStream() {
        try {
            CharStream charStream = CharStreams.fromStream(getInputStream());
            return charStream;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private InputStream getInputStream() throws FileNotFoundException {
        InputStream inputStream;

        if (useStdin) inputStream = stdinToInputStream();
        else {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
        }

        return inputStream;
    }


    public InputStream stdinToInputStream() {
        StringBuilder multiLineInput = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        try {
            // Read input until EOF
            while ((line = reader.readLine()) != null) {
                multiLineInput.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = new ByteArrayInputStream(multiLineInput.toString().getBytes());
        return inputStream;
    }
    public void setUseStdin(boolean useStdin) {
        this.useStdin = useStdin;
    }
}
