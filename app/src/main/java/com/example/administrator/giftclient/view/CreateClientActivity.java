package com.example.administrator.giftclient.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.Client;
import com.example.administrator.giftclient.model.PushClient;
import com.example.administrator.giftclient.model.User;
import com.example.administrator.giftclient.presenter.CreateClientPresenter;
import com.example.administrator.giftclient.service.CameraService;

/**
 * Created by Administrator on 30/5/2017.
 */

public class CreateClientActivity extends AppCompatActivity implements CreateClientInterface, View.OnClickListener {
    private CreateClientPresenter createClientPresenter;
    private PushClient pushClient;
    private EditText name, des;
    private Button create;
    private User user;
    private TextView stt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
        name = (EditText) findViewById(R.id.et_client_name);
        des = (EditText) findViewById(R.id.et_descripton);
        create = (Button) findViewById(R.id.bt_add_client);
        stt = (TextView) findViewById(R.id.tv_status);
        create.setOnClickListener(this);
        user = getIntent().getBundleExtra("data").getParcelable("user");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void response(String res) {
        if (res.contains("iIdUserName"))
            stt.setText(getString(R.string.success));
        else if (res.equals("update"))
            stt.setText(getString(R.string.update_success));
    }

    @Override
    public void onClick(View v) {
        Client client = new Client();
        client.setIdUser(user.getId());
        client.setName(name.getText().toString());
        client.setDescription(des.getText().toString());
        client.setToken(getToken());
        pushClient = new PushClient(this, client);
        createClientPresenter = new CreateClientPresenter(this, pushClient);
        createClientPresenter.connect();
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        return sharedPreferences.getString("token", "");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i=new Intent(this, CameraService.class);
        startService(i);
    }
}
