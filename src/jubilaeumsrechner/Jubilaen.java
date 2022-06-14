package jubilaeumsrechner;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Dies ist die Hauptklasse des Jubilaeumsrechnerprojekts, welche die Benutzeroberflaeche generiert und alle Berechnungen anstoesst.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Jubilaen {

	public static JFrame frame1 = new JFrame("Jubiläumsrechner");
    private JButton buttonBerechnen = new JButton("Berechnen");
    private Ergebnisse ergebnisse = new Ergebnisse();
    private ArrayList<Jubilaeum> jubilaeumsliste = new ArrayList<Jubilaeum>();
    private Zeitrechner zeitAnfang;
    private Zeitrechner zeitEnde;
    
    public Jubilaen() {
    	zeitAnfang = new Zeitrechner("Jubiläum","07.08.2004 09:00:00");
    	zeitEnde = new Zeitrechner("Berechnen bis","01.01."+(Calendar.getInstance().get(Calendar.YEAR)+2)+" 00:00:00");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(500,500));
		frame1.setMinimumSize(new Dimension(500,500));
		frame1.setMaximumSize(new Dimension(500,500));
		frame1.setResizable(true);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridBagLayout());
		
		cp.add(zeitAnfang,new GridBagFelder(0,0,1,1,1,0.10));
		cp.add(zeitEnde,new GridBagFelder(0,1,1,1,1,0.10));
		cp.add(buttonBerechnen,new GridBagFelder(0,2,1,1,1,0.02));
		cp.add(ergebnisse,new GridBagFelder(0,3,1,1,1,0.78));
		
		buttonBerechnen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				berechnen();
				ausgabe();
			}
		});
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		zeitAnfang.umrechnen();
		zeitEnde.umrechnen();
    }
    
    /**
     * Diese Methode berechnet die wichtigsten Datumsangaben und startet alle relevanten Berechnungsmethoden.
     */
    private void berechnen() {
    	jubilaeumsliste.clear();
    	Calendar jubilaeumsCal = zeitAnfang.dorZeit.getGregKalender();
    	Calendar heute = Calendar.getInstance();
    	Calendar until = zeitEnde.dorZeit.getGregKalender();
    	long jubilaeumsCalSek = jubilaeumsCal.getTimeInMillis()/1000;
    	long heuteSek = heute.getTimeInMillis()/1000;
    	long untilSek = until.getTimeInMillis()/1000;
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().ausgabe(jubilaeumsCalSek, heuteSek, untilSek, "Sekunden", 1));
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().ausgabe(jubilaeumsCalSek, heuteSek, untilSek, "Minuten", 60));
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().ausgabe(jubilaeumsCalSek, heuteSek, untilSek, "Stunden", 60*60));
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().ausgabe(jubilaeumsCalSek, heuteSek, untilSek, "Tage", 60*60*24));
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().ausgabe(jubilaeumsCalSek, heuteSek, untilSek, "Wochen", 60*60*24*7));
    	jubilaeumsliste.addAll(new Jubilaeumsgenerator().sonderfall(jubilaeumsCalSek, heuteSek, untilSek, "Monate"));
    	jahre(jubilaeumsCalSek,heuteSek,untilSek);
    }
    
    /**
     * Diese Methode rechnet manuell die Anzahl vergangener Jahre aus, was im anderen Berechnungsraster schwierig gewesen ist.
     * @param jubilaeumsCalSek Die Unix-Sekunden-Zahl des Jubilaeums.
     * @param heuteSek Die Unix-Sekunden-Zahl des aktuellen Zeitpunkts.
     * @param untilSek Die Unix-Sekunden-Zahl des Datums bis zu welchem berechnet wird.
     */
    private void jahre(long jubilaeumsCalSek, long heuteSek, long untilSek) {
    	ArrayList<Jubilaeum> jubilaeumslisteTemp = new ArrayList<Jubilaeum>();
    	Calendar temp = Calendar.getInstance();
    	temp.setTimeInMillis(jubilaeumsCalSek*1000);
    	int i=0;
    	while(temp.getTimeInMillis()/1000<=untilSek) {
    		temp.add(Calendar.YEAR,1);
    		i++;
    		if(temp.getTimeInMillis()/1000>heuteSek) {
    			jubilaeumslisteTemp.add(new Jubilaeum(temp.getTimeInMillis()/1000, String.format("%,d",i)+" "+"Jahre"));
    		}
    	}
    	jubilaeumslisteTemp.remove(jubilaeumslisteTemp.size()-1);
    	jubilaeumsliste.addAll(jubilaeumslisteTemp);
    } 
    
    /**
     * Diese Methode sortiert alle Jubilaen nach Datum, rechnet sie in Dabendorfer Uhrzeiten um und traegt sie in die Jubilaeumstabelle ein.
     */
    private void ausgabe() {
    	Collections.sort(jubilaeumsliste);
    	for(Jubilaeum j:jubilaeumsliste) {
    		DabendorferZeit dz = new DabendorferZeit();
    		dz.getGregKalender().setTimeInMillis(j.getDorZeit()*1000);
    		dz.gregZuDORdirekt();
    		j.setDorZeit(dz.getDorZeit());
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    		j.setGreg(dateFormat.format(dz.getGregKalender().getTime()));
    	}
    	ergebnisse.ergebnisseEintragen(jubilaeumsliste);
    }
	
	public static void main(String[] args) {
		new Jubilaen();
	}
}