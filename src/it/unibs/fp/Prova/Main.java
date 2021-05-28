package it.unibs.fp.Prova;

import java.util.ArrayList;
import java.util.Map;

import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Grafo;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.MyMenu;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Nodo;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLParser;
import it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Percorso;

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
        
        ArrayList <Nodo> listaNodi=graf1.getNodi();
        int nextNodo=0;
        int LISTASIZE=listaNodi.size();
        int costoTotale=0;
        Percorso p=new Percorso(listaNodi.get(0));
        while(nextNodo!=LISTASIZE-1)
        {
        	Nodo attualeNodo=listaNodi.get(nextNodo);
        	int findNext=getNext(attualeNodo,listaNodi.get(LISTASIZE-1));
        	//calcolo il costo andando a cercare il costo nell hashmap tra i due nodi scelti
        	costoTotale+=attualeNodo.getCollegamenti().get(listaNodi.get(findNext));     	
        	p.addNodo(listaNodi.get(findNext));
        	
        	nextNodo=findNext;
        	System.out.println("Scelta"+nextNodo);
        }
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
    
    /**
     * ritorna l'id del nodo successivo che corrisponde anche alla posizione sull' array del nodo
     */
    public static int getNext(Nodo nodoOra,Nodo fine)
    {
    	//prendo la lista dei nodi che sono in collegamento con il nodo di partenza
    	Map<Nodo,Double> nodiConnessi=nodoOra.getCollegamenti();
    	//inzializzo le variabili che sono anche di controllo per la prima iterazione
    	double min=0;
    	int pos=-1;
    	
    	//per ogni elemento dell' hasmap prendo il nodo e il costo e trovo il costo totale calcolato
    	//con il metodo euristico
    	for (Map.Entry mapElement : nodiConnessi.entrySet())
    	{
    		Nodo actualNodo=(Nodo)mapElement.getKey();
    		double costo=(double)mapElement.getValue();
    		System.out.println("distanzeeee:"+calcolaDist(actualNodo,fine));
    		costo+=calcolaDist(actualNodo,fine);
    		//controllo che non sia il primo inserimento
    		if(pos==-1)
    		{
    			min=costo;
    			pos=actualNodo.getId();
    		}else {
    			//se è almeno il secondo inserimento controllo il minor costo
    			if(costo<min)
    			{
    				min=costo;
        			pos=actualNodo.getId();
    			}
    		}
    		
    		System.out.println(actualNodo.getId()+" "+costo);
    	}
    	
    	return pos;
    }
    
    /**
	 * Metodo euristico che calcola la distanza in lina d'aria tra due Nodi
	 * @param paese1
	 * @param paese2
	 * @return
	 */
	static public double calcolaDist(Nodo paese1,Nodo paese2)
	{
		int p1[]=paese1.getCoordinate();
		int p2[]=paese2.getCoordinate();
		
		double xdis=Math.pow(p1[0]-p2[0], 2);
		double ydis=Math.pow(p1[1]-p2[1], 2);;
		
		double dist=Math.sqrt(xdis+ydis);
		return dist;
	}
}
