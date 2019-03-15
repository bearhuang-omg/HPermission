package hbuilder.android.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by huangbei on 19-3-15.
 */

public class PermissionActivity extends Activity {

    private static final String PERMISSIONS = "permissions";
    private static final int REQUEST_CODE = 200;
    private static PermissionCallBack permissionCallBack;


    public static void request(Context context, String[] permissions, PermissionCallBack callback) {
        permissionCallBack = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PERMISSIONS, permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(!intent.hasExtra(PERMISSIONS)){
            return;
        }
        String[] permissions = intent.getStringArrayExtra(PERMISSIONS);
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(permissions,REQUEST_CODE);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != REQUEST_CODE){
            return;
        }
        int length = permissions.length;
        boolean[] showAgain = new boolean[length];
        for(int i=0;i<length;i++){
            showAgain[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }
        int granted = 0;
        for(int i=0;i<length;i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                if(showAgain[i] == true){
                    permissionCallBack.reject(permissions[i]);
                }else{
                    permissionCallBack.deadReject(permissions[i]);
                }
            }else{
                granted++;
            }
        }
        if(granted == length){
            permissionCallBack.granted();
        }
        finish();
    }
}
