package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        RadixSorter<Interval> s = new RadixSorter<>(intervals, min,max,3);
        s.sort();
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

class Interval implements  RadixSortable {
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
}
