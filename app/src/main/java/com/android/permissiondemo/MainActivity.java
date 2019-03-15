package com.android.permissiondemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hbuilder.android.permission.HPermission;
import hbuilder.android.permission.PermissionCallBack;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                request();
            }
        });
    }

    private void request(){
        HPermission.with(this).permissions(new String[]{Manifest.permission.CAMERA}).callBack(new PermissionCallBack() {
            @Override
            public void granted() {
                Toast.makeText(MainActivity.this,"授权了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void reject(String permission) {
                Toast.makeText(MainActivity.this,"拒绝了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deadReject(String permission) {
                Toast.makeText(MainActivity.this,"死命拒绝了",Toast.LENGTH_SHORT).show();
            }
        }).request();
    }
}
