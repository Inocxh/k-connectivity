package graphs.ThreeEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Intervals {
    int max;
    int min;
    ArrayList<Interval> intervals;

    public Intervals() {
        max = 0;
        min = Integer.MAX_VALUE;
        intervals = new ArrayList<>();
    }


    //a >= b always
    public void addInterval(int a, int b, int tiebreaker) {
        if (b > max) {
            max = b;
        }
        if (tiebreaker > max) {
            max = tiebreaker;
        }
        if (a < min) {
            min = a;
        }
        intervals.add(new Interval(a,b,tiebreaker));
    }
    public void sort() {
        Collections.sort(intervals);
        // RadixSorter<Interval> s = new RadixSorter<>(intervals, min,max,3);
        // s.sort();
    }

    public void sortReverse() {
        Collections.sort(intervals, new Interval.IntervalCompare());

    }


    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void swap() {
        for (Interval interval: intervals) {
            interval.swap();
        }
    }
    public void reverse() {
        Collections.reverse(intervals);
    }
}

class Interval implements  RadixSortable, Comparable<Interval> {
    int a;
    int b;
    int tiebreaker;
    private boolean visited = false;
    ArrayList<Interval> connectedToo;

    public Interval(int a, int b,int tiebreaker) {
        this.a = a;
        this.b = b;
        this.tiebreaker = tiebreaker;
        connectedToo = new ArrayList<>();
    }
    //TODO: Add reverse sort-radix flag,
    //      To reverse order, the radix function should return the radix in reverse
    @Override
    public int getRadix(int radix) {
        if (radix == 0) {
            return a;
        } else if (radix == 1) {
            return b;
        } else {
            return tiebreaker;
        }
    }

    public String toString() {
        return "(" + a +"," + b +")";
    }

    public void swap() {
        int c = a;
        a = b;
        b = c;
    }

    public void addConnectedTo(Interval interval) {
        connectedToo.add(interval);
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }

    @Override
    public int compareTo(Interval interval) {
        if(a < interval.a) {
            return -1;
        }
        else if (a > interval.a) {
            return 1;
        }
        if(b < interval.b) {
            return -1;
        }
        else if (b > interval.b) {
            return 1;
        }
        if(tiebreaker < interval.tiebreaker) {
            return -1;
        }
        else if (tiebreaker > interval.tiebreaker) {
            return 1;
        }

        return 0;
    }
    static class IntervalCompare implements Comparator<Interval> {

        @Override
        public int compare(Interval interval1, Interval interval2) {
            if(interval1.b < interval2.b) {
                return -1;
            }
            else if (interval1.b > interval2.b) {
                return 1;
            }
            if(interval1.a < interval2.a) {
                return -1;
            }
            else if (interval1.a > interval2.a) {
                return 1;
            }
            if(interval2.tiebreaker < interval1.tiebreaker) {
                return -1;
            }
            else if (interval2.tiebreaker > interval1.tiebreaker) {
                return 1;
            }

            return 0;
        }
    }
}
