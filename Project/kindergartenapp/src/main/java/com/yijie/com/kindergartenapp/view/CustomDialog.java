package com.yijie.com.kindergartenapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;

/**
 * 加载提醒对话框
 */
public class CustomDialog extends ProgressDialog

{
    TextView contentTxt;
    public CustomDialog(Context context)
    {
        super(context);
    }

    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.load_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        contentTxt = (TextView)findViewById(R.id.tv_load_dialog);
    }
    public void setTitle(String text){
            contentTxt.setText(text);
    }
    @Override
    public void show()
    {
        super.show();
    }
}
