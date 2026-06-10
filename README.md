# -_sistema_de_resolucion_de_ecuaciones_de_tercer_grado_- :.
# Proyecto Java 21 + Swing + Oracle 19c:

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/e1c734d9-a360-459b-b19c-de6715f635bf" />  

```

# Sistema de Resolución de Ecuaciones de Tercer Grado

## Descripción

Aplicación desarrollada en **Java 21**, **Swing** y **Oracle Database 19c** que permite:

* Resolver ecuaciones cúbicas (tercer grado).
* Calcular una raíz real utilizando el método de Newton-Raphson.
* Almacenar los resultados en Oracle 19c.
* Consultar historial de cálculos.
* Eliminar registros.
* Gestionar información mediante una interfaz gráfica.

---

# Tecnologías Utilizadas

* Java 21
* IntelliJ IDEA
* Swing
* Oracle Database 19c
* JDBC
* Oracle JDBC Driver (ojdbc11.jar)

---

# Estructura del Proyecto

```text
EcuacionTercerGradoOracle/

├── src/
│   ├── conexion/
│   │   └── ConexionOracle.java
│   │
│   ├── modelo/
│   │   ├── Resultado.java
│   │   └── EcuacionCubica.java
│   │
│   ├── dao/
│   │   └── ResultadoDAO.java
│   │
│   ├── vista/
│   │   └── VentanaPrincipal.java
│   │
│   └── Main.java
│
├── lib/
│   └── ojdbc11.jar
│
└── script_oracle.sql
```

---

# Base de Datos Oracle 19c

## Script de Creación

```sql
CREATE TABLE ECUACION_CUBICA (

    ID NUMBER GENERATED ALWAYS AS IDENTITY,

    A NUMBER(10,4),
    B NUMBER(10,4),
    C NUMBER(10,4),
    D NUMBER(10,4),

    RAIZ_REAL NUMBER(20,10),

    FECHA_CALCULO DATE DEFAULT SYSDATE,

    CONSTRAINT PK_ECUACION_CUBICA
    PRIMARY KEY(ID)
);
```

---

# Clase de Conexión Oracle

## ConexionOracle.java

```java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "oracle";

    public static Connection conectar() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
```

---

# Modelo

## Resultado.java

```java
package modelo;

public class Resultado {

    private int id;

    private double a;
    private double b;
    private double c;
    private double d;

    private double raizReal;

    public Resultado() {
    }

