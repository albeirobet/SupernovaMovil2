package runcode.co.supernovamovil.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import runcode.co.supernovamovil.CargaPruebaPersonas;
import runcode.co.supernovamovil.DetailActivity;
import runcode.co.supernovamovil.ListContentFragment;
import runcode.co.supernovamovil.R;
import runcode.co.supernovamovil.adapter.AdapterAlbeiro;
import runcode.co.supernovamovil.dm.Persona;

/**
 * Created by root on 10/05/17.
 */

public class ListLecturasFragment extends Fragment implements SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;
    private AdapterAlbeiro mAdapter;
    private List<Persona> itemListOriginal;
    private List<Persona> itemList;
    private SwipeRefreshLayout swipeRefresh;
    private ViewGroup container;

    private boolean registrosDisponiblesCarga = false;
    private CargaPruebaPersonas cargaPruebaPersonas = new CargaPruebaPersonas();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("ListLecturasFragment", "onCreateView");

        this.container = container;


        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(
                R.layout.swipe_albeiro, container, false);


        itemList = new ArrayList<Persona>();
        itemListOriginal = cargaPruebaPersonas.getListPersonas();
        swipeRefresh = (SwipeRefreshLayout) swipeRefreshLayout.findViewById(R.id.swipeAlbeiro);
        RecyclerView mRecyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.rvList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(swipeRefreshLayout.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        AdapterAlbeiro.OnLoadMoreListener onLoadMoreListener = new AdapterAlbeiro.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d("ListLecturasFragment", "onLoadMore");
                if (registrosDisponiblesCarga) {
                    mAdapter.setProgressMore(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            itemList.clear();
                            mAdapter.setProgressMore(false);
                            int start = mAdapter.getItemCount();
                            Log.d("El registro de Inicio", ""+start);
                            int end = start + 10;


                            if(end<itemListOriginal.size()){
                                Log.d("Normal", ""+end);
                            }else{
                                Log.d("Es el fin", ""+end);
                                end=itemListOriginal.size();
                            }
                            itemList = new ArrayList<>(itemListOriginal.subList(start, end));
                            mAdapter.addItemMore(itemList);
                            mAdapter.setMoreLoading(false);

                        }
                    }, 2000);
                }
            }
        };
        mAdapter = new AdapterAlbeiro(onLoadMoreListener, swipeRefreshLayout.getContext());
        mAdapter.setLinearLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("MainActivity_", "onRefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        loadData();

                    }
                }, 2000);
            }
        };
        swipeRefresh.setOnRefreshListener(listener);
        loadData();
        setHasOptionsMenu(true);
        return swipeRefreshLayout;

    }


    private void loadData() {
        // itemList.clear();

        /**
         if(itemListOriginal.size()>0&&itemListOriginal.size()>9) {
         itemList = new ArrayList<>(itemListOriginal.subList(0, 9));
         }else if(){
         itemList.clear();
         Snackbar.make(container, "No hay Registros!", Snackbar.LENGTH_LONG).show();
         }*/

        int contadorRegistrosCargar = 10;

        if (itemListOriginal.size() > 0) {
            if (itemListOriginal.size() > contadorRegistrosCargar) {
                itemList = new ArrayList<>(itemListOriginal.subList(0, contadorRegistrosCargar));
                registrosDisponiblesCarga = true;
            } else {
                registrosDisponiblesCarga = false;
                itemList = new ArrayList<>(itemListOriginal);
            }
        } else {
            itemList.clear();
            registrosDisponiblesCarga = false;
            Snackbar.make(container, "No hay Registros!", Snackbar.LENGTH_LONG).show();
        }
        mAdapter.addAll(itemList);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mAdapter.setFilter(itemList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", newText);
        final List<Persona> filteredModelList = filter(itemList, newText);
        if (filteredModelList.size() > 0) {
            mAdapter.setFilter(filteredModelList);
            return true;
        } else {
            Snackbar.make(container, "No hay Registros!", Snackbar.LENGTH_LONG).show();
            mAdapter.setFilter(new ArrayList<Persona>());
            return false;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit", query);
        return false;
    }


    private List<Persona> filter(List<Persona> models, String query) {
        query = query.toLowerCase();


        Log.d("Tamaño Original Lista: ", "" + models.size());
        final List<Persona> filteredModelList = new ArrayList<>();
        for (Persona model : models) {
            final String text = model.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        Log.d("Tamaño Filtrada:  ", "" + filteredModelList.size());
        return filteredModelList;
    }


}
