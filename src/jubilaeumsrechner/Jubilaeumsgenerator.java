package jubilaeumsrechner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.PriorityQueue;
 
/**
 * Diese Klasse benutzt einen Iterator, um alle Arten von Jubilaen einfach zu generieren und zurueckzugeben.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Jubilaeumsgenerator implements Iterable<Integer> {
	
	/**
	 * Diese Methode rechnet alle Ereignisse von Sekunden bis Wochen aus und gibt sie als ArrayList zurueck.
	 * 
	 * @param sekJubilaeum Die Unix-Sekunden-Zahl des Jubilaeums.
	 * @param sekHeute Die Unix-Sekunden-Zahl des aktuellen Zeitpunkts.
	 * @param sekUntil Die Unix-Sekunden-Zahl des Datums bis zu welchem berechnet wird.
	 * @param bezeichner Die Art des Jubilaeums als String.
	 * @param multiplikator Der Multiplikator, das wievielfache einer Sekunde die Ereignisart ist.
	 * @return Gibt eine ArrayList der berechneten Jubilaen zurueck.
	 */
	public ArrayList<Jubilaeum> ausgabe(long sekJubilaeum, long sekHeute, long sekUntil, String bezeichner, long multiplikator) {
		ArrayList<Jubilaeum> jubilaeumsliste = new ArrayList<Jubilaeum>();
		for(int value : this) {
        	if(sekJubilaeum+value*multiplikator < sekHeute) continue;
        	jubilaeumsliste.add(new Jubilaeum(sekJubilaeum+value*multiplikator, String.format("%,d",value)+" "+bezeichner));
            if(sekJubilaeum+value*multiplikator > sekUntil) break;
        }
		jubilaeumsliste.remove(jubilaeumsliste.size()-1);
		return jubilaeumsliste;
	}
	
	/**
	 * Diese Methode rechnet alle anderen Jubilaen aus, die durch Unregelmaessigkeit nicht genau berechenbar sind.
	 * 
	 * @param sekJubilaeum Die Unix-Sekunden-Zahl des Jubilaeums.
	 * @param sekHeute Die Unix-Sekunden-Zahl des aktuellen Zeitpunkts.
	 * @param sekUntil Die Unix-Sekunden-Zahl des Datums bis zu welchem berechnet wird.
	 * @param bezeichner Die Art des Jubilaeums als String.
	 * @return Gibt eine ArrayList der berechneten Jubilaen zurueck.
	 */
	public ArrayList<Jubilaeum> sonderfall(long sekJubilaeum, long sekHeute, long sekUntil, String bezeichner) {
		ArrayList<Jubilaeum> jubilaeumsliste = new ArrayList<Jubilaeum>();
		Calendar temp = Calendar.getInstance();
		for(int value : this) {
			temp.setTimeInMillis(sekJubilaeum*1000);
			temp.add(Calendar.MONTH,value);
			if(temp.getTimeInMillis()/1000 < sekHeute) continue;
			jubilaeumsliste.add(new Jubilaeum(temp.getTimeInMillis()/1000, String.format("%,d",value)+" "+bezeichner));
			if(temp.getTimeInMillis()/1000 > sekUntil) break;
        }
		jubilaeumsliste.remove(jubilaeumsliste.size()-1);
		return jubilaeumsliste;
	}
 
	/**
	 * Dies ist der Iterator, der die glatten Ereignisse auswirft.
	 */
    private Iterator<Integer> byFive = new Iterator<Integer>() {
        private int base = 1;
        private int value = 1;
 
        @Override
        public boolean hasNext() {
            return true;
        }
        
        @Override
        public Integer next() {
            int result = 5 * base * value;
            if(value == 20) {
                value = 3;
                base *= 10;
            } else {
                value++;
            }
            return result;
        }
    };
 
    /**
	 * Dies ist der Iterator, der die Schnapszahlen-Ereignisse auswirft.
	 */
    private Iterator<Integer> repUnit = new Iterator<Integer>() {
        private int base = 100;
        private int digit = 1;
 
        @Override
        public boolean hasNext() {
            return true;
        }
 
        @Override
        public Integer next() {
            int result = (base - 1) / 9 * digit;
            if(digit == 9) {
                base *= 10;
                digit = 1;
            } else {
                digit++;
            }
            return result;
        }
    }; 
 
    /**
     * Dieser Iterator fasst alle anderen Ereignisse zusammen und steuert den Ablauf.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private PriorityQueue<Pair> queue = new PriorityQueue<Pair>(); {
                queue.add(new Pair(byFive));
                queue.add(new Pair(repUnit));
            }
 
            @Override
            public boolean hasNext() {
                return true;
            }
 
            @Override
            public Integer next() {
                Pair pair = queue.poll();
                queue.add(pair.step());
                return pair.next;
            }
        };
    }
 
    private static class Pair implements Comparable<Pair> {
        private final int next;
        private final Iterator<Integer> iterator;
 
        private Pair(Iterator<Integer> iterator) {
            this.next = iterator.next();
            this.iterator = iterator;
        }
 
        private Pair step() {
            return new Pair(iterator);
        }
 
        @Override
        public int compareTo(Pair o) {
            return Integer.compare(next, o.next);
        }
    }
}