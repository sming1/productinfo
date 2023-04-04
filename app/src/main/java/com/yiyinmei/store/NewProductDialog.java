package com.yiyinmei.store;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class NewProductDialog extends Dialog implements View.OnClickListener {
    private final ProductFragment.ProdcutCallBack mListener;
    private Button mSure,mCancel;
    private EditText mProductName,mProductNumber;

    public NewProductDialog(@NonNull Context context, ProductFragment.ProdcutCallBack callBack) {
        super(context);
        mListener = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addprodct_dialog);
        mSure = findViewById(R.id.sure);
        mCancel = findViewById(R.id.cancel);

        mProductName = findViewById(R.id.productname);
        mProductNumber = findViewById(R.id.productnumber);

        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view == mCancel) dismiss();
        else if (view == mSure){
            String name = mProductName.getText().toString();
            String number = mProductNumber.getText().toString();
            if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(number)){
                mListener.addProductItem(name,Integer.parseInt(number));
            }
            dismiss();
        }
    }
}
