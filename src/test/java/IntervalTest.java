import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class IntervalTest {

    @Test
    public void IntervalTest1() {
        Intervals i = new Intervals();
        i.addInterval(1,3);
        i.addInterval(1,2);
        i.addInterval(1,1);
        i.addInterval(2,2);
        i.addInterval(2,3);
        i.sort();
        System.out.println(i.getIntervals().toString());
    }
}
