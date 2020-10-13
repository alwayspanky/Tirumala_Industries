package in.vencent.tirumalaindustries.info;

public class UserInfo {

    public int login_id;
    public int sm_id;
    public String login_name;
    public String login_pass;
    public String full_name;
    public String role;
    public String mobile_no;



    public UserInfo(int login_id, int sm_id, String login_name, String full_name, String login_pass, String role, String mobile_no) {
        this.login_id = login_id;
        this.sm_id = sm_id;
        this.login_name = login_name;
        this.login_pass = login_pass;
        this.full_name = full_name;
        this.role = role;
        this.mobile_no = mobile_no;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pass() {
        return login_pass;
    }

    public void setLogin_pass(String login_pass) {
        this.login_pass = login_pass;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


}
