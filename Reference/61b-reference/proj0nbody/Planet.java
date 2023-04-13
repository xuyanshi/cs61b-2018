import static java.lang.Math.hypot;

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return hypot(xxPos - p.xxPos, yyPos - p.yyPos);
    }

    public double calcForceExertedBy(Planet p) {
        double distance = this.calcDistance(p);
        return G * mass * p.mass / (distance * distance);
    }

    public double calcForceExertedByX(Planet p) {
        double force = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        return (p.xxPos - xxPos) * force / distance;
    }

    public double calcForceExertedByY(Planet p) {
        double force = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        return (p.yyPos - yyPos) * force / distance;
    }

    public double calcNetForceExertedByX(Planet[] plants) {
        double netForce = 0;
        for (Planet planet : plants) {
            if (!this.equals(planet)) {
                netForce += this.calcForceExertedByX(planet);
            }
        }
        return netForce;
    }

    public double calcNetForceExertedByY(Planet[] plants) {
        double netForce = 0;
        for (Planet planet : plants) {
            if (!this.equals(planet)) {
                netForce += this.calcForceExertedByY(planet);
            }
        }
        return netForce;
    }

    public void update(double time, double xForce, double yForce) {
        double accelerationX = xForce / mass;
        double accelerationY = yForce / mass;
        xxVel += accelerationX * time;
        yyVel += accelerationY * time;
        xxPos += xxVel * time;
        yyPos += yyVel * time;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}

