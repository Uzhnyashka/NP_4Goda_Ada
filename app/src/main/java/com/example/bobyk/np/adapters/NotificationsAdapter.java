package com.example.bobyk.np.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.main.Notification;

import java.util.List;

/**
 * Created by bobyk on 6/7/17.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context mContext;
    private List<Notification> mNotificationList;

    public NotificationsAdapter(Context context, List<Notification> notificationList) {
        mContext = context;
        mNotificationList = notificationList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.card_notification, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = mNotificationList.get(position);

        holder.mDateTextView.setText(notification.getDate());
        holder.mDescriptionTextView.setText(doTextForDescription(notification));
    }

    private String doTextForDescription(Notification notification) {
        return "Take delivery in " + notification.getRecipientLocation() + " from " +
                notification.getSenderName() + " from " + notification.getSenderLocation();
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
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
