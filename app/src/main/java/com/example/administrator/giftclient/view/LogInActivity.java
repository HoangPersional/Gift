package com.example.administrator.giftclient.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.giftclient.MainActivity;
import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.User;
import com.example.administrator.giftclient.service.GetMessageBroadCast;
import com.example.administrator.giftclient.support.ConnectServer;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 17/5/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class LogInActivity extends AppCompatActivity
        implements View.OnClickListener, ConnectServer.ConnectComplete, PopupMenu.OnMenuItemClickListener {
    public static int REQUEST_SIGN_UP = 0;
    public static int REQUEST_OK = 0;
    public static int REQUEST_FAIL = 1;
    private EditText userName, passWord;
    private Button signIn;
    private ConnectServer connectServer;
    private TextView moreOption, status;
    private ProgressBar progressBar;
    private CheckBox remember;
    private User user;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        userName = (EditText) findViewById(R.id.et_user_name);
        passWord = (EditText) findViewById(R.id.et_pass_word);
        signIn = (Button) findViewById(R.id.bt_sign_in);
        moreOption = (TextView) findViewById(R.id.tv_more_option);
        status = (TextView) findViewById(R.id.tv_status);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        remember = (CheckBox) findViewById(R.id.cb);
        signIn.setOnClickListener(this);
        moreOption.setOnClickListener(this);
        connectServer = new ConnectServer(this, this);
        user = new User();
        gson = new Gson();
        init();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sign_in) {
            login();
        } else if (v.getId() == R.id.tv_more_option) {
            setMenu(v);
        }
    }

    @Override
    public void response(String response) {
        progressBar.setVisibility(View.INVISIBLE);

        if (response.contains("sUserName")) {
            user = gson.fromJson(response, User.class);
            storeData();
            finish();
            Intent intent = new Intent(this, CreateClientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);
            intent.putExtra("data", bundle);
            startActivity(intent);
        } else if (response.equals("false")) {
            status.setText(getResources().getString(R.string.wrong_pass));

        } else if (response.equals("fail")) {
            status.setText(getResources().getString(R.string.no_user) + " " + user.getUserName());
        }
    }

    protected void setMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.more_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGN_UP && resultCode == REQUEST_OK) {
            user = (User) data.getBundleExtra("data").getParcelable("user");
            userName.setText(user.getUserName());
            passWord.setText(user.getPassWord());
            CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    login();
                }
            }.start();

        }
    }

    public void login() {
        status.setText("");
        user.setUserName(userName.getText().toString());
        user.setPassWord(passWord.getText().toString());
        if (!user.getUserName().isEmpty() && !user.getPassWord().isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            connectServer.setUrl(Config.LOG_IN);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", user.getUserName());
            map.put("password", user.getPassWord());
            connectServer.setPara(map);
            connectServer.connect();
        }
    }

    private void storeData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("userName", user.getUserName());
        editor.putString("passWord", user.getPassWord());
        editor.putString("email", user.getEmail());
        editor.putBoolean("remember", remember.isChecked());
        editor.commit();

    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        user.setUserName(sharedPreferences.getString("userName", ""));
        user.setPassWord(sharedPreferences.getString("passWord", ""));
        remember.setChecked(sharedPreferences.getBoolean("remember", false));
        if (remember.isChecked()) {
            userName.setText(user.getUserName());
            passWord.setText(user.getPassWord());
        }
    }
    public void token()
    {
        String token=getSharedPreferences(Config.PREF,0).getString("token","");
        Log.v("HHC",token);
    }
}
