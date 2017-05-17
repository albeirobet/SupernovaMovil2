package runcode.co.supernovamovil;

import java.util.ArrayList;
import java.util.List;

import runcode.co.supernovamovil.dm.Persona;

/**
 * Created by root on 17/05/17.
 */

public class CargaPruebaPersonas {

    private List<Persona> listPersonas;

    public CargaPruebaPersonas(){
        listPersonas = new ArrayList<>();
    }


    public List<Persona> getListPersonas() {
        cargarPersonas();
        return listPersonas;
    }

    public void setListPersonas(List<Persona> listPersonas) {
        this.listPersonas = listPersonas;
    }


    public void cargarPersonas(){

        Persona persona1= new Persona();
        persona1.setNombre("Eyder Ascuntar");
        persona1.setCedula("1");
        listPersonas.add(persona1);
        Persona persona2= new Persona();
        persona2.setNombre("Andrea Perez");
        persona2.setCedula("2");
        listPersonas.add(persona2);
        Persona persona3= new Persona();
        persona3.setNombre("Victor Martinez");
        persona3.setCedula("3");
        listPersonas.add(persona3);
        Persona persona4= new Persona();
        persona4.setNombre("Monica Alvarez");
        persona4.setCedula("4");
        listPersonas.add(persona4);
        Persona persona5= new Persona();
        persona5.setNombre("David Burbano");
        persona5.setCedula("5");
        listPersonas.add(persona5);
        Persona persona6= new Persona();
        persona6.setNombre("Luis Bahamon");
        persona6.setCedula("6");
        listPersonas.add(persona6);
        Persona persona7= new Persona();
        persona7.setNombre("Alberto Astudillo");
        persona7.setCedula("7");
        listPersonas.add(persona7);
        Persona persona8= new Persona();
        persona8.setNombre("Jaime Garcia");
        persona8.setCedula("8");
        listPersonas.add(persona8);


        Persona persona9= new Persona();
        persona9.setNombre("Eyder Rosales");
        persona9.setCedula("9");
        listPersonas.add(persona9);
        Persona persona10= new Persona();
        persona10.setNombre("Eyder Muñoz");
        persona10.setCedula("10");
        listPersonas.add(persona10);
        Persona persona11= new Persona();
        persona11.setNombre("Pedro Lopéz");
        persona11.setCedula("11");
        listPersonas.add(persona11);
        Persona persona12= new Persona();
        persona12.setNombre("Eyder Burgos");
        persona12.setCedula("12");
        listPersonas.add(persona12);
        Persona persona13= new Persona();
        persona13.setNombre("Eyder Guerra");
        persona13.setCedula("13");
        listPersonas.add(persona13);
        Persona persona14= new Persona();
        persona14.setNombre("Eyder Bravo");
        persona14.setCedula("14");
        listPersonas.add(persona14);
        Persona persona15= new Persona();
        persona15.setNombre("Eyder Navia");
        persona15.setCedula("15");
        listPersonas.add(persona15);
        Persona persona16= new Persona();
        persona16.setNombre("Eyder Leyton");
        persona16.setCedula("16");
        listPersonas.add(persona16);
        Persona persona17= new Persona();
        persona17.setNombre("Eyder Eraso");
        persona17.setCedula("17");
        listPersonas.add(persona17);
        Persona persona18= new Persona();
        persona8.setNombre("Eyder Gonzales");
        persona8.setCedula("18");
        listPersonas.add(persona18);
        Persona persona19= new Persona();
        persona19.setNombre("Eyder Cerón");
        persona19.setCedula("19");
        listPersonas.add(persona19);
        Persona persona20= new Persona();
        persona20.setNombre("Maritza Ortiz");
        persona20.setCedula("20");
        listPersonas.add(persona20);
        Persona persona21= new Persona();
        persona21.setNombre("Eyder Rivadeneira");
        persona21.setCedula("21");
        listPersonas.add(persona21);
        Persona persona22= new Persona();
        persona22.setNombre("Eyder Velez");
        persona22.setCedula("22");
        listPersonas.add(persona22);
        Persona persona23= new Persona();
        persona23.setNombre("Eyder Cardona");
        persona23.setCedula("23");
        listPersonas.add(persona23);
        Persona persona24= new Persona();
        persona24.setNombre("Eyder Arboleda");
        persona24.setCedula("24");
        listPersonas.add(persona24);
        Persona persona25= new Persona();
        persona25.setNombre("Eyder Ordonez");
        persona25.setCedula("25");
        listPersonas.add(persona25);
        Persona persona26= new Persona();
        persona26.setNombre("Carlos Perez");
        persona26.setCedula("26");
        listPersonas.add(persona26);
        Persona persona27= new Persona();
        persona27.setNombre("Javier Ortiz");
        persona27.setCedula("27");
        listPersonas.add(persona27);



    }
}
