package template;

public class TerraformTemplate {

    private int numberOfMachine;

    public TerraformTemplate(int argNumberOfMachine)  {
        this.numberOfMachine = argNumberOfMachine;
    }

    public String getTerraformInstanceFile() {
        String terraformInstanceFile = String.format(
            "data \"template_file\" \"cloudinit_ps1\" {\n" +
                "  template = \"${file(\"${var.userdata}/${var.cloudinit_ps1}\")}\"\n" +
            "}\n" +
            "\n" +
            "data \"template_cloudinit_config\" \"cloudinit_config\" {\n" +
                "  gzip          = false\n" +
                "  base64_encode = true\n" +
                "\n" +
                "  part {\n" +
                "    filename     = \"cloudinit.ps1\"\n" +
                "    content_type = \"text/x-shellscript\"\n" +
                "    content      = \"${data.template_file.cloudinit_ps1.rendered}\"\n" +
                "  }\n" +
            "}\n" +
            "\n" +
            "resource \"oci_core_instance\" \"testMachine\" {\n" +
                "  availability_domain = \"${lookup(data.oci_identity_availability_domains.ADs.availability_domains[0],\"name\")}\"\n" +
                "  compartment_id = var.comp_apps\n" +
                "  shape = \"VM.Standard2.4\"\n" +
                "  count = %d \n" +
                "  display_name =  \"CHED1WTES00\"\n" +
                "\n" +
                "  lifecycle {\n" +
                "      create_before_destroy = false\n" +
                "  }\n" +
                "  create_vnic_details {\n" +
                "    subnet_id = var.subnet_lb1_id\n" +
                "    assign_public_ip = false\n" +
                "    skip_source_dest_check = true\n" +
                "  }\n" +
                "\n" +
                "  source_details {\n" +
                "    source_type = \"image\"\n" +
                "    source_id = \"${var.win12_image_id}\"\n" +
                "    boot_volume_size_in_gbs = \"256\"\n" +
                "  }\n" +
                "\n" +
                "  metadata = {\n" +
                "    user_data = \"${data.template_cloudinit_config.cloudinit_config.rendered}\"\n" +
                "  }\n" +
                "\n" +
                "  preserve_boot_volume = false\n" +
            "}",numberOfMachine);

        return terraformInstanceFile;
    }
}
