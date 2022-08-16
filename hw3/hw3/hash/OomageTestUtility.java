package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    /**
     * return true if the given Oomages have a nice spread
     *
     * @param oomages a List of given Oomages
     * @param M       the number of buckets
     */
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        int N = oomages.size();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }

        for (int bucketNum : buckets) {
            if (bucketNum >= N / 2.5 || bucketNum <= N / 50) {
                return false;
            }
        }
        return true;
    }
}
