package in.vencent.tirumalaindustries.info;

public class CustomerNewOrderInfo {

    public int pid;
    public int invoice_id;
    public int p_status;
    public String item_name;
    public String quantity;
    public String rate;
    public String total_amt;
    public int invoice_status;
    public String payment_date;
    public String qty_bag;
    public String qty_size;

    public CustomerNewOrderInfo(int pid, int invoice_id, int p_status, String item_name, String quantity, String qty_bag, String qty_size, String rate, String total_amt, String add_date) {
        this.pid = pid;
        this.invoice_id = invoice_id;
        this.p_status = p_status;
        this.item_name = item_name;
        this.quantity = quantity;
        this.qty_bag = qty_bag;
        this.qty_size = qty_size;
        this.rate = rate;
        this.total_amt = total_amt;
        this.payment_date = payment_date;
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
    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
    public String getQty_bag() {
        return qty_bag;
    }

    public void setQty_bag(String qty_bag) {
        this.qty_bag = qty_bag;
    }

    public String getQty_size() {
        return qty_size;
    }

    public void setQty_size(String qty_size) {
        this.qty_size = qty_size;
    }

}
