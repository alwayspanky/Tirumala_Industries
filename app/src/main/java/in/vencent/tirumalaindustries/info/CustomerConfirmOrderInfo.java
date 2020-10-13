package in.vencent.tirumalaindustries.info;

public class CustomerConfirmOrderInfo {

    public int pid;
    public int invoice_id;
    public  int cust_id;
    public String cust_name;
    public int sm_id;
    public String item_name;
    public String quantity;
    public String rate;
    public String total_amt;
    public int p_status;
    public String add_date;
    public String gross_amt;
    public int invoice_status;
    public String balance;
    public String delivery_date;
    public String Payment_type;
    public String payment_details;
    public String payment_date;
    public int order_continue;
    public int is_confirm;

    public CustomerConfirmOrderInfo(int pid, int invoice_id, int cust_id, String cust_name, int sm_id, String item_name, String quantity, String rate, String total_amt, int p_status, String add_date, String gross_amt, int invoice_status, String balance, String delivery_date, String payment_type, String payment_details, String payment_date, int order_continue, int is_confirm) {
        this.pid = pid;
        this.invoice_id = invoice_id;
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.sm_id = sm_id;
        this.item_name = item_name;
        this.quantity = quantity;
        this.rate = rate;
        this.total_amt = total_amt;
        this.p_status = p_status;
        this.add_date = add_date;
        this.gross_amt = gross_amt;
        this.invoice_status = invoice_status;
        this.balance = balance;
        this.delivery_date = delivery_date;
        this.Payment_type = payment_type;
        this.payment_details = payment_details;
        this.payment_date = payment_date;
        this.order_continue = order_continue;
        this.is_confirm = is_confirm;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public int getP_status() {
        return p_status;
    }

    public void setP_status(int p_status) {
        this.p_status = p_status;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
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

    public int getOrder_continue() {
        return order_continue;
    }

    public void setOrder_continue(int order_continue) {
        this.order_continue = order_continue;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }


}
