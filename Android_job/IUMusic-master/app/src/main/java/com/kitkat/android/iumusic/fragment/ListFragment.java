package com.kitkat.android.iumusic.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.activity.PlayActivity;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Music;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_LIST_TYPE = "list-type";

    private RecyclerView recyclerView;
    private RecyclerCardAdapter cardAdapter;
    private RecyclerAdapter adapter;
    private OnFragmentInteractionListener mListener;

    private int mColumnCount = 1;
    private String mListType = "";

    public static ListFragment newInstance(int columnCount, String mListType) {

        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();

        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_LIST_TYPE, mListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mListType = getArguments().getString(ARG_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        viewInit(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener)
            mListener = (OnFragmentInteractionListener) context;
    }

    private void viewInit(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new RecyclerAdapter(getContext(), mListType);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
            cardAdapter = new RecyclerCardAdapter(getContext(), mListType);
            recyclerView.setAdapter(cardAdapter);
        }

    }

    public interface OnFragmentInteractionListener {
        void refreshAdapter();
    }
}
