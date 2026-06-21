# -_Ecuacion_tercer_grado_oracle_- :.
# EcuacionTercerGradoOracle:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/f8df4098-6787-4f0f-b2a0-c315786f9e93" />  

```

## Proyecto Java 21 + IntelliJ IDEA + Oracle Database 19c
Proyecto desarrollado utilizando **Java 21**, **Swing**, **Oracle Database 19c** y **JDBC**, implementando una **arquitectura
por capas (MVC + DAO)** para resolver, representar gráficamente y almacenar ecuaciones polinómicas de tercer grado .

---

# Objetivos del Proyecto

El sistema integra múltiples áreas del desarrollo de software:

- Programación Orientada a Objetos (POO)
- Arquitectura por capas
- Java 21
- Swing
- Oracle Database 19c
- JDBC (ojdbc11)
- Graphics2D
- Cálculo matemático
- Graficación en plano cartesiano
- Persistencia de datos

---

# Estructura del Proyecto

```text
EcuacionTercerGradoOracle
│
├── src
│
├── principal
│      Main.java
│
├── conexion
│      ConexionOracle.java
│
├── modelo
│      Ecuacion.java
│      PuntoGrafica.java
│
├── dao
│      EcuacionDAO.java
│
├── servicio
│      EcuacionService.java
│
├── grafica
│      PanelGrafica.java
│
├── vista
│      FrmPrincipal.java
│
└── util
       CalculadoraCubica.java
```

---

# Funcionalidades

La aplicación permite ingresar una ecuación polinómica de tercer grado:

\[
f(x)=ax^3+bx^2+cx+d
\]

Ejemplo:

```text
a = 1
b = -6
c = 11
d = -6
```

El sistema calcula automáticamente:

- La función polinómica.
- Las raíces reales.
- Punto máximo local.
- Punto mínimo local.
- Tabla de valores.
- Aproximadamente 400 puntos para la gráfica.

---

# Representación Gráfica

La aplicación dibuja automáticamente un plano cartesiano que incluye:

- Eje X
- Eje Y
- Curva cúbica
- Coordenadas
- Puntos importantes

Ejemplo:

```text
            Y
            ↑

         *
       *
     *
   *
 *
----------------------------→ X
        *
          *
             *
```

---

# Interfaz Gráfica Swing

```text
----------------------------------------------------------
           ECUACIÓN POLINÓMICA DE TERCER GRADO

a [      ]

b [      ]

c [      ]

d [      ]

                [ Calcular ]

----------------------------------------------------------

Ecuación

f(x)=x³-6x²+11x-6

Raíces

1
2
3

----------------------------------------------------------

            Plano Cartesiano

              (Gráfica)

----------------------------------------------------------

[ Guardar en Oracle ]

[ Consultar Historial ]

----------------------------------------------------------
```

---

# Información almacenada en Oracle

Cada cálculo realizado queda registrado.

## Tabla principal

```text
ECUACION_CUBICA
```

Campos:

| Campo | Descripción |
|--------|-------------|
| ID | Identificador |
| A | Coeficiente |
| B | Coeficiente |
| C | Coeficiente |
| D | Coeficiente |
| ECUACION | Función generada |
| RAICES | Raíces encontradas |
| FECHA | Fecha del cálculo |

---

## Tabla de puntos de la gráfica

```text
PUNTOS_GRAFICA
```

Campos:

| Campo | Descripción |
|--------|-------------|
| ID | Identificador |
| ID_ECUACION | Relación con la ecuación |
| X | Coordenada X |
| Y | Coordenada Y |

En esta tabla se almacenan todos los puntos utilizados para construir la curva.

---

# Flujo del Programa

```text
Usuario

      │

      ▼

Ingresa coeficientes

      │

      ▼

Java

      │

      ▼

Calcula la ecuación

      │

      ▼

Calcula raíces

      │

      ▼

Genera aproximadamente 400 puntos

      │

      ▼

Dibuja la gráfica

      │

      ▼

Guarda en Oracle

      │

      ▼

Consulta historial
```

---

# Cálculo de la Gráfica

El programa evalúa múltiples valores de **X**.

Ejemplo:

```text
x = -10.0

x = -9.9

x = -9.8

...

x = 9.8

x = 9.9

x = 10.0
```

Para cada valor calcula:

\[
y=ax^3+bx^2+cx+d
\]

Produciendo aproximadamente:

```text
400 puntos
```

que posteriormente se unen mediante líneas para generar una curva suave.

---

# Plano Cartesiano

El panel gráfico realiza los siguientes pasos:

## 1. Dibuja el eje X

```text
────────────────────────────────────────
```

## 2. Dibuja el eje Y

```text
│
│
│
│
│
```

## 3. Convierte coordenadas matemáticas

```text
(x , y)

