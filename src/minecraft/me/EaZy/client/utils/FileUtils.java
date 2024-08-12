package me.EaZy.client.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    public static final int EaZy = 212;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private FileUtils() {
    }

    public static void print(final ArrayList<String> lines, final File dir, final boolean inform) {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(dir))) {
                for (final String s : lines) {
                    bw.write(String.valueOf(s) + "\r\n");
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(final File dir, final boolean inform) {
        final ArrayList<String> lines = new ArrayList<>();
        try {
            String curLine;
            try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                while ((curLine = br.readLine()) != null && !curLine.startsWith("#")) {
                    lines.add(curLine);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
