package com.jtao.gankdemo.Activity.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jtao.gankdemo.Activity.Adapter.CategoryAdapter;
import com.jtao.gankdemo.Activity.Adapter.FragmentAdapter;
import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.ItemSeparateLine;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.MainActivity;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends Fragment {

    private Context mContext;
    private String TAG = this.getClass().toString();
    private Unbinder unbinder;

    @BindView(R.id.category_tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.category_viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();

    public CategoryFragment() { }

    public static CategoryFragment newInstance(Context context) {
        CategoryFragment f = new CategoryFragment();
        f.mContext = context;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        unbinder = ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: ");

        initView();

        return view;
    }


    private void initView() {
        List<String>tabs = new ArrayList<>();
        tabs.add("全部");
        tabs.add("Android");
        tabs.add("iOS");
        tabs.add("App");
        tabs.add("前端");
        tabs.add("瞎推荐");
        tabs.add("拓展资源");
        tabs.add("休息视频");

        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();

        for (int i = 0; i < tabs.size(); i++) {
            CategorySubFragment fragment = CategorySubFragment.newInstance(mContext);
            fragment.category = tabs.get(i);
            fragments.add(fragment);
        }

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, fragments, tabs);

        viewPager.setAdapter(fragmentAdapter);

        mTabLayout.setupWithViewPager(viewPager);
    }















































    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        Log.d(TAG, "onDestroyView: ");
    }

}
