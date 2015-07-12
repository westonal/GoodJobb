package com.coltsoftware.jobb;

import java.io.File;

public class Args {
    private String[] args;

    public Args(String[] args) {
        this.args = args;
    }

    public boolean isVerbose() {
        return hasFlag("-v");
    }

    private boolean hasFlag(String flag) {
        for (String arg : args)
            if (flag.equals(arg)) return true;
        return false;
    }

    public boolean isHelp() {
        return hasFlag("-h");
    }

    public String getDirectory() {
        return getValue("-d");
    }

    private String getValue(String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];
            if (flag.equals(arg)) return args[i + 1];
        }
        return null;
    }

    public String getOutputName() {
        return getValue("-o");
    }

    private File getOutputFile() {
        String output = getOutputName();
        if (output == null) return null;
        return new File(output);
    }

    public int getPackageVersion() {
        String value = getValue("-pv");
        if (value == null) return 1;
        return Integer.parseInt(value);
    }

    public String getPackageName() {
        return getValue("-pn");
    }

    public boolean isMain() {
        return !hasFlag("-patch");
    }

    public File getObbFile() {
        File output = getOutputFile();
        if (output != null && !output.isDirectory()) return output;
        String packageName = getPackageName();
        if (packageName == null) return null;
        String name = String.format("%s.%d.%s.obb", isMain() ? "main" : "patch", getPackageVersion(), packageName);
        if (output != null)
            return new File(output, name);
        else
            return new File(name);
    }
}
