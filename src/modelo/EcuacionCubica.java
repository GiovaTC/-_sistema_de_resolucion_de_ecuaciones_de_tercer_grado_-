package modelo;

public class EcuacionCubica {

    public static double resolver(
            double a,
            double b,
            double c,
            double d) {

        double x = 1.0;

        for(int i=0;i<100;i++) {

            double fx =
                    a*Math.pow(x,3)
                    + b*Math.pow(x,2)
                    + c*x
                    + d;

            double dfx =
                    3*a*Math.pow(x,2)
                    + 2*b*x
                    + c;

            if(Math.abs(dfx) < 0.00001)
                break;

            x = x - (fx/dfx);
        }

        return x;
    }
}
