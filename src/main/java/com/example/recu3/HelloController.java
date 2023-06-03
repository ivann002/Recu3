package com.example.recu3;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

import java.sql.Date;
import java.util.Optional;

public class HelloController {
    private DAO productDAO = new DAO();
    private Producto productoAux;
    private ObservableList<Producto> datos;

    @FXML
    private TableColumn tcProductVendor;
    @FXML
    private Button btnBorrar;
    @FXML
    private TextField txtVendedor;
    @FXML
    private TextField txtStock;
    @FXML
    private TableColumn tcBuyPrice;
    @FXML
    private TableView<Producto> tvProductos;
    @FXML
    private TableColumn tcQuantityInStock;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEscala;
    @FXML
    private TableColumn tcProductDescription;
    @FXML
    private TextField txtPrecioCompra;
    @FXML
    private TableColumn tcProductScale;
    @FXML
    private Button btnAlta;
    @FXML
    private TableColumn tcMSRP;
    @FXML
    private TextField txtPrecioVenta;
    @FXML
    private TextField txtLinea;
    @FXML
    private TableColumn tcProductName;
    @FXML
    private TableColumn tcProductLine;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableColumn tcProductCode;

    private DAO dao = new DAO();
    private ObservableList<Producto> olProductos;

    public void initialize()  {

        cargarDatosTabla();
        productoAux = new Producto("","","",
                "","","",0,0d,0d);
        realizarBindingsProductoAux(productoAux);

    }
    private void realizarBindingsProductoAux ( Producto producto) {

        txtEscala.textProperty().bindBidirectional(productoAux.productScaleProperty());
        txtDescripcion.textProperty().bindBidirectional(producto.productDescriptionProperty());
        txtID.textProperty().bindBidirectional(producto.productCodeProperty());
        txtLinea.textProperty().bindBidirectional(producto.productLineProperty());
        txtNombre.textProperty().bindBidirectional(producto.productNameProperty());
        txtPrecioCompra.textProperty().bindBidirectional(producto.buyPriceProperty(),new NumberStringConverter());
        txtPrecioVenta.textProperty().bindBidirectional(producto.MSRPProperty(), new NumberStringConverter() );
        txtStock.textProperty().bindBidirectional(producto.quantityInStockProperty(), new NumberStringConverter() );
        txtVendedor.textProperty().bindBidirectional(producto.productVendorProperty());
    }

    private void cargarDatos(){
        olProductos = dao.obtenerProductos();

        tcProductCode.setCellValueFactory(new PropertyValueFactory<Producto, String>("productCode"));
        tcProductName.setCellValueFactory(new PropertyValueFactory<Producto, String>("productName"));
        tcProductLine.setCellValueFactory(new PropertyValueFactory<Producto, String>("productLine"));
        tcProductScale.setCellValueFactory(new PropertyValueFactory<Producto, String>("productScale"));
        tcProductVendor.setCellValueFactory(new PropertyValueFactory<Producto, String>("productVendor"));
        tcProductDescription.setCellValueFactory(new PropertyValueFactory<Producto, String>("productDescription"));
        tcQuantityInStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("quantityInStock"));
        tcBuyPrice.setCellValueFactory(new PropertyValueFactory<Producto, Double>("buyPrice"));
        tcMSRP.setCellValueFactory(new PropertyValueFactory<Producto,Double>("MSRP"));
        tvProductos.setItems(olProductos);
    }

    public void onAltaClicked(ActionEvent actionEvent) {
        if ( ! productoAux.getProductCode().trim().equals("")) {
            if (productDAO.altaProducto(productoAux)) {
                cargarDatosTabla();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe introducir un código de producto.", ButtonType.OK );
            alert.showAndWait();
        }


    }
    public void onBorrarClicked(ActionEvent actionEvent) {
        Alert alert;
        String Codigo = String.valueOf(productoAux.getProductCode());
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Quieres borrar la columna?");
        Optional<ButtonType> action  = alert.showAndWait();
        if(action.get() == ButtonType.OK) {
            if (!Codigo.trim().equals("")){
                if(dao.borrarProducto(productoAux)){
                    alert = new Alert(Alert.AlertType.INFORMATION, "La columna se ha borrado exitosamente");
                    alert.showAndWait();
                    cargarDatos();
                }
            }
        }else {
            alert = new Alert(Alert.AlertType.INFORMATION, "No se han borrado los datos");
            alert.showAndWait();
        }


    }
    public void onActualizarClicked(ActionEvent actionEvent) {
        cargarDatos();


    }
    private void cargarDatosTabla () {
        datos = productDAO.obtenerProductos();

        tcProductCode.setCellValueFactory(new PropertyValueFactory<Producto, String>("productCode"));
        tcProductDescription.setCellValueFactory(new PropertyValueFactory<Producto, String>("productDescription"));
        tcProductLine.setCellValueFactory(new PropertyValueFactory<Producto, String>("productLine"));
        tcProductName.setCellValueFactory(new PropertyValueFactory<Producto, String>("productName"));
        tcProductScale.setCellValueFactory(new PropertyValueFactory<Producto, String>("productScale"));
        tcProductVendor.setCellValueFactory(new PropertyValueFactory<Producto, String>("productVendor"));

        tcBuyPrice.setCellValueFactory(new PropertyValueFactory<Producto, Double>("buyPrice"));
        tcMSRP.setCellValueFactory(new PropertyValueFactory<Producto, Double>("MSRP"));
        tcQuantityInStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("quantityInStock"));

        tvProductos.setItems(datos);
    }
