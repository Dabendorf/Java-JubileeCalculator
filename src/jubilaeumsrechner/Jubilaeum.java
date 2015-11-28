package jubilaeumsrechner;

/**
 * Diese Klasse generiert einzelne Jubilaeumsobjekte mit dem Namen und der Uhrzeit als Eigenschaft.<br>
 * Sie enthaelt ausserdem eine Comparable-Erweiterung zum Vergleichen der Jubilaen.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Jubilaeum implements Comparable<Jubilaeum> {
	
	private long dorZeit;
	private String greg;
	private String angabe;
	
	public Jubilaeum(long dorZeit, String angabe) {
		this.dorZeit = dorZeit;
		this.angabe = angabe;
	}
	
	public long getDorZeit() {
		return dorZeit;
	}
	
	public String getAngabe() {
		return angabe;
	}

	public void setDorZeit(long dorZeit) {
		this.dorZeit = dorZeit;
	}

	public String getGreg() {
		return greg;
	}

	public void setGreg(String greg) {
		this.greg = greg;
	}

	@Override
	public int compareTo(Jubilaeum o) {
		return ((Long)dorZeit).compareTo((Long)o.dorZeit);
	}
}