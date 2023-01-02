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
        double dx = p.xxPos - this.xxPos;
        return F * dx / r;
    }

    public double calcForceExertedByY(Planet p) {
        double r = this.calcDistance(p);
        double F = this.calcForceExertedBy(p);
        double dy = p.yyPos - this.yyPos;
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double Fx = 0.0;
        for (Planet p : ps) {
            if (!this.equals(p)) {
                Fx += this.calcForceExertedByX(p);
            }
        }
        return Fx;
    }

    public double calcNetForceExertedByY(Planet[] ps) {
        double Fy = 0.0;
        for (Planet p : ps) {
            if (!this.equals(p)) {
                Fy += this.calcForceExertedByY(p);
            }
        }
        return Fy;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass, aY = fY / this.mass;
        xxVel += aX * dt;
        yyVel += aY * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(this.xxPos,this.yyPos,"./images/"+this.imgFileName);
    }

    public void printPlanet() {
        System.out.print("Position: ");
        System.out.print(this.xxPos);
        System.out.print(", ");
        System.out.println(this.yyPos);
        System.out.print("Velocity: ");
        System.out.print(this.xxVel);
        System.out.print(", ");
        System.out.println(this.yyVel);
        System.out.println("Mass: "+this.mass+"  imgFileName: "+this.imgFileName);
    }
}
