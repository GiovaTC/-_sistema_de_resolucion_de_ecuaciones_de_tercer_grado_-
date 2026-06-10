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

        String sql = "SELECT * FROM ECUACION_CUBICA ORDER BY ID";

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
        } catch (Exception e){
            e.printStackTrace();
        }

        return lista;
    }

    public void eliminar(int id) {

        String sql = "DELETE FROM ECUACION_CUBICA WHERE ID = ?";

        try (
                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)
        ){
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
