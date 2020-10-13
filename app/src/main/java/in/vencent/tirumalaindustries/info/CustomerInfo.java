package in.vencent.tirumalaindustries.info;

public class CustomerInfo {

    public int customer_id;
    public int sm_id;
    public String customer_name;
    public String customer_mobile;
    public String customer_addr;
    public String cust_email;

    public CustomerInfo(int customer_id, int sm_id, String customer_name, String customer_mobile, String customer_addr, String cust_email) {

        this.customer_id = customer_id;
        this.sm_id = sm_id;
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.customer_addr = customer_addr;
        this.cust_email = cust_email;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_addr() {
        return customer_addr;
    }

    public void setCustomer_addr(String customer_addr) {
        this.customer_addr = customer_addr;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

}
