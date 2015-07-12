package com.coltsoftware.jobb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        String name = String.format("%s.%d.%s.obb", getForename(), getPackageVersion(), packageName);
        if (output != null)
            return new File(output, name);
        else
            return new File(name);
    }

    public List<String> list(String flag) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];
            if (flag.equals(arg)) result.add(args[i + 1]);
        }
        return result;
    }

    public File getResourceOutput() {
        String resOutput = getValue("-res");
        if (resOutput == null)
            return null;
        return new File(resOutput);
    }

    public String getForename() {
        return isMain() ? "main" : "patch";
    }
}
