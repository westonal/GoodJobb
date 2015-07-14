package com.coltsoftware.jobb;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class Zipper {

    private final File inPath;
    private final File outPath;
    private final int trim;
    private final List<CompressionPattern> noCompressPatterns = new ArrayList<>();

    public Zipper(File inPath, File outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
        trim = inPath.getAbsolutePath().length() + 1;
    }

    public void addNoCompressExtension(String extension) {
        noCompressPatterns.add(new FileExtensionCompressionPattern(extension));
    }

    public void addNoCompressRegex(String regex) {
        noCompressPatterns.add(new RegexCompresssionPattern(regex));
    }

    public ZipResult zip() throws IOException {
        ZipResult zipAction = new ZipResult();
        zipAction.zip();
        return zipAction;
    }

    public class ZipResult {

        public long getSize() {
            return outPath.length();
        }

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
                String fileNameInZip = infile.getAbsolutePath().substring(trim);
                ZipEntry e = new ZipEntry(fileNameInZip);
                ZipEntryDetails entryDetails = new ZipEntryDetails(fileNameInZip);
                if (noCompress(fileNameInZip)) {
                    entryDetails.setCompressed(false);
                    out.setLevel(Deflater.NO_COMPRESSION);
                    e.setMethod(ZipOutputStream.STORED);
                    e.setSize(infile.length());
                    e.setCompressedSize(infile.length());
                    e.setCrc(getCrc32(infile));
                } else {
                    entryDetails.setCompressed(true);
                    out.setLevel(Deflater.BEST_COMPRESSION);
                    out.setMethod(ZipOutputStream.DEFLATED);
                }
                out.putNextEntry(e);
                copyInStreamToOutStream(in, out);
                out.closeEntry();
                addedFiles.add(entryDetails);
            }
        }

        private long getCrc32(File infile) throws IOException {
            CRC32 crc32 = new CRC32();
            try (FileInputStream in = new FileInputStream(infile)) {
                int count;
                while ((count = in.read(copyBuffer)) != -1)
                    crc32.update(copyBuffer, 0, count);
            }
            return crc32.getValue();
        }
    }

    private boolean noCompress(String fileNameInZip) {
        for (CompressionPattern pattern : noCompressPatterns)
            if (pattern.CompressFile(fileNameInZip)) return true;
        return false;
    }

    private byte[] copyBuffer = new byte[1024 * 10];

    private void copyInStreamToOutStream(InputStream in, OutputStream out) throws IOException {
        int count;
        while ((count = in.read(copyBuffer)) != -1)
            out.write(copyBuffer, 0, count);
    }

    public List<CompressionPattern> getNoCompressPatterns() {
        return noCompressPatterns;
    }
}
