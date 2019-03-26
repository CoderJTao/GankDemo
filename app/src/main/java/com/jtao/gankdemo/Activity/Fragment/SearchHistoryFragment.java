package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jtao.gankdemo.Activity.CustomView.FlowLayout;
import com.jtao.gankdemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchHistoryFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private Unbinder unbinder;

    private List<String> historyList = new ArrayList<>();

    @BindView(R.id.flow_layout)
    FlowLayout mFlowLayout;

    private WeakReference<HistoryFragmentListener> listener;

    public interface HistoryFragmentListener {
        void historyItemClick(String history);
    }

    public SearchHistoryFragment() {
    }

    public static SearchHistoryFragment newInstance(Context context) {
        SearchHistoryFragment f = new SearchHistoryFragment();
        f.mContext = context;
        return f;
    }

    public void setListener(HistoryFragmentListener listener) {
        this.listener = new WeakReference<HistoryFragmentListener>(listener);;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        initData();
        mFlowLayout.setItemClickListener(this);
        mFlowLayout.setAdapter(historyList, R.layout.item_search_history, new FlowLayout.ItemView<String>() {
            @Override
            public void getCover(String item, FlowLayout.ViewHolder holder, View inflat, int position) {
                holder.setText(R.id.tv_label_name, item);
            }
        });
    }

    /**
     *  处理历史搜索记录的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
         String tag = (String) v.getTag();
         if (tag.equals("history_item")) {
             TextView view = (TextView) v;

             String searchText = view.getText().toString();
             if (listener != null) {
                 listener.get().historyItemClick(searchText);
             }
         }
    }


    private void initData() {
        if (historyList.size() > 0) {
            historyList.removeAll(historyList);
        }

        // 创建一个SharedPreferences接口对象
        SharedPreferences read = mContext.getSharedPreferences("search_history", Context.MODE_PRIVATE);

        // 获取文件中的值
        HashSet<String> sets = (HashSet<String>) read.getStringSet("histories", new HashSet<String>());

        historyList.addAll(sets);

        if (historyList.size() == 0) {
            historyList.add("Android");
            historyList.add("iOS");
            historyList.add("App");
            historyList.add("前端");
            historyList.add("瞎推荐");
            historyList.add("拓展资源");
            historyList.add("休息视频");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
