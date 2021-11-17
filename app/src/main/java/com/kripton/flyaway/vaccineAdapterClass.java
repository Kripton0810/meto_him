package com.kripton.flyaway;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class vaccineAdapterClass extends RecyclerView.Adapter<vaccineAdapterClass.ViewHolder> {
    List<vaccinModelClass> list;

    public vaccineAdapterClass(List<vaccinModelClass> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public vaccineAdapterClass.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycle_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull vaccineAdapterClass.ViewHolder holder, int position) {
        String add1 = list.get(position).getAdd1();
        String add2 = list.get(position).getAdd2();
        String d1 = list.get(position).getD1();
        String d2 = list.get(position).getD2();
        String fee = list.get(position).getFee();
        String age = list.get(position).getAge();
        String vaccine = list.get(position).getVaccine();
        holder.setData(add1,add2,d1,d2,vaccine,age,fee);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView add1,add2,d1,d2,total,vac,ages,fe;
        LinearLayout vaccine_number,paid;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            add1= itemView.findViewById(R.id.add1);
            add2= itemView.findViewById(R.id.add2);
            d1 = itemView.findViewById(R.id.d1);
            d2 = itemView.findViewById(R.id.d2);
            total = itemView.findViewById(R.id.totaldose);
            vac = itemView.findViewById(R.id.vaccine_name);
            ages = itemView.findViewById(R.id.age);
            paid = itemView.findViewById(R.id.paid);
            fe= itemView.findViewById(R.id.fee);
            vaccine_number = itemView.findViewById(R.id.vaccine_number);
        }
        public void setData(String add_1,String add_2,String d_1,String d_2,String vaccine,String age,String fee)
        {
            add1.setText(add_1);
            add2.setText(add_2);
            d1.setText(d_1);
            d2.setText(d_2);
            int tot = Integer.parseInt(d_1)+Integer.parseInt(d_2);
            int f = Integer.parseInt(fee);
            if(f>0)
            {
                paid.setVisibility(View.VISIBLE);
                fe.setText(fee);
            }
            else
            {
                paid.setVisibility(View.GONE);
            }

            total.setText(String.valueOf(tot));
            vac.setText(vaccine);
            ages.setText(age+"+");
            if(tot<=20)
            {
                vaccine_number.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fc1e1e")));
            }
            else
            if(tot<=50&&tot>20)
            {
                vaccine_number.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fceb03")));
            }
            else
            {

                vaccine_number.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24fc03")));
            }
        }
    }
}
