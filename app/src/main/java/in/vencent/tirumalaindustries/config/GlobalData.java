package in.vencent.tirumalaindustries.config;


import java.util.ArrayList;

import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.info.AllOrderListIfo;
import in.vencent.tirumalaindustries.info.ConfirmedOrderInfo;
import in.vencent.tirumalaindustries.info.ConfirmedOrderListInfo;
import in.vencent.tirumalaindustries.info.CustomerConfirmOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerInfo;
import in.vencent.tirumalaindustries.info.CustomerNewOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderNewInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderStatusWiseInfo;
import in.vencent.tirumalaindustries.info.ManufracturerInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.info.PendingCreditedorderInfo;
import in.vencent.tirumalaindustries.info.SalesManagerInfo;
import in.vencent.tirumalaindustries.info.StockInfo;
import in.vencent.tirumalaindustries.info.UserInfo;

/**
 * Created by abc on 11-04-2017.
 */

public class GlobalData {

    public static ArrayList<UserInfo> userInfos;
    public static ArrayList<SalesManagerInfo> salesManagerInfos;
    public static ArrayList<CustomerInfo> customerInfos;
    public static ArrayList<ManufracturerInfo> manufracturerInfos;
    public static ArrayList<StockInfo> stockInfos;
    public static CustomerOrderInfo customerOrderInfo;
    public static ArrayList<CustomerNewOrderInfo> customerNewOrderInfos;
    public static ArrayList<AdminAllorderInfo> adminAllorderInfos;
    public static OrderTotalBillInfo orderTotalBillInfo;
    public static ArrayList<AllOrderListIfo> allOrderListIfos;
    public static ArrayList<CustomerConfirmOrderInfo> customerConfirmOrderInfos;
    public static ArrayList<ConfirmedOrderInfo> confirmedOrderInfos;
    public static ArrayList<PendingCreditedorderInfo> pendingCreditedorderInfos;
    public static ArrayList<ConfirmedOrderListInfo> confirmedOrderListInfos;
    public static ArrayList<CustomerOrderNewInfo> customerOrderNewInfos;
    public static ArrayList<CustomerOrderStatusWiseInfo> customerOrderStatusWiseInfos;

}
