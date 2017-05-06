package com.kitkat.android.iumusic.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Common;

import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_ARTIST;
import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_MUSIC;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    private Context context;
    private String mListType = "";

    public RecyclerAdapter(Context context, String mListType) {
        this.context = context;
        this.mListType = mListType;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.expandable_group_item, parent, false);
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
        holder.album.setText(common.getAlbum() + " " + context.getResources().getString(R.string.album));
        holder.artist.setText(common.getArtist() + " " + context.getResources().getString(R.string.music));
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

    class Holder extends RecyclerView.ViewHolder{
        RelativeLayout listLayout;
        ImageView albumImg;
        TextView title;
        TextView album;
        TextView artist;
        int pos;

        public Holder(View itemView) {
            super(itemView);
            listLayout = (RelativeLayout) itemView.findViewById(R.id.listLayout);
            albumImg = (ImageView) itemView.findViewById(R.id.listImg);
            title = (TextView) itemView.findViewById(R.id.listTitle);
            album = (TextView) itemView.findViewById(R.id.listAlbum);
            artist = (TextView) itemView.findViewById(R.id.listArtist);

            listLayout.setOnClickListener((v) -> {

            });
        }
    }
}
