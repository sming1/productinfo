package com.yiyinmei.store;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yiyinmei.store.data.MySqliteDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private FrameLayout mContent;
    ProductFragment mProductFragment;
    BuyFragment mBuyFragment;
    SaleFragment mSaleFragment;
    CountFragment mCountFragment;
    FragmentManager mFragmentManager;
    private TextView mProduct, mBuy, mSale, mCount;
    private Map<View, Fragment> fragmentMap = new HashMap<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = findViewById(R.id.content);
        mFragmentManager = getFragmentManager();
        mProductFragment = new ProductFragment();
        mBuyFragment = new BuyFragment();
        mSaleFragment = new SaleFragment();
        mCountFragment = new CountFragment();
        mFragmentManager.beginTransaction().add(R.id.content, mProductFragment).
                add(R.id.content, mBuyFragment).
                add(R.id.content, mSaleFragment).
                add(R.id.content, mCountFragment).
                commitAllowingStateLoss();
        initView();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        mProduct = findViewById(R.id.product);
        mBuy = findViewById(R.id.buy);
        mSale = findViewById(R.id.sale);
        mCount = findViewById(R.id.count);

        mProduct.setOnClickListener(this);
        mBuy.setOnClickListener(this);
        mSale.setOnClickListener(this);
        mCount.setOnClickListener(this);

        fragmentMap.put(mProduct, mProductFragment);
        fragmentMap.put(mBuy, mBuyFragment);
        fragmentMap.put(mSale, mSaleFragment);
        fragmentMap.put(mCount, mCountFragment);
        setTextColor(mProduct);
        hideAll();
        showFragment(mProductFragment);
    }

    private void hideAll() {
        mFragmentManager.beginTransaction().hide(mProductFragment).hide(mBuyFragment).hide(mSaleFragment).hide(mCountFragment).commitAllowingStateLoss();
    }

    private void showFragment(Fragment fragment) {
        hideAll();
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        setTextColor((TextView) view);
        if (view == mProduct) {
            showFragment(mProductFragment);
            return;
        }
        if (view == mBuy) {
            showFragment(mBuyFragment);
            return;
        }
        if (view == mSale) {
            showFragment(mSaleFragment);
            return;
        }
        if (view == mCount) {
            showFragment(mCountFragment);
            return;
        }
    }

    private void setTextColor (TextView view) {
        mProduct.setTextColor(Color.BLACK);
        mBuy.setTextColor(Color.BLACK);
        mSale.setTextColor(Color.BLACK);
        mCount.setTextColor(Color.BLACK);
        view.setTextColor(Color.GREEN);
    }
}