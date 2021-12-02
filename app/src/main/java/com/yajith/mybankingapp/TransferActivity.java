package com.yajith.mybankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TransferActivity extends AppCompatActivity {
    private String name,CurrentBal;
    private TextView textview_name,textView_bal;
    private RecyclerView recyclerView;
    private MyDatabase myDatabase;
    private ArrayList<MyDataModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        if(getIntent().getExtras()!=null)
        {
            name=getIntent().getExtras().getString("Name");
            CurrentBal= String.valueOf(getIntent().getExtras().getFloat("CurrentBal"));
        }
        textview_name=findViewById(R.id.name);
        textView_bal=findViewById(R.id.bal);
        recyclerView=findViewById(R.id.recyclerview_transfer);
        textview_name.setText(name);
        textView_bal.setText(CurrentBal);
        myDatabase=new MyDatabase(this);
        arrayList=myDatabase.getAllCustomer();
        MyRecyclerViewAdapter myRecyclerViewAdapter=new MyRecyclerViewAdapter(this,arrayList,this,name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }
}