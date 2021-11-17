package com.kripton.flyaway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHolder> {
    Context ctx;
    List<recycleViewModel> list;

    public recycleViewAdapter(Context ctx, List<recycleViewModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public recycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull recycleViewAdapter.ViewHolder holder, int position) {
        vaccineAdapterClass adapterClass = list.get(position).getAdapterClass();
        holder.setRecycle(ctx,adapterClass);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycle);
        }
        public void setRecycle(Context ctx,vaccineAdapterClass adapterClass)
        {
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterClass);
            adapterClass.notifyDataSetChanged();

        }
    }
}
