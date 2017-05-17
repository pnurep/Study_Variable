package com.android.gold.collapsinglayoutwithcardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gold on 2017. 5. 17..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {

    Context mContext;

    public RecyclerAdapter(Context context) {
        this.mContext = context;
    }

    private String[] titles = {
              "Chapter One"
            , "Chapter Two"
            , "Chapter Three"
            , "Chapter Four"
            , "Chapter Five"
            , "Chapter Six"
            , "Chapter Seven"
            , "Chapter Eight"
    };

    private String [] details = {
              "Item one details"
            , "Item two details"
            ,"Item three details"
            ,"Item four details"
            ,"Item five details"
            ,"Item six details"
            ,"Item seven details"
            ,"Item eight details"
    };

    private int[] images = { R.drawable.ic_3d_rotation_black_24dp
            , R.drawable.ic_accessibility_pink_100_24dp
            , R.drawable.ic_account_box_white_24dp
            , R.drawable.ic_camera_enhance_purple_a400_24dp
            , R.drawable.ic_http_white_24dp
            , R.drawable.ic_flight_takeoff_red_400_24dp
            , R.drawable.ic_credit_card_red_200_24dp
            , R.drawable.ic_history_red_400_24dp};



    @Override
    public RecyclerAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.CardViewHolder holder, int position) {
        holder.tv1.setText(titles[position]);
        holder.tv2.setText(details[position]);
        holder.imv.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }


    public class CardViewHolder extends RecyclerView.ViewHolder{

        ImageView imv;
        TextView tv1, tv2;

        public CardViewHolder(View itemView) {
            super(itemView);
            imv = (ImageView) itemView.findViewById(R.id.item_image);
            tv1 = (TextView) itemView.findViewById(R.id.item_title);
            tv2 = (TextView) itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Toast.makeText(mContext, "Click detected on item" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
