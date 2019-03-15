package hbuilder.android.permission;

/**
 * Created by huangbei on 19-3-15.
 */

public interface PermissionCallBack {

    public void granted();
    public void reject(String permission);
    public void deadReject(String permission);

}
