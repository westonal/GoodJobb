package com.coltsoftware.goodjobb;

import java.util.regex.Pattern;

public class RegexCompresssionPattern extends CompressionPattern {

    private final Pattern pattern;

    public RegexCompresssionPattern(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean CompressFile(String fileNameInZip) {
        return pattern.matcher(fileNameInZip).matches();
    }

    @Override
    public String toString() {
        return pattern.toString();
    }
}
