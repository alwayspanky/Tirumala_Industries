package in.vencent.tirumalaindustries.info;

public class PendingCreditedorderInfo {

    public int invoice_id;
    public String invoice_date;
    public int cust_id;
    public String net_amt;
    public String gross_amt;
    public int invoice_status;
    public String pay_amount;
    public String balance;
    public String delivery_date;
    public String Payment_type;
    public String payment_details;
    public String payment_date;
    public int sm_id;
    public String customer_name;
    public String customer_mobile;
    public String customer_addr;
    public String cust_email;

    public PendingCreditedorderInfo(int invoice_id, String invoice_date, int cust_id, String net_amt, String gross_amt, int invoice_status, String pay_amount, String balance, String delivery_date, String payment_type, String payment_details, String payment_details1, String payment_date, int sm_id, String customer_name, String customer_mobile, String customer_addr, String cust_email) {
        this.invoice_id = invoice_id;
        this.invoice_date = invoice_date;
        this.cust_id = cust_id;
        this.net_amt = net_amt;
        this.gross_amt = gross_amt;
        this.invoice_status = invoice_status;
        this.pay_amount = pay_amount;
        this.balance = balance;
        this.delivery_date = delivery_date;
        this.Payment_type = payment_type;
        this.payment_details = payment_details;
        this.payment_date = payment_date;
        this.sm_id = sm_id;
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.customer_addr = customer_addr;
        this.cust_email = cust_email;

    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getNet_amt() {
        return net_amt;
    }

    public void setNet_amt(String net_amt) {
        this.net_amt = net_amt;
    }

    public String getGross_amt() {
        return gross_amt;
    }

    public void setGross_amt(String gross_amt) {
        this.gross_amt = gross_amt;
    }

    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getPayment_type() {
        return Payment_type;
    }

    public void setPayment_type(String payment_type) {
        Payment_type = payment_type;
    }

    public String getPayment_details() {
        return payment_details;
    }

    public void setPayment_details(String payment_details) {
        this.payment_details = payment_details;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
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
