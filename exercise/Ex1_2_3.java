public class Ex1_2_3 {
    public static void main(String[] args) {
        double res = 0.0;
        for(String s:args) {
            res += Double.parseDouble(s);
        }
        System.out.println(res);
    }
}
