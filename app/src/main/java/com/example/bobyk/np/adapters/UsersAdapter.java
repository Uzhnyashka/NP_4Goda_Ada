package com.example.bobyk.np.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.authorization.User;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bobyk on 6/10/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUserList = new ArrayList<>();

    private DisplayImageOptions mOptions;
    private ImageLoader imageLoader;
    private OnItemClickListener mOnItemClickListener;

    public UsersAdapter(Context context, List<User> users) {
        mContext = context;
        mUserList = users;
        notifyDataSetChanged();
        configImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_user, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User user = mUserList.get(position);
        if (user != null) {
            imageLoader.displayImage(user.getPhotoUrl(), holder.mUserImageView, mOptions);
            holder.mUserEmailTextView.setText(user.getEmail());
            holder.mUserFullNameTextView.setText(
                    user.getSurname() + " " + user.getFirstName() + " " + user.getMiddleName()
            );
        }
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mUserImageView;
        private TextView mUserFullNameTextView;
        private TextView mUserEmailTextView;
        private RelativeLayout mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mUserImageView = (CircleImageView) itemView.findViewById(R.id.iv_user);
            mUserFullNameTextView = (TextView) itemView.findViewById(R.id.tv_full_name);
            mUserEmailTextView = (TextView) itemView.findViewById(R.id.tv_email);
            mCard = (RelativeLayout) itemView.findViewById(R.id.card);
        }
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void configImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(mOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageForEmptyUri(R.color.colorWhite)
                .showImageOnFail(R.color.colorWhite)
                .showImageOnLoading(R.color.colorWhite)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }
}
