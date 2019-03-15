package com.android.permissiondemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hbuilder.android.permission.HPermission;
import hbuilder.android.permission.PermissionCallBack;
import hbuilder.android.permission.aspect.AspectPermission;
import hbuilder.android.permission.aspect.Permission;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AspectPermission.init(new AspectPermission.AspectPermissionCallBack() {
            @Override
            public void reject(String permission) {
                Toast.makeText(MainActivity.this,permission+"拒绝了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deadReject(String permission) {
                Toast.makeText(MainActivity.this,permission+"死命拒绝了",Toast.LENGTH_SHORT).show();
            }
        });

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                aspectRequest();
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

    @Permission(permissions = {Manifest.permission.CAMERA})
    private void aspectRequest(){
        Toast.makeText(MainActivity.this,"申请到了",Toast.LENGTH_SHORT).show();
    }
}
