package securityProject;

/**
 *
 * @author Ola Galal
 */
public class Pair implements Comparable<Pair> {
    public final int index;
    public final int value;

    public Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        return Integer.valueOf(this.value).compareTo(other.value);
    }
}
