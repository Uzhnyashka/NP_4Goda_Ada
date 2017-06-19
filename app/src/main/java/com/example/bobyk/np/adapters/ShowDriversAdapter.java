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
import com.example.bobyk.np.models.authorization.Driver;
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

public class ShowDriversAdapter extends RecyclerView.Adapter<ShowDriversAdapter.ViewHolder> {

    private Context mContext;
    private List<Driver> mDriverList;
    private DisplayImageOptions mOptions;
    private ImageLoader imageLoader;
    private List<ViewHolder> mHoldersList = new ArrayList<>();
    private int selectedPosition = -1;
    private OnItemClickListener mOnItemClickListener;

    public ShowDriversAdapter(Context context, List<Driver> drivers) {
        mContext = context;
        mDriverList = drivers;
        notifyDataSetChanged();
        configImageLoader();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_driver, parent, false);
        ViewHolder vh = new ViewHolder(view);
        mHoldersList.add(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(position);
        if (position == selectedPosition) {
            holder.mCardRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.auth_field_selected));
        } else {
            holder.mCardRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.auth_field));
        }
        Driver driver = mDriverList.get(position);
        imageLoader.displayImage(driver.getPhotoUrl(), holder.mDriverImageView, mOptions);
        holder.mFullNameTextView.setText(driver.getSurname() + " " + driver.getFirstName() + " "
                + driver.getMiddleName());
        holder.mEmailTextView.setText(driver.getEmail());
        holder.mCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
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

    @Override
    public int getItemCount() {
        return mDriverList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mCardRelativeLayout;
        private CircleImageView mDriverImageView;
        private TextView mEmailTextView;
        private TextView mFullNameTextView;
        private int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mDriverImageView = (CircleImageView) itemView.findViewById(R.id.iv_driver);
            mEmailTextView = (TextView) itemView.findViewById(R.id.tv_email);
            mFullNameTextView = (TextView) itemView.findViewById(R.id.tv_full_name);
            mCardRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.card);
        }

        public void bind(int position) {
            mPosition = position;
        }

        public int getBoundItemPosition() {
            return mPosition;
        }

    }

    public void setItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
