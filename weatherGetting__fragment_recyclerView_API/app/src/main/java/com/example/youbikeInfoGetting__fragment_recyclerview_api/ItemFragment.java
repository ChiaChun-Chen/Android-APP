package com.example.youbikeInfoGetting__fragment_recyclerview_api;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.youbikeInfoGetting__fragment_recyclerview_api.placeholder.mPlaceholder;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_BICYCLE_INFO = "bicycle-info";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount, String bicycleInfo) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_BICYCLE_INFO, bicycleInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(mPlaceholder.ITEMS, new MyItemRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(mPlaceholder.mPlaceholderItem mPlaceholderItem) {
                    DetailsFragment detailsFragment = new DetailsFragment();
                    getFragmentManager().beginTransaction()
                            .add(R.id.main_layout, detailsFragment.newInstance(Integer.parseInt(mPlaceholderItem.mId)
                                    , getArguments().getString(ARG_BICYCLE_INFO)), "detailFrag")
                            .addToBackStack("back")
                            .commit();
                }
            }));
        }
        return view;
    }
}