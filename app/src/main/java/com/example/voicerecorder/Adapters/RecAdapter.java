package com.example.voicerecorder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicerecorder.OnSelectListener;
import com.example.voicerecorder.R;
import com.example.voicerecorder.RecViewHolder;

import java.io.File;
import java.util.List;

public class RecAdapter  extends RecyclerView.Adapter<RecViewHolder> {

    private Context context;
    private List<File> fileList;
    private OnSelectListener onSelectListener;

    public RecAdapter(Context context, List<File> fileList , OnSelectListener selectListener) {
        this.context = context;
        this.fileList = fileList;
        this.onSelectListener = selectListener;
    }

    @NonNull

    @Override
    public RecViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return  new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.txtName.setText(fileList.get(position).getName());
        holder.txtName.setSelected(true);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.OnSelected(fileList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
