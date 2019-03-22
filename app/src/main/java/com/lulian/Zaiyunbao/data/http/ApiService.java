package com.lulian.Zaiyunbao.data.http;

import com.lulian.Zaiyunbao.Bean.BaseBean;
import com.lulian.Zaiyunbao.Bean.RentOrderDetailBean;
import com.lulian.Zaiyunbao.Bean.WalletListDetails;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //登录用户
    @POST("/api/AppUser/Login")
    Observable<String> login(@Header("Authorization") String authorization,
                             @Body RequestBody loginBody);

    //注册
    @POST("/api/AppUser/Register")
    Observable<String> register(@Header("Authorization") String authorization,
                                @Query("Phone") String Phone, @Query("Password") String Password, @Query("UserType") String UserType);

    //发送短信验证 verifyType（1.用户注册 2.重置密码 3.重置支付密码 4.更新联系方式, 5其他）
    @POST("/api/AppUser/SendVerifySms")
    Observable<String> sendVerifySms(@Header("Authorization") String authorization,
                                     @Query("phone") String Phone, @Query("verifyType") String verifyType);

    //验证手机号码是否注册
    @POST("/api/AppUser/PhoneIsExists")
    Observable<String> phoneIsExists(@Header("Authorization") String authorization,
                                     @Query("Phone") String Phone);

    //文件图片上传(证件)
    @POST("/api/AppCommand/UploadCard")
    Observable<String> uploadImage(@Header("authorization") String authorization, @Query("userId") String user,
                                   @Body MultipartBody fileBody, @Query("fileType") String fileType);

    //注册后保存用户信息(1.个人传入参数UserClass,UserId, Name, Sex, IdCard, IdFrontUrl, IdBackUrl
    // 2.企业传入参数UserClass,UserId, OrgName,OrgType,OrgCode,LicUrl,Remark)
    @POST("/api/AppUser/SaveUserInfo")
    Observable<String> saveUserInfo(@Header("Authorization") String authorization,
                                    @Body RequestBody loginBody);

    @POST("/api/System_PersonalInfo/GetUserInfo")
    Observable<String> GetUserInfo(@Header("Authorization") String authorization,
                                   @Query("UserID") String UserID);

    //修改密码
    @POST("/api/AppUser/ModifyPwd")
    Observable<String> modifyPwd(@Header("Authorization") String authorization, @Query("Phone") String Phone,
                                 @Query("Pwd") String pwd);

    //消息列表
    @POST("/api/AppMy/MessagesList")
    Observable<String> messagesList(@Header("Authorization") String authorization, @Query("UserId") String UserId,
                                    @Body RequestBody messageBody);

    //检测密码是否准确
    @POST("/api/AppMy/PwdIsRight")
    Observable<String> pwdIsRight(@Header("Authorization") String authorization, @Query("UserId") String UserId,
                                  @Query("Password") String Password);

    //换更手机号
    @POST("/api/AppMy/ModifyPhone")
    Observable<String> modifyPhone(@Header("Authorization") String authorization, @Query("UserId") String UserId,
                                   @Query("Phone") String Phone);

    //提交帮助与反馈
    @POST("/api/AppMy/AddHelpMsg")
    Observable<String> addHelpMsg(@Header("Authorization") String authorization, @Query("SolveContent") String SolveContent,
                                  @Query("Phone") String Phone, @Query("UserId") String UserId);

    //我的钱包 returnValue Balance(余额),Deposit(押金)
    @POST("/api/AppMy/MyMoneyInfo")
    Observable<String> myMoneyInfo(@Header("Authorization") String authorization, @Query("UserId") String UserId);

    //修改支付密码
    @POST("/api/AppMy/ModifyPayPwd")
    Observable<String> modifyPayPwd(@Header("Authorization") String authorization, @Query("UserId") String UserId,
                                    @Query("PayPwd") String PayPwd);


    //获取字典枚举值 返回值 类型/DicTypeCode、值/ItemCode、名称/ItemName、序号/ItemSort
    @POST("/api/AppUser/GetDicItem")
    Observable<String> GetDicItem(@Header("Authorization") String authorization);

    /*******************************租赁**********************************************************************************************/

    //获取设备类型列表
    @POST("/api/AppZulin/EquipmentTypeList")
    Observable<String> equipmentTypeList(@Header("Authorization") String authorization);

    //获取设备规格列表
    @POST("/api/AppZulin/EquipmentSizeList")
    Observable<String> equipmentSizeList(@Header("Authorization") String authorization, @Query("TypeId") String TypeId);

    //可租设备列表 returnValue TypeName(设备类型)Quantity(可租数量)PValue(租赁单价)EquipmentName(设备名称)Norm(设备规格)Picture(图片)TypeName(设备类型)
    @POST("/api/AppZulin/EquipmentList")
    Observable<String> equipmentList(@Header("Authorization") String authorization, @Body RequestBody argsBody);

    //求租设备列表 设备详细1   returnValue TypeName(设备类型)TypeName(服务站点)TypeName(联系人)TypeName(手机号)Price(租赁单价)
    // Deposit(单押金)EquipmentName(设备名称)Norm(设备规格)Picture(图片)TypeName(设备类型)
    @POST("/api/AppZulin/EquipmentDetails1")
    Observable<String> equipmentDetails1(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId,
                                         @Query("OperatorUserId") String OperatorUserId, @Query("UID") String UID);

    //设备详细2 returnValue TypeName(设备类型)StaticLoad(静载)CarryingLoad(动载)
    // SpecifiedLoad(载重)Volume(容积)WarmLong(保温时长)BaseMaterial(材质)Model(型号)
    // Remark(说明)PValue(租赁单价)EquipmentName(设备名称)Norm(设备规格)Picture(图片)TypeName(设备类型)
    @POST("/api/AppZulin/EquipmentDetails2")
    Observable<String> equipmentDetails2(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId,
                                         @Query("OperatorUserId") String OperatorUserId, @Query("UID") String UID);

    //租赁价格表
    @POST("/api/AppZulin/RentPriceList")
    Observable<String> rentPriceList(@Header("Authorization") String authorization, @Query("EDicId") String EDicId,
                                     @Query("Operator") String Operator, @Query("RentWay") int RentWay, @Query("Count") int Count, @Query("RentDate") int RentDate);

    //租赁价格表
    @POST("/api/AppZulin/RentPriceList")
    Observable<String> rentPriceListPoint(@Header("Authorization") String authorization, @Query("EDicId") String EDicId,
                                          @Query("Operator") String Operator, @Query("RentWay") int RentWay, @Query("Count") int Count);

    //租赁价格表(全部)
    @POST("/api/AppZulin/RentPriceList")
    Observable<String> rentPriceListAll(@Header("Authorization") String authorization, @Query("EDicId") String EDicId);

    //提交租赁订单
    @POST("/api/AppZulin/EquipmentRentSubmit")
    Observable<String> equipmentRentSubmit(@Header("Authorization") String authorization, @Body RequestBody lBody);

    //获取设备 租金/押金 returnValue : ZuJin(租金)YaJin(押金)
    @POST("/api/AppZulin/GetEquipmentParams")
    Observable<String> getEquipmentParams(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId,
                                          @Query("OperatorUserId") String OperatorUserId);

    //根据仓库Id获取仓库与所属加盟商信息 返回值 : Area(地区)Name(仓库名称)ContactName(联系人)ContactPhone(联系电话)
    @POST("/api/AppZulin/GetStorehouseInfo")
    Observable<String> getStorehouseInfo(@Header("Authorization") String authorization, @Query("StorehouseId") String StorehouseId);

    //根据UID 获取个人用户地址
    @POST("/api/AppZulin/GetPersonalInfo")
    Observable<String> GetPersonalInfo(@Header("Authorization") String authorization, @Query("UserId") String UserId);

    //////////////////////////////////////////订单//////////////////////////////////////////////////////////////////////
    //租赁订单列表
    @POST("/api/AppMy/MyEquipmentRentOrderList")
    Observable<String> myEquipmentRentOrderList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //租赁订单明细
    @POST("/api/AppMy/MyEquipmentRentOrderDetail")
    Observable<String> myEquipmentRentOrderDetail(@Header("Authorization") String authorization, @Query("OrderId") String OrderId);

    //更改订单状态
    @POST("/api/AppMy/UpdateOrderStatus")
    Observable<String> updateOrderStatus(@Header("Authorization") String authorization, @Query("OrderType") int OrderType, @Query("OrderId") String OrderId,
                                         @Query("Status") int Status);

    //订单设备台账明细
    @POST("/api/AppMy/MyEquipmentRentOrderItem")
    Observable<String> myEquipmentRentOrderItem(@Header("Authorization") String authorization, @Body RequestBody Body);

    //租赁单收货
    @POST("/api/AppMy/ReceiveGood")
    Observable<String> ReceiveGood(@Header("Authorization") String authorization, @Query("Type") int Type, @Query("OrderId") String OrderId);

    /*******************************退租**********************************************************************************************/

    //设备退租 传入参数:RentOrderID(源订单Id)TargetDeliveryTime(退租时间)StoreId(仓库Id)
    // ETypeId(设备Id)ETypeName(设备名称)Count(数量)BackLink(退租联系人)BackLinkPhone(退租联系人电话)
    @POST("/api/AppManage/EquipmentBackRent")
    Observable<String> equipmentBackRent(@Header("Authorization") String authorization, @Body RequestBody Body);

    //服务站点列表
    //    过滤条件 : Name(仓库名称)Area(城市) 示例:{page: 1, rows: 30, filters: [{name: "Name",value: "", type: "like"}]}
    //    返回值 : Id(Id)Name(站点名称)Area(位置)ImgUrl(图片地址,域名+Value)UseStatus(订单创建时间)Picture(图片)
    @POST("/api/AppManage/StoreHouseList")
    Observable<String> storeHouseList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //获取可退租的设备信息
    @POST(" /api/AppManage/CanRentEquipmentList")
    Observable<String> canRentEquipmentList(@Header("Authorization") String authorization, @Query("UserId") String UserId);

    /*******************************设备管理**********************************************************************************************/
    //我的设备列表 过滤条件 : BeforePagingFilters(用户Id,必传)EquipmentBaseNo(设备编号)UseStatus(使用状态：1=闲置 2=载物 3=报修 4= 报废).
    @POST("/api/AppManage/MyEquipmentList")
    Observable<String> MyEquipmentList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //我的设备操作
    // 示例:{"UseStatus":1,"Ids": ["674db0be-70af-4ede-9afc-c9e525a6e5bf","674db0be-70af-4ede-9afc-c9e525a6e5bf" ,"674db0be-70af-4ede-9afc-c9e525a6e5bf"]}
    @POST("/api/AppManage/MyEquipmentOpt")
    Observable<String> MyEquipmentOpt(@Header("Authorization") String authorization, @Query("Ids") String[] ids, @Query("UseStatus") int UseStatus);

    //设备详情
    @POST("/api/AppManage/MyEquipmentDetail")
    Observable<String> MyEquipmentDetail(@Header("Authorization") String authorization, @Query("Ecode") String Ecode);


    /*******************************发布转租**********************************************************************************************/
    //获取可转租设备列表 返回值 : StaticLoad(静载)CarryingLoad(动载)BaseMaterial(材质)Model(型号)EquipmentName(设备名)Picture(图片)Quantity(数量)
    @POST("/api/AppZulin/CanRentEquipmentListForUser")
    Observable<String> CanRentEquipmentListForUser(@Header("Authorization") String authorization, @Query("UserId") String UserId);

    //获取可转租设备明细
    @POST("/api/AppZulin/CanRentEquipmentDetailsForUser")
    Observable<String> CanRentEquipmentDetailsForUser(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId, @Query("UserId") String UserId);

    //订单支付(押金或租金) 参数值 OrderId(订单Id)UserId(求租方用户Id)money(金额)PayPassword(支付密码)PayRemark(备注)
    @POST("/api/AppZulin/payOrder")
    Observable<String> payOrder(@Header("Authorization") String authorization, @Body RequestBody body);


    //发布转租(挂牌转租)
    @POST("/api/AppZulin/PublishAttornRent")
    Observable<String> PublishAttornRent(@Header("Authorization") String authorization, @Query("UserId") String UserId, @Query("EDicId") String EDicId,
                                         @Query("CanCount") int CanCount, @Query("Count") int Count, @Query("Address") String Address);

    //转租单发货
    @POST("/api/AppMy/SendGood_Rent")
    Observable<String> SendGood_Rent(@Header("Authorization") String authorization, @Query("OrderId") String OrderId,
                                     @Query("EquipmentBaseNo") String[] EquipmentBaseNo,
                                     @Query("Count") int Count);

    //退租订单
    @POST("/api/AppMy/MyEquipmentBackRentOrderList")
    Observable<String> MyEquipmentBackRentOrderList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //退租发货
    @POST("/api/AppMy/SendGood_BackRent")
    Observable<String> SendGood_BackRent(@Header("Authorization") String authorization, @Query("OrderId") String OrderId, @Query("EquipmentBaseNo") String[] EquipmentBaseNo,
                                         @Query("Count") int Count);

    //发货无码设备台账获取
    @POST("/api/AppMy/GetECodeForSend")
    Observable<String> GetECodeForSend(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId,
                                       @Query("UserId") String UserId, @Query("Quantity") int Quantity);


    ////////////////////////////////////////////////////////////////////购买.////////////////////////////////////////
    //可购买设备列表
    @POST("/api/AppZulin/EquipmentBuyList")
    Observable<String> EquipmentBuyList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //可购买设备详情
    @POST("/api/AppZulin/EquipmentBuyItem")
    Observable<String> EquipmentBuyItem(@Header("Authorization") String authorization, @Query("EquipmentId") String EquipmentId);

    //提交购买订单
    @POST("/api/AppZulin/EquipmentBuyAdd")
    Observable<String> EquipmentBuyAdd(@Header("Authorization") String authorization, @Body RequestBody Body);

    //我的购买订单列表
    @POST("/api/AppMy/MyEquipmentBuyOrderList")
    Observable<String> MyEquipmentBuyOrderList(@Header("Authorization") String authorization, @Body RequestBody Body);

    //我的购买订单详情
    @POST("/api/AppMy/MyEquipmentBuyOrderDetail")
    Observable<String> MyEquipmentBuyOrderDetail(@Header("Authorization") String authorization, @Query("OrderId") String OrderId);

    //我的购买订单明细
    @POST("/api/AppMy/MyEquipmentBuyOrderItem")
    Observable<String> MyEquipmentBuyOrderItem(@Header("Authorization") String authorization, @Body RequestBody Body);

    //根据类型获取设备
    @POST("/api/AppZulin/EquipmentListForType")
    Observable<String> EquipmentListForType(@Header("Authorization") String authorization, @Query("TypeId") String TypeId);

    //添加维修单
    @POST("/api/AppManage/AddObsoleteForm")
    Observable<String> AddObsoleteForm(@Header("Authorization") String authorization, @Body RequestBody Body);

    //文件图片上传
    @POST("/api/AppCommand/UploadFile")
    Observable<String> UploadFile(@Header("authorization") String authorization, @Body MultipartBody fileBody, @Query("tablename") String tablename);

    //文件图片上传
    @POST("/api/AppCommand/Uploadproof")
    Observable<String> Uploadproof(@Header("authorization") String authorization,@Query("userId") String userId, @Body MultipartBody fileBody, @Query("tablename") String tablename);

    //根据设备编号查询设备信息
    @POST("/api/AppManage/EquipmentInfoForNo")
    Observable<String> EquipmentInfoForNo(@Header("authorization") String authorization, @Query("ECode") String ECode);

    //维修单列表
    @POST("/api/AppManage/RepairList")
    Observable<String> RepairList(@Header("authorization") String authorization, @Body RequestBody Body);

    //维修单设备明细
    @POST("/api/AppManage/RepairItem")
    Observable<String> RepairItem(@Header("authorization") String authorization, @Query("FormId") String FormId);

    //维修单设备明细详情
    @POST("/api/AppManage/RepairItemDetail")
    Observable<String> RepairItemDetail(@Header("authorization") String authorization, @Query("DetailId") String DetailId);

    @POST("/api/AppUser/getUserInfo")
    Observable<String> getUserInfo(@Header("authorization") String authorization, @Query("Phone") String Phone);


    @POST("/api/AppMy/GetEquipmentCount")
    Observable<String> GetEquipmentCount(@Header("authorization") String authorization, @Query("EquipmentId") String EquipmentId,
                                         @Query("UserId") String UserId);


    @POST("/api/AppMy/CancelOrder")
    Observable<String> CancelOrder(@Header("authorization") String authorization, @Query("OrderType") int OrderType,
                                   @Query("OrderId") String OrderId);
    //////////////////////////////////////////////////////支   付////////////////////////////////////////////////////////////

    //在发起微信支付前,调用此接口获取相关信息
    @POST("/api/PaymentForWeChatApp/PostPreMoney")
    Observable<String> wxPrePay(@Header("authorization") String authorization,
                                @Query("user") String user,
                                @Body RequestBody infoBody);


    //发起支付宝支付前,调用此接口获取相关信息
    @POST("api/APPAliPay/ToPayPage")
    Observable<String> aliPayGetInfo(@Header("authorization") String authorization,
                                     @Query("user") String user,
                                     @Body RequestBody infoBody);

    //支付宝成功支付后,进行最终确认
    @POST("/api/APPAliPay/PaySuccess")
    Observable<String> alipaySuccConfirm(@Header("authorization") String authorization,
                                         @Query("out_trade_no") String out_trade_no);

    //验证是否有支付密码
    @POST("/api/AppUser/PayPwdIsSet")
    Observable<String> PayPwdIsSet(@Header("authorization") String authorization,
                                   @Query("UserId") String UserId);


    @POST("/api/AppMy/MyBankBindList")
    Observable<String> MyBankBindList(@Header("authorization") String authorization,
                                      @Query("UserId") String UserId);

    @POST("/api/AppMy/BankBind")
    Observable<String> BankBind(@Header("authorization") String authorization,
                                @Body RequestBody infoBody);

    @POST("/api/AppMy/UnBankBind")
    Observable<String> UnBankBind(@Header("authorization") String authorization,
                                  @Query("BindId") String BindId);


    //提现申请
    @POST("/api/AppMy/AddInFund")
    Observable<String> AddInFund(@Header("authorization") String authorization,
                                 @Body RequestBody infoBody);

    //我的交易记录
    @POST("/api/AppMy/MyTradeFlowList")
    Observable<String> MyTradeFlowList(@Header("authorization") String authorization,
                                       @Body RequestBody infoBody);

    //我的交易记录详情
    @POST("/api/AppZulin/TradeFlowItem")
    Observable<BaseBean<List<WalletListDetails>>> TradeFlowItem(@Header("authorization") String authorization,
                                                                @Query("FlowId") String FlowId, @Query("UserId") String UserId);

    //统计
    @POST("/api/AppManage/StatisticsEquipmentInfo")
    Observable<String> StatisticsEquipmentInfo(@Header("authorization") String authorization,
                                               @Query("UserId") String UserId);

    //统计
    @POST("/api/AppZulin/ChangeGroundingQuantity")
    Observable<String> ChangeGroundingQuantity(@Header("authorization") String authorization,
                                               @Query("UserId") String UserId, @Query("EDicId") String EDicId, @Query("Count") int Count);

    //充值申请
    @POST("/api/AppMy/MyOutInFund")
    Observable<String> MyOutInFund(@Header("authorization") String authorization,
                                       @Body RequestBody infoBody);

    //版本升级
    @POST("/api/AppUser/GetAppVersion")
    Observable<String> GetAppVersion(@Header("authorization") String authorization);

    //版本升级
    @POST("/api/AppMy/EquipmentBackRentOrderDetail")
    Observable<BaseBean<List<RentOrderDetailBean>>> EquipmentBackRentOrderDetail(@Header("authorization") String authorization,
                                                                                 @Query("OrderId") String OrderId);

    //消息保存
    @POST("/api/AppMy/AddSystem_Messages")
    Observable<String> AddSystem_Messages(@Header("authorization") String authorization,@Body RequestBody infoBody);

    //消息数量
    @POST("/api/AppMy/GetMessagesCount")
    Observable<String> GetMessagesCount(@Header("authorization") String authorization,@Query("UserId") String UserId);

}


