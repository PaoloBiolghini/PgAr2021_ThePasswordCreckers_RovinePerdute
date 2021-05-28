package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

/*
 * Rappresenta un men� testuale generico, a pi� voci, selezionabili tramite la digitazione di un intero.
 * La scelta per uscire � sempre associata al valore 0, se presente.
 */
public class MyMenu {
	
	final private static int CORNICE = 3;
	final private static String VOCE_USCITA = "0 - Esci";
	final private static String RICHIESTA_INSERIMENTO = "\nDigita il numero dell'opzione desiderata\n> ";
	
	private String titolo;
	private String [] voci;
	private boolean uscita;
	
	/**
	 * Il metodo costurttore del men�.	
	 * @param titolo il titolo del men�, String.
	 * @param voci le varie scelte che il men� dovr� presentare a video, String[].
	 */
	public MyMenu(String titolo, String [] voci, boolean uscita) {
		this.titolo = titolo;
		this.voci = voci;
		this.uscita = uscita;
	}
	
	/**
	 * Presenta a video il men�.
	 */
	public void stampaMenu(boolean numeri) {
		System.out.print("\t");
		for (int i = 0; i < titolo.length()+2*CORNICE; i++) {
			System.out.print("-");
		}
		System.out.println();
	
		System.out.print("\t");
		for (int i = 0; i < CORNICE; i++) {
			System.out.print(" ");
		}
		System.out.println(titolo);
		
		System.out.print("\t");
		for (int i = 0; i < titolo.length()+2*CORNICE; i++) {
			System.out.print("-");
		}
		System.out.println();
		
		for (int i=0; i<voci.length; i++) {
			System.out.print("\t ");
			if(numeri) {
				System.out.print((i+1) + " - ");
			}
			System.out.println(voci[i]);
		}
		
		if (uscita) {
			System.out.println();
			System.out.println("\t " + VOCE_USCITA);
			System.out.println();
		}
	}
	
	/**
	 * Visualizza il men� e gestisce il corretto inserimento dell'opzione scelta da parte dell'utente, controllando che
	 * essa sia ammissibile per valore e formato.
	 * @return l'intero associato alla scelta effettuata dall'utente, int.
	 */
	public int scegli() {
		int min = 0;
		
		if(uscita)
			min = 0;
		else
			min = 1;
		
		stampaMenu(true);
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, min, voci.length);

	}
	  
  
  
	
}

