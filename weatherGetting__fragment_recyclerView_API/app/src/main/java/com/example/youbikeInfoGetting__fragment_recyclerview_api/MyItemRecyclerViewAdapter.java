package com.example.youbikeInfoGetting__fragment_recyclerview_api;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youbikeInfoGetting__fragment_recyclerview_api.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.youbikeInfoGetting__fragment_recyclerview_api.databinding.FragmentItemBinding;
import com.example.youbikeInfoGetting__fragment_recyclerview_api.placeholder.mPlaceholder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<mPlaceholder.mPlaceholderItem> mValues;
    private ItemClickListener mItemClickListener;

    public MyItemRecyclerViewAdapter(List<mPlaceholder.mPlaceholderItem> items, ItemClickListener itemClickListener) {
        mValues = items;
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).mId);
        holder.mContentView.setText(mValues.get(position).mContent);
        holder.mSnaView.setText(mValues.get(position).sna);
        holder.mTotView.setText(mValues.get(position).tot);

        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(mValues.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemClickListener{
        void onItemClick(mPlaceholder.mPlaceholderItem mPlaceholderItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mSnaView;
        public final TextView mTotView;
        public mPlaceholder.mPlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mSnaView = binding.txtSna;
            mTotView = binding.txtTot;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}