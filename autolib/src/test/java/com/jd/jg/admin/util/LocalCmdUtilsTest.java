package com.jd.jg.admin.util;

import org.junit.Assert;
import org.junit.Test;

public class LocalCmdUtilsTest {
    @Test
    public void exec() throws Exception {
        String s = LocalCmdUtils.exec("md5sum","/etc/profile");
        Assert.assertTrue(s.contains("/etc/profile"));
    }

}