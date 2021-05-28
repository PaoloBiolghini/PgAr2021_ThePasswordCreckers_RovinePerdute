package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;

public class Percorso {
	private ArrayList <Nodo> percorso = new ArrayList <Nodo>();
	
	public Percorso(ArrayList <Nodo> _percorso) {
		this.percorso = _percorso;
	}
	
	public void addNodo(Nodo nodo) {
		this.percorso.add(nodo);
	}
	
	public ArrayList <Nodo> getRoute(){
		return this.percorso;
	}

}