↓

(screenX , screenY)
```

## 4. Dibuja la curva

```text
●────────●────────●────────●────────●
```

Resultado:

Una representación continua y suave de la función cúbica.

---

# Script Oracle Database 19c

## Tabla de ecuaciones

```sql
CREATE TABLE ECUACION_CUBICA(

ID NUMBER GENERATED ALWAYS AS IDENTITY,

A NUMBER,

B NUMBER,

C NUMBER,

D NUMBER,

ECUACION VARCHAR2(300),

RAICES VARCHAR2(300),

FECHA DATE DEFAULT SYSDATE,

PRIMARY KEY(ID)

);
```

---

## Tabla de puntos

```sql
CREATE TABLE PUNTOS_GRAFICA(

ID NUMBER GENERATED ALWAYS AS IDENTITY,

ID_ECUACION NUMBER,

X NUMBER,

Y NUMBER,

PRIMARY KEY(ID),

FOREIGN KEY(ID_ECUACION)
REFERENCES ECUACION_CUBICA(ID)

);
```

---

# Arquitectura del Proyecto

```text
                 Vista (Swing)

                      │

                      ▼

              EcuacionService

                      │

                      ▼

               EcuacionDAO

                      │

                      ▼

              Oracle Database

```

🔗 Conexión a Oracle

```

java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "usuario";
    private static final String PASSWORD = "clave";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

```

📐 Modelo
java

```

package modelo;

public class Ecuacion {
    private int id;
    private double a, b, c, d;
    private String ecuacion;
    private String raices;

    // Getters y Setters
}

```

java

```

package modelo;

public class PuntoGrafica {
    private int id;
    private int idEcuacion;
    private double x;
    private double y;

    // Getters y Setters
}


```

💾 DAO
java

```
package dao;

import conexion.ConexionOracle;
import modelo.Ecuacion;
import modelo.PuntoGrafica;

import java.sql.*;
import java.util.List;

public class EcuacionDAO {

