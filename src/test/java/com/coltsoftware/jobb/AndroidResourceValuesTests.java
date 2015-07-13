package com.coltsoftware.jobb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AndroidResourceValuesTests {

    @Test
    public void empty() {
        AndroidResourceValue value = AndroidResourceValue.fromString("");
        assertEquals("", value.toString());
    }

    @Test
    public void plain() {
        AndroidResourceValue value = AndroidResourceValue.fromString("abc");
        assertEquals("abc", value.toString());
    }

    @Test
    public void escape_slashes() {
        AndroidResourceValue value = AndroidResourceValue.fromString("abc\\def");
        assertEquals("abc\\\\def", value.toString());
    }

    @Test
    public void escape_multiple_slashes() {
        AndroidResourceValue value = AndroidResourceValue.fromString("abc\\def\\ghi");
        assertEquals("abc\\\\def\\\\ghi", value.toString());
    }

    @Test
    public void escape_single_quote() {
        AndroidResourceValue value = AndroidResourceValue.fromString("abc\'def");
        assertEquals("abc\\\'def", value.toString());
    }

    @Test
    public void escape_multiple_single_quotes() {
        AndroidResourceValue value = AndroidResourceValue.fromString("abc\'def\'ghi");
        assertEquals("abc\\\'def\\\'ghi", value.toString());
    }

}
