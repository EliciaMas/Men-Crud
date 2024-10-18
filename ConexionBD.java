package SParcial;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    static Scanner scanner = new Scanner(System.in);
    private static final String URL = "jdbc:mysql://localhost:3306/BDFloreriaLaElegancia";
    private static final String USER = "root";
    private static final String PASSWORD = "ma213027%ca";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }// fin conectar()

    public static void insertarProducto(int codigo, String nombre, double precio, int cantidad, String fecha) {
        String query = "INSERT INTO producto (codigoProducto, nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento) VALUES (?,?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, codigo);
            pst.setString(2, nombre);
            pst.setDouble(3, precio);
            pst.setInt(4, cantidad);
            pst.setDate(5, java.sql.Date.valueOf(fecha));
            pst.executeUpdate();
            System.out.println("Producto insertado correctamente");
            
        } catch (SQLException e) {
            System.out.println("Error, el codigo ingresado ya existe");
        }
    }// fin insertarProducto()

    public static void listarProductos() {
        String query = "select * from producto;";
        try (Connection con = ConexionBD.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.println("Código: " + rs.getString("codigoProducto"));
                System.out.println("Nombre: " + rs.getString("nombreProducto"));
                System.out.println("Precio: " + rs.getDouble("precioUnitario"));
                System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
                System.out.println("Fecha de Vencimiento: " + rs.getDate("fechaVencimiento"));
                System.out.println("");
            }
            if (!hayResultados) {
                System.out.println("No hay productos disponibles.");
            }//fin if

        } catch (SQLException e) {

        }//fin catch
    }// fin listarProductos()
    
    public static void buscarProducto(int codigoProducto) {
        String query = "SELECT * FROM producto WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, codigoProducto);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Producto encontrado:");
                    System.out.println("Código: " + rs.getInt("codigoProducto"));
                    System.out.println("Nombre: " + rs.getString("nombreProducto"));
                    System.out.println("Precio: " + rs.getDouble("precioUnitario"));
                    System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
                    System.out.println("Fecha de Vencimiento: " + rs.getDate("fechaVencimiento"));
                } else {
                    System.out.println("No existe en el registro el producto con el código: " + codigoProducto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el producto: " + e.getMessage());
        }
    }//Fin buscarProducto()

    public static void actualizarProducto(int codigoProducto, String nombre, double precio) {
        String query = "UPDATE producto SET nombreProducto = ?, precioUnitario = ? WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, nombre);
            pst.setDouble(2, precio);
            pst.setInt(3, codigoProducto);
            pst.executeUpdate();
            int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Producto actualizado correctamente");
         } else {
            System.out.println("No se puede actualizar el producto con el codigo " +codigoProducto+ "porque no existe en el registro.");
         }
        } catch (SQLException e) {
           
        }
    }// fin actualizarProducto

    public static void eliminarProducto(int codigoProducto) {
        String query = "DELETE FROM producto WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, codigoProducto);
            int filasCodigo = pst.executeUpdate(); // Almacena el número de filas afectadas

        // Verificar si se eliminó algún producto
        if (filasCodigo > 0) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el producto " + codigoProducto + " Porque no existe en el registro ");
        }   
        } catch (SQLException e) {
            
        }
    }// fin eliminarProducto

    public static void main(String[] args) {
        //conectar();
        //insertarProducto("VRG100", "Virogrip", 50, 150, "2024-10-31");
        //listarProductos();
        //actualizarProducto("VRG100","Virogrip funcional",150.99);
        //eliminarProducto("VRG100");

        int opcion;
        do {
            System.out.println("****************************");
            System.out.println("****** Menu Principal ******");
            System.out.println("****************************");
            System.out.println("1) Ingresar Producto");
            System.out.println("2) Mostrar Producto");
            System.out.println("3) Buscar Producto");
            System.out.println("4) Actualizar Producto");
            System.out.println("5) Eliminar Producto");
            System.out.println("6) Salir");
            System.out.println("");
            System.out.print("Seleccione una opcion del menú: ");
            opcion = scanner.nextInt();
            System.out.println("");
            switch (opcion) {
                case 1: {
                    System.out.println("*** Ingresar Producto ***");
                    System.out.println("_________________________");
                    System.out.print("Código: ");
                    int codigo = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Precio Q: ");
                    double precio = scanner.nextDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Fecha de vencimiento (Año,mes,dia): ");
                    String fecha = scanner.nextLine();
                    insertarProducto(codigo, nombre, precio, cantidad, fecha);
                    
                    break;
                    
                }//case 1
                case 2: {
                    System.out.println("*** Mostrar Producto ***");
                    System.out.println("________________________");
                    ConexionBD.listarProductos();
                    break;
                }//case 2 
                case 3:{
                    System.out.println("*** Buscar Producto ***");
                    System.out.println("_______________________");
                    System.out.print("Ingrese el código del producto a buscar: ");
                    int codigoProducto = scanner.nextInt();
                    ConexionBD.buscarProducto(codigoProducto);
                    break;
                }
                case 4: {
                    System.out.println("*** Actualizar Producto ***");
                    System.out.println("___________________________");
                    System.out.print("Ingrese el código del producto a actualizar: ");
                    int codigoProducto = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Nuevo precio: ");
                    double precio = scanner.nextDouble();
                    ConexionBD.actualizarProducto(codigoProducto, nombre, precio);
                    break;
                }//case 3 
                case 5: {
                    System.out.println("*** Eliminar Producto ***");
                    System.out.println("_________________________");
                    System.out.print("Ingrese el código del producto a eliminar: ");
                    int codigoProducto = scanner.nextInt();
                    ConexionBD.eliminarProducto(codigoProducto);
                    break;
                }//case 4
                case 6: {
                    System.exit(0);
                    break;
                }//case 4
                default:
                    System.out.println("Ingrese una opción válida del menú.");
            }//fin switch()

        } while (opcion !=6);
    }// main
}// fin class

