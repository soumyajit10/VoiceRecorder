package com.example.voicerecorder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewHolder extends RecyclerView.ViewHolder {

    public TextView txtName ;
    public LinearLayout container;
    public RecViewHolder(@NonNull  View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.textName);
        container = itemView.findViewById(R.id.containers);

    }
}
