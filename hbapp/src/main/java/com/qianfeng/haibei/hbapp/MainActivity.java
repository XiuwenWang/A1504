package com.qianfeng.haibei.hbapp;





import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yangjw.minelibrary.http.HttpUtils;
import com.example.yangjw.minelibrary.http.RequestCallBack;
import com.qianfeng.haibei.hbapp.fragment.CategoryFragment;
import com.qianfeng.haibei.hbapp.fragment.DiscoverFragment;
import com.qianfeng.haibei.hbapp.fragment.HomeFragment;
import com.qianfeng.haibei.hbapp.fragment.SettingsFragment;
import com.qianfeng.haibei.hbapp.fragment.ddd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    private Fragment myFragment;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioGroup rg;
    private List<Fragment> fragmentList;
    private int lastIndex;
    private String listResult;
    private boolean listFlag;
    private String bannerResult;
    private boolean bannerFlag;
    private String discovuerResult;
    private boolean discoverFlag;
    private String categoryResult;
    private boolean categoryFlag;
    private Fragment myFragment2;

    private RequestCallBack callBack = new RequestCallBack() {
        @Override
        public void onSuccess(String result, int requestCode) {
            switch (requestCode) {
                case 1:
                    listResult = result;
                    listFlag = true;
                    break;
                case 2:
                    bannerResult = result;
                    bannerFlag = true;
                    break;
                case 3:
                    discovuerResult = result;
                    discoverFlag = true;
                    break;
                case 4:
                    categoryResult = result;
                    categoryFlag = true;
                    break;
            }
        }

        @Override
        public void onFailure(String error) {

        }

        @Override
        public void error(Exception ex) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求网络
        getHttp();
        //初始化视图
        initView();
        //添加fragment到list
        addFragment();
        //选择fragment显示//设置默认显示
//        if(listFlag&&bannerFlag&&categoryFlag&&discoverFlag){
            selectFragment(0, true);
//        }

        rb1.setChecked(true);

        //设置RadioGroup的监听
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1_home_activity_main:
                        selectFragment(0, true);
                        break;
                    case R.id.rb2_discover_activity_main:
                        selectFragment(1, true);
                        break;
                    case R.id.rb3_category_activity_main:
                        selectFragment(2, true);
                        break;
                    case R.id.rb4_orders_activity_main:
                        selectFragment(3, true);
                        break;
                }
            }
        });
    }

    private void getHttp() {
        //请求主界面GridView数据
        HttpUtils.requestGet("http://apicn.seashellmall.com/product/list/?size=20&p=1", callBack, 1);
        //请求主界面Viewpager数据
        HttpUtils.requestGet("http://apicn.seashellmall.com/banner/index", callBack, 2);
        //请求发现数据
        HttpUtils.requestGet("http://apicn.seashellmall.com/product/topics", callBack, 3);
        //请求分类数据
        HttpUtils.requestGet("http://apicn.seashellmall.com/category", callBack, 4);
    }

    private void addFragment() {
        manager = getFragmentManager();
        fragmentList = new ArrayList<Fragment>();
//        if(listFlag&&bannerFlag&&categoryFlag&&discoverFlag){
//            fragmentList.add(HomeFragment.newInstance(listResult, bannerResult));
            fragmentList.add(new HomeFragment());
            fragmentList.add(new ddd());
            fragmentList.add(new CategoryFragment());
            fragmentList.add(new SettingsFragment());
//        }
    }

    private void selectFragment(int index,boolean isShow) {

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        myFragment = fragmentList.get(index);
        myFragment2 = fragmentList.get(lastIndex);
        if(isShow){
            fragmentTransaction.hide(myFragment2);
        }if(myFragment.isAdded()){
            fragmentTransaction.show(myFragment);
        }else {
            fragmentTransaction.add(R.id.fl_mian,myFragment);
            fragmentTransaction.show(myFragment);
        }
        fragmentTransaction.commit();
        lastIndex = index;
    }

    private void initView() {
        rg = (RadioGroup)findViewById(R.id.rg_bottom_activity_main);
        rb1 = (RadioButton)findViewById(R.id.rb1_home_activity_main);
        rb2 = (RadioButton)findViewById(R.id.rb2_discover_activity_main);
        rb3 = (RadioButton)findViewById(R.id.rb3_category_activity_main);
        rb4 = (RadioButton)findViewById(R.id.rb4_orders_activity_main);
    }
}
