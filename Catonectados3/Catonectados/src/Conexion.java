import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//clase que permite la conexion entre el proyecto y la base de datos
public class Conexion {

    // Método estático para conectar a la base de datos PostgreSQL
    public static Connection conectar() {
        try {
            // Cambia la URL, usuario y contraseña según tu configuración
            Connection cn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/RedSocial", // URL de la base de datos
                "postgres", // Usuario
                "admin" // Contraseña
            );
            return cn;
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
        return null;
    }
    
    
    
   
}