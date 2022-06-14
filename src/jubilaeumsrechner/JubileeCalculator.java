package jubilaeumsrechner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class JubileeCalculator {

    public JubileeCalculator() {
        ArrayList<Jubilee> jubileelist = calculateJubilees();
        output(jubileelist);
    }

    /**
     * Calculates all jubilees
     */
    private ArrayList<Jubilee> calculateJubilees() {
        ArrayList<Jubilee> jubileelist = new ArrayList<Jubilee>();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        Calendar today = Calendar.getInstance();
        long todaySeconds = today.getTimeInMillis()/1000;

        
        try {
            String oldDateStr = "07-08-2004 09:00:00";
            Date oldDate = dateFormat.parse(oldDateStr);
            long oldDateUnix = (long) oldDate.getTime()/1000;

            String untilDateStr = "22-01-2025 09:00:00";
            Date untilDate = dateFormat.parse(untilDateStr);
            long untilDateUnix = (long) untilDate.getTime()/1000;

            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Seconds", 1));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Minutes", 60));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Hours", 60*60));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Days", 60*60*24));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Weeks", 60*60*24*7));
            jubileelist.addAll(new JubileeGenerator().generateMonths(oldDateUnix, todaySeconds, untilDateUnix, "Months"));
            jubileelist.addAll(new JubileeGenerator().generateYears(oldDateUnix, todaySeconds, untilDateUnix));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jubileelist;
    }

     /**
     * Sorts all jubilees and outputs them
     */
    private void output(ArrayList<Jubilee> jubileelist) {
    	Collections.sort(jubileelist);
    	for(Jubilaeum j:jubileelist) {
    		DabendorferZeit dz = new DabendorferZeit();
    		dz.getGregKalender().setTimeInMillis(j.getDorZeit()*1000);
    		dz.gregZuDORdirekt();
    		j.setDorZeit(dz.getDorZeit());
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    		j.setGreg(dateFormat.format(dz.getGregKalender().getTime()));
    	}
    	ergebnisse.ergebnisseEintragen(jubileelist);
    }

    public static void main(String[] args) {
		new JubileeCalculator();
	}
    
}