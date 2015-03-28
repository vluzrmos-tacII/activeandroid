package br.com.vluzrmos.activeandroid;

import android.app.Activity;
import android.widget.Toast;

abstract class BaseActivity extends Activity {
    public final int TOAST_LENGTH_LONG = Toast.LENGTH_LONG;
    public final int TOAST_LENGTH_SHORT = Toast.LENGTH_SHORT;

    public void showToast(String message, int time){
        Toast.makeText(this, message, time).show();
    }
}
