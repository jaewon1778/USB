package com.example.usb_java_ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MyTouchEvent extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    public static final int LONG_PRESS = 0;
    public static final int SINGLE_TAP = 1;
    public static final int DOUBLE_TAP = 2;
    public static final int FLING_UP = 3;
    public static final int FLING_DOWN = 4;
    public static final int FLING_LEFT = 5;
    public static final int FLING_RIGHT = 6;


    public GestureDetectorCompat mDetector;
    private int Gesture_n;


    public int getGesture_n(){
        return Gesture_n;
    }

    private void setGesture_n(int num){
        Gesture_n = num;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        setGesture_n(DOUBLE_TAP);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        setGesture_n(SINGLE_TAP);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        setGesture_n(LONG_PRESS);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float x, float y) {
        float abs_x = Math.abs(x);
        float abs_y = Math.abs(y);

        if(abs_x > abs_y){
            if (x>0){ //direction right
                setGesture_n(FLING_RIGHT);
            }
            else { //direction left
                setGesture_n(FLING_LEFT);
            }
        }
        else {
            if (y>0){ //direction down
                setGesture_n(FLING_DOWN);
            }
            else { //direction up
                setGesture_n(FLING_UP);
            }
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if(mDetector.onTouchEvent(event)){
            return true;
        }

        return super.onTouchEvent(event);

    }

}
