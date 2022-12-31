public class Ex4 {
    public static void windowPosSum(int[] a, int n) {
        /** your code here */
        int len = a.length;
        int[] backup = new int[len];
        for (int i = 0; i < len; i++) {
            backup[i] = a[i];
        }
        for (int i = 0; i < len; i++) {
            if (a[i] <= 0) {
                continue;
            }
            int sum = 0;
            for (int j = i; j <= i + n; j++) {
                if (j >= len) {
                    break;
                }
                sum += backup[j];
            }
            a[i] = sum;
        }
    }

    public static void main(String[] args) {
        int[] a = { 1, -1, -1, 10, 5, -1 };
        int n = 2;
        windowPosSum(a, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}
