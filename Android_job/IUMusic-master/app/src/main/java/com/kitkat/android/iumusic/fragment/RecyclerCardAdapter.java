package com.kitkat.android.iumusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.activity.PlayActivity;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Common;
import com.kitkat.android.iumusic.domain.Music;

import java.util.List;

import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_ARTIST;
import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_MUSIC;

class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.Holder> {
    private LayoutInflater inflater;
    private Context context;

    private String mListType = "";

    public RecyclerCardAdapter(Context context, String mListType) {
        this.context = context;
        this.mListType = mListType;
        Log.i("ListType", mListType);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.card_item, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Common common = null;

        if(TYPE_MUSIC.equals(mListType))
            common = Database.getMusic(position);
        else if(TYPE_ARTIST.equals(mListType))
            common = Database.getArtist(position);

        Glide.with(context).load(common.getAlbumUri())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.albumImg);

        holder.title.setText(common.getTitle());
        holder.album.setText(common.getAlbum());
        holder.artist.setText(common.getArtist());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        if(TYPE_MUSIC.equals(mListType))
            return Database.musicListSize();
        else if(TYPE_ARTIST.equals(mListType))
            return Database.artistListSize();
        else
            return 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView albumImg;
        private TextView title, album, artist;
        private int pos = 0;

        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            albumImg = (ImageView) itemView.findViewById(R.id.cardImg);
            title = (TextView) itemView.findViewById(R.id.cardTitle);
            album = (TextView) itemView.findViewById(R.id.cardAlbum);
            artist = (TextView) itemView.findViewById(R.id.cardArtist);
            cardView.setOnClickListener((v) -> showPlay()); // Lambda
        }

        private void showPlay() {
            Intent intent = new Intent(context, PlayActivity.class);
            intent.putExtra("pos", pos);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
    }
}
