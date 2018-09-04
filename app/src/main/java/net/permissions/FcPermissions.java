package net.permissions;

/**
 * Created by 2016 on 2017/12/10.
 *
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FcPermissions {
    public static final int REQ_PER_CODE = 9527;
    private static final String TAG = "FcPermissions";

    public FcPermissions() {
    }

    private static boolean hasPermissions(Context context, String... perms) {
        if(VERSION.SDK_INT < 23) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        } else {
            String[] var2 = perms;
            int var3 = perms.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String perm = var2[var4];
                boolean hasPerm = ContextCompat.checkSelfPermission(context, perm) == 0;
                if(!hasPerm) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void requestPermissions(Object object, String rationale, int requestCode, String... perms) {
        requestPermissions(object, rationale, 17039370, 17039360, requestCode, perms);
    }

    public static void requestPermissions(final Object object, String rationale,  int positiveButton,  int negativeButton, final int requestCode, final String... perms) {
        checkCallingObjectSuitability(object);
        if(hasPermissions(getActivity(object), perms)) {
            if(object instanceof FcPermissionsCallbacks) {
                ((FcPermissionsCallbacks)object).onPermissionsGranted(requestCode, Arrays.asList(perms));
            }

        } else {
            boolean shouldShowRationale = false;
            String[] activity = perms;
            int dialog = perms.length;

            for(int var9 = 0; var9 < dialog; ++var9) {
                String perm = activity[var9];
                shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
            }

            if(shouldShowRationale) {
                Activity var11 = getActivity(object);
                if(null == var11) {
                    return;
                }

                AlertDialog var12 = (new Builder(var11))
                        .setMessage(rationale)
                        .setPositiveButton(positiveButton, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FcPermissions.executePermissionsRequest(object, perms, requestCode);
                            }
                        })
                        .setNegativeButton(negativeButton, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (object instanceof FcPermissionsCallbacks) {
                                    ((FcPermissionsCallbacks) object).onPermissionsDenied(requestCode, Arrays.asList(perms));
                                }
                            }
                        }).create();
                var12.setCanceledOnTouchOutside(false);
                var12.setCancelable(false);
                var12.show();
            } else {
                executePermissionsRequest(object, perms, requestCode);
            }

        }
    }

    private static void checkCallingObjectSuitability(Object object) {
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = VERSION.SDK_INT >= 23;
        if(!isSupportFragment && !isActivity && (!isAppFragment || !isMinSdkM)) {
            if(isAppFragment) {
                throw new IllegalArgumentException("Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        return object instanceof Activity?ActivityCompat.shouldShowRequestPermissionRationale((Activity)object, perm):(object instanceof Fragment?((Fragment)object).shouldShowRequestPermissionRationale(perm):(object instanceof android.app.Fragment && ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm)));
    }

    @TargetApi(23)
    private static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if(object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity)object, perms, requestCode);
        } else if(object instanceof Fragment) {
            ((Fragment)object).requestPermissions(perms, requestCode);
        } else if(object instanceof android.app.Fragment) {
            ((android.app.Fragment)object).requestPermissions(perms, requestCode);
        }

    }

    @TargetApi(11)
    private static Activity getActivity(Object object) {
        return object instanceof Activity?(Activity)object:(object instanceof Fragment?((Fragment)object).getActivity():(object instanceof android.app.Fragment?((android.app.Fragment)object).getActivity():null));
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object object) {
        checkCallingObjectSuitability(object);
        ArrayList granted = new ArrayList();
        ArrayList denied = new ArrayList();

        for(int i = 0; i < permissions.length; ++i) {
            String perm = permissions[i];
            if(grantResults[i] == 0) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        if(!granted.isEmpty() && object instanceof FcPermissionsCallbacks) {
            ((FcPermissionsCallbacks)object).onPermissionsGranted(requestCode, granted);
        }

        if(!denied.isEmpty() && object instanceof FcPermissionsCallbacks) {
            ((FcPermissionsCallbacks)object).onPermissionsDenied(requestCode, denied);
        }

    }

    @TargetApi(11)
    private static void startAppSettingsScreen(Object object, Intent intent) {
        if(object instanceof Activity) {
            ((Activity)object).startActivityForResult(intent, 9527);
        } else if(object instanceof Fragment) {
            ((Fragment)object).startActivityForResult(intent, 9527);
        } else if(object instanceof android.app.Fragment) {
            ((android.app.Fragment)object).startActivityForResult(intent, 9527);
        }

    }

    public static boolean checkDeniedPermissionsNeverAskAgain(Object object, String rationale, @StringRes int positiveButton, @StringRes int negativeButton, List<String> deniedPerms) {
        return checkDeniedPermissionsNeverAskAgain(object, rationale, positiveButton, negativeButton, null, deniedPerms);
    }

    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object, String rationale, @StringRes int positiveButton, @StringRes int negativeButton, @Nullable OnClickListener negativeButtonOnClickListener, List<String> deniedPerms) {
        Iterator var7 = deniedPerms.iterator();

        boolean shouldShowRationale;
        do {
            if(!var7.hasNext()) {
                return false;
            }

            String perm = (String)var7.next();
            shouldShowRationale = shouldShowRequestPermissionRationale(object, perm);
        } while(shouldShowRationale);

        final Activity activity = getActivity(object);
        if(null == activity) {
            return true;
        } else {
            AlertDialog dialog = (new Builder(activity))
                    .setMessage(rationale)
                    .setPositiveButton(positiveButton, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            FcPermissions.startAppSettingsScreen(object, intent);
                            activity.finish();
                        }
                    })
                    .setNegativeButton(negativeButton, negativeButtonOnClickListener)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            return true;
        }
    }
}
