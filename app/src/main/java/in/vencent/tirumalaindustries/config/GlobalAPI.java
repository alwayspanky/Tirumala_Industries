package in.vencent.tirumalaindustries.config;

import android.widget.BaseExpandableListAdapter;

/**
 * Created by abc on 11-04-2017.
 */

public class GlobalAPI {

    public static final String Basic_url = "https://tirumalaindustries.in/api/web/";
    public static final String loginurl = Basic_url + "login";
    public static String salemanagerlist = Basic_url + "sm_list";
    public static String customerlist = Basic_url + "customer_list";
    public static  String manufracturelist = Basic_url + "manufacturer_list";
    public static String addrawmaterials = Basic_url + "add_manufacturer_raw_material";
    public static String addnewcustomer = Basic_url + "add_customer";
    public static String addmanufacturer = Basic_url + "add_manufacturer";
    public static String stocklist = Basic_url + "all_stock_list";
    public static String addneworder = Basic_url + "add_order";
    public static String customerorderlist = Basic_url + "get_item_list_orderwise";
    public static String adminallorderlist = Basic_url + "all_order_list";
    public static String saleorderlist = Basic_url + "all_order_list_smwise";
    public static String addnewStock = Basic_url + "add_stock";
    public static String shwadmincustomerlist = Basic_url + "get_item_list_custwise";
    public static String showplacorder  = Basic_url + "place_order";
    public static String allcreditependingorder = Basic_url +"all_order_list";
    public static String allmenufacturerlist = Basic_url + "get_manufacturer_raw_material_list";
    public static String addsalesmanager = Basic_url + "add_sales_manager";
    public static String brokerlist = Basic_url + "get_raw_material_list_manufacturerwise";
    public static String confirmedorder  = Basic_url +  "confirm_order";
    public static String confirmordered = Basic_url + "update_status";
    public static String orderconfirmed = Basic_url + "get_confirmed_item_list_customer_wise";
    public static String showconfirmedcustomerlist = Basic_url + "get_confirmed_item_customer_list";
    public static String dispatchedorder = Basic_url + "update_status";
    public static String disptachordeliverd = Basic_url + "update_status";
    public static String showdispatchedorderlist = Basic_url + "get_confirmed_item_list_customer_wise";
    public static String showdispatchedtodelivery = Basic_url+ "get_confirmed_item_list_customer_wise";
    public static String showdeliveredlist = Basic_url + "get_confirmed_item_customer_list";
    public static String showDeliveryOrderlist = Basic_url + "get_confirmed_item_list_customer_wise";
    public static String pendingcreditesorder = Basic_url + "get_customer_list_credit_wise";
    public static String showpendingorderlist = Basic_url + "get_confirmed_item_list_customer_wise";
    public static String changetrasportupdate = Basic_url + "update_transport";
    public static String smsservices = "http://sms.vndsms.com/vendorsms/pushsms.aspx?";
    public static String chnagecreditedorderstatus = Basic_url + "change_payment_type";
    public static String adminpendingorderlist = Basic_url + "get_pending_item_list";
    public static String showCustomerorder = Basic_url + "get_item_list_orderwise";
    public static String statusWiseOrderList = Basic_url + "get_item_list_status_wise";
}
