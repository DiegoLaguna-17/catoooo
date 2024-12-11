import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
public class Comentario {
	private String idC;
	private String comentario;
	private int idComentor;
	//constructor de nuevo comentario
	public Comentario( String idC, String comentario, int idComentor) {
		this.idC = idC;
		this.comentario = comentario;
		this.idComentor = idComentor;
	}
	//funcion para insertar comentario a la base de datos
	public  boolean subirComentario( ) {
		 String query = "INSERT INTO comentarios (idcomentario, c_comentario, c_idcomentor,c_fecha) VALUES (?, ?,? ,current_date)";
	        try (Connection conn = Conexion.conectar();
	                PreparedStatement pstmt = conn.prepareStatement(query)){
	        	
	        	
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, this.idC);
	            stmt.setString(2, this.comentario);
	            stmt.setInt(3, this.idComentor);
	            stmt.executeUpdate();
	            stmt.close();
	            
	            JOptionPane.showMessageDialog(null, "Publicacion agregada");
	            return true;
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al subir comentario "+e);
	            return false;
	        }
	        
	    }
	
	
}
