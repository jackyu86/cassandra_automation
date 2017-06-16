package com.jd.jg.admin.util;

import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.util.*;

public class Ping
{
    /**
     * @param internetProtocolAddress The internet protocol address to ping
     * @return True if the address is responsive, false otherwise
     * @throws IOException
     */
    public static boolean isReachable(String internetProtocolAddress) throws IOException
    {
        List<String> command = new ArrayList<>();
        command.add("ping");

        if (SystemUtils.IS_OS_WINDOWS)
        {
            command.add("-n");
        }
        else if (SystemUtils.IS_OS_UNIX)
        {
            command.add("-c");
        }
        else
        {
            throw new UnsupportedOperationException("Unsupported operating system");
        }

        command.add("1");
        command.add(internetProtocolAddress);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        BufferedReader standardOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String outputLine;

        while ((outputLine = standardOutput.readLine()) != null)
        {
            // Picks up Windows and Unix unreachable hosts
            if (outputLine.toLowerCase().contains("destination host unreachable"))
            {
                return false;
            }
        }

        return true;
    }
}
