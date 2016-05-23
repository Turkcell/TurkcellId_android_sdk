package com.turkcell.loginsdktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.turkcell.loginsdk.helper.LoginSdk;
import com.turkcell.loginsdk.service.callback.LoginSdkCallBack;
import com.turkcell.loginsdk.service.callback.LogoutResponse;

public class MainActivity extends Activity {

    private Button buttonLogout;
    private Button buttonLogin;
    private final static String APP_ID = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        login();

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSdk.logOut(APP_ID, LoginSdk.KEY_ENV_PROD, MainActivity.this, new LoginSdkCallBack<LogoutResponse>() {
                    @Override
                    public void onSuccess(LogoutResponse logoutResponse) {
                        Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(MainActivity.this, "Logout Fail", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void login() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);

        //logout call changed to LoginSdk.logout(context,LoginSdkCallback);
        //intent.putExtra(LoginSdk.KEY_IS_LOGOUT, "true");
        intent.putExtra(LoginSdk.KEY_FONT_PATH, "fonts/LDFComicSans.ttf");
        //intent.putExtra(LoginSdk.KEY_ENV, LoginSdk.KEY_ENV_PROD);//Default test
        intent.putExtra(LoginSdk.KEY_APP_ID, APP_ID);// zorunlu
        intent.putExtra(LoginSdk.KEY_DLL, "true");// zorunlu
        //loginforce change to ShowLoginPage
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_IS_DIRECT_LOGIN, "false");// direk wifi login sayfası açar
        intent.putExtra(LoginSdk.KEY_PAGE_BACKGROUND_COLOR, R.color.white);//Sayfan?n arka plan rengi icin
        intent.putExtra(LoginSdk.KEY_PAGE_TEXT_COLOR, R.color.lsdk_green1);//Sayfadaki textlerin renkleri
        intent.putExtra(LoginSdk.KEY_HEADER_BACKGROUND_COLOR, R.color.flatBlue);//Headerdaki background rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_BACKGROUND_COLOR, R.color.flatBlue);//Headerdaki textin arka plan rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_COLOR, R.color.white);//Headerdaki textin rengi
        //intent.putExtra(LoginSdk.KEY_HEADER_TITLE, "Turkcell Platinum");//Headerdaki text
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_COLOR, R.color.flatBlue);//Positif button renkleri
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_TEXT_COLOR, R.color.white);//Positif button text renkleri
        intent.putExtra(LoginSdk.KEY_NEGATICE_BUTTON_COLOR, R.color.lsdk_black);//Negatif button renkleri
        intent.putExtra(LoginSdk.KEY_NEGATICE_BUTTON_TEXT_COLOR, R.color.white);//Negatif button text renkleri
        intent.putExtra(LoginSdk.KEY_INPUT_BACKGROUND_COLOR, R.color.lsdk_gray_background);//Edittextler icin background rengi
        intent.putExtra(LoginSdk.KEY_SHOW_FREE_TEXT, "true");//Free Texti göster
        intent.putExtra(LoginSdk.KEY_FREE_TEXT, "bla blab blab bla bla bla");//free text alandaki text
        //intent.putExtra(LoginSdk.KEY_EXIT_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.a);
        //intent.putExtra(LoginSdk.KEY_BACK_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.b);
        //intent.putExtra(LoginSdk.KEY_CHECKHED_CHECKBOX_BUTTON_DRAWABLE, com.turkcell.loginsdktest.R.drawable.custom_icon);
        //intent.putExtra(LoginSdk.KEY_TURKCELL_LOGO_DRAWABLE, com.turkcell.loginsdktest.R.drawable.d);
        //intent.putExtra(LoginSdk.KEY_HIDE_PRELOADER, "true");//Preloader ı gizle
        intent.putExtra(LoginSdk.KEY_HINT_TEXT_COLOR, R.color.lsdk_yellow1);//Edittext hintlerin rengi
        //intent.putExtra(LoginSdk.KEY_CHECKHED_CHECKBOX_BUTTON_DRAWABLE, R.drawable.abc_ab_share_pack_mtrl_alpha);//Checkbox secili imagei
        //intent.putExtra(LoginSdk.KEY_UNCHECKHED_CHECKBOX_BUTTON_DRAWABLE, R.drawable.abc_btn_borderless_material);//Checkbox secili degil imageii

        //intent.putExtra(LoginSdk.KEY_CAPTCHA_BUTTON_DRAWABLE, R.drawable.lsdk_icon_refresh);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String resultMessage = data.getStringExtra(LoginSdk.KEY_AUTH_TOKEN) == null ? data.getStringExtra(LoginSdk.KEY_CODE) + "_" + data.getStringExtra(LoginSdk.KEY_MESSAGE) : data.getStringExtra(LoginSdk.KEY_AUTH_TOKEN);
        if (100 == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
