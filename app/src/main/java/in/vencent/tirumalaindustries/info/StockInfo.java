package in.vencent.tirumalaindustries.info;

public class StockInfo {

    public int stock_id;
    public String Manufacture_id;
    public String stock_name;
    public String stock_qty;
    public String stock_price;
    public String total_price;
    public String stock_dt_added;

    public StockInfo(int stock_id, String manufacture_id, String stock_name, String stock_qty, String stock_price, String total_price, String
            stock_dt_added) {

        this.stock_id = stock_id;
        this.Manufacture_id = manufacture_id;
        this.stock_name = stock_name;
        this.stock_qty = stock_qty;
        this.stock_price = stock_price;
        this.total_price = total_price;
        this.stock_dt_added = stock_dt_added;

    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getManufacture_id() {
        return Manufacture_id;
    }

    public void setManufacture_id(String manufacture_id) {
        Manufacture_id = manufacture_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getStock_price() {
        return stock_price;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
    public String getStock_dt_added() {
        return stock_dt_added;
    }

    public void setStock_dt_added(String stock_dt_added) {
        this.stock_dt_added = stock_dt_added;
    }
}
