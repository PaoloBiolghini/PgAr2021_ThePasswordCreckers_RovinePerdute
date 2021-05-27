package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Gestisce la lettura e scrittura su file XML, contiene metodi per il parsing
 * da file XML a it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject e viceversa.
 * @author Nicol Stocchetti.
 *
 */
public class XMLParser {
	private static XMLInputFactory inputFactory = creaInputFactory();
	private static XMLOutputFactory outputFactory = creaOutputFactory();
	
	private static XMLStreamReader reader = null;
	private static XMLStreamWriter writer = null;

	/**
	 * Crea e ritorna un oggetto di tipo XMLInputFactory, che viene usato per inizializzare
	 * la relativa variabile di classe.
	 * @return la Input Factory, XMLInputFactory.
	 */
	private static XMLInputFactory creaInputFactory() {
		XMLInputFactory inFactory = null;
		try {
			inFactory = XMLInputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione dell'Input Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return inFactory;
	}
	
	/**
	 * Crea e ritorna un oggetto di tipo XMLOutputFactory, che viene usato per inizializzare
	 * la relativa variabile di classe.
	 * @return la Output Factory, XMLOutputFactory.
	 */
	private static XMLOutputFactory creaOutputFactory() {
		XMLOutputFactory outFactory = null;
		try {
			outFactory = XMLOutputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione dell'Output Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return outFactory;
	}
	
	/**
	 * Inizializza lo stream reader, fornendogli il file XML da leggere, se è impossibile accedere al file termina il programma.
	 * @param percorsoFile il percorso del file da leggere, String.
	 * @return lo stream reader, XMLStreamReader.
	 */
	public static XMLStreamReader impostaReader(String percorsoFile) {
		try {
			XMLParser.reader = XMLParser.inputFactory.createXMLStreamReader(percorsoFile, new FileInputStream(percorsoFile));
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione del reader: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // dato che non c'è il file è inutile che il programma prosegua
		}
		return XMLParser.reader; //in teoria non dovrebbe comunque servire in quanto tutto il parsing XML viene gestito
								  //esclusivamente all'interno dei metodi di questa classe.
	}
	
	/**
	 * Inizializza lo stream writer e crea il documento XML su cui scrivere, se è impossibile creare il file termina il programma.
	 * @param percorsoFile il percorso del file da creare, String.
	 * @return lo stream writer, XMLStreamWriter.
	 */
	public static XMLStreamWriter impostaWriter(String percorsoFile) {
		try {
			XMLParser.writer = XMLParser.outputFactory.createXMLStreamWriter(new FileOutputStream(percorsoFile), "utf-8");
			//XMLParser.writer.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione del writer: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // dato che non c'è il file è inutile che il programma prosegua
		}
		return XMLParser.writer; //in teoria non dovrebbe comunque servire in quanto tutto il parsing XML viene gestito
		  						  //esclusivamente all'interno dei metodi di questa classe.
	}

	/**
	 * Ritorna l'ultimo reader creato.
	 * @return il reader, XMLStreamReader.
	 */
	public static XMLStreamReader getReader() {
		return reader;
	}

	/**
	 * Ritorna l'ultimo writer creato.
	 * @return il writer, XMLStreamWriter.
	 */
	public static XMLStreamWriter getWriter() {
		return writer;
	}
	
	/**
	 * Crea un oggetto di tipo XMLObject parsando il contenuto di un file XML, in caso di errore ritorna null.
	 * @param percorsoFile il percorso del file XML da parsare, String.
	 * @return un oggetto contenente i dati parsati a partire da un file XML (oppure null in caso di errore), XMLObject.
	 */
	public static XMLObject estraiXMLObject(String percorsoFile) {
		XMLObject oggetto = null;
		String XMLDeclarationVersion = "";
		String XMLDeclarationEncoding = "";
		ArrayList <String> textBeforeRoot = new ArrayList <String>();
		XMLElement rootElement = null;
		ArrayList <String> textAfterRoot = new ArrayList <String>();
		
		boolean rootEstratto = false;
		
		try {
			impostaReader(percorsoFile);
			while (reader.hasNext()) { //finché ci sono eventi da leggere...
				switch (reader.getEventType()) { //guarda il tipo di evento
					case XMLStreamConstants.START_DOCUMENT: //inizio del documento -> estrae la declaration
						XMLDeclarationVersion = reader.getVersion();
						XMLDeclarationEncoding = reader.getCharacterEncodingScheme();
						break;
					
					case XMLStreamConstants.START_ELEMENT: //inizio di un elemento -> è l'elemento radice, lo estrae
						rootElement = estraiXMLElement();
						rootEstratto = true;
						break;
					
					case XMLStreamConstants.COMMENT: //i commenti non vanno parsati
						break;
						
					case XMLStreamConstants.CHARACTERS: //testo all’interno di un elemento -> lo estraggo
						if (rootEstratto) {
							textAfterRoot.add(reader.getText());
						} else {
							textBeforeRoot.add(reader.getText());
						}
						break;
					
					default:
						break;
				}
				reader.next();
			}
			oggetto = new XMLObject(XMLDeclarationVersion, XMLDeclarationEncoding, textBeforeRoot, rootElement, textAfterRoot);
		} catch (XMLStreamException e) {
			System.err.println("Eccezione: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			oggetto = null;
		}
		return oggetto;
	}
	
	/**
	 * Crea un oggetto di tipo XMLObject parsando il contenuto di del file "./xml/input.xml", in caso di errore ritorna null.
	 * @return un oggetto contenente i dati parsati a partire da un file XML (oppure null in caso di errore), XMLObject.
	 */
	public static XMLObject estraiXMLObject() {
		return estraiXMLObject("./xml/input.xml");
	}
	
	/**
	 * Crea un oggetto di tipo XMLElement parsando il contenuto di un elemento XML, partendo a scorrere dal tag di apertura
	 * fino ad arrivare al corrispondente tag di chiusura. In caso di errore ritorna null.
	 * @return un oggetto contenente i dati parsati a partire da un elemento XML (oppure null in caso di errore), XMLElement.
	 */
	private static XMLElement estraiXMLElement() {
		XMLElement elemento = null;
		
		try {
			//il reader è sul tag d'inizio dell'elemento -> crea l'elemento parsando il nome del tag e i suoi attributi
			elemento = new XMLElement(reader.getLocalName());
			for (int i = 0; i < reader.getAttributeCount(); i++) {
				elemento.addAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
			} 
			reader.next();//prossimo evento
			while (reader.hasNext()) { //finché ci sono eventi da leggere...
				switch (reader.getEventType()) { //guarda il tipo di evento
					case XMLStreamConstants.START_ELEMENT: // inizio di un elemento figlio -> richiamo la funzione e lo aggiungo ai contenuti di questo elemento
						elemento.addChildElement(estraiXMLElement());
						break;
					
					case XMLStreamConstants.END_ELEMENT: // fine dell'elemento -> esco
						return elemento;
					
					case XMLStreamConstants.COMMENT: //i commenti non vanno parsati
						break;
					
					case XMLStreamConstants.CHARACTERS: //testo all’interno di un elemento -> lo estraggo e aggiungo ai contenuti di questo elemento
						elemento.addText(reader.getText());
						break;
						
					default:
						break;
				}
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.err.println("Eccezione: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			elemento = null;
		}
		return elemento;
	}
	
	
	
	

	
	
	
	/**
	 * Parsa tutte le persone presenti nel file XML inputPersone e le ritorna sotto forma di ArrayList di oggetti di tipo Persona.
	 * @return un ArrayList di persone, ArrayList<Persona>.
	 *
	public static ArrayList<Persona> creaArrayListPersone() {
		ArrayList<Persona> persone = new ArrayList<Persona>();
		//int numeroDiPersone = 0;
		
		impostaReader("./xml/inputPersone.xml");
		try {
			GestoreXML.reader.next(); //salta lo START_DOCUMENT e va allo START_ELEMENT persone
			//numeroDiPersone = Integer.parseInt(GestoreXML.reader.getAttributeValue(0));
			GestoreXML.reader.next();//va al CHARACTER tra persone e persona
			GestoreXML.reader.next(); //va allo START_ELEMENT della prima persona;
			
			do {
				persone.add(estraiPersona());
				
				GestoreXML.reader.next(); // va al CHARACTER tra l'END_ELEMENT di questa persona e lo START_ELEMENT di una nuova persona.
				GestoreXML.reader.next(); //va allo START_ELEMENT di una nuova persona oppure all'END_ELEMENT di persone, se arriviamo alla fine.
			} while(GestoreXML.reader.getLocalName() != "persone");
		
		} catch (XMLStreamException e) {
			System.out.println("Eccezione: ");
			e.printStackTrace();
		}
		return persone;
	}*/
	
	/**
	 *Parsa tutti i codici fiscali presenti nel file XML codiciFiscali e li ritorna sotto forma di ArrayList di oggetti di tipo CodiceFiscale.
	 * @return un ArrayList di codici fiscali, ArrayList<CodiceFiscale>.
	 *
	public static ArrayList<CodiceFiscale> estraiCF(){
		ArrayList<CodiceFiscale> codici = new ArrayList<CodiceFiscale>();
		int beforeElementType = 0;
		String beforeElementName = "";
		
		try {
			GestoreXML.impostaReader("./xml/codiciFiscali.xml");
			
			while(reader.getEventType() != XMLStreamConstants.CHARACTERS) {
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				
					reader.next();
			}//Arriva al primo campo CHARACTERS
			
			while(reader.hasNext()) {
				
				if(beforeElementType == XMLStreamConstants.START_ELEMENT && beforeElementName.equals("codice"))
					codici.add(new CodiceFiscale(reader.getText()));
				
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("Eccezione: ");
			e.printStackTrace();
		}
		return codici;
	}
	
	/**
	 * Dato il nome di un comune estrae il codice corrispondente dal file XML comuni.
	 * @param comune, il nome del comune, String.
	 * @return il codice associato al comune, String.
	 *
	public static String estraiCodiceComune(String comune) {
		int beforeElementType = 0;
		String beforeElementName = "";
		
		try {
			GestoreXML.impostaReader("./xml/comuni.xml");
			
			while(reader.getEventType() != XMLStreamConstants.CHARACTERS) {
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				
					reader.next();
			}//Arriva al primo campo CHARACTERS
			
			while(reader.hasNext()) {
				
				if(beforeElementType == XMLStreamConstants.START_ELEMENT && beforeElementName.equals("nome")) {
					if (reader.getText().equals(comune)) {
						reader.next();
						reader.next();
						reader.next();
						reader.next();
						return reader.getText();
					}
				}
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("Eccezione: ");
			e.printStackTrace();
		}
		
		return "NON TROVATO";
	}
	
	/**
	 * Controlla se il codice fornito corrisponde a quello di un comune.
	 * @param codice il codice, String.
	 * @return true se esiste un comune corrispondente, altrimenti false, boolean.
	 *
	public static boolean esistenzaCodiceComune(String codice) {
		int beforeElementType = 0;
		String beforeElementName = "";
		
		try {
			GestoreXML.impostaReader("./xml/comuni.xml");
			
			while(reader.getEventType() != XMLStreamConstants.CHARACTERS) {
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				
					reader.next();
			}//Arriva al primo campo CHARACTERS
			
			while(reader.hasNext()) {
				
				if(beforeElementType == XMLStreamConstants.START_ELEMENT && beforeElementName.equals("nome")) {
					reader.next();
					reader.next();
					reader.next();
					reader.next();
					if (reader.getText().equals(codice))
						return true;		
				}
				beforeElementType = reader.getEventType();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT)
					beforeElementName = reader.getLocalName();
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("Eccezione: ");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * A partire da un oggetto di tipo CodiceFiscale genera il codice XML che descrive la persona ad esso associata.
	 * @param cf il codice fiscale, it.unibs.arnaldo.geriatricpark.codicefiscale.CodiceFiscale
	 * @param codiciEstratti i codici fiscali estratti dal file XML codiciFiscali, ArrayList<CodiceFiscale>
	 */
	
	/**----------------------------------
	
	private static void scriviElemento(XMLElement element) {
		
		try {
			writer.writeStartElement(element.getTag()); //apertura del tag
			
			if (element.getAttributes() != null) {//scrittura attributi
				for (String key : element.getOrderedAttributes()) {
					writer.writeAttribute(key, element.getAttributes().get(key));
				}
			}
			
			if (element.getContent() != null) {//scrittura contenuto
				for (Object obj : element.getContent()) {
					if (è stringa)
				}
			}
			
			
			
			
			writer.writeStartElement("persona"); // scrittura del tag di apertura persona
			writer.writeAttribute("id", Integer.toString(cf.getPersona().getId())); // inserisce l'attributo id
			
			writer.writeStartElement("nome"); // scrittura del tag di apertura nome
			writer.writeCharacters(cf.getPersona().getNome()); // inserisce nome nel CHARACTERS
			writer.writeEndElement(); // chiusura dell'ultimo tag aperto
			
			writer.writeStartElement("cognome"); // scrittura del tag di apertura cognome
			writer.writeCharacters(cf.getPersona().getCognome()); // inserisce cognome nel CHARACTERS
			writer.writeEndElement(); // chiusura dell'ultimo tag aperto
			
			writer.writeStartElement("sesso"); // e così via...
			writer.writeCharacters(cf.getPersona().getSesso() + ""); 
			writer.writeEndElement();
			
			writer.writeStartElement("comune_nascita");
			writer.writeCharacters(cf.getPersona().getComuneDiNascita()); 
			writer.writeEndElement();
			
			writer.writeStartElement("data_nascita");
			writer.writeCharacters(Integer.toString(cf.getPersona().getAnnoDiNascita()) + "-" + Integer.toString(cf.getPersona().getMeseDiNascita()) + "-" +  Integer.toString(cf.getPersona().getGiornoDiNascita())); 
			writer.writeEndElement();
			
			writer.writeStartElement("codice_fiscale");
			
			for(CodiceFiscale codiceEstratto : codiciEstratti) {
				if(codiceEstratto.getValore().equals(cf.getValore())) {
					presenteInElenco = true;
					break;
				}
			}
			
			if(presenteInElenco)
				writer.writeCharacters(cf.getValore()); 
			else
				writer.writeCharacters("ASSENTE"); 
			
			writer.writeEndElement();
			
			writer.writeEndElement(); //chiusura del tag persona
			
		} catch (Exception e) {
			System.err.println("Eccezione: ");
			}
	}*/
	
	/**
	 * Genera il documento XML codiciPersone e lo compila come richiesto.
	 * @param codiciPersone i codici fiscali generati dal documento inputPersone, ArrayList<CodiceFiscale>
	 * @param codiciEstratti i codici fiscali generati dal documento codiciFiscali, ArrayList<CodiceFiscale>
	 * @param codiciInvalidi i codici fiscali generati dal documento codiciFiscali che risultano invalidi, ArrayList<CodiceFiscale>
	 * @param codiciSpaiati i codici fiscali generati dal documento codiciFiscali che risultano spaiati rispetto a quelli generati
	 * dal file inputPersone, ArrayList<CodiceFiscale>
	 */
	
	/**-----------------------------------------------------------------------
	public static void scriviDocumento(XMLObject object) {
		XMLElement rootElement = object.getRootElement();
		
		impostaWriter("./xml/output.xml");
		
		try {
			writer.writeStartDocument(object.getXMLDeclarationEncoding(), object.getXMLDeclarationVersion());
			
			if (object.getTextBeforeRoot() != null) {
				for (String text : object.getTextBeforeRoot()) {
					writer.writeCharacters(text);
				}
			}
			
			writer.writeStartElement(rootElement.getTag()); // apertura del tag radice
			if (rootElement.getAttributes() != null) {
				for (String key : rootElement.getOrderedAttributes()) {
					writer.writeAttribute(key, rootElement.getAttributes().get(key));
				}
			}
			
			
			
			
			writer.writeStartElement("persone"); // apertura del tag persone
			writer.writeAttribute("numero", Integer.toString(codiciPersone.size())); // inserisce l'attributo id
			
			for (CodiceFiscale codicePersona : codiciPersone)
				scriviPersona(codicePersona, codiciEstratti);
			
			writer.writeEndElement(); // chiusura del tag persone
			
			
			writer.writeStartElement("codici"); // apertura del tag codici
			
			writer.writeStartElement("invalidi"); // apertura del tag invalidi
			writer.writeAttribute("numero", Integer.toString(codiciInvalidi.size())); // inserisce l'attributo numero
			
			for (CodiceFiscale cf : codiciInvalidi) {
				writer.writeStartElement("codice");
				writer.writeCharacters(cf.getValore());
				writer.writeEndElement();
			}
			
			writer.writeEndElement(); // chiusura del tag invalidi
			
			writer.writeStartElement("spaiati"); // apertura del tag spaiati
			writer.writeAttribute("numero", Integer.toString(codiciSpaiati.size())); // inserisce l'attributo numero
			
			for (CodiceFiscale cf : codiciSpaiati) {
				writer.writeStartElement("codice");
				writer.writeCharacters(cf.getValore());
				writer.writeEndElement();
			}
			
			writer.writeEndElement(); // chiusura del tag spaiati
			
			writer.writeEndElement(); // chiusura del tag codici
			
			
			
			
			
			
			
			
			
			
			writer.writeEndElement(); // chiusura del tag radice output
			writer.writeEndDocument(); // apertura della fine del documento
			writer.flush(); // svuota il buffer e procede alla scrittura
			writer.close(); // chiusura del documento e delle risorse impiegate
			} catch (Exception e) { // se c’è un errore viene eseguita questa parte
			System.err.println("Errore nella scrittura del file XML!");
			}
	}*/
	
}

