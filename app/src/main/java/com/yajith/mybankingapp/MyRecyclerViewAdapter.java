package com.yajith.mybankingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<MyDataModel> arrayList;
    private String fromname;
    private MyDatabase myDatabase;
    public MyRecyclerViewAdapter(Context context,ArrayList<MyDataModel> arrayList,Activity activity,String fromname)
    {
        this.fromname=fromname;
        this.context=context;
        this.activity=activity;
        this.arrayList=arrayList;
        myDatabase=new MyDatabase(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.custom_recycler_layout, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(listItem);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyDataModel myDataModel=arrayList.get(position);
        holder.textView.setText(myDataModel.name);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context.getClass().getName().equalsIgnoreCase("com.yajith.mybankingapp.ViewCustomerActivity")) {
                    Intent intent = new Intent(activity, TransferActivity.class);
                    intent.putExtra("Name", myDataModel.name);
                    intent.putExtra("CurrentBal", myDataModel.CurrentBal);
                    context.startActivity(intent);
                }
                else
                {
                    if(fromname.equalsIgnoreCase(myDataModel.name))
                    {
                        Toast.makeText(context, "Self Transfer not allowed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        EditText edittext = new EditText(context);
                        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        alert.setMessage("Enter Your Amount");
                        alert.setTitle("Transfer Money");
                        TextView textView = new TextView(context);
                        textView.setText("From " + fromname + " to " + myDataModel.name);
                        alert.setView(textView);
                        alert.setView(edittext);
                        alert.setPositiveButton("Transfer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String amt = edittext.getText().toString();
                                myDatabase.tranfer(myDataModel.name, fromname, Float.parseFloat(amt));
                                dialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Money Transfered Successfully");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        activity.finish();
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                        alert.show();
                    }


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        MaterialCardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.name);
            cardView=itemView.findViewById(R.id.card);
        }
    }
}
