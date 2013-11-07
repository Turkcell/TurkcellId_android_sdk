TurkcellId Android SDK
======================

Turkcell ID SDK provides an authentication mechanism for Android applications by using Turkcell user info.

## Sample Code

```java 
 package com.example.app;
 
  import tr.com.turkcell.turkcellid.TurkcellIdManager;
  import android.app.Activity;
  import android.os.Bundle;
  import android.widget.Toast;
 
 
  public class ExampleActivity extends Activity implements TurkcellIdManager.TurkcellIdListener {
 
         private TurkcellIdManager turkcellIdManager;
 
         @Override
         protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 turkcellIdManager = new TurkcellIdManager(this, YOUR_APPLICATION_ID);
                 turkcellIdManager.showLoginDialog(this, true);
         }
 
         @Override
         public void onTurkcellIdAuthorized(String authToken) {
                 Toast.makeText(this, "Authorized successfully, authToken: " + authToken, Toast.LENGTH_LONG).show();
         }
 
         @Override
         public void onTurkcellIdDialogDismissed() {
                 Toast.makeText(this, "dialog closed", Toast.LENGTH_SHORT).show();
         }
 
  }
```