/*

public void initialize()  {

        cargarDatosTabla();
        productoAux = new Producto("","","",
                "","","",0,0d,0d);
        realizarBindingsProductoAux(productoAux);
        cargarGestorDobleCLick();
    }

    private void cargarDatosTabla () {
        datos = productDAO.obtenerProductos();

        tcProductCode.setCellValueFactory(new PropertyValueFactory<Producto, String>("productCode"));
        tcProductDescription.setCellValueFactory(new PropertyValueFactory<Producto, String>("productDescription"));
        tcProductLine.setCellValueFactory(new PropertyValueFactory<Producto, String>("productLine"));
        tcProductName.setCellValueFactory(new PropertyValueFactory<Producto, String>("productName"));
        tcProductScale.setCellValueFactory(new PropertyValueFactory<Producto, String>("productScale"));
        tcProductVendor.setCellValueFactory(new PropertyValueFactory<Producto, String>("productVendor"));

        tcBuyPrice.setCellValueFactory(new PropertyValueFactory<Producto, Double>("buyPrice"));
        tcMSRP.setCellValueFactory(new PropertyValueFactory<Producto, Double>("MSRP"));
        tcQuantityInStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("quantityInStock"));

        tvProductos.setItems(datos);
    }

    private void realizarBindingsProductoAux ( Producto producto) {

        txtEscala.textProperty().bindBidirectional(productoAux.productScaleProperty());
        txtDescripcion.textProperty().bindBidirectional(producto.productDescriptionProperty());
        txtID.textProperty().bindBidirectional(producto.productCodeProperty());
        txtLinea.textProperty().bindBidirectional(producto.productLineProperty());
        txtNombre.textProperty().bindBidirectional(producto.productNameProperty());
        txtPrecioCompra.textProperty().bindBidirectional(producto.buyPriceProperty(),new NumberStringConverter());
        txtPrecioVenta.textProperty().bindBidirectional(producto.MSRPProperty(), new NumberStringConverter() );
        txtStock.textProperty().bindBidirectional(producto.quantityInStockProperty(), new NumberStringConverter() );
        txtVendedor.textProperty().bindBidirectional(producto.productVendorProperty());
    }

    // Detectar el doble click en la tabla y cargar los datos en los campos de edición
    private void cargarGestorDobleCLick () {
        tvProductos.setRowFactory(tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    productoAux.setProductCode(row.getItem().getProductCode());
                    productoAux.setProductLine(row.getItem().getProductLine());
                    productoAux.setProductName(row.getItem().getProductName());
                    productoAux.setProductScale(row.getItem().getProductScale());
                    productoAux.setProductVendor(row.getItem().getProductVendor());
                    productoAux.setProductDescription(row.getItem().getProductDescription());
                    productoAux.setBuyPrice(row.getItem().getBuyPrice());
                    productoAux.setMSRP(row.getItem().getMSRP());
                    productoAux.setQuantityInStock(row.getItem().getQuantityInStock());
                }
            });
            return row;
        });
    }

   @javafx.fxml.FXML
    public void onActualizarClicked(ActionEvent actionEvent) {

        if ( ! productoAux.getProductCode().trim().equals("")) {
            if (productDAO.actualizarProducto(productoAux)) {
                cargarDatosTabla();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado un producto con el código '"
                        + productoAux.getProductCode() + "' .", ButtonType.OK );
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar el código del producto a actualizar.", ButtonType.OK );
            alert.showAndWait();
        }



    }

    @javafx.fxml.FXML
    public void onAltaClicked(ActionEvent actionEvent) {
        if ( ! productoAux.getProductCode().trim().equals("")) {
            if (productDAO.altaProducto(productoAux)) {
                cargarDatosTabla();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe introducir un código de producto.", ButtonType.OK );
            alert.showAndWait();
        }


    }

    @javafx.fxml.FXML
    public void onBorrarClicked(ActionEvent actionEvent) {

        Alert alert;

        if ( ! productoAux.getProductCode().trim().equals("")) {

            alert = new Alert(Alert.AlertType.CONFIRMATION, "¿ Desea borrar el producto con el código '"
                    + productoAux.getProductCode() + "' ?.", ButtonType.YES, ButtonType.NO );

            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {

                if (productDAO.borrarProducto(productoAux)) {
                    cargarDatosTabla();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado un producto con el código '"
                            + productoAux.getProductCode() + "' .", ButtonType.OK );
                    alert.showAndWait();
                }
            }
        }
        else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar el código del producto a borrar.", ButtonType.OK );
            alert.showAndWait();
        }
    }*/
}