package com.shenbianvip.app.uppaytester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.shenbianvip.app.uppaylibrary.BaseActivity;
import com.unionpay.UPPayAssistEx;

public class APKActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apk_test);
        try{
            Button btn0 = (Button) findViewById(com.shenbianvip.app.uppaylibrary.R.id.btn0);
            btn0.setTag(0);
            btn0.setOnClickListener(mClickListener);

            TextView tv = (TextView) findViewById(com.shenbianvip.app.uppaylibrary.R.id.guide);
            tv.setTextSize(16);
            updateTextView(tv);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        // activity —— 用于启动支付控件的活动对象
        // spId —— 保留使用，这里输入null
        // sysProvider —— 保留使用，这里输入null
        // orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
        // mMode参数解释： 0 - 启动银联正式环境;1 - 连接银联测试环境
        int ret = UPPayAssistEx.startPay(this, null, null, tn, mode);

        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(APKActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }

    @Override
    public void updateTextView(TextView tv) {
        String txt = "接入指南：\n1:拷贝sdkStd目录下的UPPayAssistEx.jar到libs目录下\n2:获取tn后通过UPPayAssistEx.startPay(...)方法调用控件";
        tv.setText(txt);
    }

}
