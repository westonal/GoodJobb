package com.coltsoftware.jobb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AndroidResourceKeyTest {

    @Test
    public void inserts_underscores_before_capitals() {
        assertEquals("main_obb_version", AndroidResourceKey.fromString("mainObbVersion").toString());
    }

}