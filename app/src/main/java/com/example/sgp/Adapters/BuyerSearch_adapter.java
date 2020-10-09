package com.example.sgp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.R;

import java.util.ArrayList;
import java.util.List;

public class BuyerSearch_adapter extends RecyclerView.Adapter<BuyerSearch_adapter.Buyer_ViewHolder>  {

    ArrayList<Database_Class> BuyerCardList;
    ArrayList<Database_Class> BuyerCardListFull;

    public BuyerSearch_adapter(ArrayList<Database_Class> mCardList){
        BuyerCardList = mCardList;
        BuyerCardListFull=new ArrayList<>(mCardList);
    }

    public static class Buyer_ViewHolder extends RecyclerView.ViewHolder{

        TextView  SellerNameValue,SellerPhno,  CropNameValue,  QuantityValue,  WeightValue,  AreaValue;

        public Buyer_ViewHolder(@NonNull View itemView) {
            super(itemView);

            SellerNameValue = itemView.findViewById(R.id.txt_sellerName_value);
            SellerPhno=itemView.findViewById(R.id.txt_sellerPhno_value);
            CropNameValue = itemView.findViewById(R.id.txt_crop_value);
            QuantityValue = itemView.findViewById(R.id.txt_quantity_value);
            WeightValue = itemView.findViewById(R.id.txt_weight_value);
            AreaValue = itemView.findViewById(R.id.txt_area_value);

        }
    }

    @NonNull
    @Override
    public BuyerSearch_adapter.Buyer_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_card_view, parent, false);
        return new Buyer_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerSearch_adapter.Buyer_ViewHolder holder, int position) {
        Database_Class CurrentItem = BuyerCardList.get(position);

        holder.SellerNameValue.setText(CurrentItem.mNameValue);
        holder.SellerPhno.setText(CurrentItem.mPhnoValue);
        holder.CropNameValue.setText(CurrentItem.mCropNameValue);
        holder.QuantityValue.setText(CurrentItem.mQuantityValue);
        holder.WeightValue.setText(CurrentItem.mWeightValue);
        holder.AreaValue.setText(CurrentItem.mAreaValue);

    }

    @Override
    public int getItemCount() {
        return BuyerCardList.size();
    }

    /*@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                ArrayList<Database_Class> tempList = new ArrayList<>();
                if(charSequence == null || charSequence.length()==0){
                    tempList= BuyerCardListFull;
                    //tempList.addAll(BuyerCardListFull);
                }else if(charSequence.length()>0){
                    String filterPattern = charSequence.toString().toLowerCase();

                    for(Database_Class item: BuyerCardListFull){
                        if(item.mCropNameValue.toLowerCase().contains(filterPattern)){
                            tempList.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values=tempList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                BuyerCardList.clear();
                BuyerCardList.addAll( (List) filterResults.values);
                BuyerSearch_adapter.this.notifyDataSetChanged();

            }
        };
    }*/


}
