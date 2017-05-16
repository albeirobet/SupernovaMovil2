package runcode.co.supernovamovil.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import runcode.co.supernovamovil.DetailActivity;
import runcode.co.supernovamovil.ListContentFragment;
import runcode.co.supernovamovil.R;
import runcode.co.supernovamovil.dm.Persona;

/**
 * Created by root on 16/05/17.
 */

public class AdapterLecturas extends RecyclerView.Adapter<AdapterLecturas.ViewHolder> {


    private static final int LENGTH = 18;

    private final String[] mPlaces;
    private final String[] mPlaceDesc;
    private final Drawable[] mPlaceAvators;
    private List<Persona> listPersonas = new ArrayList<>();

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<Persona> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public AdapterLecturas (OnLoadMoreListener onLoadMoreListener) {
        mPlaces = null;
        mPlaceDesc = null;
        mPlaceAvators = null;
        this.onLoadMoreListener=onLoadMoreListener;
        itemList =new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;
    }


    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }



    public AdapterLecturas(Context context) {
        Resources resources = context.getResources();
        cargarDatosEjemplo();
        mPlaces = resources.getStringArray(R.array.places);
        mPlaceDesc = resources.getStringArray(R.array.place_desc);
        TypedArray a = resources.obtainTypedArray(R.array.place_avator);
        mPlaceAvators = new Drawable[a.length()];
        for (int i = 0; i < mPlaceAvators.length; i++) {
            mPlaceAvators[i] = a.getDrawable(i);
        }
        a.recycle();
    }




    public void cargarDatosEjemplo() {

        for (int i = 0; i < LENGTH; i++) {
            Persona persona = new Persona();
            persona.setCedula("PA-" + (int) (Math.random() * 35000 + 1));
            persona.setNombre("Eyder Ascuntar " + (int) (Math.random() * 35000 + 1));
            listPersonas.add(persona);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.avator.setImageDrawable(mPlaceAvators[5]);
        holder.name.setText("" + position + "   " + listPersonas.get(position).getCedula());
        holder.description.setText("" + position + "   " + listPersonas.get(position).getNombre());
        holder.description2.setText("" + position + "   " + listPersonas.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return LENGTH;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;
        public TextView description2;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {


            super(inflater.inflate(R.layout.item_list, parent, false));

            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);
            description2 = (TextView) itemView.findViewById(R.id.list_desc2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }


}
