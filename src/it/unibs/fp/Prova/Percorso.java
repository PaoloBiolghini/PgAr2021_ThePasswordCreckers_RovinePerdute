package it.unibs.fp.Prova;

import java.util.ArrayList;

public class Percorso {

	private int nCittà;
	private double carburante;
	private double distanza;
	private ArrayList<String> listaCittà;
	private Paese lastPaese;
	private String arrivo;
	
	/**
	 * costruttore per creare un percorso da 0
	 * @param _arrivo
	 */
	public Percorso(String _arrivo) {
		this.arrivo=_arrivo;
		nCittà=0;
		carburante=0;
		listaCittà=new ArrayList<>();
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
		nCittà=p.getnCittà();
		carburante=p.getCarburante();
		distanza=p.getCarburante();
		arrivo=p.getArrivo();
		listaCittà=(ArrayList<String>) p.getListaCittà().clone();
	}
	
	public int getnCittà() {
		return nCittà;
	}

	public double getCarburante() {
		return carburante;
	}

	public double getDistanza() {
		return distanza;
	}

	public ArrayList<String> getListaCittà() {
		return listaCittà;
	}

	public String getArrivo() {
		return arrivo;
	}
	
	
/**
 * invocato per aggiungere le città al percorso
 * calcola distanza carburante ed aggiorna la lista della città
 * @param p
 */
	public void addCittà(Paese p)
	{
		//controllo se non è ancora stato inserito nessun paese
		if(lastPaese==null)
		{
			lastPaese=p;
			nCittà=1;
			listaCittà.add(p.getNome());
			return;
		}
		
		distanza+=calcolaDist(p,lastPaese);
		listaCittà.add(p.getNome());
		nCittà++;
		//formula aggiunta del carburante in base alla distanza
		lastPaese=p;
		
		
	}
	
	/**
	 * dati due punti calcola la distanza
	 * @param paese1
	 * @param paese2
	 * @return
	 */
	public double calcolaDist(Paese paese1,Paese paese2)
	{
		Posizione p1=paese1.getPosizione();
		Posizione p2=paese2.getPosizione();
		
		double xdis=Math.pow(p1.getX()-p2.getX(), 2);
		double ydis=Math.pow(p1.getY()-p2.getY(), 2);;
		double zdis=Math.pow(p1.getZ()-p2.getZ(), 2);;
		double dist=Math.sqrt(xdis+ydis+zdis);
		return dist;
	}
}
