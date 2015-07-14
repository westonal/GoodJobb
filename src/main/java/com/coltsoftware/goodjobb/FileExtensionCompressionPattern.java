package com.coltsoftware.goodjobb;

import java.util.regex.Pattern;

public class FileExtensionCompressionPattern extends RegexCompresssionPattern {
    private String extension;

    public FileExtensionCompressionPattern(String extension) {
        super(".*" + Pattern.quote(extension) + "$");
        this.extension = extension;
    }

    @Override
    public String toString() {
        return extension;
    }
}
