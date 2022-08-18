/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * help to find the longest item in the String list.
     *
     * @param arr String list asciis.
     * @return the length of the longest word in the String list.
     */
    private static int findMax(String[] arr) {
        int max = Integer.MIN_VALUE;
        for (String s : arr) {
            max = max > s.length() ? max : s.length();
        }
        return max;
    }

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max = findMax(asciis);

        String[] res = new String[asciis.length];
        System.arraycopy(asciis, 0, res, 0, asciis.length);
        for (int i = max - 1; i >= 0; i--) {
            sortHelperLSD(res, i);
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int R = 256;
        int[] count = new int[R + 1];
        for (String ascii : asciis) {
            count[charAtorMinchar(index, ascii)]++;
        }

        // calculate the sum in count[]
        int[] starts = new int[R + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i++) {
            starts[i] = pos;
            pos += count[i];
        }

        // put the item in the correct position
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String item = asciis[i];
            int place = starts[charAtorMinchar(index, item)];
            sorted[place] = item;
            starts[charAtorMinchar(index, item)] += 1;
        }

        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
    }

    /**
     * return the index position of a char in a String, if that position
     * don't have char, return 0.
     */
    private static int charAtorMinchar(int index, String item) {
        if (index < item.length() && index >= 0) {
            return item.charAt(index) + 1;
        } else {
            return 0;
        }
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
