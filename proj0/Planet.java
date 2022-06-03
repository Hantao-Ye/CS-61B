public class Planet {
    private static final double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
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
        return Math.sqrt(Math.pow(Math.abs(this.xxPos - p.xxPos), 2) + Math.pow(Math.abs(this.yyPos - p.yyPos), 2));
    }

    public double calcForceExertedBy(Planet p) {
        return G * this.mass * p.mass / Math.pow(this.calcDistance(p), 2);
    }

    public double calcForceExertedByX(Planet p) {
        return calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        return calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double res = 0;
        for (Planet p : planets) {
            if (this.equals(p))
                continue;
            
            res += this.calcForceExertedByX(p);
        }
        return res;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double res = 0;
        for (Planet p : planets) {
            if (this.equals(p))
                continue;
            
            res += this.calcForceExertedByY(p);
        }
        return res;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;

        this.xxVel += dt * aX;
        this.yyVel += dt * aY;

        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}