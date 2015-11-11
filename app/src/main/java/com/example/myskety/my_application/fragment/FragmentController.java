package com.example.myskety.my_application.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentController {

	private static FragmentController fragmentController;
	private int containerID;
	private FragmentManager fragmentManager;
	private ArrayList<Fragment> fragments;
	
	private FragmentController(FragmentActivity activity, int containerID){
		this.fragmentManager = activity.getSupportFragmentManager();
		this.containerID = containerID;
		initFragment();
	}
	
	public static FragmentController getInstance(FragmentActivity activity, int containerID){
		if (fragmentController == null) {
			fragmentController = new FragmentController(activity, containerID);
		}
		return fragmentController;
	}
	
	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new Fragment_Home());
		fragments.add(new Fragment_Sort());
		fragments.add(new Fragment_Message());
		fragments.add(new Fragment_Mine());
		
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		for(Fragment fragment : fragments){
			fragmentTransaction.add(containerID, fragment);
		}
		fragmentTransaction.commit();
	}
	
	public void showFragment(int position){
		hideFragments();
		Fragment fragment = fragments.get(position);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.show(fragment);
		fragmentTransaction.commit();
	}
	
	private void hideFragments(){
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		for(Fragment fragment : fragments){
			if (fragment != null) {
				fragmentTransaction.hide(fragment);
			}
		}
		fragmentTransaction.commit();
	}
	
	public Fragment getFragment(int position){
		return fragments.get(position);
	}
}
