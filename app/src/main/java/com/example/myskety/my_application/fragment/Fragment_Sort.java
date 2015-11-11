package com.example.myskety.my_application.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myskety.my_application.R;
import com.example.myskety.my_application.View.IconView;
import com.example.myskety.my_application.View.MyTextView;
import com.example.myskety.my_application.activity.Activity_AddProduct;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Sort extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<Fragment> mTabFragment;
    private View rootView;
    private Fragment_Sort_Life mFragmentLife;
    private Fragment_Sort_Book mFragmentBook;
    private Fragment_Sort_Study mFragmentStudy;
    private Fragment_Sort_Elect mFragmentElect;
    private Fragment_Sort_Other mFragmentOther;


    private MyTextView lifeMyTextView;
    private MyTextView bookMyTextView;
    private MyTextView studyMyTextView;
    private MyTextView electMyTextView;
    private MyTextView otherMyTextView;
    private ArrayList<MyTextView> myTextViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.fragment_sort, null);
        initViewPager();

        rootView.findViewById(R.id.tb_sort).findViewById(R.id.sort_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_AddProduct.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public void initViewPager(){
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabFragment.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mTabFragment.get(arg0);
            }
        };




        mTabFragment = new ArrayList<Fragment>();
        mFragmentLife = new Fragment_Sort_Life();
        mFragmentBook = new Fragment_Sort_Book();
        mFragmentStudy = new Fragment_Sort_Study();
        mFragmentElect = new Fragment_Sort_Elect();
        mFragmentOther = new Fragment_Sort_Other();


        mTabFragment.add(mFragmentLife);
        mTabFragment.add(mFragmentBook);
        mTabFragment.add(mFragmentStudy);
        mTabFragment.add(mFragmentElect);
        mTabFragment.add(mFragmentOther);


        myTextViews = new ArrayList<MyTextView>();

        lifeMyTextView = (MyTextView)rootView.findViewById(R.id.id_life);
        lifeMyTextView.setIconAlpha(1.0f);
        lifeMyTextView.setOnClickListener(this);
        bookMyTextView = (MyTextView)rootView.findViewById(R.id.id_book);
        bookMyTextView.setOnClickListener(this);
        studyMyTextView = (MyTextView)rootView.findViewById(R.id.id_study);
        studyMyTextView.setOnClickListener(this);
        electMyTextView = (MyTextView)rootView.findViewById(R.id.id_elect);
        electMyTextView.setOnClickListener(this);
        otherMyTextView = (MyTextView)rootView.findViewById(R.id.id_other);
        otherMyTextView.setOnClickListener(this);

        myTextViews.add(lifeMyTextView);
        myTextViews.add(bookMyTextView);
        myTextViews.add(studyMyTextView);
        myTextViews.add(electMyTextView);
        myTextViews.add(otherMyTextView);

        mViewPager = (ViewPager)rootView.findViewById(R.id.id_ViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset > 0){
            MyTextView left = myTextViews.get(position);
            MyTextView right = myTextViews.get(position + 1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void resetOtherTabs() {
        for(int i = 0; i < myTextViews.size(); i++){
            myTextViews.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_life:
                resetOtherTabs();
                myTextViews.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;

            case R.id.id_book:
                resetOtherTabs();
                myTextViews.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;

            case R.id.id_study:
                resetOtherTabs();
                myTextViews.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;

            case R.id.id_elect:
                resetOtherTabs();
                myTextViews.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.id_other:
                resetOtherTabs();
                myTextViews.get(4).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(4, false);
                break;

        }

    }
}
