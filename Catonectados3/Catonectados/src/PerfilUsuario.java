import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
public class PerfilUsuario {
	private  int idUsuario;
	private String usuario;
	private String contraseña;
	private String nombre;
	private String apellido;
	private String foto;
	private String estado;
	private Hashtable<String,String>intereses;
	private ArrayList<String>detalles;
	//constructor del usuario, utilizado en el registro de un nuevo usuario
	public PerfilUsuario(String usuario, String contraseña, String nombre, String apellido, String foto,
			String estado) {
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.apellido = apellido;
		this.foto = foto;
		this.estado = estado;
	}
	
	
	
	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getContraseña() {
		return contraseña;
	}



	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido() {
		return apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public String getFoto() {
		return foto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Hashtable<String, String> getIntereses() {
		return intereses;
	}



	public void setIntereses(Hashtable<String, String> intereses) {
		this.intereses = intereses;
	}



	public ArrayList<String> getDetalles() {
		return detalles;
	}



	public void setDetalles(ArrayList<String> detalles) {
		this.detalles = detalles;
	}


	//funcion que realiza la insercion del nuevo usuario en la base de datos
	public boolean insertarPerfilUsuario() {
        String sql = "INSERT INTO perfilusuario (pu_usuario, pu_contraseña,"
        		+ "pu_nombre,pu_apellido,pu_foto,pu_estado) VALUES (?, ?,?,?,?,?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,this.usuario);
            pstmt.setString(2,this.contraseña);
            pstmt.setString(3,this.nombre);
            pstmt.setString(4,this.apellido);
            pstmt.setString(5,this.foto);
            pstmt.setString(6,this.estado);
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("Perfil ingresado");
            conn.close();
            return filasInsertadas > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
            return false;
        }
    }

	@Override
	public String toString() {
		return "Perfil \nUsuario: " +usuario+ "\ncontraseña: " + contraseña + "\nnombre: " + nombre + "\napellido: "
				+ apellido + "\nfoto:" + foto + "\nestado:" + estado;
	}
	
	
	
	
	
}
