package runcode.co.supernovamovil.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

//import runcode.co.supernovamovil.LecturasActivity;
import runcode.co.supernovamovil.LecturasActivity;
import runcode.co.supernovamovil.MainActivity;
import runcode.co.supernovamovil.R;

import static runcode.co.supernovamovil.R.id.toolbar;

public class MyFragment extends Fragment {

    int pos;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_1, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setTitle(getArguments().getString("msg"));

        //pos = getArguments().getInt("num");
        /**
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("MainActivity", "Deleting at pos - " + pos);
                LecturasActivity activity = (LecturasActivity) getActivity();
                activity.delete(pos);
            }
        });
         */

        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        FrameLayout fl = (FrameLayout) v.findViewById(R.id.frame_layout);
        tv.setText(getArguments().getString("num"));
        fl.setBackgroundColor(getArguments().getInt("colour"));


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lecturas, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.lecturas_pendientes) {

            Intent intent = new Intent(this.getContext(), MainActivity.class);
            this.getContext().startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public int getPosition() {
        return pos;
    }

    public static MyFragment newInstance(String num,String text,int clr) {

        MyFragment f = new MyFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        b.putInt("colour", clr);
        b.putString("num",num);
        f.setArguments(b);
        return f;
    }


}