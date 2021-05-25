package it.unibs.fp.Prova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Paese {

	private String nome;
	private int id;
	private Posizione posizione;
	private ArrayList<Integer> sentieri=new ArrayList<>();
	
	public Paese(int _id,String _nome,double _x,double _y, double _z)
	{
		this.id=_id;
		this.nome=_nome;
		posizione= new Posizione( _x, _y, _z);
		
	}
	
	
	public Posizione getPosizione() {
		return posizione;
	}


	public String getNome() {
		return nome;
	}
	
	public void addSentiero(Integer _id)
	{
		sentieri.add(_id);
	}
	
	
	
}
