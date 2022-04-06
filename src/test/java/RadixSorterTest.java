import graphs.RadixSortable;
import graphs.RadixSorter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class RadixSorterTest {

    @Test
    public void RadixTest1() {
        RadixSortable test = new NotInteger(0b11_10_01);
        assert test.getRadix(26) == 1;
        assert test.getRadix(27) == 1;
        assert test.getRadix(28) == 1;
        assert test.getRadix(29) == 0;
        assert test.getRadix(30) == 0;
        assert test.getRadix(31) == 1;
    }
    @Test
    public void SortTest1() {
        NotInteger[] collec = new NotInteger[10];
        int i = 0;
        for (int j = 9; j >= 0; j--) {
            collec[i] = new NotInteger(j);
            i++;
        }
        RadixSorter<NotInteger> sorter = new RadixSorter<>(Arrays.asList(collec), NotInteger.getMinRadix(),NotInteger.getMaxRadix(),NotInteger.getRadixDepth());
        sorter.sort();
        assert isSorted(collec);
    }
    @Test
    //Sort for depth > 1
    public void SortTest2() {
        NotInteger[] collec = new NotInteger[1_0000];
        int i = 0;
        for (int j = 9999; j >= 0; j--) {
            collec[i] = new NotInteger(j);
            i++;
        }
        RadixSorter<NotInteger> sorter = new RadixSorter<>(Arrays.asList(collec), NotInteger.getMinRadix(),NotInteger.getMaxRadix(),NotInteger.getRadixDepth());
        sorter.sort(0, 0,10000);
        assert isSorted(collec);
    }

    @Test
    //Seems to run in linear time, needs more testing.
    //But still an order of magnitude lower than the simple standard implementation of sort
    public void SortTest3() {
        final int size = 3000000;
        Random generator = new Random(42);
        Random gen2 = new Random(42);
        NotInteger[] list = new NotInteger[size];
        Integer[] list2 = new Integer[size];
        for (int i = 0; i < list.length; i++) {
            list[i] = new NotInteger(generator.nextInt(0,10));
            list2[i] = gen2.nextInt(0,10);
        }
        RadixSorter<NotInteger> s = new RadixSorter<>(Arrays.asList(list), NotInteger.getMinRadix(),NotInteger.getMaxRadix(),NotInteger.getRadixDepth());
        long start = System.nanoTime();
        Arrays.sort(list2);
        long end = System.nanoTime() - start;
        long start2 = System.nanoTime();
        s.sortFrom(28);
        long end2 = System.nanoTime() - start2;
        System.out.println("Comparesort: " + end);
        System.out.println("Radix sort : " + end2);
        System.out.println("Difference : " + Math.abs(end-end2));
        assert  isSorted(list);
    }

    static boolean isSorted(NotInteger[] list) {
        for (int i=1; i<list.length; i++) {
            if (list[i-1].i > list[i].i)  {return false;}
        }
        return true;
    }
}



class NotInteger implements RadixSortable {
    public int i;
    public NotInteger(int i) {
        this.i = i;
    }

    public int getRadix(int d) {
        /// 123, d = 1
        ///  2 = 123/10*1 % 10
        //
        //return i / POWERS_OF_10[d] % 10;
        return (i>>(31-d)) & 1;
    }

    public static int getMaxRadix() {
        return 1;
    }

    public static int getMinRadix() {
        return 0;
    }

    public static int getRadixDepth() {
        return 32;
    }

    public String toString() {
        return Integer.toString(i);
    }
}
