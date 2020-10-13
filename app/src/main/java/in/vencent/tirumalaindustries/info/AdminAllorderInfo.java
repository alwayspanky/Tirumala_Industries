package in.vencent.tirumalaindustries.info;

public class AdminAllorderInfo {

    public int invoice_id;
    public int cust_id;
    public int invoice_status;
    public String customer_name;
    public String customer_mobile;
    public int sm_id;
    public String invoice_date;


    public AdminAllorderInfo(int invoice_id, int cust_id, int invoice_status, String customer_name, String customer_mobile, int sm_id, String invoice_date) {

        this.invoice_id = invoice_id;
        this.cust_id = cust_id;
        this.invoice_status = invoice_status;
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.sm_id = sm_id;
        this.invoice_date = invoice_date;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
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
    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }
    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }
    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

}
