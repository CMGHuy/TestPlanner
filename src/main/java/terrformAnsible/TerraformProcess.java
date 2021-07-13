package terrformAnsible;

import util.*;
import template.TerraformTemplate;
import java.io.*;

public class TerraformProcess {

    private int machineCount;

    public void createInstances() {

        /*
            -------- Preparation --------
            Delete all terraform.tfstate & terraform.tfstate.backup file
            Source set-env-dev.sh file, have to be sent down to terraform apply and plan due to starting new process
            Remove old instances.tf file
            Remove old output
        */
        OSCommands commandRunner = new OSCommands();

        commandRunner.runCommand("rm  ~/mnt/terraform/nativ/*tfstate*");
        commandRunner.runCommand("rm  ~/mnt/terraform/nativ/*.txt");
        commandRunner.runCommand("rm ~/mnt/terraform/nativ/instances.tf");
        commandRunner.runCommand("rm ~/mnt/terraform/nativ/terraOut.txt");
        // processBuilder.processBuilderRunner("source ~/terraform/set-env-dev.sh"); // to be considered back

        /*
            -------- Population of terraform instance file --------
            Populate terraform template with argument of numberOfMachine from Input
        */
        TerraformTemplate terraInstanceFile = new TerraformTemplate(machineCount);
        String terraIntanceFile = terraInstanceFile.getTerraformInstanceFile();

        try {
            //System.out.println(terraInstanceFile.getTerraformInstanceFile());

            // Create new instances.tf file with the appropriate number of created instances
            File file = new File("/home/ubuntu/mnt/terraform/nativ/instances.tf");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(terraIntanceFile);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            -------- Run terraform step --------
            Setting environment variables
            Terraform plan
            Terraform apply
         */
        commandRunner.runCommand("source ~/mnt/terraform/set-env-dev.sh " +
                                "&& cd ~/mnt/terraform/nativ/ && terraform plan " +
                                "&& terraform apply -no-color -auto-approve");

        // Terraform output to export list of IP addresses
        commandRunner.runCommand("source ~/mnt/terraform/set-env-dev.sh " +
                                "&& cd ~/mnt/terraform/nativ/ && terraform output >> terraOut.txt");

    }

    public void setMachineCount(int machineCount) {
        this.machineCount = machineCount;
    }
}
