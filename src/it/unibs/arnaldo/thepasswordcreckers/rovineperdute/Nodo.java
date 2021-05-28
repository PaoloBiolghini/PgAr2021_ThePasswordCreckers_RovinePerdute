package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Nodo {
	private int id;
	private String nome;
	private int [] coordinate = new int [3];
	private HashMap <Nodo, Double> collegamenti;
	
	
	public Nodo(int id, String nome, int[] coordinate, HashMap<Nodo, Double> collegamenti) {
		super();
		this.id = id;
		this.nome = nome;
		this.coordinate = coordinate;
		this.collegamenti = collegamenti;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int[] getCoordinate() {
		return coordinate;
	}


	public void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}


	public HashMap<Nodo, Double> getCollegamenti() {
		return collegamenti;
	}


	public void setCollegamenti(HashMap<Nodo, Double> collegamenti) {
		this.collegamenti = collegamenti;
	}


	@Override
	public String toString() {
		StringJoiner nod =new StringJoiner(", ","[ "," ]" );
		nod.add("Nodo id= " +id)
				.add("Nome= "+nome)
				.add("Coordinate= " + Arrays.toString(coordinate));
		for (Map.Entry<Nodo,Double> chiave : collegamenti.entrySet()) {
			nod.add("collegamento: " + chiave.getKey().id).add("peso -> "+String.format("%.2f",chiave.getValue()));
		}
		return nod.toString();
	}
	
}
