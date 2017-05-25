package runcode.co.supernovamovil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import runcode.co.supernovamovil.dm.Persona;
import runcode.co.supernovamovil.fragment.MyFragment;

public class LecturasActivity extends AppCompatActivity {

    private MyPagerAdapter mpg;
    private ArrayList<MyFragment> fragmentlist = new ArrayList<>();
    public FragmentManager fmr = getSupportFragmentManager();
    private List<Persona> itemListOriginal;
    private CargaPruebaPersonas cargaPruebaPersonas = new CargaPruebaPersonas();

    private boolean isLastPageSwiped;
    private int counterPageScroll;
    private  int posicion;
    private  int cantidadRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturas);

        //Lista con todos los elementos desde la bd
        itemListOriginal = cargaPruebaPersonas.getListPersonas();

        Bundle bundle = getIntent().getExtras();
        String cedula = bundle.getString("cedula");
        String nombre = bundle.getString("nombre");
        posicion = bundle.getInt("posicion");

        final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        mpg = new MyPagerAdapter(fmr, fragmentlist);
        pager.setAdapter(mpg);




        cantidadRegistros = 5;
        if (posicion < itemListOriginal.size() && (itemListOriginal.size() - posicion) > 5) {
            for (int q = posicion; q < cantidadRegistros; q++) {
                Persona persona = new Persona();
                persona = itemListOriginal.get(q);
                int r = new Random().nextInt(100) + 155;
                int g = new Random().nextInt(100) + 155;
                int b = new Random().nextInt(100) + 155;
                mpg.getFragmentList().add(MyFragment.newInstance(persona.getCedula(), persona.getNombre(), Color.rgb(r, g, b)));
                mpg.notifyDataSetChanged();
            }
        }


        Log.d("TOTAL PAGINAS",""+mpg.getCount());

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            boolean lastPageChange = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int lastIdx = mpg.getCount() - 1;

                Log.d("POSICION ACTUAL", "pos:" + position);
                Log.d("TOTAL PAGINAS",""+(mpg.getCount()-1));
                if (position == lastIdx) {

                    Log.d("AGREGAR NUEVAS", "pos:" + position);
                    posicion=position+1;
                    if (posicion < itemListOriginal.size() && (itemListOriginal.size() - posicion) > 5) {
                        Log.d("PASÉ LA CONDICION", "pos:" + position);
                        for (int q = posicion; q < cantidadRegistros; q++) {
                            Persona persona = new Persona();
                            persona = itemListOriginal.get(q);
                            int r = new Random().nextInt(100) + 155;
                            int g = new Random().nextInt(100) + 155;
                            int b = new Random().nextInt(100) + 155;
                            fragmentlist.add(MyFragment.newInstance(persona.getCedula(), persona.getNombre(), Color.rgb(r, g, b)));
                            mpg.notifyDataSetChanged();
                        }


                    }

                }

            }

            @Override
            public void onPageSelected(int i) {
                // pgText.setCurrentItem(i/2);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int lastIdx = mpg.getCount() - 1;

                int curItem = pager.getCurrentItem();
                if (curItem == lastIdx /*&& lastPos==lastIdx*/ && state == 1)
                    lastPageChange = true;
                else lastPageChange = false;
            }
        });


    }

    public void delete(int pos) {
        int i = 0;
        while (i <= fragmentlist.size()) {
            if (pos == fragmentlist.get(i).getPosition()) break;
            i++;

        }
        fragmentlist.remove(i);
        mpg.notifyDataSetChanged();
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<MyFragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, ArrayList<MyFragment> f) {
            super(fm);
            this.fragmentList = f;
        }

        public ArrayList<MyFragment> getFragmentList() {
            return fragmentList;
        }

        public void setFragmentList(ArrayList<MyFragment> fragmentList) {
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int pos) {
            return fragmentlist.get(pos);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
