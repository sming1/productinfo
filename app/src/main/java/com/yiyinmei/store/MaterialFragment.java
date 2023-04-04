package com.yiyinmei.store;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yiyinmei.store.beans.ProductInfo;
import com.yiyinmei.store.data.DataBaseUtils;
import com.yiyinmei.store.data.MySqliteDatabase;
import com.yiyinmei.store.data.ProductRecycleBaseAdaper;

import java.util.ArrayList;

public class MaterialFragment extends Fragment implements ProductRecycleBaseAdaper.OnItemClickListener, View.OnClickListener{

    private ArrayList<ProductInfo> mMaterialInfoList = new ArrayList<>(20);
    private RecyclerView recycleView;
    ProductRecycleBaseAdaper adaper;
    private int mPosition;
    private Button mAddProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMaterialInfoList = queryAllProduct();
        adaper = new ProductRecycleBaseAdaper(mMaterialInfoList, this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_material,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.productrv);
        mAddProduct = view.findViewById(R.id.addProduct);
        mAddProduct.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        recycleView.setAdapter(adaper);
        adaper.notifyDataSetChanged();
        adaper.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private ArrayList<ProductInfo> queryAllProduct() {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        Cursor cursor = database.getReadableDatabase().query("material", null, null, null, null, null, null);
        if (cursor != null) {
            mMaterialInfoList.clear();
            while (cursor.moveToNext()) {
                int _id = cursor.getColumnIndex("_id");
                int name = cursor.getColumnIndex("name");
                int store = cursor.getColumnIndex("store");
                int event = cursor.getColumnIndex("event");

                int infoId = cursor.getInt(_id);
                String infoName = cursor.getString(name);
                int infoStore = cursor.getInt(store);
                String infoEvent = cursor.getString(event);

                Log.e("sunming", "_id " + infoId + " name " + infoName + " store " + infoStore);
                ProductInfo productInfo = new ProductInfo(infoId, infoName, infoStore, infoEvent);
                mMaterialInfoList.add(productInfo);
            }
        }
        if (adaper !=null){
            adaper.notifyDataSetChanged();
        }
        return mMaterialInfoList;
    }

    private void addProductInfoItem(String name,int number) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("store",number);
        contentValues.put("event","第一次添加");
        DataBaseUtils.insert(database,"material",contentValues);
        queryAllProduct();
    }

    private ProductFragment.ProdcutCallBack mProductCallBack = new ProductFragment.ProdcutCallBack() {
        @Override
        public void addProduct(int number) {
            Toast.makeText(getActivity(), "addProduct" + number, Toast.LENGTH_SHORT).show();
            incressNumber(number);
        }

        @Override
        public void reduceProduct(int number) {
            Toast.makeText(getActivity(), "reduceProduct" + number, Toast.LENGTH_SHORT).show();
            reduceTo(number);
        }

        @Override
        public void addProductItem(String name, int number) {
            addProductInfoItem(name,number);
        }
    };

    private void incressNumber(int number) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("store", mMaterialInfoList.get(mPosition).getStore() + number);
        DataBaseUtils.update(database,"material", contentValues, "_id = ?", new String[]{mMaterialInfoList.get(mPosition).getmId() + ""});
        queryAllProduct();
        adaper.notifyDataSetChanged();

    }

    private void reduceTo(int number) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("store", mMaterialInfoList.get(mPosition).getStore() - number);
        DataBaseUtils.update(database,"material", contentValues, "_id = ?", new String[]{mMaterialInfoList.get(mPosition).getmId() + ""});
        queryAllProduct();
        adaper.notifyDataSetChanged();

    }

    private void deleteItem() {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        DataBaseUtils.delete(database,"material","_id = ?", new String[]{mMaterialInfoList.get(mPosition).getmId() + ""});
        queryAllProduct();
        adaper.notifyDataSetChanged();

    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getActivity(), "position" + position, Toast.LENGTH_SHORT).show();
        new ProductInfoDialog(getActivity(), mProductCallBack).show();
        mPosition = position;
    }

    @Override
    public void onLongClick(int position) {
        Toast.makeText(getActivity(), "onLongClick" + position, Toast.LENGTH_SHORT).show();
        mPosition = position;
        new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage("删除该条目吗？")
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.sure,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
                        deleteItem();
                    }
                }).create().show();
    }

    @Override
    public void onClick(View view) {
        new NewProductDialog(getActivity(),mProductCallBack).show();
    }


    public interface ProdcutCallBack {
        void addProduct(int number);

        void reduceProduct(int number);

        void addProductItem(String name,int number);
    }


}
