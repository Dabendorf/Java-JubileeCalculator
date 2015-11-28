package jubilaeumsrechner;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse generiert die Tabelle der zukuenftigen Jubilaen, welche nach der Berechnung angezeigt werden.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Ergebnisse extends JPanel {
	
	private JTable tabelle1 = new JTable();
	private ArrayList<Jubilaeum> jubilaeumsliste;

	public void ergebnisseEintragen(ArrayList<Jubilaeum> arrList) {
	    jubilaeumsliste = arrList;
	    generiere();
	}
	
	/**
	 * Diese Methode erstellt aus den ihr vorgegebenen Werten die Jubilaeumstabelle.
	 */
	public void generiere() {
		Vector<Object> eintraege = new Vector<Object>();
		for(Jubilaeum jub:jubilaeumsliste) {
			Vector<Object> zeile = new Vector<Object>();
			zeile.add(String.format("%,d",jub.getDorZeit()));
			zeile.add(jub.getGreg());
			zeile.add(jub.getAngabe());
			eintraege.add(zeile);
		}

		Vector<String> titel = new Vector<String>();
		titel.add("DORzeit");
		titel.add("Greg.-Zeit");
		titel.add("Ereignis");
		tabelle1 = new JTable(eintraege, titel);
		
		tabelle1.getColumn("DORzeit").setPreferredWidth(20);
	    tabelle1.getColumn("Greg.-Zeit").setPreferredWidth(30);
	    tabelle1.getColumn("Ereignis").setPreferredWidth(50);
	    tabelle1.getTableHeader().setBackground(Color.lightGray);
	    tabelle1.setEnabled(false);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	    for(int x=0;x<tabelle1.getColumnCount();x++) {
	    	tabelle1.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    	tabelle1.getTableHeader().getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    }
	    this.setLayout(new GridLayout(1,1));
	    tabelle1.setDefaultRenderer(String.class, centerRenderer);
	    tabelle1.setVisible(true);
	    this.removeAll();
	    this.add(new JScrollPane(tabelle1));
	    Jubilaen.frame1.pack();
	}
}