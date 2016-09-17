package com.xiaoyu.myweibo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoyu.myweibo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {

    private static DiscoveryFragment mFragment;

    private DiscoveryFragment() {
        super();
    }

    public static DiscoveryFragment getInstance() {

        if (mFragment == null) {
            mFragment = new DiscoveryFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_discovery_frag, container, false);
    }

}
