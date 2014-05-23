package com.turkcell.sdktester;

import tr.com.turkcell.turkcellid.TurkcellIdManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TurkcellIdTestActivity extends FragmentActivity implements TurkcellIdManager.TurkcellIdListener {

	private TurkcellIdManager turkcellIdManager;
	private EditText appIdText;
	private CheckBox testServer;
	private CheckBox fullScreenCheckBox;
	private CheckBox hasSmsSupport;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout container = new LinearLayout(this);
		container.setOrientation(LinearLayout.VERTICAL);

		testServer = new CheckBox(this);
		testServer.setText("Test server");
		container.addView(testServer);

		appIdText =  new EditText(this);
		appIdText.setText("123"); //121212 - test
		container.addView(appIdText);

		fullScreenCheckBox = new CheckBox(this);
		fullScreenCheckBox.setText("Full screen");
		container.addView(fullScreenCheckBox);
		
		hasSmsSupport = new CheckBox(this);
		hasSmsSupport.setText("SMS support");
		container.addView(hasSmsSupport);

		Button autoLoginButton =  new Button(this);
		autoLoginButton.setText("Auto Login With Turkcell ID");
		autoLoginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				turkcellIdManager = new TurkcellIdManager(TurkcellIdTestActivity.this, appIdText.getText().toString(), testServer.isChecked());
				turkcellIdManager.showLoginDialog(TurkcellIdTestActivity.this, fullScreenCheckBox.isChecked());
			}
		});
		container.addView(autoLoginButton);
		
		Button noSmsSupportButton =  new Button(this);
		noSmsSupportButton.setText("Device has SMS support");
		noSmsSupportButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				turkcellIdManager = new TurkcellIdManager(TurkcellIdTestActivity.this, appIdText.getText().toString(), testServer.isChecked());
				turkcellIdManager.showLoginDialog(TurkcellIdTestActivity.this, fullScreenCheckBox.isChecked(), hasSmsSupport.isChecked());
			}
		});
		container.addView(noSmsSupportButton);

		Button logoutButton =  new Button(this);
		logoutButton.setText("Logout");
		logoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				turkcellIdManager = new TurkcellIdManager(TurkcellIdTestActivity.this, appIdText.getText().toString(), testServer.isChecked());
				turkcellIdManager.logout();
				Toast.makeText(TurkcellIdTestActivity.this, "logged out", Toast.LENGTH_SHORT).show();
			}
		});
		container.addView(logoutButton);

		Button changePasswordButton =  new Button(this);
		changePasswordButton.setText("Change password");
		changePasswordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				turkcellIdManager = new TurkcellIdManager(TurkcellIdTestActivity.this, appIdText.getText().toString(), testServer.isChecked());
				turkcellIdManager.showChangePasswordDialog(TurkcellIdTestActivity.this, fullScreenCheckBox.isChecked());
			}
		});
		container.addView(changePasswordButton);

		setContentView(container);
	}

	@Override
	public void onTurkcellIdAuthorized(String authToken) {
		Toast.makeText(this, "success, authToken: " + authToken, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onTurkcellIdDialogDismissed() {
		Toast.makeText(this, "dialog closed", Toast.LENGTH_SHORT).show();
	}

}
