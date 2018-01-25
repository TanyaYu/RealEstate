package com.tanyayuferova.realestate.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanyayuferova.realestate.databinding.ItemRealEstateBinding;
import com.tanyayuferova.realestate.entity.RealEstate;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying real estates items
 *
 * Created by Tanya Yuferova on 1/24/2018.
 */

public class RealEstateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public interface OnClickRealEstateHandler {
        void onClickRealEstate(View view, RealEstate realEstate);
    }

    private List<RealEstate> data;
    private OnClickRealEstateHandler onClickHandler;

    public RealEstateAdapter(){}

    public RealEstateAdapter(OnClickRealEstateHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public class RealEstateAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ItemRealEstateBinding binding;

        public RealEstateAdapterViewHolder(ItemRealEstateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RealEstate item) {
            binding.setItem(item);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickHandler.onClickRealEstate(binding.getRoot(), binding.getItem());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRealEstateBinding itemBinding = ItemRealEstateBinding.inflate(layoutInflater, parent, false);
        return new RealEstateAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RealEstate item = data.get(position);
        ((RealEstateAdapterViewHolder) holder).bind(item);
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public void setData(List<RealEstate> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<RealEstate> getData() {
        return data;
    }

    public void addItem(RealEstate item) {
        if(data == null)
            data = new ArrayList<>();
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data = null;
        notifyDataSetChanged();
    }
}
