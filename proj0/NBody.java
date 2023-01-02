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
        Planet[] ps = new Planet[numberOfPlanets];
        for (int i = 0; i < numberOfPlanets; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            ps[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return ps;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] ps = readPlanets(filename);

        String image = "./images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, image);

        for(Planet p:ps) {
            p.draw();
        }
        StdDraw.show();
    }
}
