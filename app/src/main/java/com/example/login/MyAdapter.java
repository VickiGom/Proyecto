package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    String data1[], data2[], data3[];
    int images[];
    Context context;
    public MyAdapter(Context ct, String s1[],String s2[],String s3[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
    myViewHolder.mytext1.setText(data1[i]);
    myViewHolder.mytext2.setText(data2[i]);
    myViewHolder.mytext3.setText(data3[i]);
    myViewHolder.myImage.setImageResource(images[i]);


    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mytext1, mytext2, mytext3;
        ImageView myImage;
        ConstraintLayout secondLayou;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mytext1 = itemView.findViewById(R.id.nombreView);
            mytext3 = itemView.findViewById(R.id.casoView);
            mytext2 = itemView.findViewById(R.id.telView);
            myImage = itemView.findViewById(R.id.imaView);
            secondLayou = itemView.findViewById(R.id.secondLayou);
        }
    }
}
