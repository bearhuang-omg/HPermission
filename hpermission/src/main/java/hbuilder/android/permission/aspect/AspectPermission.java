package hbuilder.android.permission.aspect;

import android.content.Context;

import hbuilder.android.permission.PermissionActivity;
import hbuilder.android.permission.PermissionCallBack;

/**
 * Created by huangbei on 19-3-15.
 */

public class AspectPermission {

    private static AspectPermissionCallBack aspectCallBack;
    private String[] permissions;
    private Context context;
    private PermissionCallBack callback;

    public static void init(AspectPermissionCallBack callBack){
        aspectCallBack = callBack;
    }

    public static AspectPermissionCallBack aspectPermission(){
        return aspectCallBack;
    }

    public static AspectPermission with(Context context) {
        AspectPermission permisson = new AspectPermission(context);
        return permisson;
    }

    public AspectPermission(Context context){
        this.context = context;
    }

    public AspectPermission permissions(String[] permissions){
        this.permissions = permissions;
        return this;
    }

    public AspectPermission callBack(PermissionCallBack callback){
        this.callback = callback;
        return this;
    }

    public void request(){
        if (permissions == null || permissions.length <= 0) {
            return;
        }
        PermissionActivity.request(context, permissions, callback);
    }

    public interface AspectPermissionCallBack {
        public void reject(String permission);
        public void deadReject(String permission);
    }
}
