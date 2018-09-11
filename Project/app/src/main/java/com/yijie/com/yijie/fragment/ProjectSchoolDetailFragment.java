package com.yijie.com.yijie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ProjectDetailActivity;
import com.yijie.com.yijie.activity.TempActivity;
import com.yijie.com.yijie.activity.newschool.NewContactActivity;
import com.yijie.com.yijie.activity.newschool.NewSchoolIntroduction;
import com.yijie.com.yijie.adapter.CardAdapter;
import com.yijie.com.yijie.base.APPApplication;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolContact;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.view.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
 * 学校信息
 */

public class ProjectSchoolDetailFragment extends BaseFragment {
    protected boolean isPrepared;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_ziNumber)
    TextView tvZiNumber;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_contact_edit)
    TextView tvContactEdit;
    @BindView(R.id.tv_simple_edit)
    TextView tvSimpleEdit;
    @BindView(R.id.ll_root)
    NestedScrollView rlRoot;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageView expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;

    private List<Integer> mList = new ArrayList<>();
    private SchoolMain schoolMain;
    private List<SchoolContact> schoolContact;
    //如果是true显示编辑按钮
    Boolean changeText;
    String schoolId;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_studentdetail;
    }

    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        LogUtil.e("=======================可见=============================");
        super.onResume();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        //保证图片在手机屏幕中间显示
        LinearSnapHelper mLinearySnapHelper = new LinearSnapHelper();
        mLinearySnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    private void init() {
        recyclerView.setNestedScrollingEnabled(false);
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        getSchoolDetail(schoolId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        getSchoolDetail(schoolId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        //获取父activity中得学校id
        ProjectDetailActivity tempActivity = (ProjectDetailActivity) mActivity;
        schoolId = tempActivity.schoolId;
        getSchoolDetail(schoolId);

    }


    /**
     * 通过学校id查询学校信息
     *
     * @param schoolId
     */
    public void getSchoolDetail(String schoolId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId", schoolId);
        getinstance.post(Constant.SELECTDETAIL, stringStringHashMap, new BaseCallback<String>() {
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
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    School school = gson.fromJson(data.toString(), School.class);
                    //学校
                    schoolMain = school.getSchoolMain();
                    String img = schoolMain.getImg();
                    if (!TextUtils.isEmpty(img)){
                        recyclerView.setVisibility(View.VISIBLE);
                        String[] split = img.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerView.setAdapter(new CardAdapter(mActivity, strings,schoolMain.getId()));
                    }else {
                        recyclerView.setVisibility(View.GONE);
                    }
                    expandTextView.setText(schoolMain.getContent());
                    String detailAddress = schoolMain.getDetailAddress();
                    APPApplication application = (APPApplication) mActivity.getApplication();
                    application.setDetailAdress(detailAddress);
                    tvAdress.setText(schoolMain.getDetailAddress());
                    //学校联系人
                    schoolContact = school.getSchoolContact();
                    for (int i = 0; i < schoolContact.size(); i++) {
                        tvName.setText(schoolContact.get(i).getUserName());
                        tvZiNumber.setText(schoolContact.get(i).getTelephone());
                        tvPhone.setText(schoolContact.get(i).getCellphone());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_simple_edit, R.id.tv_contact_edit})
    public void onViewClicked(View view) {
        Bundle mBundle = new Bundle();
        Intent intent = new Intent();
        switch (view.getId()) {
            //简历编辑
            case R.id.tv_simple_edit:
                mBundle.putSerializable("schoolMain", schoolMain);
                intent.putExtras(mBundle);
                intent.putExtra("isFromNet", true);
                intent.setClass(mActivity, NewSchoolIntroduction.class);
                startActivity(intent);
                break;
            //联系人编辑
            case R.id.tv_contact_edit:
                intent.putExtra("schoolId", schoolId);
                if (schoolContact.size()>0){
                    mBundle.putSerializable("tempSchoolContact", schoolContact.get(0));
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, NewContactActivity.class);
                startActivity(intent);
                break;
        }
    }

}
