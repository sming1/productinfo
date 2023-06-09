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
import com.yiyinmei.store.beans.SalaryInfo;
import com.yiyinmei.store.data.DataBaseUtils;
import com.yiyinmei.store.data.MySqliteDatabase;
import com.yiyinmei.store.data.ProductRecycleBaseAdaper;

import java.util.ArrayList;

public class CountFragment extends Fragment implements ProductRecycleBaseAdaper.OnItemClickListener, View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_count,container,false);
    }
    private ArrayList<SalaryInfo> salaryInfoList = new ArrayList<>(20);
    private RecyclerView recycleView;
    ProductRecycleBaseAdaper adaper;
    private int mPosition;
    private Button mAddProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salaryInfoList = queryAllProduct();
        adaper = new ProductRecycleBaseAdaper(salaryInfoList, this);
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

    private ArrayList<SalaryInfo> queryAllProduct() {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        Cursor cursor = database.getReadableDatabase().query("salary", null, null, null, null, null, null);
        if (cursor != null) {
            salaryInfoList.clear();
            while (cursor.moveToNext()) {
                int _id = cursor.getColumnIndex("_id");
                int name = cursor.getColumnIndex("name");
                int number = cursor.getColumnIndex("number");
                int salary = cursor.getColumnIndex("salary");
                int event = cursor.getColumnIndex("event");

                int infoId = cursor.getInt(_id);
                String infoName = cursor.getString(name);
                String infonumber = cursor.getString(number);
                String infosalary = cursor.getString(salary);
                String infoEvent = cursor.getString(event);

                Log.e("sunming", "_id " + infoId + " name " + infoName + " number " + infonumber+" salary "+salary);
                SalaryInfo salaryInfo = new SalaryInfo(infoId, infoName, infonumber, infosalary,infoEvent);
                salaryInfoList.add(salaryInfo);
            }
        }
        if (adaper !=null){
            adaper.notifyDataSetChanged();
        }
        return salaryInfoList;
    }

    private void addProductInfoItem(String name,int number) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("store",number);
        contentValues.put("event","第一次添加");
        DataBaseUtils.insert(database,"salary",contentValues);
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
        contentValues.put("store", salaryInfoList.get(mPosition).getStore() + number);
        DataBaseUtils.update(database,"salary", contentValues, "_id = ?", new String[]{salaryInfoList.get(mPosition).getmId() + ""});
        queryAllProduct();
        adaper.notifyDataSetChanged();

    }

    private void reduceTo(int number) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("store", salaryInfoList.get(mPosition).getStore() - number);
        DataBaseUtils.update(database,"salary", contentValues, "_id = ?", new String[]{salaryInfoList.get(mPosition).getmId() + ""});
        queryAllProduct();
        adaper.notifyDataSetChanged();

    }

    private void deleteItem(int positon) {
        MySqliteDatabase database = new MySqliteDatabase(this.getActivity());
        ContentValues contentValues = new ContentValues();
        contentValues.put("store", salaryInfoList.get(mPosition).getmId());
        DataBaseUtils.delete(database,"salary","_id = ?", new String[]{salaryInfoList.get(mPosition).getmId() + ""});
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
                        deleteItem(position);
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
