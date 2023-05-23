package com.example.usb_java_ui;

import android.view.View;

import java.util.ArrayList;

public class ObjectTree {

    private View currentView;
    private ObjectTree parentObject;
    private int indexOfCurrentObject;
    private int numOfChildObject;
    //    private ObjectTree[] childObject;
    private ArrayList<ObjectTree> childObjectList;

    public View getCurrentView() {
        return this.currentView;
    }

    public ObjectTree getParentObject() {
        return this.parentObject;
    }

    public int getIndexOfCurrentObject() {
        return this.indexOfCurrentObject;
    }

    public int getNumOfChildObject() {
        return this.numOfChildObject;
    }

    public ArrayList<ObjectTree> getChildObjectList() {
        return this.childObjectList;
    }

    public ObjectTree getChildObjectOfIndex(int index){
        if(childObjectList!=null){
            return this.childObjectList.get(index);
        }
        else {
            return null;
        }
    }

    public ObjectTree rootObject(){
        this.currentView = null;
        this.parentObject = null;
        this.indexOfCurrentObject = 0;
        this.numOfChildObject = 0;
        this.childObjectList = new ArrayList<>();
        return this;
    }

    public ObjectTree initObject(View newView){
        newView.setFocusableInTouchMode(true);
        this.currentView = newView;
        this.parentObject = null;
        this.indexOfCurrentObject = -1;
        this.numOfChildObject = 0;
        this.childObjectList = new ArrayList<>();
        return this;
    }

    public void addChild(ObjectTree newChildObject){
        newChildObject.parentObject = this;
        newChildObject.indexOfCurrentObject = this.numOfChildObject;
        this.numOfChildObject++;
        this.childObjectList.add(newChildObject);

    }

    public void addChildViewList(ArrayList<View> newChildViewList){
        int childLen = newChildViewList.size();
        for (int i = 0; i<childLen; i++){
            this.addChild(new ObjectTree().initObject(newChildViewList.get(i)));
        }
    }

    public void addChildObjectList(ArrayList<ObjectTree> newChildObjectList){
        int childLen = newChildObjectList.size();
        for (int i = 0; i<childLen; i++){
            this.addChild(newChildObjectList.get(i));
        }
    }
    public void addChildViewArr(View[] newChildViewArr){
        int childLen = newChildViewArr.length;
        for (View view : newChildViewArr) {
            this.addChild(new ObjectTree().initObject(view));
        }
    }

    public void addChildObjectArr(ObjectTree[] newChildObjectArr){
        int childLen = newChildObjectArr.length;
        for (ObjectTree objectTree : newChildObjectArr) {
            this.addChild(objectTree);
        }
    }

    public void deleteAllChildFocusable(){
        if(this.getNumOfChildObject()!=0){
            for (ObjectTree child : this.getChildObjectList()){
                child.deleteAllChildFocusable();
            }
        }
        if(this.getCurrentView() != null){
            this.getCurrentView().setFocusableInTouchMode(false);
        }
    }


}
