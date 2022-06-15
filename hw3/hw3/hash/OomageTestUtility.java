package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int [] buckets = new int[M];
        int size = oomages.size();
        for (Oomage o : oomages) {
            int b = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[b] ++;
        }

        for (int b : buckets) {
            if (b < size / 50 || b > size / 2.5) {
                return false;
            }
        }
        return true;
    }
}
