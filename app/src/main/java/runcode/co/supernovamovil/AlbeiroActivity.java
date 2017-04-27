package runcode.co.supernovamovil;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import runcode.co.supernovamovil.adapter.AdapterAlbeiro;
import runcode.co.supernovamovil.dm.Persona;

public class AlbeiroActivity extends AppCompatActivity implements AdapterAlbeiro.OnLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener{

    //EndlessRecyclerView

    private AdapterAlbeiro mAdapter;
    private ArrayList<Persona> itemList;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albeiro);

        itemList = new ArrayList<Persona>();
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterAlbeiro(this);
        mAdapter.setLinearLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity_","onStart");
        loadData();
    }

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

    private void loadData() {
        itemList.clear();
        for (int i = 1; i <= 20; i++) {
            itemList.add(new Persona("Persona " + i));
        }
        mAdapter.addAll(itemList);
    }

}
