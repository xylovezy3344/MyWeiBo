package com.xiaoyu.myweibo.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyu.myweibo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends Fragment {

    private static MyselfFragment mFragment;

    private MyselfFragment() {
        super();
    }

    public static MyselfFragment getInstance() {

        if (mFragment == null) {
            mFragment = new MyselfFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.myself_frag, container, false);
    }

}
