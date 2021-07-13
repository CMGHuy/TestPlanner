import terrformAnsible.*;

public class InstancesCreation {

    public static void main(String[] args) {

        /* Old code
        Scanner scanner = new Scanner(System.in);

        // Prompt for number of created machine
        // Get the number of machine as an int, check if its between 0 and 10
        boolean check = false;
        int numberOfMachine = 0;

        // Simple check Input, must be scrape later
        while(!check){
            try {
                System.out.print("**** Enter the number of test machines: ");
                numberOfMachine = scanner.nextInt();
                if (numberOfMachine <= 13 && numberOfMachine >= 0) check = true;
                else {
                    System.out.println("Number of machine must be an integer between 0 and 13!");
                }
            }
            // If an exception appears prints message below
            catch (InputMismatchException e) {
                System.err.println("Please enter a number! ");
                scanner.next(); // clear scanner wrong Input
                // continues to loop if exception is found
            }
        }

        System.out.print("Enter the number of test machines: ");
        int numberOfMachine = scanner.nextInt();
        */
        int numberOfMachine = Integer.parseInt(args[0]);

        System.out.println(String.format("Terraform will create %d machine(s)", numberOfMachine));

        // Start Terraform
        TerraformProcess terraformer = new TerraformProcess();
        terraformer.setMachineCount(numberOfMachine);
        terraformer.createInstances();

        // Start Ansible
        AnsibleProcess ansibler = new AnsibleProcess();
        ansibler.createHostsFile();
        ansibler.installDependencies();
        //System.out.println("check ~/terraform/nativ/terraLog.txt");
    }
}