public class Ex2 {
    public static int max_array(int[] m) {
        int n = m.length;
        if (n <= 0) {
            return -1;
        } else if (n == 1) {
            return m[0];
        }
        int ans = m[0];
        for (int i = 1; i < n; i++) {
            if (m[i] > ans) {
                ans = m[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 9, 2, 15, 2, 22, 10, 6 };
        System.out.println(max_array(nums));
    }
}
