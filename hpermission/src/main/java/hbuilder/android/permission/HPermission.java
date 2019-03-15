package hbuilder.android.permission;

import android.content.Context;

/**
 * Created by huangbei on 19-3-15.
 */

public class HPermission {

    private PermissionCallBack callBack;
    private String[] permissions;
    private Context context;

    private HPermission(Context context){
        this.context = context;
    }

    public static HPermission with(Context context){
        return new HPermission(context);
    }

    public HPermission permissions(String[] permissions){
        this.permissions = permissions;
        return this;
    }

    public HPermission callBack(PermissionCallBack callBack){
        this.callBack = callBack;
        return this;
    }

    public void request(){
        if(permissions == null || permissions.length <= 0){
            return;
        }
        PermissionActivity.request(context,permissions,callBack);
    }

}
