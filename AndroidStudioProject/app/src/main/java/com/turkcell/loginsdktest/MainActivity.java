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
    private String appId = "123";
    private String ENV = LoginSdk.KEY_ENV_PROD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.turkcell.loginsdktest.R.layout.activity_main);
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
                LoginSdk.logOut(appId, ENV, MainActivity.this, new LoginSdkCallBack<LogoutResponse>() {
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
        intent.putExtra(LoginSdk.KEY_DLL, "false");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "false");// zorunlu
        intent.putExtra(LoginSdk.KEY_IS_DIRECT_LOGIN, "false");// zorunlu
        setIntentDefValues(intent);
        return intent;
    }

    private Intent getLoginWithout3gIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);
        intent.putExtra(LoginSdk.KEY_DLL, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_IS_DIRECT_LOGIN, "false");// zorunlu
        setIntentDefValues(intent);
        return intent;
    }


    private Intent getLsdkIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);

        intent.putExtra(LoginSdk.KEY_DLL, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_IS_DIRECT_LOGIN, "true");// zorunlu

        setIntentDefValues(intent);

        return intent;
    }

    private void setIntentDefValues(Intent intent) {
        intent.putExtra(LoginSdk.KEY_APP_ID, appId);// zorunlu
        intent.putExtra(LoginSdk.KEY_PAGE_BACKGROUND_COLOR, R.color.white);//Sayfan?n arka plan rengi icin
        intent.putExtra(LoginSdk.KEY_PAGE_TEXT_COLOR, R.color.lsdk_gray1);//Sayfadaki textlerin renkleri
        intent.putExtra(LoginSdk.KEY_HEADER_BACKGROUND_COLOR, R.color.lsdk_defaultHeaderColor);//Headerdaki background rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_BACKGROUND_COLOR, R.color.lsdk_defaultHeaderColor);//Headerdaki textin arka plan rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_COLOR, R.color.white);//Headerdaki textin rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TITLE, "Turkcell Platinum");//Headerdaki text
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_COLOR, R.color.lsdk_defaultSignInButtonColor);//Positif button renkleri
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_TEXT_COLOR, R.color.c_363E4F);//Positif button text renkleri
        intent.putExtra(LoginSdk.KEY_NEGATICE_BUTTON_COLOR, R.color.c_f4f5f8);//Negatif button renkleri
        intent.putExtra(LoginSdk.KEY_NEGATICE_BUTTON_TEXT_COLOR, R.color.c_363E4F);//Negatif button text renkleri
        intent.putExtra(LoginSdk.KEY_INPUT_BACKGROUND_COLOR, R.color.lsdk_gray_background);//Edittextler icin background rengi
        intent.putExtra(LoginSdk.KEY_ENV, ENV);//Default test
        //intent.putExtra(LoginSdk.KEY_FONT_PATH, "fonts/LDFComicSansBold.ttf");
        intent.putExtra(LoginSdk.KEY_EXIT_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.a);
        intent.putExtra(LoginSdk.KEY_BACK_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.b);
        intent.putExtra(LoginSdk.KEY_CHECKHED_CHECKBOX_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.custom_icon);
        intent.putExtra(LoginSdk.KEY_TURKCELL_LOGO_DRAWABLE, com.turkcell.loginsdktest.R.drawable.d);
        intent.putExtra(LoginSdk.KEY_SHOW_FREE_TEXT, "true");
        intent.putExtra(LoginSdk.KEY_FREE_TEXT, "asdas");
        intent.putExtra(LoginSdk.KEY_HINT_TEXT_COLOR, R.color.lsdk_yellow1);
        intent.putExtra(LoginSdk.KEY_CAPTCHA_BUTTON_DRAWABLE, R.drawable.lsdk_icon_refresh);
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
