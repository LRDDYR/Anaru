package com.example.lrd.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lrd.R;
import com.example.lrd.adapter.BAdapter;
import com.example.lrd.bean.Bbean;
import com.example.lrd.call.MessageEvent;
import com.example.lrd.call.RequestBeanCallback;
import com.example.lrd.http.Url;
import com.example.lrd.ui.base.BaseActivity;
import com.example.lrd.ui.mvp.BRVAPresenterImpl;
import com.example.lrd.ui.mvp.BRVAView;
import com.example.lrd.utils.HttpManager;
import com.example.lrd.views.refresh_view.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created By LRD
 * on 2018/8/1  notes：BRVAH的使用
 */
public class BRVAHActivity extends BaseActivity implements BRVAView{
	@BindView(R.id.rv_brvah)
	RecyclerView mRecyclerView;
	@BindView(R.id.pull_to_refresh)
	PullToRefreshView mRefresh;
	private BRVAPresenterImpl mBrvaPresenter;

	@Override
	protected int getContentView() {
		return R.layout.brvah_layout;
	}

	@Override
	protected boolean isShowStatusBarHeight() {
		return false;
	}

	@Override
	public void init() {
		//注册事件
		EventBus.getDefault().register(this);

		mBrvaPresenter = new BRVAPresenterImpl(this, this);
		mBrvaPresenter.validate(mRefresh,mRecyclerView);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void Event(MessageEvent messageEvent) {
		switch (messageEvent.message) {
			case "Banner":
				mBrvaPresenter.setAutoPlay();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
