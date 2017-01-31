package com.projectxi.berlemstudio.contentmanagement;

/**
 * Created by patawat on 1/25/2017 AD.
 */

public class sceneLabel {

//    private final static sceneLabel label = new sceneLabel("ประถม");
    private final static sceneLabel[] label = {new sceneLabel("ประถม"),new sceneLabel("มัธยมต้น"), new sceneLabel("มัธยมปลาย")};

    private String labelName;
    private String labelColor;
    public sceneLabel(){}
    public sceneLabel(String labelName){
        this.labelName = labelName;
    }

    public void setLabelName(String labelName){
        this.labelName = labelName;
    }

    public static  sceneLabel getInstance(int index){
        return label[index];
    }

    public void setLabelColor(String labelColor){
        this.labelColor = labelColor;
    }

    public String getLabelName(){
        return this.labelName;
    }

    public String getLabelColor(){
        return this.labelColor;
    }

}
