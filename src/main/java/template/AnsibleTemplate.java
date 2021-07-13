package template;

public class AnsibleTemplate {

    // Create new host file
    private String hostIp;
    private int hostCurrentCount;

    public AnsibleTemplate(String hostIp, int hostCurrentCount) {
        this.hostIp = hostIp;
        this.hostCurrentCount = hostCurrentCount;
    }

    public String getAnsibleHostFile() {
        String ansibleHostFile = String.format(
            "[wintel%d]\n" +
            "%s\n" +
            "\n" +
            "[wintel%d:vars]\n" +
            "ansible_user=VLE\n" +
            "ansible_password=Cheetah2020!\n" +
            "ansible_connection=winrm\n" +
            "ansible_winrm_server_cert_validation=ignore\n\n", hostCurrentCount, hostIp, hostCurrentCount);

        return ansibleHostFile;
    }
}
