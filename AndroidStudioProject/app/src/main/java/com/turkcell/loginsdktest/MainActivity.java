package com.turkcell.loginsdktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private String appId = "201";
    private String ENV = LoginSdk.KEY_ENV_TEST;
    private LoginSdk.Language LANG = LoginSdk.Language.TR;

    // This will get the radiogroup
    private RadioGroup radioGroup;
    // This will get the radiobutton in the radiogroup that is checked
    private RadioButton checkedRadioButton;


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

        //#######Language Selection
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    Toast.makeText(getApplicationContext(), checkedRadioButton.getText() + "", Toast.LENGTH_SHORT).show();

                    if (checkedRadioButton.getText().toString().equalsIgnoreCase(LoginSdk.Language.TR.toString())) {
                        LANG = LoginSdk.Language.TR;
                    } else if (checkedRadioButton.getText().toString().equalsIgnoreCase(LoginSdk.Language.EN.toString())) {
                        LANG = LoginSdk.Language.EN;
                    } else if (checkedRadioButton.getText().toString().equalsIgnoreCase(LoginSdk.Language.UA.toString())) {
                        LANG = LoginSdk.Language.UA;
                    } else if (checkedRadioButton.getText().toString().equalsIgnoreCase(LoginSdk.Language.RU.toString())) {
                        LANG = LoginSdk.Language.RU;
                    }

                }
            }
        });
        //#########################

    }

    private Intent getLogin3gIntent() {
        Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);
        intent.putExtra("dll", "false");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "false");// zorunlu
        intent.putExtra("isDirectLogin", "false");// zorunlu
        //intent.putExtra(LoginSdk.KEY_SHOW_FREE_TEXT,"true");
        //intent.putExtra(LoginSdk.KEY_FREE_TEXT,"asdas");
        intent.putExtra(LoginSdk.KEY_TURKCELL_LOGO_DRAWABLE, R.drawable.icon_dialog_info);
        setIntentDefValues(intent);
        return intent;
    }

    private Intent getLoginWithout3gIntent() {
        final Intent intent = new Intent(this, com.turkcell.loginsdk.activity.LoginSDKMainActivity.class);
        intent.putExtra("dll", "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        //intent.putExtra(LoginSdk.KEY_TURKCELL_LOGO_DRAWABLE, R.drawable.homelogouttopiconsmall);
        intent.putExtra("isDirectLogin", "false");// zorunlu
        //intent.putExtra(LoginSdk.KEY_SHOW_FREE_TEXT,"true");
        //intent.putExtra(LoginSdk.KEY_FREE_TEXT,"asdas");


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

    private void setIntentDefValues(final Intent intent) {
        intent.putExtra("appId", appId);// zorunlu
        intent.putExtra(LoginSdk.KEY_SHOW_LOGIN_PAGE, "true");// zorunlu
        intent.putExtra(LoginSdk.KEY_PAGE_BACKGROUND_COLOR, R.color.lsdk_dark_blue);//Sayfan?n arka plan rengi icin
        intent.putExtra(LoginSdk.KEY_HEADER_BACKGROUND_COLOR, R.color.lsdk_dark_blue);//Headerdaki background rengi
        intent.putExtra(LoginSdk.KEY_HINT_TEXT_COLOR, R.color.lsdk_yellow2);
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_BACKGROUND_COLOR, R.color.lsdk_dark_blue);//Headerdaki textin arka plan rengi
        intent.putExtra(LoginSdk.KEY_HEADER_TEXT_COLOR, R.color.white);//Headerdaki textin rengi
        //intent.putExtra(LoginSdk.KEY_HEADER_TITLE, "Sign In Page");//Headerdaki text
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_COLOR, R.color.lsdk_dark_blue_2);//Pozitif button renkleri
        intent.putExtra(LoginSdk.KEY_POSITIVE_BUTTON_TEXT_COLOR, R.color.white);//Pozitif button text renkleri
        intent.putExtra(LoginSdk.KEY_NEGATIVE_BUTTON_COLOR, R.color.lsdk_button_yellow_background);//Negatif button renkleri
        intent.putExtra(LoginSdk.KEY_NEGATIVE_BUTTON_TEXT_COLOR, R.color.lsdk_black);//Negatif button text renkleri
        intent.putExtra(LoginSdk.KEY_CHECKHED_CHECKBOX_BUTTON_DRAWABLE, R.drawable.checkbox_active);
        intent.putExtra(LoginSdk.KEY_UNCHECKHED_CHECKBOX_BUTTON_DRAWABLE, R.drawable.checkbox_normal);
        intent.putExtra(LoginSdk.KEY_EXIT_BUTTON_DRAWABLE, R.drawable.icon_ustbar_close_new);
        intent.putExtra(LoginSdk.KEY_BACK_BUTTON_DRAWABLE, R.drawable.icon_ustbar_back);
        intent.putExtra(LoginSdk.KEY_PAGE_TEXT_COLOR, R.color.white);//Sayfadaki textlerin renkleri
        intent.putExtra(LoginSdk.KEY_FONT_PATH, "fonts/Turkcell_Satura_Demi.ttf");//Sayfadaki textler icin fontun path'i
        intent.putExtra(LoginSdk.KEY_INPUT_BACKGROUND_COLOR, R.color.lsdk_dark_blue_2);//Edittextler icin background rengi
        intent.putExtra(LoginSdk.KEY_ENV, ENV);//Default test
        intent.putExtra(LoginSdk.KEY_CAPTCHA_BUTTON_DRAWABLE, R.drawable.reload_icon);
        // intent.putExtra(LoginSdk.KEY_LANGUAGE, LoginSdk.Language.EN);
        // intent.putExtra(LoginSdk.KEY_DEFAULT_REGION_CODE, "+90");
        intent.putExtra(LoginSdk.KEY_LANGUAGE, LANG);


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