package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSCommands {
     public void runCommand(String commandAsString){
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Run this on Windows, cmd, /c = terminate after this run
        processBuilder.command("bash", "-c", commandAsString);

        try {
            Process process = processBuilder.start();

            // blocked :(
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            //System.out.println("\nExited with error code : " + exitCode);
            System.out.println(String.format("command ### %s ### exited with error code: %d",commandAsString, exitCode));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
