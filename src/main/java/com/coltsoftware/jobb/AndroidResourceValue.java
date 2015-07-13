package com.coltsoftware.jobb;

public final class AndroidResourceValue {
    private String text;

    private AndroidResourceValue(String str) {
        this.text = str;
        encodeSlashes();
        encodeSingleQuote();
    }

    private void encodeSlashes() {
        text = text.replace("\\", "\\\\");
    }

    private void encodeSingleQuote() {
        text = text.replace("\'", "\\'");
    }

    public static AndroidResourceValue fromString(String str) {
        return new AndroidResourceValue(str);
    }

    @Override
    public String toString() {
        return text;
    }
}
