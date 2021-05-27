package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;

public class RovinePerduteTestN {

	public static void main(String[] args) {
		XMLObject fileXML;
		XMLElement city;
		ArrayList <Nodo> nodi = new ArrayList <Nodo>();
		
		fileXML = XMLParser.estraiXMLObject();
		
		System.out.println(fileXML.toString());
		
		System.out.println("\n_________________________\n");
		
		//Scorre gli oggetti contenuti nell'elemento root del file XML, che corrispondono alle varie città
		for (Object oggetto : fileXML.getRootElement().getContent()) {
			//Controlla che l'oggetto sia un XMLElement, perché potrebbero anche esserci per esempio stringhe di testo tra un XMLElement e l'altro
			if(!(oggetto instanceof XMLElement)) {
				continue; //Se è un oggetto diverso dall'XMLElement non lo considera e passa all'oggetto successivo...
			}
			city = (XMLElement) oggetto;
			
			//Se per caso l'elemento non è di tipo city lo ignora.
			if (!city.getTag().equals("city")) {
				continue;
			}
			//Parsing dall'XMLElement contenente i dati della città alla classe Nodo (che rappresenta una città nel grafo).
			nodi.add(TraduttoreXMLObjectNodo.daXMLElementANodo(city));
		}
		//Ora ho una lista di nodi (città).
		
		//Scorro ciascun nodo e imposto i suoi collegamenti con altri nodi.
		for (Nodo nodo : nodi) {
			TraduttoreXMLObjectNodo.impostaCollegamenti(nodo, nodi, fileXML.getRootElement());
		}
		
		
		
		for (Nodo nodo : nodi) {
			System.out.println(nodo.toString());
		}

		System.out.println("\n_________________________\n");
		
		XMLElement e;
		HashMap<Nodo, Double> collegamenti = new HashMap<Nodo, Double>();
		
		collegamenti.put(new Nodo(2, "MEME", new int[]{1, 2, 1} , null), 3.3);
		collegamenti.put(new Nodo(4, "UBINA", new int[]{1, 2, 1} , null), 6.7);
		collegamenti.put(new Nodo(9, "MEME", new int[]{1, 2, 1} , collegamenti), 3.3);
		
		e = TraduttoreXMLObjectNodo.daNodoAXMLElement(new Nodo(33, "UBE", new int[]{1, 2, 1} , collegamenti));
		System.out.println(e.toString());
		
		System.out.println("\n_________________________\n");
		
		XMLObject o;
		ArrayList <Nodo> nodiz = new ArrayList <Nodo>();
		
		nodiz.add(new Nodo(33, "UBE", new int[]{1, 2, 1} , collegamenti));
		nodiz.add(new Nodo(23, "UBINA", new int[]{7, 4, 3} , collegamenti));
		nodiz.add(new Nodo(13, "MEME", new int[]{5, 6, 8} , collegamenti));
		o = TraduttoreXMLObjectNodo.daNodiAXMLObject(nodiz);
		System.out.println(o.toString());
		
	}
	
}
