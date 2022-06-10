package graphs.Nadara;

import java.util.Objects;

public class Pair{
    public int fst;
    public int sec;

    public Pair(int x,int y) {
        this.fst = x;
        this.sec = y;
    }
    public String toString() {
        return "["+fst+";"+sec+"]";
    }

    @Override
    public boolean equals(Object o) {
        /*
            The below 3 lines of code are standard generated code for java, this is to implement the standard method compare.
            The last line just compares pairs, such that both (1,3) and (3,1( are equal, but also (1,2) is equal to (2,1),
            since we are using undirected edges.
         */
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return fst == pair.fst && sec == pair.sec || fst == pair.sec && sec == pair.fst;
    }

    @Override
    public int hashCode() {
        // reducing object to integer, such that its possible to use a hashing function.
        return Objects.hash(fst, sec);
    }
}

