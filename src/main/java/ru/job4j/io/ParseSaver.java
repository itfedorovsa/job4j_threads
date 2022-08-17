package ru.job4j.io;

import java.io.*;

public class ParseSaver {
    private final File output;

    public ParseSaver(File output) {
        this.output = output;
    }

    public void saveContent(String content) {
        try (PrintWriter o = new PrintWriter(
                new BufferedOutputStream(new FileOutputStream(output)))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
