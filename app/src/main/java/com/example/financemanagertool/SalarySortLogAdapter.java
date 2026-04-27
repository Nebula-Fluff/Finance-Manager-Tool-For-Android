package com.example.financemanagertool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SalarySortLogAdapter extends RecyclerView.Adapter<SalarySortLogAdapter.ViewHolder> {

    private List<String[]> dataList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String[] row);
    }

    public SalarySortLogAdapter(List<String[]> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salary_sort_log_item_table_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] row = dataList.get(position);
        holder.tvId.setText(row[0]);
        holder.tvDate.setText(row[1]);
        holder.tvSalaryInValue.setText(row[2]);
        holder.tvReserveOutValue.setText(row[3]);
        holder.tvDailyOutValue.setText(row[4]);
        holder.tvFlexOutValue.setText(row[5]);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(row));
    }

    @Override
    public int getItemCount() { return dataList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDate, tvSalaryInValue, tvReserveOutValue, tvDailyOutValue, tvFlexOutValue;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvSalaryInValue = itemView.findViewById(R.id.tv_salary_in_value);
            tvReserveOutValue = itemView.findViewById(R.id.tv_reserve_out_value);
            tvDailyOutValue = itemView.findViewById(R.id.tv_daily_out_value);
            tvFlexOutValue = itemView.findViewById(R.id.tv_flex_out_value);
        }
    }
}