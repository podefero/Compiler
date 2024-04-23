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
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter multi-line input
        System.out.println("Enter multi-line input (enter an empty line to finish):");

        // Read input until an empty line is encountered
        StringBuilder multiLineInput = new StringBuilder();
        String line;

        try {
            while (!(line = scanner.nextLine()).isEmpty()) {
                multiLineInput.append(line).append("\n");
            }
        } catch (NoSuchElementException e) {
            // End of input reached
        } finally {
            scanner.close();
        }

        InputStream inputStream = new ByteArrayInputStream(multiLineInput.toString().getBytes());
        return inputStream;
    }
    public void setUseStdin(boolean useStdin) {
        this.useStdin = useStdin;
    }
}
