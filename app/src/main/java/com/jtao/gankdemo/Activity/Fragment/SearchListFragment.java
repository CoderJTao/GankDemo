package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtao.gankdemo.R;

public class SearchListFragment extends Fragment {
    private Context mContext;

    public SearchListFragment() {
    }

    public static SearchListFragment newInstance(Context context) {
        SearchListFragment f = new SearchListFragment();
        f.mContext = context;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container,false);

        return view;
    }
}
