package com.example.recu3;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import java.sql.*;

public class DAO {
    private final String servidor = "jdbc:mariadb://localhost:3306/productos";
    private final String usuario = "root";
    private final String passwd = "";

    private Connection conexionBBDD;

    public ObservableList<Producto> obtenerProductos() {

        ObservableList<Producto> datosResultadoConsulta = FXCollections.observableArrayList();
        try {
            // Nos conectamos
            conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "SELECT * "
                    + "FROM products "
                    + "ORDER By productName";

            // Ejecutamos la consulta y nos devuelve una matriz de filas (registros) y columnas (datos)
            ResultSet resultadoConsulta = conexionBBDD.createStatement().executeQuery(SQL);

            // Debemos cargar los datos en el ObservableList (Que es un ArrayList especial)
            // Los datos que devuelve la consulta no son directamente cargables en nuestro objeto
            while (resultadoConsulta.next()) {
                datosResultadoConsulta.add(new Producto(
                        resultadoConsulta.getString("productCode"),
                        resultadoConsulta.getString("productName"),
                        resultadoConsulta.getString("productLine"),
                        resultadoConsulta.getString("productScale"),
                        resultadoConsulta.getString("productVendor"),
                        resultadoConsulta.getString("productDescription"),
                        resultadoConsulta.getInt("quantityInStock"),
                        resultadoConsulta.getDouble("buyPrice"),
                        resultadoConsulta.getDouble("MSRP"))
                );
                System.out.println("Row [1] added " + resultadoConsulta.toString());
            }
            conexionBBDD.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            conexionBBDD.close();
        } finally {
            return datosResultadoConsulta;
        }
    }

    // Alta de un nuevo producto
    //
    public Boolean altaProducto(Producto producto) {

        int registrosAfectadosConsulta = 0;

        try {
            // Nos conectamos
            conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "INSERT INTO products ("
                    + " productCode ,"
                    + " productName ,"
                    + " productLine ,"
                    + " productScale ,"
                    + " productVendor ,"
                    + " productDescription ,"
                    + " quantityInStock ,"
                    + " buyPrice ,"
                    + " MSRP  )"
                    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conexionBBDD.prepareStatement(SQL);
            st.setString(1, producto.getProductCode());
            st.setString(2, producto.getProductName());
            st.setString(3, producto.getProductLine());
            st.setString(4, producto.getProductScale());
            st.setString(5, producto.getProductVendor());
            st.setString(6, producto.getProductDescription());

            st.setInt(7, producto.getQuantityInStock());
            st.setDouble(8, producto.getBuyPrice());
            st.setDouble(9, producto.getMSRP());

            // Ejecutamos la consulta preparada (con las ventajas de seguridad y velocidad en el servidor de BBDD
            // nos devuelve el número de registros afectados. Al ser un Insert nos debe devolver 1 si se ha hecho correctamente
            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexionBBDD.close();

            if (registrosAfectadosConsulta == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            return false;
        }
    }
    public Boolean borrarProducto(Producto nfc) {

        int registrosAfectadosConsulta = 0;

        try {

            conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "DELETE FROM products "
                    + " WHERE productCode = ? ";

            PreparedStatement st = conexionBBDD.prepareStatement(SQL);

            st.setInt(1, Integer.valueOf(nfc.getProductCode()));

            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexionBBDD.close();

            if (registrosAfectadosConsulta == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            return false;
        }
    }
    public Boolean actualizarProducto(Producto producto) {

        int registrosAfectadosConsulta = 0;

        try {
            conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "UPDATE products ("
                    + " productCode ,"
                    + " productName ,"
                    + " productLine ,"
                    + " productScale ,"
                    + " productVendor ,"
                    + " productDescription ,"
                    + " quantityInStock ,"
                    + " buyPrice ,"
                    + " MSRP  )"
                    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conexionBBDD.prepareStatement(SQL);
            st.setString(1, producto.getProductCode());
            st.setString(2, producto.getProductName());
            st.setString(3, producto.getProductLine());
            st.setString(4, producto.getProductScale());
            st.setString(5, producto.getProductVendor());
            st.setString(6, producto.getProductDescription());

            st.setInt(7, producto.getQuantityInStock());
            st.setDouble(8, producto.getBuyPrice());
            st.setDouble(9, producto.getMSRP());

            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexionBBDD.close();

            if (registrosAfectadosConsulta == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            return false;
        }
    }

}
