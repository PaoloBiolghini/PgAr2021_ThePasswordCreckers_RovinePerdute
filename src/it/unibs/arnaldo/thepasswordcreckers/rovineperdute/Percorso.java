package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;

public class Percorso {
	private ArrayList <Nodo> percorso = new ArrayList <Nodo>();
	private double costoTotale;
	
	public Percorso(Nodo _percorso) {
		this.percorso.add(_percorso);
	}

	public void addNodo(Nodo nodo) {
		this.percorso.add(nodo);
	}
	
	public ArrayList <Nodo> getPercorso(){
		return this.percorso;
	}
	
	public void setCostoTotale(double _costoTotale) {
		this.costoTotale = _costoTotale;
	}
	
	public double getCostoTotale() {
		return this.costoTotale;
	}

}
