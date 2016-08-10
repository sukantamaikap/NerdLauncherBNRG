package com.bignerdranch.android.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smaikap on 10/8/16.
 */
public class NardLauncherFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public static NardLauncherFragment newInstance() {
        return new NardLauncherFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_nerd_launcher_recycler_view);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return view;
    }
}
