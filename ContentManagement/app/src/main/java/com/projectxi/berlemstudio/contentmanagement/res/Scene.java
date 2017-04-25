package com.projectxi.berlemstudio.contentmanagement.res;

import java.io.Serializable;

/**
 * Created by patawat on 1/18/2017 AD.
 */

public class Scene implements Serializable{
    private String id;
    private String name;
    private String des;
    private String img_path;
    private String scene;
    private String tag;
    private String eng;
    public Scene(String name, String des, String img_path, String scene, String tag){
        this.id = "null";
        this.name = name;
        this.des = des;
        this.img_path = img_path;
        this.scene = scene;
        this.tag = tag;
    }
    public Scene(String id, String name, String des, String img_path, String scene, String tag){
        this.id = id;
        this.name = name;
        this.des = des;
        this.img_path = img_path;
        this.scene = scene;
        this.tag = tag;
    }
    public Scene(String id, String name, String des, String img_path, String scene, String tag, String eng){
        this.id = id;
        this.name = name;
        this.des = des;
        this.img_path = img_path;
        this.scene = scene;
        this.tag = tag;
        this.eng = eng;
    }
    public String getEng(){
        return this.eng;
    }
    public String getId(){
        return this.id;
    }
    public void setID(String id){
        this.id = id;
    }
    public String getName(){
        return this.name;
    }
    public String getDes(){
        return this.des;
    }
    public String getImg_path(){
        return this.img_path;
    }
    public String getScene(){ return this.scene;}
    public String getTag(){ return this.tag; }
    public void setName(String name){
        this.name = name;
    }
    public void setDes(String des){
        this.des = des;
    }
    public void setImg_path(String Img_path){
        this.img_path = Img_path;
    }

}
