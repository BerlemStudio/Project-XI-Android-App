package com.projectxi.berlemstudio.contentmanagement.model;

import android.provider.BaseColumns;

/**
 * Created by patawat on 2/11/2017 AD.
 */

public class Story_db {
    private Story_db(){

    }

    public class column implements BaseColumns {
        public static final String TABLE_NAME = "table_name";
        public static final String STORY_NAME = "story_name";
        public static final String STORY_Description = "story_description";
        public static final String Created_by = "created_by";
        public static final String SCENE_LIST = "scene_list";
    }

}
