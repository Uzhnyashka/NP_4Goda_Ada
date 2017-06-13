package com.example.bobyk.np.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.main.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/13/17.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private Context mContext;
    private List<City> mCityList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public CitiesAdapter(Context context, List<City> cities) {
        mContext = context;
        mCityList = cities;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_city, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        City city = mCityList.get(position);
        holder.mCityNameTextView.setText(city.getName());
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCityNameTextView;
        private RelativeLayout mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mCityNameTextView = (TextView) itemView.findViewById(R.id.tv_city_name);
            mCard = (RelativeLayout) itemView.findViewById(R.id.card);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
