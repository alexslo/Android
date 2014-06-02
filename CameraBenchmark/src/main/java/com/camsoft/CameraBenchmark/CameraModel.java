package com.camsoft.CameraBenchmark;
/**
 * Created by alex on 23.01.14.
 */
import android.util.Log;
import android.hardware.Camera;
public class CameraModel {


    /**
     *  1 - BACK Camera will be used
     *  2 - FRONT Camera will be used
     */
    public String getAllCameraParams(int CurrentCamera ) {

        if (CurrentCamera > Camera.getNumberOfCameras())
        {
            CurrentCamera = -1;
            return "Devices not have this cameras number";
        }

        if (CurrentCamera ==1)
        {
            CurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        else if(CurrentCamera ==2)
        {
            CurrentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else
        {
            CurrentCamera = -1;
            return "Not Current Camera.CameraInfo Number ";
        }
        int cameraCount = 0;
        Camera mCamera = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for ( int camIdx = 0; camIdx < Camera.getNumberOfCameras(); camIdx++ ) {
            Camera.getCameraInfo( camIdx, cameraInfo );
            if ( cameraInfo.facing == CurrentCamera  ) {
                try {
                    mCamera = Camera.open( camIdx );
                } catch (RuntimeException e) {
                    //Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                    return "Camera.open error: " + e.getLocalizedMessage();
                }
            }
        }

        Camera.Parameters CamParams = mCamera.getParameters();
        String AllCamParams= CamParams.flatten();
        mCamera.release();
        return AllCamParams;

    }

}

