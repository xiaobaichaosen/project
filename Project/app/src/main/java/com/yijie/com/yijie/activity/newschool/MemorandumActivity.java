package com.yijie.com.yijie.activity.newschool;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.Item;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.MyItemTouchHelperCallback;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolMemoInfo;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/2/24.
 * 备忘录
 */

public class MemorandumActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MomorandumAdapter momorandumAdapter;
    private ArrayList<SchoolMemoInfo> mList;
    int userId = 0;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_memorandum);

    }

    @Override
    public void init() {
        title.setText("备忘录");
        mList=new ArrayList<>();
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        String json= (String) SharedPreferencesUtils.getParam(this,"user","");

        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        momorandumAdapter = new MomorandumAdapter(this, mList); // Create Instance of KendergardAdapterRecyclerView
        recyclerView.setAdapter(momorandumAdapter); // Set Adapter for RecyclerView


        School school= (School) getIntent().getExtras().getSerializable("schoolMemory");
        //回显数据
        if (school!=null) {
            List<SchoolMemoInfo> schoolMemoInfo = school.getSchoolMemoInfo();
            mList.addAll(schoolMemoInfo);
            momorandumAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(momorandumAdapter.getItemCount() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_recommend, R.id.btn_send, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recommend:
                School school = new School();
                school.setSchoolMemoInfo(mList);
                EventBus.getDefault().post(school);
                finish();
                break;
            case R.id.btn_send:
                SchoolMemoInfo schoolMemoInfo = new SchoolMemoInfo();
                schoolMemoInfo.setMemoContent(etContent.getText().toString());
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                schoolMemoInfo.setCreateTime(sdf.format(d));
                schoolMemoInfo.setCreateBy(userId);
                schoolMemoInfo.setSendType(1);
                mList.add(schoolMemoInfo);
                etContent.setText("");
                momorandumAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(momorandumAdapter.getItemCount() - 1);
                break;

            case R.id.back:
                finish();
                break;


        }
    }


}