    public Resultado(
            double a,
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
```

---

# Resolución Matemática

## EcuacionCubica.java

```java
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
```

---

# Acceso a Datos

## ResultadoDAO.java

```java
package dao;

import conexion.ConexionOracle;
import modelo.Resultado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultadoDAO {

    public void guardar(Resultado r) {

        String sql = """
                INSERT INTO ECUACION_CUBICA
                (A,B,C,D,RAIZ_REAL)
                VALUES
                (?,?,?,?,?)
                """;

        try (
                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)
        ) {

            ps.setDouble(1, r.getA());
            ps.setDouble(2, r.getB());
            ps.setDouble(3, r.getC());
            ps.setDouble(4, r.getD());
            ps.setDouble(5, r.getRaizReal());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Resultado> listar() {

        List<Resultado> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM ECUACION_CUBICA ORDER BY ID";

        try (
                Connection cn =
                        ConexionOracle.conectar();

                Statement st =
                        cn.createStatement();

                ResultSet rs =
                        st.executeQuery(sql)
        ) {

            while(rs.next()) {

                Resultado r =
                        new Resultado();

                r.setId(rs.getInt("ID"));
                r.setA(rs.getDouble("A"));
                r.setB(rs.getDouble("B"));
                r.setC(rs.getDouble("C"));
                r.setD(rs.getDouble("D"));
                r.setRaizReal(
                        rs.getDouble("RAIZ_REAL")
                );

                lista.add(r);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }

    public void eliminar(int id){

        String sql =
                "DELETE FROM ECUACION_CUBICA WHERE ID=?";

        try(
                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
```

---

# Interfaz Gráfica Swing

## VentanaPrincipal.java

```java
package vista;

import dao.ResultadoDAO;
import modelo.EcuacionCubica;
import modelo.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    JTextField txtA = new JTextField();
    JTextField txtB = new JTextField();
    JTextField txtC = new JTextField();
    JTextField txtD = new JTextField();

    JButton btnCalcular =
            new JButton("Calcular");

    JButton btnGuardar =
            new JButton("Guardar");

    JButton btnConsultar =
            new JButton("Consultar");

    JButton btnEliminar =
            new JButton("Eliminar");

    JLabel lblResultado =
            new JLabel("Raíz:");

    JTable tabla =
            new JTable();

    DefaultTableModel modelo;

    ResultadoDAO dao =
            new ResultadoDAO();

    double raizCalculada;

    public VentanaPrincipal() {

        setTitle(
                "Ecuación de Tercer Grado Oracle"
        );

        setSize(900,600);

        setDefaultCloseOperation(
                EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(new GridLayout(5,2));

        panel.add(new JLabel("A"));
        panel.add(txtA);

        panel.add(new JLabel("B"));
        panel.add(txtB);

        panel.add(new JLabel("C"));
        panel.add(txtC);

        panel.add(new JLabel("D"));
        panel.add(txtD);

        panel.add(btnCalcular);
        panel.add(btnGuardar);

        add(panel,BorderLayout.NORTH);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("A");
        modelo.addColumn("B");
        modelo.addColumn("C");
        modelo.addColumn("D");
        modelo.addColumn("RAIZ");

        tabla.setModel(modelo);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        JPanel sur =
                new JPanel();

        sur.add(lblResultado);
        sur.add(btnConsultar);
        sur.add(btnEliminar);

        add(sur,BorderLayout.SOUTH);

        eventos();
    }

    private void eventos() {

        btnCalcular.addActionListener(e -> {

            double a =
                    Double.parseDouble(txtA.getText());

            double b =
                    Double.parseDouble(txtB.getText());

            double c =
                    Double.parseDouble(txtC.getText());

            double d =
                    Double.parseDouble(txtD.getText());

            raizCalculada =
                    EcuacionCubica.resolver(
                            a,b,c,d
                    );

            lblResultado.setText(
                    "Raíz = " +
                    raizCalculada
            );
        });

        btnGuardar.addActionListener(e -> {

            Resultado r =
                    new Resultado(
                            Double.parseDouble(txtA.getText()),
                            Double.parseDouble(txtB.getText()),
                            Double.parseDouble(txtC.getText()),
                            Double.parseDouble(txtD.getText()),
                            raizCalculada
                    );

            dao.guardar(r);

            JOptionPane.showMessageDialog(
                    null,
                    "Registro guardado"
            );
        });

        btnConsultar.addActionListener(e -> {

            modelo.setRowCount(0);

            dao.listar().forEach(r -> {

                modelo.addRow(new Object[]{
                        r.getId(),
                        r.getA(),
                        r.getB(),
                        r.getC(),
                        r.getD(),
                        r.getRaizReal()
                });

            });
        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila >= 0){

                int id =
                        Integer.parseInt(
                                modelo.getValueAt(
                                        fila,
                                        0
                                ).toString()
                        );

                dao.eliminar(id);

                JOptionPane.showMessageDialog(
                        null,
                        "Registro eliminado"
                );
            }
        });
    }
}
```

---

# Clase Principal

## Main.java

```java
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                () -> new VentanaPrincipal()
                        .setVisible(true)
        );
    }
}
```

---

# Casos de Prueba

## Ejemplo 1

Ecuación:

```text
x³ - 6x² + 11x - 6 = 0
```

Coeficientes:

```text
A = 1
B = -6
C = 11
D = -6
```

Raíces esperadas:

```text
1
2
3
```

---

## Ejemplo 2

Ecuación:

```text
x³ - 1 = 0
```

Coeficientes:

```text
A = 1
B = 0
C = 0
D = -1
```

Raíz real:

```text
1
```

---

## Ejemplo 3

Ecuación:

```text
x³ + 3x² - 4x - 12 = 0
```

Coeficientes:

```text
A = 1
B = 3
C = -4
D = -12
```

Raíces esperadas:

```text
2
-2
-3
```

---

# Dependencia JDBC Oracle

Agregar el archivo:

```text
ojdbc11.jar
```

en la carpeta:

```text
lib/
```

y añadirlo al proyecto desde IntelliJ IDEA.

---

# Funcionalidades

* Resolver ecuaciones de tercer grado.
* Calcular raíz real por Newton-Raphson.
* Guardar resultados en Oracle 19c.
* Consultar historial.
* Eliminar registros.
* Interfaz gráfica desarrollada con Swing.
* Arquitectura organizada en capas (Modelo, DAO, Vista y Conexión).

---

# Autor

Proyecto académico desarrollado con:

* Java 21
* Swing
* Oracle Database 19c
* JDBC
* IntelliJ IDEA
:. . / .
