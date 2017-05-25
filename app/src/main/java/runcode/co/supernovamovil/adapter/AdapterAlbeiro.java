package runcode.co.supernovamovil.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.conn.ConnectTimeoutException;

import java.util.ArrayList;
import java.util.List;


import runcode.co.supernovamovil.DetailActivity;
import runcode.co.supernovamovil.LecturasActivity;
import runcode.co.supernovamovil.R;

import runcode.co.supernovamovil.dm.Persona;

public class AdapterAlbeiro extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<Persona> itemList;
    private Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean isProgress;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public AdapterAlbeiro(OnLoadMoreListener onLoadMoreListener, Context context) {
        this.context=context;
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
                Log.d("visibleItemCount", ""+visibleItemCount);
                totalItemCount = mLinearLayoutManager.getItemCount();
                Log.d("totalItemCount", ""+totalItemCount);
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                Log.d("firstVisibleItem", ""+firstVisibleItem);
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if (viewType == VIEW_ITEM) {
            return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    public void addAll(List<Persona> lst){
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Persona> lst){
        itemList.addAll(lst);
        notifyItemRangeChanged(0,itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {
            Persona singleItem = (Persona) itemList.get(position);

            Drawable drawableIcono = this.context.getResources().getDrawable(R.drawable.a_avator);
            ((StudentViewHolder) holder).list_avatar.setImageDrawable(drawableIcono);
            ((StudentViewHolder) holder).list_title.setText(singleItem.getNombre());
            ((StudentViewHolder) holder).list_desc.setText(singleItem.getCedula());
            ((StudentViewHolder) holder).list_desc2.setText("Dirección");
        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        this.isProgress=isProgress;
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            itemList.remove(itemList.size() - 1);
            notifyItemRemoved(itemList.size());
        }
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView list_title;
        public TextView list_desc;
        public TextView list_desc2;
        public ImageView list_avatar;

        public StudentViewHolder(View v) {
            super(v);
            list_title = (TextView) v.findViewById(R.id.list_title);
            list_desc  = (TextView) v.findViewById(R.id.list_desc);
            list_desc2  = (TextView) v.findViewById(R.id.list_desc2);
            list_avatar = (ImageView) v.findViewById(R.id.list_avatar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Click en el Objeto","Será¡? "+getAdapterPosition());
                    Log.d("Encontré el ID","Será¡? "+list_title.getText().toString());



                    Context context = v.getContext();
                    Intent intent = new Intent(context, LecturasActivity.class);


                    Bundle bundle = new Bundle();
                    bundle.putString("cedula", list_desc.getText().toString());
                    bundle.putString("nombre", list_title.getText().toString());
                    bundle.putInt("posicion",getAdapterPosition());
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }


    public void setFilter(List<Persona> countryModels){
        itemList.clear();
        itemList.addAll(countryModels);
        notifyDataSetChanged();
    }


    public boolean isProgress() {
        return isProgress;
    }

    public void setProgress(boolean progress) {
        isProgress = progress;
    }
}
