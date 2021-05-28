package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {
    ArrayList<Nodo> nodi;
    private String map;
    public Grafo(String _map){
        this.map=_map;
        nodi=TraduttoreXMLObjectNodo.daXMLObjectANodi(XMLParser.estraiXMLObject(map));
    }

    /**
     * imposto i nodi del grafo con i loro vari collegamenti a partire dal oggetto XML creato durante la lettura del file
     * @param mappa rappresenta il file XML da cui costruiremmo l'oggetto XML
     * @return
     */




    /**
     * Setto i pesi dei collegamenti di ogni nodo
     *
     */
 public void setEdgeTonatiuh(){
        double edge;
     HashMap<Nodo,Double> c;
        for(int i=0;i<nodi.size();i++) {
               c= nodi.get(i).getCollegamenti();
               for(Nodo key:c.keySet()){
                   edge= addEdgeTonatiuh(nodi.get(i),key);
                   nodi.get(i).getCollegamenti().put(key,edge);
               }

        }
 }

    /**
     * Calcolo la distanza euclidea di ogni nodo prendendo  le coordinate X e Y
     * @param a nodo di partenza
     * @param b nodo a cui è collegato il nodo di partenza
     * @return
     */
    public double addEdgeTonatiuh(Nodo a, Nodo b){
        double edge;
        double distanzaX=Math.pow(b.getCoordinate()[0]-a.getCoordinate()[0],2);
        double distanzaY=Math.pow(b.getCoordinate()[1]-a.getCoordinate()[1],2);
        edge=Math.sqrt(distanzaX+distanzaY);
        return edge;
    }


    public void setEdgeMetztli(){
        double edge;
        HashMap<Nodo,Double> c;
        for(int i=0;i<nodi.size();i++) {
            c= nodi.get(i).getCollegamenti();
            for(Nodo key:c.keySet()){
                edge= addEdgeMetztli(nodi.get(i),key);
                nodi.get(i).getCollegamenti().put(key,edge);
            }

        }
    }

    public double addEdgeMetztli(Nodo a,Nodo b){
        double edge;
        edge=Math.abs(a.getCoordinate()[2]-b.getCoordinate()[2]);
        return edge;
    }

 public void stampa(){
nodi.stream().forEach(System.out::println);

 }

}
