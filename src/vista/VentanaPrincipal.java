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