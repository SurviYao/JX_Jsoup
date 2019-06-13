package com.example.administrator.jx_new_jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity context;
    public ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        dialog=new ProgressDialog(context);
        dialog.setMessage("正在加载……");
        dialog.setCancelable(false);
    }

    public void show_short(String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public void show_long(String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
