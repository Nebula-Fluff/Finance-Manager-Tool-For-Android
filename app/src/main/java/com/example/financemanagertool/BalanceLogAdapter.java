package com.example.financemanagertool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BalanceLogAdapter extends RecyclerView.Adapter<BalanceLogAdapter.ViewHolder> {

    private List<String[]> dataList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String[] row);
    }

    public BalanceLogAdapter(List<String[]> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.balance_log_item_table_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] row = dataList.get(position);
        holder.tvId.setText(row[0]);
        holder.tvDate.setText(row[1]);
        holder.tvAccount.setText(row[2]);
        holder.tvChange.setText(row[3]);
        holder.tvRemark.setText(row[4]);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(row));
    }

    @Override
    public int getItemCount() { return dataList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDate, tvAccount, tvChange, tvRemark;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAccount = itemView.findViewById(R.id.tv_account);
            tvChange = itemView.findViewById(R.id.tv_change);
            tvRemark = itemView.findViewById(R.id.tv_remark);
        }
    }
}