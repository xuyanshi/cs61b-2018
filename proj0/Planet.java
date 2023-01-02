public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

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
        double x1 = this.xxPos, y1 = this.yyPos;
        double x2 = p.xxPos, y2 = p.yyPos;
        double x = x1 - x2, y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

    static final double G = 6.67e-11;

    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);
        return G * this.mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double r = this.calcDistance(p);
        double F = this.calcForceExertedBy(p);
        double dx = p.xxPos-this.xxPos;
        return F*dx/r;
    }
    public double calcForceExertedByY(Planet p) {
        double r = this.calcDistance(p);
        double F = this.calcForceExertedBy(p);
        double dy = p.yyPos-this.yyPos;
        return F*dy/r;
    }
}
