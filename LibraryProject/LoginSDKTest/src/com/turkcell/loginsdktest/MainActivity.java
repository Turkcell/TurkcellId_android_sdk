package com.turkcell.loginsdktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.turkcell.loginsdk.helper.LoginSdk;
import com.turkcell.loginsdk.service.callback.LoginSdkCallBack;
import com.turkcell.loginsdk.service.callback.LogoutResponse;

public class MainActivity extends Activity {

    private Button buttonLogin3g;
    private Button buttonLoginWithout3g;
    private Button buttonLogout;
    private TextView textView;
    private static final int LOGINSDK_REQUEST_CODE = 111;
    private String appId = "11111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();
    }

    private void setButtons() {
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("AppId : " + appId);
        buttonLogin3g = (Button) findViewById(R.id.buttonLogin3g);
        buttonLoginWithout3g = (Button) findViewById(R.id.buttonLoginWithout3g);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogin3g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(getLogin3gIntent(), LOGINSDK_REQUEST_CODE);
            }
        });
        buttonLoginWithout3g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getLoginWithout3gIntent(), LOGINSDK_REQUEST_CODE);

            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSdk.logOut(MainActivity.this, new LoginSdkCallBack<LogoutResponse>() {
                    @Override
                    public void onSuccess(LogoutResponse value) {
                        Toast.makeText(MainActivity.this, "logout Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception exception) {
                        Toast.makeText(MainActivity.this, "logout failed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

    private Intent getLogin3gIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);
        intent.putExtra("dll", "false");// zorunlu
        intent.putExtra("isDirectLogin", "false");// zorunlu
        setIntentDefValues(intent);
        return intent;
    }

    private Intent getLoginWithout3gIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);
        intent.putExtra("dll", "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra("isDirectLogin", "false");// zorunlu
        setIntentDefValues(intent);
        return intent;
    }


    private Intent getLsdkIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);

        intent.putExtra("dll", "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra("isDirectLogin", "true");// zorunlu

        setIntentDefValues(intent);

        return intent;
    }

    private void setIntentDefValues(Intent intent) {
        intent.putExtra("appId", appId);// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra("pageBackgroundColor", R.color.white);//Sayfan?n arka plan rengi icin
        intent.putExtra("pageTextColor", R.color.lsdk_gray1);//Sayfadaki textlerin renkleri
        intent.putExtra("headerBackgroundColor", R.color.flatBlue);//Headerdaki background rengi
        intent.putExtra("headerTextBackgroundColor", R.color.flatBlue);//Headerdaki textin arka plan rengi
        intent.putExtra("headerTextColor", R.color.white);//Headerdaki textin rengi
        intent.putExtra("headerTitle", "Turkcell Platinum");//Headerdaki text
        intent.putExtra("positiveButtonColor", R.color.flatBlue);//Positif button renkleri
        intent.putExtra("positiveButtonTextColor", R.color.white);//Positif button text renkleri
        intent.putExtra("negativeButtonColor", R.color.black);//Negatif button renkleri
        intent.putExtra("negativeButtonTextColor", R.color.white);//Negatif button text renkleri
        intent.putExtra("inputBackgroundColor", R.color.lsdk_gray_background);//Edittextler icin background rengi
        //	intent.putExtra("Env", "PROD");//Default test
         intent.putExtra(LoginSdk.KEY_FONT_PATH, "fonts/LDFComicSansBold.ttf");
        //	intent.putExtra("exitButtonDrawable", com.turkcell.loginsdktest.R.drawable.a);
//	intent.putExtra("backButtonDrawable", com.turkcell.loginsdktest.R.drawable.b);
//	intent.putExtra("checkedCheckBoxButtonDrawable", com.turkcell.loginsdktest.R.drawable.custom_icon);
//	intent.putExtra("turkcellLogoDrawable", com.turkcell.loginsdktest.R.drawable.d);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (LOGINSDK_REQUEST_CODE == requestCode) {

            StringBuilder stringBuilder = new StringBuilder();
            String status = data.getStringExtra("status") != null ? data.getStringExtra("status") : "";
            String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "";
            String loginType = data.getStringExtra("loginType") != null ? data.getStringExtra("loginType") : "";
            String authToken = data.getStringExtra("authToken") != null ? data.getStringExtra("authToken") : "";

            stringBuilder.append("status: " + status);
            stringBuilder.append("\n");
            stringBuilder.append("message: " + message);
            stringBuilder.append("\n");
            stringBuilder.append("loginType: " + loginType);
            stringBuilder.append("\n");
            stringBuilder.append("authToken: " + authToken);
            stringBuilder.append("\n");

            if (resultCode == Activity.RESULT_OK) {
                stringBuilder.append("RESULT:OK \n");


            } else {
                stringBuilder.append("RESULT:NOT-OK \n");
            }

            textView.setText(stringBuilder.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
