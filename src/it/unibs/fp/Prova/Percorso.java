package it.unibs.fp.Prova;

import java.util.ArrayList;

public class Percorso {

	private int nCitt�;
	private double carburante;
	private double distanza;
	private ArrayList<String> listaCitt�;
	private Paese lastPaese;
	private int idArrivo;
	
	/**
	 * costruttore per creare un percorso da 0
	 * @param _arrivo
	 */
	public Percorso(int _arrivo) {
		this.idArrivo=_arrivo;
		nCitt�=0;
		carburante=0;
		listaCitt�=new ArrayList<>();
		distanza=0;
		lastPaese=null;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * costruttore invocato nella suddivisione di un percorso in altri 
	 * @param p
	 */
	public Percorso(Percorso p)
	{
		this.nCitt�=p.getnCitt�();
		this.carburante=p.getCarburante();
		this.distanza=p.getCarburante();
		this.idArrivo=p.getIdArrivo();
		this.listaCitt�=(ArrayList<String>) p.getListaCitt�().clone();
	}
	
	public int getnCitt�() {
		return nCitt�;
	}

	public double getCarburante() {
		return carburante;
	}

	public double getDistanza() {
		return distanza;
	}

	public ArrayList<String> getListaCitt�() {
		return listaCitt�;
	}


	public int getIdArrivo() {
		return idArrivo;
	}

/**
 * invocato per aggiungere le citt� al percorso
 * calcola distanza carburante ed aggiorna la lista della citt�
 * @param p
 */
	public void addCitt�(Paese p)
	{
		//controllo se non � ancora stato inserito nessun paese
		if(lastPaese==null)
		{
			lastPaese=p;
			nCitt�=1;
			listaCitt�.add(p.getNome());
			return;
		}
		
		distanza+=calcolaDist(p,lastPaese);
		listaCitt�.add(p.getNome());
		nCitt�++;
		//formula aggiunta del carburante in base alla distanza
		lastPaese=p;
		
		
		
	}
	
	/**
	 * dati due punti calcola la distanza
	 * @param paese1
	 * @param paese2
	 * @return
	 */
	static public double calcolaDist(Paese paese1,Paese paese2)
	{
		Posizione p1=paese1.getPosizione();
		Posizione p2=paese2.getPosizione();
		
		double xdis=Math.pow(p1.getX()-p2.getX(), 2);
		double ydis=Math.pow(p1.getY()-p2.getY(), 2);;
		//double zdis=Math.pow(p1.getZ()-p2.getZ(), 2);;
		double dist=Math.sqrt(xdis+ydis);
		return dist;
	}
}
