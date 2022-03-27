import java.util.Arrays;

public class RadixSorter<E extends RadixSortable > {
    E[] elements;
    final int min;
    final int max;
    final int depth;

    int[] bucketSizes;
    int[] bucketInitialStarts;
    int[] bucketStarts;

    public RadixSorter(E[] es, int min, int max,int depth) {
        this.min = min;
        this.max = max;
        this.elements = es;
        this.depth = depth;

        bucketInitialStarts = new int[max-min+1];
        bucketStarts = new int[max-min+1];
    }

    public void sort(int depth, int start, int end) {
        //If we have an empty bucket or a single element bucket, just return without allocation
        if (start == end || start+1 == end) {
            return;
        }
        //Pass through the elements to find the size of the buckets
        bucketSizes = bucketInitSizes(depth,start,end);
        //Save the offset of buckets to quickly find the right place for a given element
        bucketStarts(bucketSizes,bucketInitialStarts,start);
        bucketStarts(bucketSizes,bucketStarts,start);

        //At each iteration, we either swap, increment or both
        //Thus an element is set into its right place on every iteration, meaning that the total run time is at most 2*n
        int i = start;
        while (i < end) {
            E elem = elements[i];
            int index = bucketStarts[elem.getRadix(depth)-min];
            int rightfulBucketStart = bucketInitialStarts[elem.getRadix(depth)-min];
            if (i >= rightfulBucketStart && i <= index) {
                bucketStarts[elem.getRadix(depth)-min]++;
                i++;
            } else {
                E t = elements[index];
                elements[index] = elements[i];
                elements[i] = t;
                bucketStarts[elem.getRadix(depth)-min]++;
            }
        }
        //Make new sorter so we don't delete the bucket information for this sorter when recursing
        RadixSorter s = new RadixSorter(elements,min,max,this.depth);

        //If we are not at max depth, recursively sort each of the buckets.
        if (depth < this.depth) {
            for (int j = 1;j < bucketInitialStarts.length;j++) {
                //System.out.println("Sorting recursively from " + bucketRanges[j-1] + " to " + bucketRanges[j]);
                s.sort(depth+1, bucketInitialStarts[j-1], bucketInitialStarts[j]);
            }
            s.sort(depth+1,bucketInitialStarts[bucketInitialStarts.length-1],end);
        }
    }

    //Stores the size of bucket for a radix in index *radix*-min
    int[] bucketInitSizes(int depth, int start, int end) {
        //For each element in the sublist, get the radix and increment the bucket size
        int[] sizes = new int[max-min+1];
        for (int i = start; i < end; i++) {
            sizes[elements[i].getRadix(depth)-min]++;
        }
        return sizes;
    }
    //Returns a list of starting indices of each bucket, given a bucket size list
    void bucketStarts(int[] sizes,int[] array,int offset) {
        array[0] = offset;
        for (int i = 1; i < sizes.length; i++) {
            array[i] = array[i-1]+sizes[i-1];
        }
    }

}
