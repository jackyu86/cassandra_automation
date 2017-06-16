package com.jd.jg.admin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LocalCmdUtils {

    public static String exec(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            BufferedReader standardOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String outputLine;

            while ((outputLine = standardOutput.readLine()) != null) {
                sb.append(outputLine);
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("failed to execute " + command);
        }
    }

}
