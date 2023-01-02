public class NBody {
    public static double readRadius(String path) {
        In in = new In(path);
        int numberOfPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int numberOfPlanets = in.readInt();
        double radius = in.readDouble();
        for (int i=0;i<numberOfPlanets;i++) {
            
        }
    }
}
