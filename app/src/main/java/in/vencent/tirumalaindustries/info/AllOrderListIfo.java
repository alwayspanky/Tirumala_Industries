package in.vencent.tirumalaindustries.info;

public class AllOrderListIfo {

    public int invoice_id;
    public int cust_id;
    public int sm_id;
    public int invoice_status;
    public String Payment_type;
    public String customer_name;


    public AllOrderListIfo(int invoice_id, int cust_id, int sm_id, int invoice_status, String payment_type, String customer_name) {
        this.invoice_id = invoice_id;
        this.cust_id = cust_id;
        this.sm_id = sm_id;
        this.invoice_status = invoice_status;
        this.Payment_type = payment_type;
        this.customer_name = customer_name;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getPayment_type() {
        return Payment_type;
    }

    public void setPayment_type(String payment_type) {
        Payment_type = payment_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }
}
