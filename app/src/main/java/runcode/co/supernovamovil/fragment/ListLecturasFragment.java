package runcode.co.supernovamovil.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import runcode.co.supernovamovil.DetailActivity;
import runcode.co.supernovamovil.ListContentFragment;
import runcode.co.supernovamovil.R;
import runcode.co.supernovamovil.adapter.AdapterAlbeiro;
import runcode.co.supernovamovil.dm.Persona;

/**
 * Created by root on 10/05/17.
 */

public class ListLecturasFragment extends Fragment  {

    private DrawerLayout mDrawerLayout;
    private AdapterAlbeiro mAdapter;
    private ArrayList<Persona> itemList;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("MainActivity_","onStart");



        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(
                R.layout.swipe_albeiro, container, false);

        itemList = new ArrayList<Persona>();

        swipeRefresh=(SwipeRefreshLayout)swipeRefreshLayout.findViewById(R.id.swipeAlbeiro);
        RecyclerView mRecyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.rvList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(swipeRefreshLayout.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        AdapterAlbeiro.OnLoadMoreListener onLoadMoreListener= new AdapterAlbeiro.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d("MainActivity_","onLoadMore");
                mAdapter.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemList.clear();
                        mAdapter.setProgressMore(false);
                        int start = mAdapter.getItemCount();
                        int end = start + 15;
                        for (int i = start + 1; i <= end; i++) {
                            itemList.add(new Persona("Persona " + i));
                        }
                        mAdapter.addItemMore(itemList);
                        mAdapter.setMoreLoading(false);
                    }
                },2000);
            }
        };
        mAdapter = new AdapterAlbeiro(onLoadMoreListener, swipeRefreshLayout.getContext());
        mAdapter.setLinearLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("MainActivity_","onRefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        loadData();

                    }
                },2000);
            }
        };
        swipeRefresh.setOnRefreshListener(listener);
        loadData();
        return swipeRefreshLayout;

    }

    private void loadData() {
        itemList.clear();
        for (int i = 1; i <= 20; i++) {
            itemList.add(new Persona("Persona " + i));
        }
        mAdapter.addAll(itemList);
    }


}
