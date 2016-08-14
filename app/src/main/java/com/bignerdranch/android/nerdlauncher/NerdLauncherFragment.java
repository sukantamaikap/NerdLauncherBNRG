package com.bignerdranch.android.nerdlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by smaikap on 10/8/16.
 */
public class NerdLauncherFragment extends Fragment {
    private static final String TAG = "NerdLauncherFragment";
    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_nerd_launcher_recycler_view);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.setupAdapter();
        return view;
    }

    private void setupAdapter() {
        final Intent startUpIntent = new Intent(Intent.ACTION_MAIN);
        startUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager packageManager = this.getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startUpIntent, 0);
        Collections.sort(activities, new Comparator<ResolveInfo>() {

            @Override
            public int compare(ResolveInfo resolveInfo, ResolveInfo t1) {
                final PackageManager packageManager = NerdLauncherFragment.this.getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(resolveInfo.loadLabel(packageManager).toString(), resolveInfo.loadLabel(packageManager).toString());
            }
        });

        Log.i(TAG, "Found " + activities.size() + "activities");
        this.mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;

        public ActivityHolder(View itemView) {
            super(itemView);
            this.mNameTextView = (TextView) itemView;
            this.mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(final ResolveInfo resolveInfo) {
            this.mResolveInfo = resolveInfo;
            final PackageManager packageManager = NerdLauncherFragment.this.getActivity().getPackageManager();
            final String appName = this.mResolveInfo.loadLabel(packageManager).toString();
            this.mNameTextView.setText(appName);
        }

        @Override
        public void onClick(View view) {
            final ActivityInfo activityInfo = this.mResolveInfo.activityInfo;
            final Intent intent = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private final List<ResolveInfo> mActivities;

        private ActivityAdapter(final List<ResolveInfo> activities) {
            this.mActivities = activities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater layoutInflater = LayoutInflater.from(NerdLauncherFragment.this.getActivity());
            final View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(final ActivityHolder holder, final int position) {
            final ResolveInfo resolveInfo = this.mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return this.mActivities.size();
        }
    }
}
