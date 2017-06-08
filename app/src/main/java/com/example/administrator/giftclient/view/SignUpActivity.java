package com.example.administrator.giftclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.User;
import com.example.administrator.giftclient.support.ConnectServer;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 19/5/2017.
 */

public class SignUpActivity extends AppCompatActivity
        implements View.OnClickListener, ConnectServer.ConnectComplete {
    EditText userName, passWord, confirmPassWord, email;
    Button signUp;
    ConnectServer connectServer;
    TextView status, timeLeft;
    ProgressBar progressBar;
    private User user;
    private Gson gson;
    public static int RESULT_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userName = (EditText) findViewById(R.id.et_user_name);
        passWord = (EditText) findViewById(R.id.et_pass_word);
        confirmPassWord = (EditText) findViewById(R.id.et_confirm_pass_word);
        email = (EditText) findViewById(R.id.et_email);
        signUp = (Button) findViewById(R.id.bt_sign_up);
        status = (TextView) findViewById(R.id.tv_status);
        timeLeft = (TextView) findViewById(R.id.tv_time_left);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        signUp.setOnClickListener(this);
        connectServer = new ConnectServer(this, this);
        user = new User();
        gson = new Gson();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            sendBackData(RESULT_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sign_up:
                singUp();
                break;
        }
    }

    public void singUp() {
        user.setUserName(userName.getText().toString());
        user.setPassWord(passWord.getText().toString());
        String confirmPass = confirmPassWord.getText().toString();
        user.setEmail(email.getText().toString());
        if (!user.getUserName().isEmpty() && !user.getPassWord().isEmpty() && (user.getPassWord().equals(confirmPass))) {
            HashMap<String, String> map = new HashMap<>();
            map.put("username", user.getUserName());
            map.put("password", user.getPassWord());
            map.put("email", user.getEmail());
            connectServer.setPara(map);
            connectServer.setUrl(Config.SIGN_UP);
            connectServer.connect();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void response(String response) {
        progressBar.setVisibility(View.INVISIBLE);
        if (response.contains("sUserName")) {
            RESULT_CODE = 0;
            status.setText(getResources().getString(R.string.sign_up_success));
            user = gson.fromJson(response, User.class);
            status.setTextColor(getResources().getColor(R.color.editTextUserNameBorder));
            CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
                String seconds = getResources().getString(R.string.seconds);

                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished / 1000 - 1 <= 1)
                        seconds = getResources().getString(R.string.second);
                    timeLeft.setText(getResources().getString(R.string.go_page) +
                            " " + (millisUntilFinished / 1000 - 1) +
                            " " + seconds);
                }

                @Override
                public void onFinish() {
                    sendBackData(RESULT_CODE);
                }
            };
            countDownTimer.start();
        } else {
            RESULT_CODE = 1;
            status.setText(getResources().getString(R.string.sign_up_fail));
            status.setTextColor(getResources().getColor(R.color.fail_color));
        }
    }

    public void sendBackData(int code) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtra("data", bundle);
        setResult(code, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        sendBackData(RESULT_CODE);
        super.onBackPressed();

    }
}
