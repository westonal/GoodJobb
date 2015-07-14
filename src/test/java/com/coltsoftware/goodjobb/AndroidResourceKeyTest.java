package com.coltsoftware.goodjobb;

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

    @Test
    public void spaces_go_to_underscore() {
        assertEquals("test_space", AndroidResourceKey.fromString("test space").toString());
    }

    @Test
    public void test_all_elements() {
        assertEquals("path1_path2_dir_three_file_name_with_capitals_and_symbols",
                AndroidResourceKey.fromString("path1\\Path2\\DirThree\\File name WithCapitals and sym\'bols").toString());
    }

}