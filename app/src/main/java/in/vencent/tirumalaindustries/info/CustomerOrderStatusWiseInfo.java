package in.vencent.tirumalaindustries.info;

public class CustomerOrderStatusWiseInfo {

    public int pid;
    public int invoice_id;
    public int cust_id;
    public String cust_name;
    public int sm_id;
    public String item_name;
    public String quantity;
    public String rate;
    public String total_amt;
    public int p_status;
    public String add_date;
    public String vehicle_no;
    public String driver_name;
    public String driver_number;
    public String qty_bag;
    public String qty_size;


    public CustomerOrderStatusWiseInfo(int pid, int invoice_id, int cust_id, String cust_name, int sm_id, String item_name, String quantity, String rate, String total_amt, int p_status, String add_date, String vehicle_no, String driver_name, String driver_number, String qty_bag, String qty_size) {

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
        this.vehicle_no = vehicle_no;
        this.driver_name = driver_name;
        this.driver_number = driver_number;
        this.qty_bag = qty_bag;
        this.qty_size = qty_size;
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

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_number() {
        return driver_number;
    }

    public void setDriver_number(String driver_number) {
        this.driver_number = driver_number;
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
