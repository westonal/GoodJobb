package com.coltsoftware.jobb;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws IOException {

        String pathname = ;
        String outputName = ;
        boolean verbose = true;

        Zipper zipper = new Zipper(new File(pathname), new File(outputName));

        if (verbose) {
            out.println("Creating zip");
            out.println("These patterns will not be compressed");
            zipper.getNoCompressPatterns().forEach(out::println);
        }

        Zipper.ZipResult zipResult = zipper.zip();

        if (verbose) {
            out.println("Files:");
            zipResult.getAddedFiles().forEach(out::println);
        }

        out.println("Complete");
    }
}
