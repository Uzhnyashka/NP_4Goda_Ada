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
import com.example.bobyk.np.models.main.Delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public class DeliveriesAdapter extends RecyclerView.Adapter<DeliveriesAdapter.ViewHolder> {

    private Context mContext;
    private List<Delivery> mDeliveryList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public DeliveriesAdapter(Context context, List<Delivery> deliveries) {
        mContext = context;
        mDeliveryList = deliveries;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_delivery, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Delivery delivery = mDeliveryList.get(position);
        holder.mDeliveryIdTextView.setText(delivery.getId());
        holder.mSendDateTextView.setText(delivery.getSendDate());
        holder.mSenderLocationTextView.setText(delivery.getSenderLocation());
        holder.mRecipientDateTextView.setText(delivery.getRecipientDate());
        holder.mRecipientLocationTextView.setText(delivery.getRecipientLocation());
        holder.mStatusTextView.setText(delivery.getStatus());
        holder.mPriceTextView.setText(String.valueOf(delivery.getPrice()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeliveryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDeliveryIdTextView;
        private TextView mSendDateTextView;
        private TextView mSenderLocationTextView;
        private TextView mRecipientDateTextView;
        private TextView mRecipientLocationTextView;
        private TextView mStatusTextView;
        private TextView mPriceTextView;
        private RelativeLayout mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mDeliveryIdTextView = (TextView) itemView.findViewById(R.id.tv_delivery_id);
            mSendDateTextView = (TextView) itemView.findViewById(R.id.tv_send_date);
            mSenderLocationTextView = (TextView) itemView.findViewById(R.id.tv_sender_location);
            mRecipientDateTextView = (TextView) itemView.findViewById(R.id.tv_recipient_date);
            mRecipientLocationTextView = (TextView) itemView.findViewById(R.id.tv_recipient_location);
            mStatusTextView = (TextView) itemView.findViewById(R.id.tv_status);
            mPriceTextView = (TextView) itemView.findViewById(R.id.tv_price);
            mCardView = (RelativeLayout) itemView.findViewById(R.id.card);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
