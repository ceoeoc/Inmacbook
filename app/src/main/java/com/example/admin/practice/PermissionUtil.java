package com.example.admin.practice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    public static int checkSelfPermission(@NonNull Context context, @NonNull String permission){
        if(permission == null){
            throw new IllegalArgumentException("permission is null");
        }

        return context.checkPermission(permission,android.os.Process.myPid(), android.os.Process.myUid());
    }

    public static boolean checkPermissions(Activity activity, String permissions[]){
        for(int i = 0 ; i < permissions.length;i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(final @NonNull Activity activity, final @NonNull String[] permissions, final int requestCode){
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(activity,permissions,requestCode);
        }else if(activity instanceof ActivityCompat.OnRequestPermissionsResultCallback){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];

                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();

                    final int permissionCount = permissions.length;
                    for(int i = 0 ; i < permissionCount; i++){
                        grantResults[i] = packageManager.checkPermission(permissions[i],packageName);
                    }

                    ((ActivityCompat.OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(
                            requestCode,permissions,grantResults);
                }
            });
        }
    }
    public static boolean verifyPermission(int[] grantresults){
        if(grantresults.length< 1){
            return false;
        }
        for(int result : grantresults){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public static void requestAllPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.READ_CALL_LOG},1);
    }
}
