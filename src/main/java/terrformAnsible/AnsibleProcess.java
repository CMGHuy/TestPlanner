package terrformAnsible;

import template.AnsibleTemplate;
import util.*;

import java.io.*;
import java.util.ArrayList;

public class AnsibleProcess {

    public void createHostsFile(){
        String terraformOutputContent = "";
        String strCurrentLine = "";
        // String for Ansible hosts to write to file
        String ansibleHostsString = "";

        // Ansible host IP ArrayList
        ArrayList<String> ansibleHostIps = new ArrayList<String>();

        // Get Terraform output, render reader object to String, line by line
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/home/ubuntu/mnt/terraform/nativ/terraOut.txt"));
            while ((strCurrentLine = reader.readLine()) != null) {
                terraformOutputContent = terraformOutputContent.concat(strCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the ArrayList of IP Addresses
        TerraformOutputParser terraParser = new TerraformOutputParser(terraformOutputContent);
        // AnsibleHostIps contains all IPs, formatted
        ansibleHostIps= terraParser.stringProcessor();

        // Populate AnsibleTemplate with these ips
        // Generate new host file
        for (int i = 0; i < ansibleHostIps.size(); i++) {
            AnsibleTemplate template = new AnsibleTemplate(ansibleHostIps.get(i), i);
            ansibleHostsString = ansibleHostsString.concat(template.getAnsibleHostFile());
        }

        // Write this host file to a file
        try {
            // Create new hosts file with the appropriate number of created instances and IP Addresses
            File file = new File("/home/ubuntu/mnt/ansible/hosts");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(ansibleHostsString);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installDependencies(){
        // Run Ansible Playbook
        OSCommands commandRunner = new OSCommands();
        commandRunner.runCommand("ansible-playbook -i ~/mnt/ansible/hosts ~/mnt/ansible/play.yml");
    }
}
