package in.vencent.tirumalaindustries.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.fragment.app.FragmentActivity;

import in.vencent.tirumalaindustries.activity.Activity_AddNewOrder;
import in.vencent.tirumalaindustries.activity.Activity_CustomerAddorder;
import in.vencent.tirumalaindustries.activity.Activity_CustomerOrder;
import in.vencent.tirumalaindustries.activity.Activity_SalesmanagerList;
import in.vencent.tirumalaindustries.activity.LoginActivity;
import in.vencent.tirumalaindustries.navigations.Activity_Completedorder;
import in.vencent.tirumalaindustries.navigations.Activity_PendingCreditOrder;

/**
 * Created by abc on 11-04-2017.
 */

public class PreferenceUtil {


    public static void putUserInfo(Context context, String username, String password)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("UserName", username);
        editor.putString("Password", password);
        editor.commit();
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void putRole(Context context, String role) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Role", role);
        editor.commit();
    }

    public static String getRole(Context context) {
        return getSharedPreference(context).getString("Role", "");
    }

    public static void putUserInfo1(Context context, int login_id, String login_name, String login_pass) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("LoginId", login_id);
        editor.putString("Username", login_name);
        editor.putString("password", login_pass);
        editor.commit();
    }

    public static void putSmId(Context context, int sm_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("SmId", sm_id);
        editor.commit();
    }
    public static int getSmID(Context context) {
        return getSharedPreference(context).getInt("SmId", 0);
    }

    public static void putManufactureID(Context context, int manu_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("id", manu_id);
        editor.commit();
    }
    public static int getManufactureID(Context context) {
        return getSharedPreference(context).getInt("id", 0);
    }

    public static void putLoginEmail(Context context, String login_name) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("LoginEmail", login_name);
        editor.commit();
    }
    public static String getLoginEmail(Context context) {
        return getSharedPreference(context).getString("LoginEmail", "");
    }

    public static void putFullName(Context context, String full_name) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("FullName", full_name);
        editor.commit();
    }
    public static String getFullName(Context context) {
        return getSharedPreference(context).getString("FullName", "");
    }

    public static void putOrderId(Context context, int order_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("order_id", order_id);
        editor.commit();
    }
    public static int getOrderId(Context context) {
        return getSharedPreference(context).getInt("order_id", 0);
    }


    public static void putTotal(Context context, String total) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("total", total);
        editor.commit();
    }
    public static String  getTotal(Context context) {
        return getSharedPreference(context).getString("total", "");
    }

    public static void putCustomerId(Context context, int cust_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("customer_id", cust_id);
        editor.commit();
    }
    public static int getCustomerId(Context context) {
        return getSharedPreference(context).getInt("customer_id", 0);
    }

    public static void putPaymentType(Context context, String payment_type) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Payment_type", payment_type);
        editor.commit();
    }
    public static String  getPaymentType(Context context) {
        return getSharedPreference(context).getString("Payment_type", "");
    }

    public static void putCustomerName(Context context, String customerName) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("customer_name", customerName);
        editor.commit();
    }
    public static String  getCustomerName(Context context) {
        return getSharedPreference(context).getString("customer_name", "");
    }


    public static void putInvoiceId(Context context, int invoice_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(" invoice_id",  invoice_id);
        editor.commit();
    }
    public static int getInvoiceId(Context context) {
        return getSharedPreference(context).getInt(" invoice_id", 0);
    }

    public static void putInvoiceStatus(Context context, int invoice_status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("invoice_status", invoice_status);
        editor.commit();
    }
    public static int getInvoiceStatus(Context context) {
        return getSharedPreference(context).getInt("invoice_status", 0);
    }

    public static void putCustomerMobile(Context context, String customerMobile) {

        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("customer_mobile", customerMobile);
        editor.commit();
    }
    public static String getCustomerMobile(Context context) {
        return getSharedPreference(context).getString("customer_mobile", "");
    }

    public static void putManagerMobile(Context context, String mobile_no) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("mobile_no", mobile_no);
        editor.commit();
    }
    public static String getManagerMobile(Context context) {
        return getSharedPreference(context).getString("mobile_no", "");
    }

}
