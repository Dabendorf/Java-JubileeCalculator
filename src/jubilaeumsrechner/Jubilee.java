package jubilaeumsrechner;

/**
 * This class generates Jubilee objects including type and date.<br>
 * It also includes comparable to later sort everything
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */ 
public class Jubilee implements Comparable<Jubilee> {
	
	private long dorZeit;
	private String greg;
	private String angabe;
	
	public Jubilee(long dorZeit, String angabe) {
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
	public int compareTo(Jubilee o) {
		return ((Long)dorZeit).compareTo((Long)o.dorZeit);
	}
}