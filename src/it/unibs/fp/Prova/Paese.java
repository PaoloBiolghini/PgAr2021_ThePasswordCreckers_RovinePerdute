package it.unibs.fp.Prova;

import java.util.HashMap;
import java.util.Map;

public class Paese {

	private String nome;
	private Posizione posizione;
	private Map<Integer,String> sentieri=new HashMap();
	
	public Paese(String _nome,double _x,double _y, double _z)
	{
		this.nome=_nome;
		posizione= new Posizione( _x, _y, _z);
	}
	
	
	public Posizione getPosizione() {
		return posizione;
	}


	public String getNome() {
		return nome;
	}
	
	public void addSentiero()
	{
		
	}
	
	
	
}
