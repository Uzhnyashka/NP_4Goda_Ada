package com.example.bobyk.np.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.main.Message;

import java.util.Calendar;
import java.util.List;

/**
 * Created by bobyk on 6/7/17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context mContext;
    private List<Message> mMessageList;
    private Calendar mCalendar;

    public MessagesAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
        notifyDataSetChanged();
        mCalendar = Calendar.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.card_notification, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        mCalendar.setTimeInMillis(message.getDate());
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int year = mCalendar.get(Calendar.YEAR);
        String d = "";
        String m = "";
        if (day < 10) {
            d += "0";
        }
        if (month < 10) {
            m += "0";
        }
        d += String.valueOf(day);
        m += String.valueOf(month);
        holder.mDateTextView.setText(d + "." + m + "." + year);
        holder.mDescriptionTextView.setText(doTextForDescription(message));
    }

    private String doTextForDescription(Message message) {
        if (message.getStatus().equals("Sent")) {
            return message.getSenderFullName() + " sent delivery #" +
                    message.getDeliveryId() + " to you in" +
                    message.getRecipientLocation() + "from " + message.getSenderLocation();
        } else if (message.getStatus().equals("Delivered")) {
            return "Delivery #" + message.getDeliveryId() + " from " + message.getSenderFullName() +
                    " delivered to " + message.getRecipientLocation();
        } else if (message.getStatus().equals("Obtained")) {
            return "Delivery #" + message.getDeliveryId() + " was obtained";
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mLabelTextView;
        private TextView mDescriptionTextView;
        private TextView mDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mLabelTextView = (TextView) itemView.findViewById(R.id.tv_label);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_description);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
