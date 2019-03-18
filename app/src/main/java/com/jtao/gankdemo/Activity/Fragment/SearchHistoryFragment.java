package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jtao.gankdemo.Activity.Database.HistoryDao;
import com.jtao.gankdemo.Activity.Database.OpenHelper_history;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchHistoryFragment extends Fragment {

    private Context mContext;

    private List<String> historyList;


    public SearchHistoryFragment() {
    }

    public static SearchHistoryFragment newInstance(Context context) {
        SearchHistoryFragment f = new SearchHistoryFragment();
        f.mContext = context;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        return view;
    }

    public void setHistoryList(List<String> list) {
        this.historyList = list;
    }
}
