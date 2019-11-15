package com.bharatsync.utils;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatsync.permissions.Permission;
import com.bharatsync.permissions.RxPermissions;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RxPermissionsSample";

    private Camera camera;
    private SurfaceView surfaceView;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        findViewById(R.id.enableCamera).setOnClickListener(this);
        /*disposable = RxView.clicks(findViewById(R.id.enableCamera))
                // Ask for permissions when button is clicked
                .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) {
                                   Log.i(TAG, "Permission result " + permission);
                                   if (permission.granted) {
                                       releaseCamera();
                                       camera = Camera.open(0);
                                       try {
                                           camera.setPreviewDisplay(surfaceView.getHolder());
                                           camera.startPreview();
                                       } catch (IOException e) {
                                           Log.e(TAG, "Error while trying to display the camera preview", e);
                                       }
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // Denied permission without ask never again
                                       Toast.makeText(MainActivity.this,
                                               "Denied permission without ask never again",
                                               Toast.LENGTH_SHORT).show();
                                   } else {
                                       // Denied permission with ask never again
                                       // Need to go to the settings
                                       Toast.makeText(MainActivity.this,
                                               "Permission denied, can't enable the camera",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                                Log.e(TAG, "onError", t);
                            }
                        },
                        new Action() {
                            @Override
                            public void run() {
                                Log.i(TAG, "OnComplete");
                            }
                        });*/
    }
    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    @Override
    public void onClick(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);

       rxPermissions.requestEach(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) {
                                   Log.i(TAG, "Permission result " + permission);
                                   if (permission.granted) {
                                       releaseCamera();
                                       camera = Camera.open(0);
                                       try {
                                           camera.setPreviewDisplay(surfaceView.getHolder());
                                           camera.startPreview();
                                       } catch (IOException e) {
                                           Log.e(TAG, "Error while trying to display the camera preview", e);
                                       }
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // Denied permission without ask never again
                                       Toast.makeText(MainActivity.this,
                                               "Denied permission without ask never again",
                                               Toast.LENGTH_SHORT).show();
                                   } else {
                                       // Denied permission with ask never again
                                       // Need to go to the settings
                                       Toast.makeText(MainActivity.this,
                                               "Permission denied, can't enable the camera",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                                Log.e(TAG, "onError", t);
                            }
                        },
                        new Action() {
                            @Override
                            public void run() {
                                Log.i(TAG, "OnComplete");
                            }
                        });
    }
}
