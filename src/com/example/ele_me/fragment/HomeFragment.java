/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneContentFragment.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:44:01 
 * @version: V1.0   
 */
package com.example.ele_me.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ele_me.R;
import com.example.ele_me.activity.DianPingWebActivity;
import com.example.ele_me.activity.RestaurantDetailActivity;
import com.example.ele_me.adapter.HomePageRestaurantAdapter;
import com.example.ele_me.entity.RestaurantEntity;
import com.example.ele_me.util.RefreshableListView;
import com.example.ele_me.util.RefreshableListView.OnRefreshListener;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnClickListener {
	private View currentView;
	private LinearLayout openMenu;
	private RefreshableListView mListView;
	private HomePageRestaurantAdapter adapter;
	private List<RestaurantEntity> mlist;
	private int total = 21;
	private int step = 7;
	private int add = 7;
	private View listHeaderView;
	private ImageView head_pic;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_home_layout,
				container, false);
		mListView = (RefreshableListView) currentView
				.findViewById(R.id.mineList);
		openMenu = (LinearLayout) currentView
				.findViewById(R.id.linear_above_toHome);
		listHeaderView = getActivity().getLayoutInflater().inflate(
				R.layout.home_head_view, null);
		head_pic = (ImageView) listHeaderView
				.findViewById(R.id.iv_home_head);
		openMenu.setOnClickListener(this);
		getDate();
		setListener();
		return currentView;
	}

	public void setListener() {

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				new NewDataTask().execute();
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						RestaurantDetailActivity.class);
				intent.putExtra("name", mlist.get(position-1).getName());
				startActivity(intent);
			}
		});
		head_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						DianPingWebActivity.class);
				startActivity(intent);
			}
		});
	}

	private class NewDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			int current = mListView.getAdapter().getCount();
			if (current < total) {
				add += step;
				mListView.removeHeaderView(listHeaderView);
				getDate();
			}

			mListView.completeRefreshing();

			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.linear_above_toHome):
			openMenu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) getActivity()
							.findViewById(R.id.slidingpanellayout);
					if (slidingPaneLayout.isOpen()) {
						slidingPaneLayout.closePane();
					} else {
						slidingPaneLayout.openPane();
					}
				}
			});

			break;
		}
	}

	private void getDate() {

		mlist = new ArrayList<RestaurantEntity>();
		RestaurantEntity restaurant1 = new RestaurantEntity();
		restaurant1.setLogo("drawable://" + R.drawable.pic_jigongbao);
		restaurant1.setName("台中太陽餅(店)");
		restaurant1.setItem_msg("月售10萬盒 / 50 元 / 10片");
		restaurant1.setRate_numbers(1);
		restaurant1.setIs_rest(true);
		restaurant1.setPromotion("特賣 平均每份省2$");
		restaurant1.setIs_half(true);
		restaurant1.setIs_mins(true);
		mlist.add(restaurant1);
		restaurant1 = null;

		RestaurantEntity restaurant2 = new RestaurantEntity();
		restaurant2.setLogo("drawable://" + R.drawable.pic_jixiang);
		restaurant2.setName("台中雞腳凍(店)");
		restaurant2.setItem_msg("月售10萬包 / 120 元 / 10隻");
		restaurant2.setPromotion("【爆】購買加送點數");
		restaurant2.setIs_mins(true);
		restaurant2.setRate_numbers(2);
		mlist.add(restaurant2);
		restaurant2 = null;

		RestaurantEntity restaurant3 = new RestaurantEntity();
		restaurant3.setLogo("drawable://" + R.drawable.pic_milishi);
		restaurant3.setName("台中雞排禮盒(店)");
		restaurant3.setItem_msg("月售10萬箱 / 120 元 / 10片");
		restaurant3.setIs_favor(true);
		restaurant3.setRate_numbers(3);
		restaurant3.setPromotion("【新】赠500ml康师傅果汁！");
		restaurant3.setIs_half(true);
		mlist.add(restaurant3);
		restaurant3 = null;

		RestaurantEntity restaurant4 = new RestaurantEntity();
		restaurant4.setLogo("drawable://" + R.drawable.pic_shaxian);
		restaurant4.setName("大雅雞腿堡紀念皮包(店)");
		restaurant4.setItem_msg("月售500份 / 170 元 ");
		restaurant4.setIs_rest(true);
		restaurant4.setRate_numbers(4);
		restaurant4.setPromotion("【爆】購買加送點數及雞腿堡一份");
		restaurant4.setIs_mins(true);
		mlist.add(restaurant4);
		restaurant4 = null;

		RestaurantEntity restaurant5 = new RestaurantEntity();
		restaurant5.setLogo("drawable://" + R.drawable.pic_shiguifan);
		restaurant5.setName("大里茶葉罐(店)");
		restaurant5.setItem_msg("月售5000份 / 170 元  / 1kg");
		restaurant5.setIs_favor(true);
		restaurant5.setRate_numbers(5);
		restaurant5.setIs_mins(true);
		mlist.add(restaurant5);
		restaurant5 = null;

		RestaurantEntity restaurant6 = new RestaurantEntity();
		restaurant6.setLogo("drawable://" + R.drawable.pic_tengqi);
		restaurant6.setName("霧峰薯條組");
		restaurant6.setItem_msg("月售9000份 / 190 元  / 1kg");
		restaurant6.setPromotion("【爆】網友推薦第一名");
		restaurant6.setRate_numbers(6);
		restaurant6.setIs_mins(true);
		mlist.add(restaurant6);
		restaurant6 = null;

		RestaurantEntity restaurant7 = new RestaurantEntity();
		restaurant7.setLogo("drawable://" + R.drawable.pic_xiaohongmao);
		restaurant7.setName("尖石木瓜牛奶教學組(店)");
		restaurant7.setItem_msg("月售10萬份 / 1900 元  / 木瓜、牛奶、砂糖各1kg");
		restaurant7.setRate_numbers(7);
		restaurant7.setPromotion("【新】身體調理自己來");
		restaurant7.setIs_mins(true);
		mlist.add(restaurant7);
		restaurant7 = null;

		adapter = new HomePageRestaurantAdapter(getActivity(), mlist);
		mListView.setAdapter(adapter);
		mListView.addHeaderView(listHeaderView);
	}

}
