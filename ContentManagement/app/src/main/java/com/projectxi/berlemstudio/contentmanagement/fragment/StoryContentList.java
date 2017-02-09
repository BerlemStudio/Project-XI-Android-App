package com.projectxi.berlemstudio.contentmanagement.fragment;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projectxi.berlemstudio.contentmanagement.R;

/**
 * Created by patawat on 2/8/2017 AD.
 */

public class StoryContentList extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_story_contents_list, container, false);
    }
}