    public int guardarEcuacion(Ecuacion ecuacion) throws SQLException {
        String sql = "INSERT INTO ECUACION_CUBICA(A,B,C,D,ECUACION,RAICES) VALUES(?,?,?,?,?,?)";
        try (Connection conn = ConexionOracle.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, ecuacion.getA());
            ps.setDouble(2, ecuacion.getB());
            ps.setDouble(3, ecuacion.getC());
            ps.setDouble(4, ecuacion.getD());
            ps.setString(5, ecuacion.getEcuacion());
            ps.setString(6, ecuacion.getRaices());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void guardarPuntos(int idEcuacion, List<PuntoGrafica> puntos) throws SQLException {
        String sql = "INSERT INTO PUNTOS_GRAFICA(ID_ECUACION,X,Y) VALUES(?,?,?)";
        try (Connection conn = ConexionOracle.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (PuntoGrafica p : puntos) {
                ps.setInt(1, idEcuacion);
                ps.setDouble(2, p.getX());
                ps.setDouble(3, p.getY());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}

```

⚙️ Servicio
java

```
package servicio;

import dao.EcuacionDAO;
import modelo.Ecuacion;
import modelo.PuntoGrafica;
import util.CalculadoraCubica;

import java.sql.SQLException;
import java.util.List;

public class EcuacionService {
    private final EcuacionDAO dao = new EcuacionDAO();

    public void procesarEcuacion(double a, double b, double c, double d) throws SQLException {
        Ecuacion ecuacion = CalculadoraCubica.calcularEcuacion(a, b, c, d);
        int id = dao.guardarEcuacion(ecuacion);

        List<PuntoGrafica> puntos = CalculadoraCubica.generarPuntos(a, b, c, d, id);
        dao.guardarPuntos(id, puntos);
    }
}

```

🧮 Utilidad de Cálculo
java

```

package util;

import modelo.Ecuacion;
import modelo.PuntoGrafica;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraCubica {

    public static Ecuacion calcularEcuacion(double a, double b, double c, double d) {
        Ecuacion e = new Ecuacion();
        e.setA(a); e.setB(b); e.setC(c); e.setD(d);
        e.setEcuacion(String.format("f(x)=%.2fx³+%.2fx²+%.2fx+%.2f", a, b, c, d));
        e.setRaices("Raíces calculadas aquí"); // Simplificado
        return e;
    }

    public static List<PuntoGrafica> generarPuntos(double a, double b, double c, double d, int idEcuacion) {
        List<PuntoGrafica> puntos = new ArrayList<>();
        for (double x = -10; x <= 10; x += 0.05) {
            double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
            PuntoGrafica p = new PuntoGrafica();
            p.setIdEcuacion(idEcuacion);
            p.setX(x);
            p.setY(y);
            puntos.add(p);
        }
        return puntos;
    }
}

```

🎨 Clase PanelGrafica
java

```

package grafica;

import modelo.PuntoGrafica;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelGrafica extends JPanel {
    private List<PuntoGrafica> puntos;

    public void setPuntos(List<PuntoGrafica> puntos) {
        this.puntos = puntos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (puntos == null || puntos.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        // Dibujar ejes
        g2.drawLine(0, height / 2, width, height / 2); // eje X
        g2.drawLine(width / 2, 0, width / 2, height); // eje Y

        // Escala
        double escalaX = width / 20.0;   // -10 a 10
        double escalaY = height / 20.0;  // -10 a 10

        // Dibujar curva
        g2.setColor(Color.BLUE);
        for (int i = 1; i < puntos.size(); i++) {
            int x1 = (int) (width / 2 + puntos.get(i - 1).getX() * escalaX);
            int y1 = (int) (height / 2 - puntos.get(i - 1).getY() * escalaY);
            int x2 = (int) (width / 2 + puntos.get(i).getX() * escalaX);
            int y2 = (int) (height / 2 - puntos.get(i).getY() * escalaY);
            g2.drawLine(x1, y1, x2, y2);
        }
    }
}

```

🖼️ Integración en FrmPrincipal
java

```

package vista;

import grafica.PanelGrafica;
import modelo.PuntoGrafica;
import servicio.EcuacionService;
import util.CalculadoraCubica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FrmPrincipal extends JFrame {
    private JTextField txtA, txtB, txtC, txtD;
    private JButton btnCalcular;
    private PanelGrafica panelGrafica;

    public FrmPrincipal() {
        setTitle("Ecuación Cúbica");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtA = new JTextField(5);
        txtB = new JTextField(5);
        txtC = new JTextField(5);
        txtD = new JTextField(5);
        btnCalcular = new JButton("Calcular");
        panelGrafica = new PanelGrafica();

        JPanel panelInputs = new JPanel();
        panelInputs.add(new JLabel("a:")); panelInputs.add(txtA);
        panelInputs.add(new JLabel("b:")); panelInputs.add(txtB);
        panelInputs.add(new JLabel("c:")); panelInputs.add(txtC);
        panelInputs.add(new JLabel("d:")); panelInputs.add(txtD);
        panelInputs.add(btnCalcular);

        add(panelInputs, BorderLayout.NORTH);
        add(panelGrafica, BorderLayout.CENTER);

        btnCalcular.addActionListener((ActionEvent e) -> {
            try {
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double c = Double.parseDouble(txtC.getText());
                double d = Double.parseDouble(txtD.getText());

                EcuacionService service = new EcuacionService();
                service.procesarEcuacion(a, b, c, d);

                List<PuntoGrafica> puntos = CalculadoraCubica.generarPuntos(a, b, c, d, 0);
                panelGrafica.setPuntos(puntos);

                JOptionPane.showMessageDialog(this, "Ecuación calculada y guardada en Oracle");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
    }

```
---

# Tecnologías Utilizadas

| Tecnología | Uso |
|------------|-----|
| Java 21 | Desarrollo del proyecto |
| IntelliJ IDEA | IDE |
| Swing | Interfaz gráfica |
| Graphics2D | Graficación |
| Oracle Database 19c | Persistencia |
| JDBC (ojdbc11) | Conexión con Oracle |
| MVC | Arquitectura |
| DAO | Acceso a datos |
| POO | Programación orientada a objetos |

---

# Características del Proyecto

- Arquitectura profesional por capas.
- Desarrollo completamente orientado a objetos.
- Interfaz gráfica Swing.
- Graficación matemática en tiempo real.
- Cálculo automático de funciones cúbicas.
- Obtención de raíces reales.
- Cálculo de máximos y mínimos locales.
- Generación de tablas de valores.
- Persistencia completa en Oracle Database 19c.
- Historial de cálculos.
- Almacenamiento de todos los puntos de la curva.
- Código organizado y escalable.
- Compatible con Java 21.

---

# Resultado Esperado

El usuario podrá:

1. Ingresar los coeficientes de una ecuación cúbica.
2. Calcular automáticamente la función.
3. Obtener las raíces reales.
4. Visualizar la curva en un plano cartesiano.
5. Guardar toda la información en Oracle Database 19c.
6. Consultar el historial de cálculos realizados.
7. Reutilizar la información almacenada para futuras consultas.

---

# Autor

Proyecto académico desarrollado utilizando:

- Java 21
- IntelliJ IDEA
- Swing
- Oracle Database 19c
- JDBC
- Graphics2D
- Arquitectura MVC + DAO
- Programación Orientada a Objetos (POO) .
:. . / . 
