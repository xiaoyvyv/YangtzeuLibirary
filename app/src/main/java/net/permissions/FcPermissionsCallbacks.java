package net.permissions;

/**
 * Created by 2016 on 2017/12/10.
 */
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import java.util.List;

public interface FcPermissionsCallbacks extends OnRequestPermissionsResultCallback {
    void onPermissionsGranted(int var1, List<String> var2);

    void onPermissionsDenied(int var1, List<String> var2);
}
