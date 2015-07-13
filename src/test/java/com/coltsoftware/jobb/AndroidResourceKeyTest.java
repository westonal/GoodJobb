package com.coltsoftware.jobb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AndroidResourceKeyTest {

    @Test
    public void inserts_underscores_before_capitals() {
        assertEquals("main_obb_version", AndroidResourceKey.fromString("mainObbVersion").toString());
    }

    @Test
    public void inserts_underscores_before_numeral() {
        assertEquals("_7abc", AndroidResourceKey.fromString("7abc").toString());
    }

    @Test
    public void removes_forward_slashes() {
        assertEquals("abc_def", AndroidResourceKey.fromString("abc/def").toString());
    }

    @Test
    public void removes_back_slashes() {
        assertEquals("abc_def", AndroidResourceKey.fromString("abc\\def").toString());
    }

    @Test
    public void strips_other_chars() {
        assertEquals("success", AndroidResourceKey.fromString("s$%^&*()uc[{]}'@c!\"£$%^ess").toString());
    }

}