package com.example.usb_java_ui;

import static com.example.usb_java_ui.MyTouchEvent.DOUBLE_TAP;
import static com.example.usb_java_ui.MyTouchEvent.FLING_DOWN;
import static com.example.usb_java_ui.MyTouchEvent.FLING_LEFT;
import static com.example.usb_java_ui.MyTouchEvent.FLING_RIGHT;
import static com.example.usb_java_ui.MyTouchEvent.FLING_UP;
import static com.example.usb_java_ui.MyTouchEvent.LONG_PRESS;
import static com.example.usb_java_ui.MyTouchEvent.SINGLE_TAP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

public class Touchpad extends Dialog {
    int objIndex;
    private View[] objList;
    private MyTouchEvent myTouchEvent;
    private View v_touch;

    private ObjectTree root;
    private ObjectTree curObj;
    private ObjectTree toolRootObj;
    private ObjectTree tempObj;
    private boolean isToolbar;
    int x_val;

    @Override
    public void dismiss() {
        curObj.getCurrentView().clearFocus();
        super.dismiss();
    }

    @SuppressLint("MissingInflatedId")
    public Touchpad(@NonNull Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        super(context, android.R.style.Theme_Material_NoActionBar_Fullscreen);
        setContentView(R.layout.touchpad);
        if (context instanceof Activity){
            setOwnerActivity((Activity) context);
        }

        objIndex = 0;

        myTouchEvent = new MyTouchEvent();
        myTouchEvent.mDetector = new GestureDetectorCompat(getContext(),myTouchEvent);
        myTouchEvent.mDetector.setOnDoubleTapListener(myTouchEvent);
        ImageView imageView = findViewById(R.id.img_touch);
        imageView.setColorFilter(Color.parseColor("#55FF0000"));
        int offset = 100;
        x_val = 440;
//        imageView.setX(600);
//        imageView.setY(1160);
        isToolbar = false;
        v_touch = findViewById(R.id.v_touch);
        v_touch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!myTouchEvent.mDetector.onTouchEvent(event)){
                    return true;
                }
                int gesture_n = myTouchEvent.getGesture_n();
                switch(gesture_n){
                    case LONG_PRESS: // 앱 종료 버튼
                        break;
                    case SINGLE_TAP: // 음성 출력
                        Toast.makeText(context, "index : "+ curObj.getIndexOfCurrentObject()+" ChildNum : "+curObj.getNumOfChildObject(), Toast.LENGTH_SHORT).show();
                        break;
                    case DOUBLE_TAP: // Click or 자식 Object 접근
                        if(curObj.getNumOfChildObject()!=0){
                            curObj = curObj.getChildObjectOfIndex(0);
                        }
                        else {
                            curObj.getCurrentView().callOnClick();
                        }
                        break;
                    case FLING_UP: // 다음 Object
                        int next_index = curObj.getIndexOfCurrentObject()+1;
                        if(next_index < curObj.getParentObject().getNumOfChildObject()){
                            curObj = curObj.getParentObject().getChildObjectOfIndex(next_index);
                            if (isToolbar){
                                x_val = x_val+offset;
                                imageView.setX(x_val);
                                imageView.setY(115);
                            }
                        }
                        break;
                    case FLING_DOWN: // 이전 Object
                        int prev_index = curObj.getIndexOfCurrentObject()-1;
                        if(prev_index >= 0){
                            curObj = curObj.getParentObject().getChildObjectOfIndex(prev_index);
                            if (isToolbar){
                                x_val = x_val-offset;
                                imageView.setX(x_val);
                                imageView.setY(115);
                            }
                        }

                        break;
                    case FLING_LEFT: // ToolBar 접근
                        if (!isToolbar){
                            curObj.getCurrentView().clearFocus();
                            tempObj = curObj;
                            curObj = toolRootObj.getChildObjectOfIndex(0);
                            isToolbar = true;
                            x_val = 440;
                            imageView.setX(x_val);
//                            imageView.setX(540);
//                            imageView.setX(640);
                            imageView.setY(115);


                        } else {
                            curObj = tempObj;
                            imageView.setX(600);
                            imageView.setY(1160);
                            isToolbar = false;
                            curObj.getCurrentView().requestFocus();
                        }

                        break;
                    case FLING_RIGHT: // 이전 액티비티 or 부모 Object 접근
                        if (isToolbar){
                            curObj = tempObj;
                            imageView.setX(600);
                            imageView.setY(1160);
                            isToolbar = false;
                            curObj.getCurrentView().requestFocus();
                            break;
                        }
                        if(curObj.getParentObject().getCurrentView()!=null){
                            curObj = curObj.getParentObject();
                        } else {
                            onBackPressed();
                        }
                        break;
                }
//                curObj.getCurrentView().setFocusableInTouchMode(true);
                curObj.getCurrentView().requestFocus();

                return true;
            }
        });

    }

    public void setObjList(View[] objList) {
        this.objList = objList;
    }

    public View getObj() {
        return objList[objIndex];
    }

    public void setRoot(ObjectTree root) {
        this.root = root;
    }

    public void setCurObj(ObjectTree curObj) {
        this.curObj = curObj;
    }
    public void setToolRootObjCurObj(ObjectTree toolRootObj) {
        this.toolRootObj = toolRootObj;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        getOwnerActivity().onBackPressed();
    }
}