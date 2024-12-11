import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
public class Publicacion {
	private String idPublicacion;
	private String foto;
	//constructor de una nueva publicacion
	public Publicacion(String idPublicacion, String foto) {
		this.idPublicacion = idPublicacion;
		this.foto = foto;
	}
	//funcion  para insertar la publicacion en la base  de dato
	 public  boolean subirPublicacion( ) {
		 String query = "INSERT INTO publicaciones (idpublicacion, pub_foto, pub_fecha) VALUES (?, ?, current_date)";
	        try (Connection conn = Conexion.conectar();
	                PreparedStatement pstmt = conn.prepareStatement(query)){
	        	
	        	
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, this.idPublicacion);
	            stmt.setString(2, this.foto);
	            stmt.executeUpdate();
	            stmt.close();
	            
	            JOptionPane.showMessageDialog(null, "Publicacion agregada");
	            return true;
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al subir publicacion "+e);
	            return false;
	        }
	        
	    }
	
}
