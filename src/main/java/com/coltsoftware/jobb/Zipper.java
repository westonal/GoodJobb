package com.coltsoftware.jobb;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class Zipper {

    private final File inPath;
    private final File outPath;
    private final int trim;
    private final List<Pattern> noCompressPatterns = new ArrayList<>();

    public Zipper(File inPath, File outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
        trim = inPath.getAbsolutePath().length() + 1;
        addNoCompressExtension(".mp3");
        addNoCompressExtension(".jpg");
        addNoCompressExtension(".png");
    }

    public void addNoCompressExtension(String extension) {
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(extension) + "$", Pattern.CASE_INSENSITIVE);
        noCompressPatterns.add(pattern);
    }

    public ZipResult zip() throws IOException {
        ZipResult zipAction = new ZipResult();
        zipAction.zip();
        return zipAction;
    }

    public class ZipResult {

        public class ZipEntryDetails {

            private boolean compressed;
            private String relativeFileName;

            public ZipEntryDetails(String relativeFileName) {
                this.relativeFileName = relativeFileName;
            }

            private void setCompressed(boolean compressed) {
                this.compressed = compressed;
            }

            public boolean isCompressed() {
                return compressed;
            }

            public String getRelativeFileName() {
                return relativeFileName;
            }

            @Override
            public String toString() {
                return String.format("  %s%s", getRelativeFileName(), isCompressed() ? " (compressed)" : "");
            }
        }

        private final List<ZipEntryDetails> addedFiles = new ArrayList<>();

        public List<ZipEntryDetails> getAddedFiles() {
            return addedFiles;
        }

        private void zip() throws IOException {
            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outPath))) {
                addFolderToZip(out, inPath);
            }
        }

        private void addFolderToZip(ZipOutputStream out, File folder) throws IOException {
            for (File file : folder.listFiles())
                if (file.isDirectory())
                    addFolderToZip(out, file);
                else
                    addFileToZip(out, file);
        }

        private void addFileToZip(ZipOutputStream out, File infile) throws IOException {
            try (FileInputStream in = new FileInputStream(infile)) {
                String substring = infile.getAbsolutePath().substring(trim);
                ZipEntry e = new ZipEntry(substring);
                ZipEntryDetails entryDetails = new ZipEntryDetails(substring);
                if (noCompress(substring)) {
                    entryDetails.setCompressed(false);
                    out.setLevel(0);
                } else {
                    entryDetails.setCompressed(true);
                    out.setLevel(9);
                }
                out.putNextEntry(e);
                copyInStreamToOutStream(in, out);
                addedFiles.add(entryDetails);
            }
        }
    }

    private boolean noCompress(String extension) {
        for (Pattern pattern : noCompressPatterns)
            if (pattern.matcher(extension).matches()) return true;
        return false;
    }

    private byte[] copyBuffer = new byte[1024 * 10];

    private void copyInStreamToOutStream(InputStream in, OutputStream out) throws IOException {
        int count;
        while ((count = in.read(copyBuffer)) != -1)
            out.write(copyBuffer, 0, count);
    }

    public List<Pattern> getNoCompressPatterns() {
        return noCompressPatterns;
    }
}
