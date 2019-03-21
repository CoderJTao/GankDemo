package com.jtao.gankdemo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jtao.gankdemo.Activity.CustomView.CalendarItem;
import com.jtao.gankdemo.Activity.Fragment.CategoryFragment;
import com.jtao.gankdemo.Activity.Fragment.GirlFragment;
import com.jtao.gankdemo.Activity.Fragment.LikeFragment;
import com.jtao.gankdemo.Activity.Fragment.NewFragment;
import com.jtao.gankdemo.Activity.Util.DateUtil;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.canlendar_content)
    LinearLayout mCalendar;

    @BindView(R.id.contentview)
    ConstraintLayout mainContainer;

    @BindView(R.id.nav_bar)
    LinearLayout navBar;

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.nav_title)
    TextView title;

    @BindView(R.id.nav_item_right)
    ImageButton rightItem;

    @BindView(R.id.calendar_scrollView_content)
    LinearLayout calendarContent;

    private BottomNavigationView bottomNavigationView;
    private NewFragment newFragment;
    private CategoryFragment categoryFragment;
    private GirlFragment girlFragment;
    private LikeFragment likeFragment;
    private Fragment[] fragments;

    private List<String> datesList;
    private List<String> showDatesList;
    private List<CalendarItem> showCalendarItem;
    private boolean isInitFlag = false;

    private int lastSelectedFragment;  // 记录上次选择的fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        ImmersedStatusbarUtils.initAfterSetContentView(this, navBar);

        datesList = new ArrayList<>();
        showDatesList = new ArrayList<>();
        showCalendarItem = new ArrayList<>();
        initDateList();

        initView();
    }

    private void initView() {

        // init fragment
        newFragment = NewFragment.newInstance(this);
        categoryFragment = CategoryFragment.newInstance(this);
        girlFragment = GirlFragment.newInstance(this);
        likeFragment = LikeFragment.newInstance(this);
        fragments = new Fragment[]{newFragment, categoryFragment, girlFragment, likeFragment};
        lastSelectedFragment = 0;

        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, newFragment).show(newFragment).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.tabbar_content);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private void initDateList() {
        // 获取所有日期
        NetworkService.getNewsDates(new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();

                try {
                    JSONObject object = new JSONObject(jsonStr);

                    JSONArray lists = object.getJSONArray("results");

                    for (int i = 0; i < lists.length(); i++) {
                        if (i < 7) {
                            showDatesList.add(lists.getString(i));
                        }
                        datesList.add(lists.getString(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    @OnClick(R.id.nav_item_left)
    public void leftItemClick() {
        switch (lastSelectedFragment) {
            case 0:
                // 点击日历
                calendarViewClick();
                break;
            case 1:
                Toast.makeText(this, "点击了添加", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "点击了转换", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick(R.id.nav_item_right)
    public void rightItemClick() {
        Intent intent = new Intent(this, SearchActivity.class);

        startActivity(intent);
    }

    // 判断选择菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tabbar_new:
                    if (lastSelectedFragment != 0) {
                        switchFragment(lastSelectedFragment, 0);
                        lastSelectedFragment = 0;
                        leftItem.setVisibility(View.VISIBLE);
                        leftItem.setImageResource(R.mipmap.calendar);
                    }
                    return true;
                case R.id.tabbar_category:
                    if (lastSelectedFragment != 1) {
                        switchFragment(lastSelectedFragment, 1);
                        lastSelectedFragment = 1;
                        leftItem.setVisibility(View.INVISIBLE);
                    }
                    return true;
                case R.id.tabbar_girl:
                    if (lastSelectedFragment != 2) {
                        switchFragment(lastSelectedFragment, 2);
                        lastSelectedFragment = 2;
                        leftItem.setVisibility(View.VISIBLE);
                        leftItem.setImageResource(R.mipmap.transfer);
                    }
                    return true;
                case R.id.tabbar_like:
                    if (lastSelectedFragment != 3) {
                        switchFragment(lastSelectedFragment, 3);
                        lastSelectedFragment = 3;
                        leftItem.setVisibility(View.VISIBLE);
                        leftItem.setImageResource(R.mipmap.setting);
                    }
                    return true;
            }

            return false;
        }
    };

    // 切换fragment
    private void switchFragment(int lastSelected, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(fragments[lastSelected]);

        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.containerView, fragments[index]);
        } else  {
            transaction.show(fragments[index]);
        }

        transaction.commitAllowingStateLoss();
    }

    private void calendarViewClick() {
        if (!isInitFlag) {
            // 初始化 日期 视图
            for (int i = 0; i < showDatesList.size(); i++) {
                String dateTime = datesList.get(i);

                String test = "2019-01-21";
                final CalendarItem item = new CalendarItem(this);
                item.setData(dateTime, test);
                item.setListenr(this);
                calendarContent.addView(item);

                showCalendarItem.add(item);
            }

            //TODO: 添加最后一个 列表 图片
            ImageButton imgBtn = new ImageButton(this);
            imgBtn.setImageResource(R.mipmap.calendar_history);
            imgBtn.getBackground().setAlpha(0);

            calendarContent.addView(imgBtn);

            imgBtn.setOnClickListener(this);

            isInitFlag = true;
        }

        if (mCalendar.getVisibility() == View.GONE) {
            mCalendar.setVisibility(View.VISIBLE);
        } else {
            mCalendar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        // 日期 视图 item 点击
        if (v instanceof CalendarItem) {
            // 日期点击事件处理
            String clickDateStr = "";

            for (int i = 0; i < showCalendarItem.size(); i++) {
                CalendarItem item = showCalendarItem.get(i);
                item.setColor(((CalendarItem) v).dateStr);
                if (item.dateStr.equals(((CalendarItem) v).dateStr)) {
                    clickDateStr = item.dateStr;
                }
            }

            if (!clickDateStr.equals("")) {
                getNewsData(clickDateStr);
            }

        } else if (v instanceof ImageButton) {
            // 历史日期列表点击 -> 进入历史列表
            Intent intent = new Intent(this, HistoryActivity.class);

            String[] dates = new String[datesList.size()];
            for (int i = 0; i < datesList.size(); i++) {
                dates[i] = datesList.get(i);
            }
            intent.putExtra("dateList", dates);
            startActivity(intent);
        }
    }

    /**
     *  点击了日期，切换至当前日期的数据
     * @param selectDate
     */
    private void getNewsData(String selectDate) {

    }


    // TODO: 转场动画
    /**
     *
     *  覆盖即将到来的跳转动画
     *
     * @param enterAnim
     * @param exitAnim
     */
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }
}

/*
 API 相关
* class GankApi {
  /// gank api urls.
  static const String API_GANK_HOST = 'http://gank.io';
  static const String API_SPECIAL_DAY = "$API_GANK_HOST/api/day/";
  static const String API_DATA = "$API_GANK_HOST/api/data/";
  static const String API_SEARCH = "$API_GANK_HOST/api/search/query";
  static const String API_TODAY = "$API_GANK_HOST/api/today";
  static const String API_HISTORY = "$API_GANK_HOST/api/day/history";
  static const String API_HISTORY_CONTENT =
      "$API_GANK_HOST/api/history/content";
  static const String API_SUBMIT = "$API_GANK_HOST/api/add2gank";
  static const String CHECK_UPDATE = "$API_GANK_HOST/api/checkversion";

  ///获取最新一天的数据
  static getTodayData() async {
    HttpResponse response = await HttpManager.fetch(API_TODAY);
    return response.data;
  }

  ///获取指定日期的数据 [date:指定日期]
  static getSpecialDayData(String date) async {
    HttpResponse response =
        await HttpManager.fetch(API_SPECIAL_DAY + date.replaceAll("-", "/"));
    return response.data;
  }

  ///获取分类数据 [category:分类, page: 页数, count:每页的个数]
  static getCategoryData(String category, int page, {count = 20}) async {
    String url = API_DATA + category + "/$count/$page";
    HttpResponse response = await HttpManager.fetch(url);
    return response.data;
  }

  ///获取所有的历史干货日期.
  static getHistoryDateData() async {
    HttpResponse response = await HttpManager.fetch(API_HISTORY);
    return response.data['results'];
  }

  ///搜索[简易搜索，后面拆分页]
  static searchData(String search) async {
    HttpResponse response = await HttpManager.fetch(
        API_SEARCH + "/$search/category/all/count/50/page/1");
    return response.data['results'];
  }

  ///提交干货[url:干货地址,desc:干货描述,type:干货类型,debug:true为测试提交，false为正式提交
  static submitData(url, desc, who, type, {debug = false}) async {
    FormData formData = FormData.from({
      'url': url,
      'desc': desc,
      'who': who,
      'type': type,
      'debug': debug,
    });
    HttpResponse response =
        await HttpManager.fetch(API_SUBMIT, params: formData, method: 'post');
    return response.data;
  }

  ///获取所有的历史干货.
  static getHistoryContentData(int page, {count = 20}) async {
    HttpResponse response =
        await HttpManager.fetch(API_HISTORY_CONTENT + '/$count/$page');
    return response.data['results'];
  }
}

* */
