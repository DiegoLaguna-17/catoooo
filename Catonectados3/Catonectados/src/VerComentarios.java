import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VerComentarios extends JFrame {

	JPanel panPrincipal;
	JPanel panTitulo;
	JPanel panAux;
	JLabel lblTitulo;
	JScrollPane scrollComents;
	public VerComentarios(String idPublicacion) {
		//construccion del frame para ver comentarios
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(300,300);
		panPrincipal=new JPanel(new BorderLayout());
		this.getContentPane().add(panPrincipal);
		lblTitulo=new JLabel("Comentarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO


		panTitulo=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panTitulo.setBackground(new Color(23, 80, 172)); // FONDO AZUL
		panTitulo.add(lblTitulo);
		panAux=new JPanel();
		panAux.setLayout(new BoxLayout(panAux, BoxLayout.Y_AXIS));
		scrollComents=new JScrollPane(panAux);
		sacarComentarios(panAux,idPublicacion);//obtiene los comentarios de una publicacion
		panPrincipal.add(panTitulo,BorderLayout.NORTH);
		panPrincipal.add(scrollComents,BorderLayout.CENTER);
		
		
		setVisible(true);
	}

	
	
	//obtiene los comentarios de una publicacion
	
	private static void sacarComentarios(JPanel panResultados, String idP) {
		System.out.println(idP);
		String query = "SELECT pu.pu_foto,pu.pu_usuario,c.c_comentario, c.c_fecha  FROM comentarios c, perfilusuario pu"
				+ " WHERE c.c_idcomentor=pu.id_usuario and c.idcomentario like ?";
        
			try (Connection conn = Conexion.conectar();
	                PreparedStatement pstmt = conn.prepareStatement(query)){
	            
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, "%" + idP + "%");
	            
	            
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                // Crear un panel para cada resultado
		        		ImageIcon fotoPub=createScaledImageIcon(rs.getString("pu_foto"),20,20);
		        		JLabel fotoP=new JLabel();
		        		fotoP.setIcon(fotoPub);
		        		JPanel panContenedor=new JPanel(new GridLayout(2,1));
		                JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER));
		                JLabel lblFecha = new JLabel(rs.getString("c_fecha"));
		                JLabel lblComentario=new JLabel(rs.getString("c_comentario"));
		                JLabel lblUsuario=new JLabel(rs.getString("pu_usuario"));
		                JPanel panCom =new JPanel(new FlowLayout());
		                panCom.add(lblComentario);
		                panCom.setBackground(new Color(115, 185, 238)); // FONDO AMARILLO
		                panelUsuario.setBackground(new Color(84, 148, 218)); // FONDO AMARILLO

		                panelUsuario.add(lblUsuario);
		                panelUsuario.add(fotoP);
		                panelUsuario.add(lblFecha);
		               panContenedor.add(panelUsuario);
		               panContenedor.add(panCom);	
		               panContenedor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
		               panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
		               
		                panResultados.add(panContenedor);
		                // Acción del botón de enviar solicitud
		                
		                
		
		                // Agregar el panel del usuario al panel de resultados
	                
	            }
	
	            rs.close();
	            stmt.close();
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al buscar comentarios: " + e.getMessage());
	        }
        
    }
	//funcion para escalar una imagen a gusto
	private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        
        // Escalar la imagen a la nueva dimensión
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        // Crear un ImageIcon a partir de la imagen escalada
        return new ImageIcon(imagenEscalada);
    }
}
