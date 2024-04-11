package compilers.util;


import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler {
    private String outputFileArgs;
    private boolean useStdout;
    private String[] outputFiles;

    public OutputHandler(String outputFileArgs) {
        this.outputFileArgs = outputFileArgs;
        if (outputFileArgs == null || outputFileArgs == "[]") useStdout = true;
            //trim [ ]
        else this.outputFileArgs = outputFileArgs.substring(1, outputFileArgs.length() - 1);

        //split
        if (!useStdout) {
            outputFiles = this.outputFileArgs.split(",");
            if (outputFiles.length == 0)
                outputFiles = new String[]{this.outputFileArgs};
        }
    }

    private void writeFile(String content, String filePath) throws IOException {
        if (!useStdout) {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
        } else
            System.out.println(content);

    }

    public void outputAsm(String asm) throws IOException {
        writeFile(asm, getRelativeOutputFile("asm"));
    }

    public void outputLex(String lex) throws IOException {
        writeFile(lex, getRelativeOutputFile("lex"));
    }

    public void outputAST(MutableGraph graph) throws IOException {
        if (!useStdout)
            Graphviz.fromGraph(graph).width(1920).height(1080).render(Format.DOT).toFile(new File(getRelativeOutputFile("dot")));
        else
            System.out.println(Graphviz.fromGraph(graph).render(Format.DOT));
    }


    private String getRelativeOutputFile(String fileExtension) throws IOException {
        for (String file : outputFiles) {
            if (file.endsWith("." + fileExtension)) return fileExtension + "/" + file;
        }
        throw new IOException("Can't find relative file for " + fileExtension + " extension\nOutputFile Arguments: " + outputFileArgs);
    }

}
