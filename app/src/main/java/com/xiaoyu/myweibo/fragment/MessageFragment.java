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
public class MessageFragment extends Fragment {

    private static MessageFragment mFragment;

    private MessageFragment() {
        super();
    }

    public static MessageFragment getInstance() {

        if (mFragment == null) {
            mFragment = new MessageFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_message_frag, container, false);
    }

}
