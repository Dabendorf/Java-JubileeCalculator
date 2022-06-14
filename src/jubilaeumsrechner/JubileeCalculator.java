package jubilaeumsrechner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Main class of the jubilee calculator calculating all jubilees for a date from now until a certain date.
 * 
 * @author Lukas Schramm
 * @version 1.1
 *
 */ 
public class JubileeCalculator {

    public JubileeCalculator() {
        String jubileeString = "2004-08-07 09:00:00";
        String untilString = "2026-01-01 09:00:00";
        ArrayList<Jubilee> jubileelist = calculateJubilees(jubileeString, untilString);
        output(jubileelist);
    }

    /**
     * Calculates all jubilees
     */
    private ArrayList<Jubilee> calculateJubilees(String oldDateStr, String untilDateStr) {
        ArrayList<Jubilee> jubileelist = new ArrayList<Jubilee>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar today = Calendar.getInstance();
        long todaySeconds = today.getTimeInMillis()/1000;

        
        try {
            Date oldDate = dateFormat.parse(oldDateStr);
            long oldDateUnix = (long) oldDate.getTime()/1000;

            Date untilDate = dateFormat.parse(untilDateStr);
            long untilDateUnix = (long) untilDate.getTime()/1000;

            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Seconds", 1));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Minutes", 60));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Hours", 60*60));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Days", 60*60*24));
            jubileelist.addAll(new JubileeGenerator().generateRegularJubilees(oldDateUnix, todaySeconds, untilDateUnix, "Weeks", 60*60*24*7));
            jubileelist.addAll(new JubileeGenerator().generateMonths(oldDateUnix, todaySeconds, untilDateUnix, "Months"));
            jubileelist.addAll(new JubileeGenerator().generateYears(oldDateUnix, todaySeconds, untilDateUnix, "Years"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jubileelist;
    }

     /**
     * Sorts all jubilees and outputs them
     */
    private void output(ArrayList<Jubilee> jubileelist) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Collections.sort(jubileelist);

        for(Jubilee j: jubileelist) {
            j.setGreg(dateFormat.format(new Date(j.getUnix()*1000)));
            System.out.println(j.getGreg()+": "+j.getType());
        }
    }

    public static void main(String[] args) {
		new JubileeCalculator();
	}
    
}