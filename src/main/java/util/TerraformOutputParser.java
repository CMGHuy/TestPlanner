package util;

import java.util.ArrayList;
import java.util.Scanner;

public class TerraformOutputParser {

    private String ipsOutput = "";

    /*
    private String sampleString = "testMachine_ips = [\n"+
            "  \"10.57.51.42\",\n"+
            "  \"10.57.51.43\",\n"+
            "  \"10.57.51.41\",\n"+
            "]";
    */

    public TerraformOutputParser(String output) {
       ipsOutput = output;
    }

    ArrayList<String> hostIps = new ArrayList<String>();

    public ArrayList<String>  stringProcessor (){

        // Formatting String
        String ipContent = ipsOutput.substring(ipsOutput.indexOf("[") + 1,ipsOutput.indexOf("]")).replace("\n", "");
        String newLineRemovedIps = ipContent.replace("\r", "");

        Scanner scan = new Scanner(newLineRemovedIps);
        scan.useDelimiter(",");
        while(scan.hasNext()){
           //add ips to hostIps String array list
           hostIps.add(scan.next().replaceAll("\""," ").replaceAll("\\s+",""));
        }
        return hostIps;
    }

    /* for testing
    public static void InstancesCreation(String[] args) {
        stringProcessor();
        for (int i = 0; i < hostIps.size(); i++) {
            System.out.println(i);
            System.out.println(hostIps.get(i));
        }
    }
    */
}
