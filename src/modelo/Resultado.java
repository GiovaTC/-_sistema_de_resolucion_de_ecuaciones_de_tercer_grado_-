package modelo;

public class Resultado {

    private int id;

    private double a;
    private double b;
    private double c;
    private double d;

    private double raizReal;

    public Resultado() {}

    public Resultado(double a,
                     double b,
                     double c,
                     double d,
                     double raizReal) {

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.raizReal = raizReal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getRaizReal() {
        return raizReal;
    }

    public void setRaizReal(double raizReal) {
        this.raizReal = raizReal;
    }
}
