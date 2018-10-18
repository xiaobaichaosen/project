package com.yijie.com.kindergartenapp.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.adapter.FilterAdapter;
import com.yijie.com.kindergartenapp.adapter.LoadMorePoiAdapter;
import com.yijie.com.kindergartenapp.adapter.LoadMorePopuAdapter;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.KindergartenMember;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.fragment.yijie.LoadMoreYijieAdapter;
import com.yijie.com.kindergartenapp.utils.AnimationUtil;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;
import android.widget.AdapterView.OnItemClickListener;
public class TwoFragment extends BaseFragment implements OnItemClickListener{
    RegistKindActivity tempActivity;
    @BindView(R.id.btn_two)
    Button btnTwo;
    Unbinder unbinder;
    @BindView(R.id.tv_down)
    TextView tvDown;
    Unbinder unbinder1;
    @BindView(R.id.et_realName)
    EditText etRealName;
    @BindView(R.id.et_kinder)
    AutoCompleteTextView etKinder;
    @BindView(R.id.view_line)
    View viewLine;
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private PopupWindow mPopupWindow;
    private boolean isPopWindowShowing = false;
    private List<KindergartenDetail> dataList=new ArrayList<>();
    private  int fromYDelta;
    private KindergartenDetail kinderDetail;
    LoadMorePopuAdapter loadMoreWrapperAdapter;
    ArrayAdapter<KindergartenDetail> adapter;
    FilterAdapter mAdapter;
    public KindergartenMember getKindergartenMember() {
        return kindergartenMember;
    }

    public void setKindergartenMember(KindergartenMember kindergartenMember) {
        this.kindergartenMember = kindergartenMember;
    }

    private KindergartenMember kindergartenMember;
    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();

        }
        unbinder1.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_down, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_down:
//                if (isPopWindowShowing) {
//                    tvDown.startAnimation(AnimationUtil.createRotatedownAnimation());
//                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(mActivity, fromYDelta));
//                    mPopupWindow.getContentView().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPopupWindow.dismiss();
//                        }
//                    }, AnimationUtil.ANIMATION_OUT_TIME);
//                } else {
//                    showPopupWindow();
//                }
                break;
            case R.id.btn_two:

                tempActivity.stepView.setStep(3);
                if (dataList.contains(kinderDetail)&&kinderDetail.getKindName().equals(etKinder.getText().toString().trim())){
                    //证明已经注册过，跳转到发送申请页面

                    if (onButtonClick != null) {
                        kindergartenMember.setKinderId(kinderDetail.getId());
                        kindergartenMember.setKindName(kinderDetail.getKindName());
                        kindergartenMember.setMemerName(etRealName.getText().toString().trim());
                        onButtonClick.onSend(kindergartenMember);

                    }
                }else{
                    //还没注册，跳转到注册页面
                    if (onButtonClick != null) {
                        kindergartenMember.setKindName(etKinder.getText().toString().trim());
                        kindergartenMember.setMemerName(etRealName.getText().toString().trim());
                        onButtonClick.onClick(kindergartenMember);
                    }
                }
                break;
        }
    }

    private void showPopupWindow() {
        //andorid 7.0如果内容超过屏幕showasdropdown 将会失效需要适配
        final View contentView = LayoutInflater.from(mActivity).inflate(R.layout.selectlist, null);
        RecyclerView recyclerView= contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        //设置适配器
        loadMoreWrapperAdapter = new LoadMorePopuAdapter(dataList, R.layout.adapter_popu_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.base_dimen_500));
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
//        mPopupWindow.setOutsideTouchable(false);
//        mPopupWindow.setFocusable(false);
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        fromYDelta = -contentHeight - 50;
        mPopupWindow.showAsDropDown(viewLine, 0, 0);
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(mActivity, fromYDelta));
        tvDown.startAnimation(AnimationUtil.createRotateupAnimation());
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMorePopuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                kinderDetail = dataList.get(position);
                etKinder.setText(kinderDetail.getKindName());
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopWindowShowing = false;
            }
        });
        isPopWindowShowing = true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
         kinderDetail = mAdapter.getmList().get(position);
         etKinder.setText(kinderDetail.getKindName());
    }
    //1、定义接口
    public interface OnButtonClick {
        public void onClick(KindergartenMember kindergartenMember);
        public void onSend(KindergartenMember kindergartenMember);
    }

    @Override
    protected void initView() {
        dataList.clear();
        getData();
        tempActivity = (RegistKindActivity) getActivity();
        etKinder.setThreshold(1);  //设置输入一个字符 提示，默认为2
        etKinder.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {




    }

    @Override
    protected void onInvisible() {

    }



    /**
     * 获取注册过的园所名字
     */
    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", 1 + "");
        stringStringHashMap.put("pageSize", Integer.MAX_VALUE+ "");
        getinstance.post(Constant.selectKinderNameList, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                loading.showError();
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
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
                        KindergartenDetail kindergartenDetail = gson.fromJson(jsonObject1.toString(), KindergartenDetail.class);
                        dataList.add(kindergartenDetail);
                    }

                    mAdapter = new FilterAdapter(dataList,mActivity);
                    etKinder.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });

    }
    @Override
    protected int getLayoutId() {

        return R.layout.fragment_two;
    }
}
