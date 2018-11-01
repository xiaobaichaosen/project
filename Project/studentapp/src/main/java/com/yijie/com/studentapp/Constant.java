package com.yijie.com.studentapp;

/**
 * Created by 奕杰平台 on 2018/1/17.
 */

public class Constant {

 //外网地址图片服务器
     public static final  String baseUrl="http://116.62.242.223:8083/yijie";
     public static final  String  basepicUrl ="http://116.62.242.223:8089";
 //服务器地址
//=========other======
// public static final  String baseUrl="http://192.168.0.138:8083";
// public static final  String   basepicUrl= "http://192.168.0.138:8089";
 //=======================


//===============me
// public static final  String baseUrl="http://192.168.0.163:8083";
// public static final  String   basepicUrl= "http://192.168.0.163:8089";
 //================
 //个人图片
    public static final  String  infoUrl=basepicUrl+"/yijie/upload/student/student_user_id_";
    //园所图片地址
    public static final  String kinderUrl=basepicUrl+"/yijie/upload/kinder/kinder_user_id_";
    //登陆接口
    public static final  String LOGINURL=baseUrl+"/studentUser/login";
    //验证码登录
    public static final  String UPDATEBYCELLPHONE=baseUrl+"/studentUser/updateByCellphone";
    //未注册用户获取验证码接口
    public static final  String SENDSMSCODE=baseUrl+"/studentUser/sendSmsCode";
   //注册用户获取验证码接口
   public static final  String SENDSMSCODELOGIN=baseUrl+"/studentUser/sendSmsCodeLogin";

    //注册接口
    public static final  String REGISTURL=baseUrl+"/studentUser/reg";
    //更新接口
    public static final  String GETVERSIONUPDATE=baseUrl+"/versionUpdate/getVersionUpdate";

    //获取注册过的学校列表
    public static final  String SCHOOLMAINRAGELIST=baseUrl+"/schoolMainRageList/select";
    //个人信息
    public static final  String STUDENTINFO=baseUrl+"/studentInfo/saveOrUpdateApp";
    //联系方式
    public static final  String STUDENTCONTACT=baseUrl+"/studentContact/saveOrUpdate";
    //获取同一项目通讯录
    public static final  String SELECTBYSCHOOLPRACTICEID=baseUrl+"/studentUser/selectBySchoolPracticeId";
    //头像上传接口
    public static final  String HEADPICUPLOAD=baseUrl+"/student/headPicUpload";
    //添加自我评价，相关意向
    public static final  String STUDENTRESUME=baseUrl+"/studentResume/saveOrUpdate";
    //档案荣誉证书上传图片接口
    public static final  String CERTIFICATEUPLOADAPP=baseUrl+"/student/certificateUploadApp";
    //简历查询
    public static final  String STUDENTRESUMEDETAIL=baseUrl+"/studentResumeDetail/studentUserId";
    //教育背景
    public static final  String STUDENTEDUCATION=baseUrl+"/studentEducation/saveOrUpdate";
    //工作经历
    public static final  String STUDENTWORKDUE=baseUrl+"/studentWorkDue/saveOrUpdate";
    //删除教育背景
    public static final  String DELETEEDUCATIONBYID=baseUrl+"/studentEducation/deleteById";
    //删除工作经历
    public static final  String DELETWORKDUEBYID=baseUrl+"/studentWorkDue/deleteById";
    //档案荣誉证书获取图片接口
    public static final  String honoraryCcertificateGetUrl=baseUrl+"/getPhotoList";
    //园所详情查询接口
    public static final  String KINDERGARTENDETAILBYID=baseUrl+"/KindergartenDetail/selectKindDetailByKinderId";
    //简历审核
    public static final  String STATEUPDATE=baseUrl+"/studentResume/stateUpdate";
    //学生学校考勤签到
    public static final  String SAVESIGNIN=baseUrl+"/studentSignIn/saveSignIn";
    //学校考勤签到初始化数据
    public static final  String SELECTSTUDENTSIGNIN=baseUrl+"/studentSignIn/selectStudentSignIn";
    //我的足迹
    public static final  String STUDENTSIGNIN=baseUrl+"/studentSignIn/selectMyTracks";
    //考勤日历中打过卡的日期
    public static final  String SELECTCURRMONTHSIGN=baseUrl+"/studentSignIn/selectCurrMonthSign";
    //考勤日历点击具体日期考勤i情况
    public static final  String SELECTCURRDAYSIGN=baseUrl+"/studentSignIn/selectCurrDaySign";
    //yijie正在招聘的园所
    public static final  String SELECTBEINGRECRUITLIST=baseUrl+"/beingRecruited/selectBeingRecruitList";
    //添加浏览足迹
    public static final  String STUDENTBROWSEFOOTMARK=baseUrl+"/studentBrowseFootmark/saveBrowseFootmark";
    //浏览足迹列表
    public static final  String SELECTBROWSEFOOTMARKLIST=baseUrl+"/studentBrowseFootmark/selectBrowseFootmarkList";
    //删除浏览足迹
    public static final  String DELETEBROWSEFOOT=baseUrl+"/studentBrowseFootmark/delete";
    //学生对园所的评价
    public static final  String SAVESTUDENTEVALUAGTE=baseUrl+"/studentEvaluate/saveStudentEvaluagte";
    //学生查询对园所的评价
    public static final  String SELECTSTUDENTEVALUATE=baseUrl+"/studentEvaluate/selectStudentEvaluate";
    //园所首页信息
    public static final  String GETKINDERMATCHHOMEPAGE=baseUrl+"/kinderResumeLibrary/getKinderMatchHomePage";
    //查询是否匹配园所
    public static final  String QUERYISMATCHKINDER=baseUrl+"/kinderResumeLibrary/queryIsMatchKinder";
    //查询合作园所列表
    public static final  String SELECTCOOPERATEKINDLIST=baseUrl+"/kinderResumeLibrary/selectCooperateKindList";
    //园所图片获取地址
    public static final  String KINDERPICUIL=basepicUrl+"/yijie/upload/kinder/kinder_user_id_";
    //学生报名
    public static final  String RESUMESTATEUPDATE=baseUrl+"/kinderResumeLibrary/resumeStateUpdate";
   //获取同伴id
   public static final  String SELECTBYSTUDENTUSERID=baseUrl+"/studentResume/selectByStudentUserId";
   //获取学校下面的项目列表
   public static final  String SELECTSCHOOLPRACTICELIST=baseUrl+"/schoolPractice/selectSchoolPracticeList";



}
