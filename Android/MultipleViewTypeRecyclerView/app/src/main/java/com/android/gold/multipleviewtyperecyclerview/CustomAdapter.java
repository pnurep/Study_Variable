package com.android.gold.multipleviewtyperecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Gold on 2017. 5. 14..
 */

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int USER = 3;

    private Context mContext;
    private List<Object> mObjects;

    public CustomAdapter(Context context, List<Object> objects) {
        this.mContext = context;
        this.mObjects = objects;
    }

    @Override
    public int getItemViewType(int position) {

        if (mObjects.get(position) instanceof String) {
            return TEXT;
        } else if (mObjects.get(position) instanceof Integer) {
            return IMAGE;
        } else if (mObjects.get(position) instanceof User) {
            return USER;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch(viewType){
            case TEXT :
                view = LayoutInflater.from(mContext).inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(view);
            case IMAGE :
                view = LayoutInflater.from(mContext).inflate(R.layout.row_image, parent, false);
                return new ImageViewHolder(view);
            case USER :
                view = LayoutInflater.from(mContext).inflate(R.layout.row_user, parent, false);
                return new UserViewHolder(view);
            default :
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case TEXT :
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.textView.setText(mObjects.get(position).toString());
                break;
            case IMAGE :
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                //imageViewHolder.imageView.setImageResource((Integer) mObjects.get(position));

                Glide.with(mContext).load(mObjects.get(position)).into(imageViewHolder.imageView);

                break;
            case USER :
                User user = (User) mObjects.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvAddress.setText(user.getAddress());
                userViewHolder.tvName.setText(user.getName());
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imv_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mObjects.get(getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mObjects.get(getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = (User) mObjects.get(getAdapterPosition());
                    Toast.makeText(mContext, user.getAddress(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
