package com.yajith.mybankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewCustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyDatabase myDatabase;
    private ArrayList<MyDataModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        recyclerView=findViewById(R.id.recyclerview);
        myDatabase=new MyDatabase(this);
        arrayList=myDatabase.getAllCustomer();
        MyRecyclerViewAdapter myRecyclerViewAdapter=new MyRecyclerViewAdapter(this,arrayList,this,null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);

    }
}