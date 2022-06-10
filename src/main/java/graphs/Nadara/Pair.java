package graphs.Nadara;

import java.util.Objects;

/*
    A simple datastructure to hold to pairs, only used in EdgeLabelledGraph.
    If we were smarter we'd have used it to represent paths, but it was added late in development and no one wanted to grandfather it in.
 */
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return fst == pair.fst && sec == pair.sec || fst == pair.sec && sec == pair.fst;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fst, sec);
    }
}

