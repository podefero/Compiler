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
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
    }

    public void outputAsm(String asm) throws IOException {
        String file = getRelativeOutputFile("asm");
        if (file.isEmpty()) System.out.println(asm);
        else
            writeFile(asm, file);
    }

    public void outputLex(String lex) throws IOException {
        String file = getRelativeOutputFile("lex");
        if (file.isEmpty()) System.out.println(lex);
        else
            writeFile(lex, file);
    }

    public void outputAST(MutableGraph graph) throws IOException {
        String file = getRelativeOutputFile("dot");
        if (file.isEmpty()) System.out.println(Graphviz.fromGraph(graph).render(Format.DOT));
        else
            Graphviz.fromGraph(graph).width(1920).height(1080).render(Format.DOT).toFile(new File(getRelativeOutputFile("dot")));
    }


    private String getRelativeOutputFile(String fileExtension) throws IOException {
        if (outputFiles != null) {
            for (String file : outputFiles) {
                if (file.endsWith("." + fileExtension)) return file;
            }
        }
        return "";
    }

}
