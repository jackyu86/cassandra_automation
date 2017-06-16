package com.jd.jg.admin.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

public class TemplateUtils {

    public static String merge(String tplPath,String tplName, Map<String,Object> model){
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            cfg.setDirectoryForTemplateLoading(new File(tplPath));
        } catch (IOException e) {
            throw new RuntimeException("failed to set template path",e);
        }
        cfg.setDefaultEncoding("UTF-8");


        Template temp = null;
        try {
            temp = cfg.getTemplate(tplName);
        } catch (IOException e) {
            throw new RuntimeException("failed to get template "+tplName,e);
        }
        Writer out = new StringWriter();
        try {
            temp.process(model, out);
        } catch (Exception e) {
            throw new RuntimeException("failed to merge template",e);
        }

        return out.toString();
    }
}
