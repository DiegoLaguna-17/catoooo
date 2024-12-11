import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AñadirComentario extends JFrame {
	JPanel panPrincipal;
	JPanel panFormulario;
	JPanel panLbl;
	JPanel panArea;
	JPanel panBoton;
	JLabel lblComentario;
	JTextArea txtComentario;
	JButton btnEnviar;
	//construcción del frame de añadir comentario
	public AñadirComentario(int usuario,String publicacion) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(300,300);
		panPrincipal=new JPanel(new BorderLayout());
		this.getContentPane().add(panPrincipal);
		panFormulario=new JPanel(new GridLayout(3,1));
		panLbl=new JPanel(new FlowLayout(FlowLayout.CENTER));
		lblComentario=new JLabel("Comentario:");
		panLbl.add(lblComentario);
		panArea=new JPanel(new FlowLayout(FlowLayout.CENTER));
		txtComentario=new JTextArea(5,20);
		txtComentario.setPreferredSize(new Dimension(200,100));
		panArea.add(txtComentario);
		panBoton=new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnEnviar=new JButton ("Subir comentario");
		panBoton.add(btnEnviar);
		panFormulario.add(panLbl);
		panFormulario.add(panArea);
		panFormulario.add(panBoton);
		
		panPrincipal.add(panFormulario,BorderLayout.CENTER);
		//accion del boton enviar para subir el comentario
		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtComentario.getText().equals("")) {
					StringBuilder idComent=new StringBuilder();
					idComent.append(publicacion);
					int cc=cantPublicacion(publicacion)+1;
					idComent.append("C");
					idComent.append(cc);
					
					Comentario nuevo=new Comentario(idComent.toString(),txtComentario.getText(),usuario);
					nuevo.subirComentario();
					txtComentario.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Comentario invalido");
				}
					
			}
			
		});
		
		
		setVisible(true);
	}
	
	//funcion para obtenerla cantidad de comentarios de un publicacion
	private static int cantPublicacion(String idPubli) {
	    String query = "SELECT COUNT(*) AS total FROM comentarios WHERE idcomentario LIKE ?";
	    try (Connection conn = Conexion.conectar();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        // Configura el parámetro en el PreparedStatement correcto
	        pstmt.setString(1,  "%"+idPubli +"%");

	        // Ejecuta la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int total = rs.getInt("total");
	                System.out.println("solicitudes r" + total);
	                return total;
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al contar comentarios: " + e.getMessage());
	    }
	    return 0;
	}

}
