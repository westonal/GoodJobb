package com.coltsoftware.jobb;

import java.util.regex.Pattern;

public final class AndroidResourceKey {
    private String text;

    public static AndroidResourceKey fromString(String text) {
        return new AndroidResourceKey(text);
    }

    private AndroidResourceKey(String text) {
        this.text = text;
        removeSlashes();
        replaceSpaces();
        insertUnderscoresBeforeCapitals();
        lowerCase();
        removeIllegalCharacters();
        ensureLegalStartCharacter();
    }

    private void replaceSpaces() {
        text = text.replace(' ', '_');
    }

    private void insertUnderscoresBeforeCapitals() {
        text = text.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2");
    }

    private void ensureLegalStartCharacter() {
        if (Pattern.matches("^[0-9].*", text))
            text = "_" + text;
    }

    private void removeIllegalCharacters() {
        text = text.replaceAll("[^_0-9a-z]", "");
    }

    private void lowerCase() {
        text = text.toLowerCase();
    }

    private void removeSlashes() {
        text = text.replaceAll("[\\\\/]", "_");
    }

    @Override
    public String toString() {
        return text;
    }
}
