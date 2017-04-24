package com.projectxi.berlemstudio.contentmanagement.res;

/**
 * Created by patawat on 4/23/2017 AD.
 */

public class Scene_unlock extends Scene {
    private boolean unlock;
    public Scene_unlock(String id, String name, String des, String img_path, String scene, String tag,boolean unlock){
        super(id, name,des,img_path,scene,tag);
        this.unlock = unlock;
    }
    public boolean getUnlock(){
        return unlock;
    }
    public void setUnlock(boolean unlock){
        this.unlock = unlock;
    }
}
