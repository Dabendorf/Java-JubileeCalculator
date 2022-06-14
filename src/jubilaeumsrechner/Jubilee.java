package jubilaeumsrechner;

/**
 * This class generates Jubilee objects including type and date.<br>
 * It also includes comparable to later sort everything
 * 
 * @author Lukas Schramm
 * @version 1.1
 *
 */ 
public class Jubilee implements Comparable<Jubilee> {
	
	private String greg;
	private String type;
    private long unix;
	
	public Jubilee(long unix, String type, String greg) {
		this.type = type;
        this.unix = unix;
        this.greg = greg;
	}
	
	public String getType() {
		return type;
	}

	public String getGreg() {
		return greg;
	}

	public void setGreg(String greg) {
		this.greg = greg;
	}

    public long getUnix() {
        return unix;
    }

	@Override
	public int compareTo(Jubilee o) {
		return ((Long)unix).compareTo((Long)o.unix);
	}
}