package com.yiyinmei.store;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ProductInfoDialog extends Dialog implements View.OnClickListener {
    private final ProductFragment.ProdcutCallBack mProductCallBack;
    private TextView productDialogname;
    private TextView productinfoadd;
    private TextView productinforeduce;
    private EditText productinfonumber;
    private Button cancel;
    private Button sure;
    private boolean mAdd = true;
    private boolean mReduce = false;

    public ProductInfoDialog(@NonNull Context context, ProductFragment.ProdcutCallBack prodcutCallBack) {
        super(context);
        mProductCallBack = prodcutCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prodctinfo_dialog);
        productDialogname = findViewById(R.id.productDialogname);
        productinfoadd = findViewById(R.id.productinfoadd);
        productinforeduce =findViewById(R.id.productinforeduce);
        productinfonumber =findViewById(R.id.productinfonumber);
        cancel =findViewById(R.id.cancel);
        sure =findViewById(R.id.sure);

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        productinfoadd.setOnClickListener(this);
        productinforeduce.setOnClickListener(this);
        mAdd = true;
        productinfoadd.setTextColor(Color.RED);
    }


    @Override
    public void onClick(View view) {
        if (view == cancel) {
            this.dismiss();
        }else if (view == sure) {
            String temp = productinfonumber.getEditableText().toString();
            if (!TextUtils.isEmpty(temp)){
                int number = Integer.parseInt(temp);
                if (mAdd)mProductCallBack.addProduct(number);
                else if (mReduce) mProductCallBack.reduceProduct(number);

            }
            this.dismiss();
        }else if (view == productinfoadd){
            productinfoadd.setTextColor(Color.RED);
            productinforeduce.setTextColor(Color.BLACK);
            mAdd = true;
            mReduce = false;
        }else if (view == productinforeduce){
            productinforeduce.setTextColor(Color.RED);
            productinfoadd.setTextColor(Color.BLACK);
            mReduce = true;
            mAdd = false;
        }
    }
}
