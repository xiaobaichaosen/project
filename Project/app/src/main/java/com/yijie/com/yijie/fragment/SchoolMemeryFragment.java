package com.yijie.com.yijie.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.TempActivity;
import com.yijie.com.yijie.activity.newschool.MomorandumAdapter;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.bean.school.SchoolMemoInfo;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.SoftKeyBoardListener;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.MyPopuList;
import com.yijie.com.yijie.view.MyPopuWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/5/17.
 * 学校备忘录碎片
 */

public class SchoolMemeryFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_send)
    Button btnSend;
    Unbinder unbinder;
    @BindView(R.id.rl_root)
    LinearLayout rlRoot;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;


    private ArrayList<SchoolMemoInfo> mList;
    //项目列表
    private ArrayList<String> projectList = new ArrayList<String>();
    int userId = 0;
    private MomorandumAdapter momorandumAdapter;
    MyPopuWindow myPopuWindow = null;
    String schoolId;
    //项目列表
    private List<SchoolSimple> dataList;
    private String schoolName;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memery;

    }


    @Override
    protected void initView() {

        SoftKeyBoardListener.setListener(mActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                recyclerView.scrollToPosition(momorandumAdapter.getItemCount() - 1);
            }

            @Override
            public void keyBoardHide(int height) {



            }
        });

        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        getMemoryList(schoolId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        getMemoryList(schoolId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        dataList = new ArrayList<>();
        mList = new ArrayList<>();
        String json = (String) SharedPreferencesUtils.getParam(mActivity, "user", "");

        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //获取父activity中得学校id
        TempActivity tempActivity = (TempActivity) mActivity;
        schoolId = tempActivity.schoolId;
        schoolName = tempActivity.schoolName;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager); // Set LayoutManager in the RecyclerView
        momorandumAdapter = new MomorandumAdapter(mActivity, mList); // Create Instance of KendergardAdapterRecyclerView
        recyclerView.setAdapter(momorandumAdapter); // Set Adapter for RecyclerView
        WindowManager wm1 = mActivity.getWindowManager();
        final int width1 = wm1.getDefaultDisplay().getWidth();
        int height1 = wm1.getDefaultDisplay().getHeight();
        final PopupWindow popupWindow = new PopupWindow(mActivity);
        popupWindow.setOutsideTouchable(true);
        final View popuwindowView = LayoutInflater.from(mActivity).inflate(R.layout.popuwindow_inflate, null);
        final TextView tvEdit = popuwindowView.findViewById(R.id.tv_edit);
        final TextView tvDelect = popuwindowView.findViewById(R.id.tv_delect);
        final TextView tvContactProject = popuwindowView.findViewById(R.id.tv_contactProject);

        popupWindow.setContentView(popuwindowView);

        momorandumAdapter.setOnLongClickListener(new MomorandumAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                //获取自身的长宽高
                popuwindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                final int popupHeight = popuwindowView.getMeasuredHeight();
                final int popupWidth = popuwindowView.getMeasuredWidth();

                //获取需要在其上方显示的控件的位置信息
                final int[] location = new int[2];
                view.getLocationOnScreen(location);
                //在控件上方显示
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, width1 / 2 - popupWidth / 2, location[1] - popupHeight);
                //删除项目
                tvDelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();

                        myPopuWindow = new MyPopuWindow(mActivity);
                        myPopuWindow.setOnClick(new MyPopuWindow.onConfirm() {
                            @Override
                            public void onClick() {
                                delectMemory(mList.get(position).getId()+"");
                            }
                        });


                        myPopuWindow.show();
                    }
                });
                //设置提醒
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        TimePickerView.Type yeartype = TimePickerView.Type.ALL;
                        String format = "yyyy-MM-dd HH:mm";
                        ViewUtils.alertTimerPicker(mActivity, yeartype, format, new ViewUtils.TimerPickerCallBack() {
                            @Override
                            public void onTimeSelect(final String date) {
                                LogUtil.e("推送参数====" + mList.get(position).getMemoContent().toString() + "---" + date + "---" + userId);
                                setMemoryWarn(mList.get(position).getMemoContent().toString(), date, userId + "", schoolId, schoolName);


                            }
                        });
                    }
                });
                //关系项目
                tvContactProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        if (projectList.size() > 0) {
                            MyPopuList myPopuList = new MyPopuList(mActivity, projectList, new ViewUtils.OnWheelViewClick() {
                                @Override
                                public void onClick(View view, int i) {
//                                ShowToastUtils.showToastMsg(mActivity);
                                    if (null == mList.get(position).getProjectName()) {
                                        associatePractice(mList.get(position).getId()+"", dataList.get(i).id);
                                    } else {
                                        ShowToastUtils.showToastMsg(mActivity, "已经关联的项目不能重新关联");
                                    }

                                }
                            });
                            myPopuList.show();
                        } else {
                            ShowToastUtils.showToastMsg(mActivity, "请先创建项目");
                        }


                    }
                });

            }
        });
    }

    @Override
    public void onResume() {
        isPrepared = true;
        dataList.clear();
        mList.clear();
        initData();
        //获取项目列表待优化
        getProjectList();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        getMemoryList(schoolId);

    }

    /**
     * 设置备忘录提醒
     *
     * @param name
     * @param time
     * @param userId
     */
    public void setMemoryWarn(String name, String time, String userId, String schoolId, String schoolName) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("time", time);
        stringStringHashMap.put("userId", userId);
        stringStringHashMap.put("schoolId", schoolId);
        stringStringHashMap.put("schoolName", schoolName);
        getinstance.post(Constant.REMINDSET, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) throws JSONException {
                LogUtil.e(o);
                JSONObject jsonObject = new JSONObject(o);
                String resMessage = jsonObject.getString("resMessage");
                ShowToastUtils.showToastMsg(mActivity, resMessage);
                commonDialog.dismiss();
                statusLayoutManager.showSuccessLayout();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }
        });
    }

    /**
     * 根据学校id查询备忘录类别
     *
     * @param schoolId
     */
    public void getMemoryList(String schoolId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId", schoolId);
        stringStringHashMap.put("pageStart", "1");
        stringStringHashMap.put("pageSize", Integer.MAX_VALUE + "");
        getinstance.post(Constant.MEMORYLIST, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);

                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        SchoolMemoInfo schoolSimple = gson.fromJson(jsonObject1.toString(), SchoolMemoInfo.class);
                        Integer id = schoolSimple.getCreateBy();
                        if (id == userId) {
                            schoolSimple.setSendType(0);
                        } else {
                            schoolSimple.setSendType(1);
                        }
                        mList.add(schoolSimple);
                    }
                    momorandumAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(momorandumAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                if (mList.size() == 0) {
//                  statusLayoutManager.showEmptyLayout();
//                } else {
                statusLayoutManager.showSuccessLayout();
//                }
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }
        });
    }

    /**
     * 根据学校id查询项目列表
     *
     * @param
     */
    private void getProjectList() {
        String json = (String) SharedPreferencesUtils.getParam(mActivity, "user", "");
        int userId = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", 0 + "");
        stringStringHashMap.put("userId", userId + "");
        stringStringHashMap.put("pageSize", Integer.MAX_VALUE + "");
        stringStringHashMap.put("schoolId", schoolId);
        getinstance.post(Constant.PROJECTLIST, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        SchoolSimple schoolSimple = gson.fromJson(jsonObject1.toString(), SchoolSimple.class);
                        dataList.add(schoolSimple);
                        projectList.add(schoolSimple.projectName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
            }
        });

    }

    /**
     * 根据id删除备忘录
     *
     * @param memoryId
     */
    public void delectMemory(String memoryId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", memoryId);
        getinstance.post(Constant.DELECTMEMORY, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                mList.clear();
                getMemoryList(schoolId);
                ShowToastUtils.showToastMsg(mActivity, "删除成功！");
                myPopuWindow.dismiss();
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }

    /**
     * 发送一条备忘录
     *
     * @param schoolMemoInfo
     */
    public void sendMemoryMessage(SchoolMemoInfo schoolMemoInfo) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        getinstance.postJson(Constant.ADDMEMORY, schoolMemoInfo, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                mList.clear();
                getMemoryList(schoolId);
                ShowToastUtils.showToastMsg(mActivity, "发送成功！");
                etContent.setText("");
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }

    /**
     * 关联项目
     */
    public void associatePractice(String id, String practiceId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", id);
        stringStringHashMap.put("practiceId", practiceId);
        getinstance.post(Constant.ASSOCIATEPRACTICE, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                mList.clear();
                getMemoryList(schoolId);
                ShowToastUtils.showToastMsg(mActivity, "关联成功！");
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.et_content, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_content:
                break;
            case R.id.btn_send:
                SchoolMemoInfo schoolMemoInfo = new SchoolMemoInfo();
                schoolMemoInfo.setSchoolId(Integer.parseInt(schoolId));
                schoolMemoInfo.setMemoContent(etContent.getText().toString().trim());
                schoolMemoInfo.setCreateBy(userId);
                sendMemoryMessage(schoolMemoInfo);

                break;

        }
    }


}
