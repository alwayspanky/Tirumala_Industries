package in.vencent.tirumalaindustries.info;

public class ManufracturerInfo {
    public int id;
    public String rm_name;
    public String rm_mobile;
    public String rm_email;
    public String rm_address;

    public ManufracturerInfo(int id, String rm_name, String rm_mobile, String rm_email, String rm_address) {
        this.id = id;
        this.rm_name = rm_name;
        this.rm_mobile = rm_mobile;
        this.rm_email = rm_email;
        this.rm_address = rm_address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRm_name() {
        return rm_name;
    }

    public void setRm_name(String rm_name) {
        this.rm_name = rm_name;
    }

    public String getRm_mobile() {
        return rm_mobile;
    }

    public void setRm_mobile(String rm_mobile) {
        this.rm_mobile = rm_mobile;
    }

    public String getRm_email() {
        return rm_email;
    }

    public void setRm_email(String rm_email) {
        this.rm_email = rm_email;
    }

    public String getRm_address() {
        return rm_address;
    }

    public void setRm_address(String rm_address) {
        this.rm_address = rm_address;
    }
}
