package in.vencent.tirumalaindustries.rowitemlist;

public class RowItemAdminAllorderInfo {

    public int invoice_id;
    public int cust_id;
    public int invoice_status;
    public String customer_name;
    public String customer_mobile;
    public int sm_id;
    public String invoice_date;

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

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }
    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    } public int getSm_id() {
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
