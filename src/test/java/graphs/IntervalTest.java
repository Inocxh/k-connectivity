package graphs;

import graphs.Intervals;
import org.junit.jupiter.api.Test;

public class IntervalTest {

    @Test
    public void IntervalTest1() {
        Intervals i = new Intervals();
        i.addInterval(1,3,1);
        i.addInterval(1,2,2);
        i.addInterval(1,1,3);
        i.addInterval(2,2,4);
        i.addInterval(2,2,5);
        i.addInterval(2,3,6);
        i.sort();
        System.out.println(i.getIntervals().toString());
    }
}
