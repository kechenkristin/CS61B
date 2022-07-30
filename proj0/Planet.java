import java.lang.Math;

public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;


	public Planet(double xp, double yP, double xV, double yV, double m, String img) {
		xxPos = xp;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img; }

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}


	public double calcDistance(Planet p) {
		double x_square = (xxPos - p.xxPos) * (xxPos - p.xxPos);
		double y_square = (yyPos - p.yyPos) * (yyPos - p.yyPos);
		return Math.sqrt(x_square + y_square);
	}

	public double calcForceExertedBy(Planet p) {
		double distance = calcDistance(p);
		double gravity = 6.67 * Math.pow(10, -11);
		return gravity * mass * p.mass / (distance * distance);
	}

	public double calcForceExertedByX(Planet p) {
		double force = calcForceExertedBy(p);
		double distance = calcDistance(p);
		return force * (p.xxPos - xxPos) / distance;
	}

	public double calcForceExertedByY(Planet p) {
		double force = calcForceExertedBy(p);
		double distance = calcDistance(p);
		return force * (p.yyPos- yyPos) / distance;
	}


	public double calcNetForceExertedByX(Planet[] ps) {
		double result = 0.0;
		for (Planet p : ps) {
			if (!this.equals(p)){
				result += calcForceExertedByX(p);
			}
		}
		return result;
	}

	public double calcNetForceExertedByY(Planet[] ps) {
		double result = 0.0;
		for (Planet p : ps) {
			if (!this.equals(p)){
				result += calcForceExertedByY(p);
			}
		}
		return result;
	}

	public void update(double dt, double fX, double fY) {
		xxVel = xxVel + dt * fX / mass;
		yyVel = yyVel + dt * fY / mass;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}


}

		
