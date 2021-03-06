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

public class ListLecturasFragment extends Fragment implements SearchView.OnQueryTextListener{

    private DrawerLayout mDrawerLayout;
    private AdapterAlbeiro mAdapter;
    private List<Persona> itemListOriginal;
    private List<Persona> itemListFiltrada;
    private List<Persona> itemList;
    private SwipeRefreshLayout swipeRefresh;
    private ViewGroup container;

    //Bandera para identificar si aún hay registros para carga
    private boolean registrosDisponiblesCarga = false;
    //Bandera para identificar si es el final de la carga de registros
    private boolean finalRegistrosCarga=false;
    private CargaPruebaPersonas cargaPruebaPersonas = new CargaPruebaPersonas();
    private boolean activoBusqueda =false;
    private RecyclerView mRecyclerView;

    private MenuItem item;
    private SearchView searchView;
    private LinearLayoutManager mLayoutManager;
    private AdapterAlbeiro.OnLoadMoreListener onLoadMoreListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private String textoBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("ListLecturasFragment", "onCreateView");

        this.container = container;


        swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(
                R.layout.swipe_albeiro, container, false);

        //Lista que se muestra en pantalla
        itemList = new ArrayList<Persona>();
        //Lista con todos los elementos desde la bd
        itemListOriginal = cargaPruebaPersonas.getListPersonas();
        //Lista filtrada según busqueda del usuario
        itemListFiltrada = new ArrayList<Persona>();


        //Se obtiene el SwipeRefresh
        swipeRefresh = (SwipeRefreshLayout) swipeRefreshLayout.findViewById(R.id.swipeAlbeiro);
        //Se obtiene el RecyclerView
        mRecyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.rvList);
        //Se asocia el Swipe a una LayoutManager
        mLayoutManager = new LinearLayoutManager(swipeRefreshLayout.getContext());
        //Se asocia el LayoutManager al RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Inicializo el texto a buscar
        textoBuscar="";


        //Se crea un listener para cargar mas objetos al RecyclerView
        onLoadMoreListener = new AdapterAlbeiro.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d("ListLecturasFragment", "onLoadMore");
                //Se verifica si hay aun mas registros disponibles para la carga en el recyclerview y si hemos llegado al final de la lista
                if (registrosDisponiblesCarga&&finalRegistrosCarga==false) {
                    //Activo la barra de progreso

                    if(!mAdapter.isProgress())
                        mAdapter.setProgressMore(true);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Limpio la lista de visualización en pantalla
                            itemList.clear();
                            //Desactivo barra de progreso
                            mAdapter.setProgressMore(false);
                            //Verifico el item de inicio cargado en el adapter
                            int start = mAdapter.getItemCount();
                            Log.d("El registro de Inicio", ""+start);
                            //Defino el registro de Fin
                            int end = start + 10;
                            //Verifico si está activa la ventana de busqueda
                            if(!activoBusqueda){
                                if(end<itemListOriginal.size()){
                                    Log.d("Normal", ""+end);
                                }else{
                                    Log.d("Es el fin", ""+end);
                                    end=itemListOriginal.size();
                                    finalRegistrosCarga=true;
                                }
                                itemList = new ArrayList<>(itemListOriginal.subList(start, end));
                                mAdapter.addItemMore(itemList);
                                mAdapter.setMoreLoading(false);
                            }else{
                                if(end<itemListFiltrada.size()){
                                    Log.d("Normal Filtrada", ""+end);
                                }else{
                                    Log.d("Es el fin Filtrada", ""+end);
                                    end=itemListFiltrada.size();
                                    finalRegistrosCarga=true;
                                }
                                itemList = new ArrayList<>(itemListFiltrada.subList(start, end));
                                mAdapter.addItemMore(itemList);
                                mAdapter.setMoreLoading(false);
                            }

                        }
                    }, 2000);
                }else{
                    Log.d("finalRegistrosCarga", ""+finalRegistrosCarga);
                    Log.d("registrosDisponibles", ""+registrosDisponiblesCarga);
                }

            }
        };

        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Refrescando", "onRefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        if(!activoBusqueda) {
                            loadData("On Refresh");
                        }else{
                            realizarBusquedaRegistros(textoBuscar);
                        }
                        finalRegistrosCarga=false;

                    }
                }, 2000);
            }
        };

        loadData("Original");
        //Para que el menu aparezca en los fragments :D
        setHasOptionsMenu(true);
        return swipeRefreshLayout;

    }


    private void loadData(String origen) {

        mAdapter = new AdapterAlbeiro(onLoadMoreListener, swipeRefreshLayout.getContext());
        mAdapter.setLinearLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(listener);

        Log.d("loadData", origen);

        int contadorRegistrosCargar = 10;
        if (itemListOriginal.size() > 0) {
            if (itemListOriginal.size() > contadorRegistrosCargar) {
                itemList = new ArrayList<>(itemListOriginal.subList(0, contadorRegistrosCargar));
                registrosDisponiblesCarga = true;
                finalRegistrosCarga=false;
            } else {
                registrosDisponiblesCarga = false;
                finalRegistrosCarga=true;
                itemList = new ArrayList<>(itemListOriginal);
            }
        } else {
            itemList.clear();
           // registrosDisponiblesCarga = false;
            finalRegistrosCarga=true;
            Snackbar.make(container, "No hay Registros!", Snackbar.LENGTH_LONG).show();
        }
        mAdapter.addAll(itemList);
    }



//2285000
//3.800.000
//humberto.sandoval@openenglish.com
//Carlos Andres Leon


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        activoBusqueda =false;
                        loadData("Cerrando Busqueda");
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        activoBusqueda =true;
                        return true;
                    }
                });
    }








    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        Log.d("onQueryTextSubmit", newText);
        this.textoBuscar=newText;
        return realizarBusquedaRegistros(textoBuscar);
    }

    //109.900
    public boolean realizarBusquedaRegistros(String newText){

        itemListFiltrada = new ArrayList<Persona>();
        itemListFiltrada = filter(itemListOriginal, newText);
        int contadorRegistrosCargar = 10;
        if (itemListFiltrada.size() > 0) {

            mAdapter = new AdapterAlbeiro(onLoadMoreListener, swipeRefreshLayout.getContext());
            mAdapter.setLinearLayoutManager(mLayoutManager);
            mAdapter.setRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            swipeRefresh.setOnRefreshListener(listener);

            if (itemListFiltrada.size() > contadorRegistrosCargar) {
                itemList = new ArrayList<>(itemListFiltrada.subList(0, contadorRegistrosCargar));
                registrosDisponiblesCarga = true;
                finalRegistrosCarga=false;
            } else {
                registrosDisponiblesCarga = false;
                finalRegistrosCarga=true;
                itemList = new ArrayList<>(itemListFiltrada);
            }
            mAdapter.setFilter(itemList);
            return true;
        } else {
            Snackbar.make(container, "No hay Registros!", Snackbar.LENGTH_LONG).show();
            registrosDisponiblesCarga = false;
            finalRegistrosCarga=true;
            itemList.clear();
            mAdapter.setFilter(itemList);

            return false;
        }
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
