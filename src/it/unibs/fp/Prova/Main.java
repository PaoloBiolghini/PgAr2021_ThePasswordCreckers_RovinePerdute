package it.unibs.fp.Prova;

import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Grafo;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.MyMenu;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLParser;

public class Main {
    static String titolo="Seleziona quale mappa usare";
    static String[] voci={"Mappa da 5","Mappa da 12","Mappa da 50","Mappa da 200","Mappa da 2000","Mappa da 10000"};
    static String[] mappe={"PgAr_Map_5.xml","PgAr_Map_12.xml","PgAr_Map_50.xml","PgAr_Map_200.xml","PgAr_Map_2000.xml","PgAr_Map_10000.xml"};
    public static void main(String[] args) {
        String map="";
        MyMenu menu = new MyMenu(titolo, voci, false);
       int scelta= menu.scegli();
        switch(scelta){
            case 1:
               map=mappe[0];
               break;
            case 2:
                map=mappe[1];
                break;
            case 3:
                map=mappe[2];
                break;
            case 4:
                map=mappe[3];
                break;
            case 5:
                map=mappe[4];
                break;
            case 6:
                map=mappe[5];
                break;
            default:
                System.err.println("errore nella selezione");
        }


        Grafo graf1=new Grafo(map);
        graf1.setEdgeTonatiuh();
        //graf1.stampa();
/*
        //XMLObject file= XMLParser.estraiXMLObject(map);
        //.out.println(file.toString());
        long start = System.currentTimeMillis();



        System.out.println("--------------------------------------");
        //Grafo graf2=new Grafo(map);

        graf1.setEdgeMetztli();
        //graf2.stampa();
        long end = System.currentTimeMillis();
        System.out.println("Totale:\n");
        System.out.println("Inizio: \t" + start);
        System.out.println("Fine: \t\t" + end);
        System.out.println("\nDurata: \t" + (end - start) + " (" + ((end - start) / 1000.0) + " secondi)");
        */

    }
}
