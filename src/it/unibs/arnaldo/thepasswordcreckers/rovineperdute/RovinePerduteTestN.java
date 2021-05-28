package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;

public class RovinePerduteTestN {
    static String titolo="Rovine perdute";
    static String[] voci={"Mappa da 5","Mappa da 12","Mappa da 50","Mappa da 200","Mappa da 2000","Mappa da 10000"};

    public static void main(String[] args) {

MyMenu menu=new MyMenu(titolo,voci,false);
menu.scegli();


        /*
        long start = System.currentTimeMillis();

        XMLObject fileXML;
        ArrayList<Nodo> nodi;

        System.out.println("\n_____Parsing del fileXML all'XMLObject e toString() dell'XMLObject:_____\n");
        fileXML = XMLParser.estraiXMLObject("PgAr_Map_5.xml");

        System.out.println(fileXML.toString());

        long step1 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step1 - start) + " (" + ((step1 - start) / 1000.0) + " secondi)");

        System.out.println("\n_____Creazione dell'ArrayList di nodi a partire dall'XMLObject e toString() dei nodi:_____\n");

        nodi = TraduttoreXMLObjectNodo.daXMLObjectANodi(fileXML);

        for (Nodo nodo : nodi) {
            System.out.println(nodo.toString());
        }

        long step2 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step2 - step1) + " (" + ((step2 - step1) / 1000.0) + " secondi)");

        System.out.println("\n_____Ricreazione dell'XMLObject dall'ArrayList di nodi e toString() dell'XMLObject:_____\n");

        XMLObject objct;

        objct = TraduttoreXMLObjectNodo.daNodiAXMLObject(nodi, "\t", "\n");
        System.out.println(objct.toString());

        long step3 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step3 - step2) + " (" + ((step3 - step2) / 1000.0) + " secondi)");

        System.out.println("\n_____Parsing dell'XMLObject al fileXML:_____\n");

        XMLParser.scriviDocumento(objct,"output.xml");

        long step4 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step4 - step3) + " (" + ((step4 - step3) / 1000.0) + " secondi)");

        System.out.println("\n_____FileXML di output completato._____\n");

        long end = System.currentTimeMillis();
        System.out.println("Totale:\n");
        System.out.println("Inizio: \t" + start);
        System.out.println("Fine: \t\t" + end);
        System.out.println("\nDurata: \t" + (end - start) + " (" + ((end - start) / 1000.0) + " secondi)");


        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");

        //Per controllare solo il parsing da xml a XMLObject e di nuovo a xml.

        XMLObject fileXML2;
        ArrayList<Nodo> nodi2;

        long step00 = System.currentTimeMillis();

        System.out.println("\n_____Parsing del fileXML all'XMLObject e toString() dell'XMLObject:_____\n");
        fileXML2 = XMLParser.estraiXMLObject("PgAr_Map_50.xml");

        //System.out.println(fileXML.toString());

        long step11 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step11 - step00) + " (" + ((step11 - step00) / 1000.0) + " secondi)");

        System.out.println("\n_____Parsing dell'XMLObject al fileXML:_____\n");

        XMLParser.scriviDocumento(fileXML2, "zzzzzz.xml");

        long step22 = System.currentTimeMillis();
        System.out.println("\nDurata: \t" + (step22 - step11) + " (" + ((step22 - step11) / 1000.0) + " secondi)");

        System.out.println("\n_____FileXML di output completato._____\n");

        System.out.println("Totale:\n");
        System.out.println("Inizio: \t" + step00);
        System.out.println("Fine: \t\t" + step22);
        System.out.println("\nDurata: \t" + (step22 - step00) + " (" + ((step22 - step00) / 1000.0) + " secondi)");

        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");

        //Test Parser finale

        ArrayList<Nodo> percorso1 = new ArrayList<Nodo>();
        ArrayList<Nodo> percorso2 = new ArrayList<Nodo>();
        Percorso p1;
        Percorso p2;
        XMLObject prcrsi;

        percorso1.add(new Nodo(0, "CAMISANO", new int[]{0, 0, 1}, null));
        percorso1.add(new Nodo(1, "CASALE", new int[]{2, 1, 1}, null));
        percorso1.add(new Nodo(2, "SERGNANO", new int[]{2, 3, 2}, null));

        percorso2.add(new Nodo(3, "SPINO D'ADDA", new int[]{3, 3, 3}, null));
        percorso2.add(new Nodo(4, "PANDINO", new int[]{4, 4, 5}, null));

        p1 = new Percorso(percorso1);
        p2 = new Percorso(percorso2);

        prcrsi = TraduttoreXMLObjectNodo.daPercorsoAXMLObject(p1, p2, "   ", "\n");

        XMLParser.scriviDocumento(prcrsi, "Routes.xml");

        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
		/*

		System.out.println("\n_________________________\n");
		
		XMLElement e;
		HashMap<Nodo, Double> collegamenti = new HashMap<Nodo, Double>();
		
		collegamenti.put(new Nodo(2, "MEME", new int[]{1, 2, 1} , null), 3.3);
		collegamenti.put(new Nodo(4, "UBINA", new int[]{1, 2, 1} , null), 6.7);
		collegamenti.put(new Nodo(9, "MEME", new int[]{1, 2, 1} , collegamenti), 3.3);
		
		e = TraduttoreXMLObjectNodo.daNodoAXMLElement(new Nodo(33, "UBE", new int[]{1, 2, 1} , collegamenti), "\t\t\t", "\n");
		System.out.println(e.toString());
		
		System.out.println("\n_________________________\n");
		
		XMLObject o;
		ArrayList <Nodo> nodiz = new ArrayList <Nodo>();
		
		nodiz.add(new Nodo(33, "UBE", new int[]{1, 2, 1} , collegamenti));
		nodiz.add(new Nodo(23, "UBINA", new int[]{7, 4, 3} , collegamenti));
		nodiz.add(new Nodo(13, "MEME", new int[]{5, 6, 8} , collegamenti));
		o = TraduttoreXMLObjectNodo.daNodiAXMLObject(nodiz, "\t", "\n");
		System.out.println(o.toString());
		
		XMLParser.scriviDocumento(o);

	}
	*/
    }
}
