public class NBody {

    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int firstItemInFile = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int num = in.readInt();
        Planet[] ps = new Planet[num];
        double radius = in.readDouble();

        for (int i = 0; i < num; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double xv = in.readDouble();
            double yv = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            Planet p = new Planet(xp, yp, xv, yv, m, img);
            ps[i] = p;
        }
        return ps;
    }

    public static void main(String[] args) {
        // collecting all needed input
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] ps = readPlanets(filename);
        double radius = readRadius(filename);

        // drawing the background
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();


        // creating an animation
        int num = ps.length;
        double tVar = 0.0;

        while (tVar <= T) {
            double[] xForces = new double[num];
            double[] yForces = new double[num];

            for (int i = 0; i < num; i++) {
                xForces[i] = ps[i].calcNetForceExertedByX(ps);
                yForces[i] = ps[i].calcNetForceExertedByX(ps);
            }

            for (int i = 0; i < num; i++) {
                ps[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (Planet p : ps) {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            tVar += dt;
        }

        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                    ps[i].yyVel, ps[i].mass, ps[i].imgFileName);

        }
    }
}
