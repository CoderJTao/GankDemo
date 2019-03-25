package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

public class CategorySubFragment extends Fragment {
    private Context mContext;

    public CategorySubFragment() { }

    public static CategorySubFragment newInstance(Context context) {
        CategorySubFragment f = new CategorySubFragment();
        f.mContext = context;
        return f;
    }

}
