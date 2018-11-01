package com.yijie.com.kindergartenapp;

import com.yijie.com.kindergartenapp.bean.KindergartenDetail;

/**
 * Created by 奕杰平台 on 2018/1/17.
 */

public class Constant {

    //外网地址图片服务器
//    public static final  String baseUrl="http://116.62.242.223:8083/yijie";
//    public static final  String  basepicUrl ="http://116.62.242.223:8089";
    //服务器地址
//=========other======
// public static final  String baseUrl="http://192.168.0.138:8083";
// public static final  String   basepicUrl= "http://192.168.0.138:8089";
    //=======================


    //===============me
 public static final  String baseUrl="http://192.168.0.163:8083";
 public static final  String   basepicUrl= "http://192.168.0.163:8089";
    //================
    //图片获取地址
    public static final  String certificateUrl=basepicUrl+"/yijie/upload/kinder/kinder_user_id_";
    //学生图片地址
    public static final  String  infoUrl=basepicUrl+"/yijie/upload/student/student_user_id_";
    //登陆接口
    public static final  String LOGINURL=baseUrl+"/kinderUser/login";
    //获取手机验证码接口
    public static final  String SENDSMSCODE=baseUrl+"/kinderUser/sendSmsCode";

    //更换绑定手机号码
    public static final  String REPLACECELLPHONE=baseUrl+"/kinderUser/replaceCellphone";
    //获取邮箱验证码接口
    public static final  String SENDEAMIL=baseUrl+"/kinderUser/sendEamil";
    //注册接口
    public static final  String REGISTURL=baseUrl+"/kinderUser/reg";
    //注册验证接口
    public static final  String CHECKKINDERUSER=baseUrl+"/kinderUser/checkKinderUser";
    //园所注册信息接口
    public static final  String KINDERGARTENDETAIL=baseUrl+"/KindergartenDetail/saveOrUpdate";
    //园所招聘发布列表
    public static final  String SELECTPUBLISHLIST=baseUrl+"/kindergartenDiscovery/selectPublishList";
    //通过园所id详情查询接口
    public static final  String KINDERGARTENDETAILBYID=baseUrl+"/KindergartenDetail/select";
    //通过手机号园所详情查询接口
    public static final  String SELECTBYCELLPHONE=baseUrl+"/KindergartenDetail/selectByCellPhone";
    //园所提需求
    public static final  String KINDERGARTENNEED=baseUrl+"/kindergartenNeed/saveOrUpdate";
    //园所是否可以提需求
    public static final  String ISSENDREQUEST=baseUrl+"/kindergartenNeed/isSendRequest";
    //园所发送审核
    public static final  String UPDATEKINDERAUDITSTATUS=baseUrl+"/KindergartenDetail/updateKinderAuditStatus";
    //项目需求列表
    public static final  String SELECTDEMANDLIST=baseUrl+"/kindergartenNeed/selectDemandList";
    //获取注册时候得所有园所名称
    public static final  String selectKinderNameList=baseUrl+"/KindergartenDetail/selectKinderNameList";
    //发送审核(注册园所用户)
    public static final  String regKinderMember=baseUrl+"/kinderUser/regKinderMember";
    //查询招聘发布详情
    public static final  String SELECTRECRUITDETAILBYID=baseUrl+"/kindergartenNeed/selectRecruitDetailById";
    //查询更多简历数据
    public static final  String SELECTALREADYRESUMELIST=baseUrl+"/kinderResumeLibrary/selectAlreadyResumeList";
    //查询待选简历列表
    public static final  String SELECTBYKINDERNEEDID=baseUrl+"/kinderResumeLibrary/selectByKinderNeedId";
    //获取发现列表
    public static final  String SELECTBYKINDERID=baseUrl+"/kindergartenDiscovery/selectByKinderId";
    //查询简历接口
    public static final  String SELECTBYSTUDENTUSERID=baseUrl+"/studentResumeDetail/studentUserId";
    //更新简历状态接口
    public static final  String RESUMESTATEUPDATE=baseUrl+"/kinderResumeLibrary/resumeStateUpdate";
    //取消招聘
    public static final  String CANCELRECRUIT=baseUrl+"/kindergartenNeed/cancelRecruit";
    //修改密码
    public static final  String SETPWD=baseUrl+"/kinderUser/setPwd";

    //头像上传接口
    public static final  String headUploadUrl=baseUrl+"/kinder/headPicUpload";
    //荣誉证书上传图片接口
    public static final  String HONORARYCCERTIFICATEUPLOADURL=baseUrl+"/kinder/certificateUpload";
    //营业执照上传图片接口
    public static final  String LICENSEUPLOAD=baseUrl+"/kinder/licenseUpload";
    //园所上传图片接口
    public static final  String ENVIRONMENTUPLOAD=baseUrl+"/kinder/environmentUpload";
    //园所宿舍环境图片上传
    public static final  String ATTACHMENTUPLOAD=baseUrl+"/kinder/attachmentUpload";

    //档案荣誉证书获取图片接口
    public static final  String honoraryCcertificateGetUrl=baseUrl+"/getPhotoList";
    //更新接口
    public static final  String GETVERSIONUPDATE=baseUrl+"/versionUpdate/getVersionUpdate";
}
