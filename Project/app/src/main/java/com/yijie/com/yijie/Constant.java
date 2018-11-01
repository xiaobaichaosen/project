package com.yijie.com.yijie;

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
   //学生图片地址
   //个人图片
   public static final  String  infoUrl=basepicUrl+"/yijie/upload/student/student_user_id_";
  public static final  String kinderUrl=basepicUrl+"/yijie/upload/kinder/kinder_user_id_";
    //获取验证码接口
    public static final  String getRegistCodeUrl=baseUrl+"/getRegistCode";
    public static final  String sendSmsCode=baseUrl+"/user/sendSmsCode";
    //修改密码
    public static final  String setPwd=baseUrl+"/user/setPwd";
    //通过手机号获取用户
    public static final  String selectByMoible=baseUrl+"/user/selectByMoible";
    //园所发送审核
    public static final  String UPDATEKINDERAUDITSTATUS=baseUrl+"/KindergartenDetail/updateKinderAuditStatus";
    //园所详情查询接口
    public static final  String KINDERGARTENDETAILBYID=baseUrl+"/KindergartenDetail/select";
    //忘记密码
    public static final  String updatePassword=baseUrl+"/user/updatePassword";
    //验证码登录
    public static final  String verfyCodeLogin=baseUrl+"/user/verfyCodeLogin";

    //图片服务器地址
    public static final  String picUrl=basepicUrl+"/yijie/upload/";
    //头像获取地址
    public static final  String getheadUrl=basepicUrl+"/yijie/upload/school/school_id_";
    //头像上传接口
    public static final  String headUrl=baseUrl+"/school/headPicUpload";
    //注册接口
    public static final  String registUrl=baseUrl+"/rigst";
    //新建学校接口
    public static final  String NEWSCHOOL=baseUrl+"/school/create";

 //判断学校是否被创建
 public static final  String SELECTSCHOOLBYNAME=baseUrl+"/school/selectByName";
   // 修改联系人(有id),新建无id
    public static final  String MODECONTACT=baseUrl+"/schoolContact/saveOrUpdate";
    // 查询学校联系人列表
    public static final  String SELECTCONTACT=baseUrl+"/schoolContact/select";
    //删除联系人
    public static final  String DELETECONTACT=baseUrl+"/schoolContact/delete";
    //查询所有的联系人
    public static final  String SELECTALLCONTACT=baseUrl+"/schoolContact/select";
    //登陆接口
    public static final  String LOGIN=baseUrl+"/user/login";
    //所有项目列表
    public static final  String PROJECTLIST=baseUrl+"/schoolPractice/select";
    //园所需求列表和模糊查询
    public static final  String SELECTGROUNPLIST=baseUrl+"/kindergartenNeed/selectGrounpList";
    //通过学校id查询学校详情
    public static final  String SELECTDETAIL=baseUrl+"/school/detail";
    //更新实习薪资
    public static final  String UPDATESHIPMEMONY=baseUrl+"/schoolSalaryInfo/saveOrUpdate";
    //查询实习薪资
    public static final  String SELECTSHIPMEMONY=baseUrl+"/schoolSalaryInfo/select";
    //查询学校列表
    public static final  String SCHOOLLIST=baseUrl+"/school/select";
    //根据学校id查询备忘录列表
    public static final  String MEMORYLIST=baseUrl+"/schoolMemoInfo/select";
    //根据项目id查询备忘录列表
    public static final  String SELECTBYPRACTICEID=baseUrl+"/schoolMemoInfo/selectByPracticeId";

    //添加一条备忘录
    public static final  String ADDMEMORY=baseUrl+"/schoolMemoInfo/saveOrUpdate";
    //新建实习详情
    public static final  String NEWSHIPDETAIL=baseUrl+"/schoolPractice/saveOrUpdate";
    //根据id，删除备忘录
    public static final  String DELECTMEMORY=baseUrl+"/schoolMemoInfo/delete";
    //修改学校简介
    public static final  String UPDATESCHOOLSIMPLE=baseUrl+"/school/saveOrUpdate";
   //学校转移给其他开发老师
   public static final  String UPDATECREATEBY=baseUrl+"/school/updateCreateBy";
   //学校转移给其他开发老师
   public static final  String UPDATEPRACTICEACCEPTID=baseUrl+"/schoolPractice/updatePracticeAcceptId";

    //查询项目详情
    public static final  String SCHOOLPROJECTDETAIL=baseUrl+"/schoolProjectDetail/select";
    //添加院校联络人
    public static final  String SCHOOLLIAISONS=baseUrl+"/schoolLiaisons/saveOrUpdate";
    //添加培训详情
    public static final  String SCHOOLTRAINDETAIL=baseUrl+"/schoolTrainDetail/saveOrUpdate";
    //备忘录关联项目id
    public static final  String ASSOCIATEPRACTICE=baseUrl+"/schoolMemoInfo/associatePractice";
   //备忘录消息提醒通知
    public static final  String REMINDSET=baseUrl+"/schoolMemoInfo/remindSet";
    //更改状态接口
    public static final  String STATEUPDATE=baseUrl+"/schoolPractice/stateUpdate";
    //发现页列表
    public static final  String SELFDISCOVERY=baseUrl+"/selfDiscovery/select";
    //新增待培训列表
    public static final  String SELECTTRAINLIST=baseUrl+"/schoolPractice/selectTrainList";
    //接收待培训项目
    public static final  String CONFIRMPROJECT=baseUrl+"/schoolPractice/confirmProject";
   //接收待培训项目
   public static final  String READPRACTICEUPDATE=baseUrl+"/schoolPractice/readPracticeUpdate";
    //查询用户列表
    public static final  String GETUSERLISTBYROLENAME=baseUrl+"/user/getUserListByRoleName";
    //查询待审核简历列表
    public static final  String SELECTRESUMELIST=baseUrl+"/studentResume/selectResumeList";
    //查询待分配简历列表
    public static final  String SELECTRESUMETAKEALLOTLIST=baseUrl+"/studentResume/selectResumeTakeAllotList";
    //查询未通过简历列表
    public static final  String SELECTRESUMENOTPASS=baseUrl+"/studentResume/selectResumeNotPass";
    //查询已通过简历列表
    public static final  String SELECTRESUMEALLOCATED=baseUrl+"/studentResume/selectResumeAllocated";

    //查询简历接口
    public static final  String SELECTBYSTUDENTUSERID=baseUrl+"/studentResumeDetail/studentUserId";
    //简历审核接口
    public static final  String SENDRESUMEAUDIT=baseUrl+"/studentResume/sendResumeAudit";
    //分享学生简历
    public static final  String GETSTUDENTRESUMEHTML=baseUrl+"/studentResumeDetail/getStudentResumeHtml";
    //园所需求列表
    public static final  String SELECTDEMANDLIST=baseUrl+"/kindergartenNeed/selectDemandList";
    //学生状态列表
    public static final  String SELECTSTUHOMEPAGELIST=baseUrl+"/studentResume/selectStuHomePageList";
    //需求详细
    public static final  String SELECTKINDERDEMANDDETAILS=baseUrl+"/kinderResumeLibrary/selectKinderDemandDetails";
    //考勤签到园所列表
    public static final  String GETSHOWALLKINDATMAP=baseUrl+"/studentSignIn/getShowAllKindAtMap";
    //考勤签到通过园所id查询园所详情
    public static final  String GETKINDERINFOBYKINDERID=baseUrl+"/studentSignIn/getKinderInfoByKinderId";

 //往期合作，正在合作，心愿所列表
 public static final  String SELECTCOOPERGARDENANDNEWGARDEN=baseUrl+"/kinderResumeLibrary/selectCooperGardenAndNewGarden";
    //更新接口
    public static final  String GETVERSIONUPDATE=baseUrl+"/versionUpdate/getVersionUpdate";
}
