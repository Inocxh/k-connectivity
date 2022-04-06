package graphs;

import java.util.ArrayList;

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
    public void addInterval(int a, int b) {
        if (b > max) {
            max = b;
        }
        if (a < min) {
            min = a;
        }
        intervals.add(new Interval(a,b));
    }
    public void sort() {
        RadixSorter<Interval> s = new RadixSorter<>(intervals, min,max,2);
        s.sort();
    }
    public ArrayList<Interval> getIntervals() {
        return intervals;
    }
}

class Interval implements  RadixSortable {
    int a;
    int b;

    public Interval(int a, int b) {
        this.a = a;
        this.b = b;
    }
    //TODO: Add reverse sort-radix flag,
    //      To reverse order, the radix function should return the radix in reverse
    @Override
    public int getRadix(int radix) {
        return (radix == 0) ? a : b;
    }

    public String toString() {
        return "(" + a +","+b+")";
    }
}
