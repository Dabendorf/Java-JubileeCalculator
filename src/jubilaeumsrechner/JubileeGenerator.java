package jubilaeumsrechner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.PriorityQueue;

public class JubileeGenerator implements Iterable<Integer> {
    /**
     * 
	 * Calculates all jubilees based on seconds, minutes, hours, days and weeks
	 * 
	 * @param unixjubilee Unixtime of the jubilee
	 * @param unixnow Unixtime of now
	 * @param unixuntil Unixtime until which we calculate jubilees
	 * @param identifier The type of jubilee as String
	 * @param multiplicator A multiplicator identifing the type in seconds
	 * @return Returns list of jubilees
	 */
	public ArrayList<Jubilee> generateRegularJubilees(long unixjubilee, long unixnow, long unixuntil, String identifier, long multiplicator) {
		ArrayList<Jubilee> listofjubilees = new ArrayList<Jubilee>();
		for(int value : this) {
        	if(unixjubilee+value*multiplicator < unixnow) continue;
        	listofjubilees.add(new Jubilee(unixjubilee+value*multiplicator, String.format("%,d",value)+" "+identifier, identifier));
            if(unixjubilee+value*multiplicator > unixuntil) break;
        }
		listofjubilees.remove(listofjubilees.size()-1);
		return listofjubilees;
	}
	
	/**
	 * Calculates all jubilees based on months (since these are not regular)
	 * 
	 * @param unixjubilee Unixtime of the jubilee
	 * @param unixnow Unixtime of now
	 * @param unixuntil Unixtime until which we calculate jubilees
	 * @param identifier The type of jubilee as String
	 * @return Returns list of jubilees
	 */
	public ArrayList<Jubilee> generateMonths(long unixjubilee, long unixnow, long unixuntil, String identifier) {
		ArrayList<Jubilee> listofjubilees = new ArrayList<Jubilee>();
		Calendar temp = Calendar.getInstance();
		for(int value : this) {
			temp.setTimeInMillis(unixjubilee*1000);
			temp.add(Calendar.MONTH,value);
			if(temp.getTimeInMillis()/1000 < unixnow) continue;
			listofjubilees.add(new Jubilee(temp.getTimeInMillis()/1000, String.format("%,d",value)+" "+identifier, identifier));
			if(temp.getTimeInMillis()/1000 > unixuntil) break;
        }
		listofjubilees.remove(listofjubilees.size()-1);
		return listofjubilees;
	}

    /**
     * Calculates all jubilees based on years (since these are not regular)
     * @param unixjubilee Unixtime of the jubilee
     * @param unixnow Unixtime of now
     * @param unixuntil Unixtime until which we calculate jubilees
     */
    public ArrayList<Jubilee> generateYears(long unixjubilee, long unixnow, long unixuntil) {
    	ArrayList<Jubilee> jubileelistTemp = new ArrayList<Jubilee>();
    	Calendar temp = Calendar.getInstance();
    	temp.setTimeInMillis(unixjubilee*1000);
    	int i=0;
    	while(temp.getTimeInMillis()/1000<=unixuntil) {
    		temp.add(Calendar.YEAR,1);
    		i++;
    		if(temp.getTimeInMillis()/1000>unixnow) {
    			jubileelistTemp.add(new Jubilee(temp.getTimeInMillis()/1000, String.format("%,d",i)+" "+"Years", "Years"));
    		}
    	}
    	jubileelistTemp.remove(jubileelistTemp.size()-1);
    	return jubileelistTemp;
    }
 
	/**
	 * This iterator generates round jubilees
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
	 * This iterator generates repdigits
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
     * This is the main Iterator to generate jubilees
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
