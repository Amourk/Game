package com.example.dtlp.tap_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dtlp.R;

/**
 * Created by cool on 2017-03-28.
 */
public class fragment_history extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){

        return  inflater.inflate(R.layout.fragment_history,group,false);
    }

}
