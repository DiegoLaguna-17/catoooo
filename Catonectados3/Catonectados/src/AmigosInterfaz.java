import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AmigosInterfaz extends JFrame {

    JPanel panPrincipal;
    JPanel panIzq;
    JPanel panDer;
    JPanel panTAmigos;
    JPanel panTComun;
    JPanel panAmigos;
    JPanel panComun;
    JPanel panFAmigos;
    JPanel panFComun;
    JPanel panLAmigos;
    JPanel panLComun;

    JTextField txtAmigos;
    JTextField txtComun;
    JButton btnAmigos;
    JButton btnComun;
    JLabel lblTAmigos;
    JLabel lblTComun;

    public AmigosInterfaz(String usuario) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(800, 700);
    	setExtendedState(JFrame.MAXIMIZED_BOTH); // Para pantalla completa


        // Panel principal
        panPrincipal = new JPanel(new GridLayout(1, 2));
        this.getContentPane().add(panPrincipal);

        // Panel izquierdo
        panIzq = new JPanel(new BorderLayout());
        panIzq.setBackground(new Color(255, 223, 0)); // Amarillo brillante

        // Panel derecho
        panDer = new JPanel(new BorderLayout());
        panDer.setBackground(new Color(0, 102, 204)); // Azul intenso

        // Título "Mis Amigos"
        lblTAmigos = new JLabel("Mis Amigos", SwingConstants.CENTER);
        lblTAmigos.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblTAmigos.setForeground(Color.WHITE);
        lblTAmigos.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espacio arriba y abajo
        panTAmigos = new JPanel(new BorderLayout());
        panTAmigos.setBackground(new Color(23, 80, 172)); // FONDO 1
        panTAmigos.add(lblTAmigos, BorderLayout.CENTER);

        // Título "Amigos en Común"
        lblTComun = new JLabel("Amigos en Común", SwingConstants.CENTER);
        lblTComun.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblTComun.setForeground(Color.WHITE);
        lblTComun.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espacio arriba y abajo
        panTComun = new JPanel(new BorderLayout());
        panTComun.setBackground(new Color(51, 115, 196)); // FONDO 2
        panTComun.add(lblTComun, BorderLayout.CENTER);

        // Sección de búsqueda de amigos
        panAmigos = new JPanel(new BorderLayout());
        txtAmigos = new JTextField(20);
        btnAmigos = new JButton("Buscar");
        btnAmigos.setBackground(new Color(84, 148, 218)); // FONDO 3
        btnAmigos.setForeground(Color.WHITE);
        btnAmigos.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panFAmigos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Espacio entre componentes
        panFAmigos.setBackground(new Color(51, 115, 196)); // FONDO 2
        panFAmigos.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espacio arriba y abajo
        panFAmigos.add(txtAmigos);
        panFAmigos.add(btnAmigos);
        panAmigos.add(panFAmigos, BorderLayout.NORTH);

        panLAmigos = new JPanel();
        panLAmigos.setLayout(new BoxLayout(panLAmigos, BoxLayout.Y_AXIS));
        panLAmigos.setBackground(new Color(115, 185, 238)); // PANEL DE MUESTRA DE RESULTADOS DE AMIGOS 
        JScrollPane scrollAmigos = new JScrollPane(panLAmigos);
        int idSol=obtenerPerfilID(usuario); 
        sacarAmigos(panLAmigos,idSol);
        panAmigos.add(scrollAmigos, BorderLayout.CENTER);
        

     // Sección de búsqueda de amigos en común
        panComun = new JPanel(new BorderLayout());
        panComun.setBackground(new Color(240, 248, 255)); // Cambiado a un color azul claro (AliceBlue)
        txtComun = new JTextField(20);
        btnComun = new JButton("Buscar");
        btnComun.setBackground(new Color(84, 148, 218)); //FONDO 3
        btnComun.setForeground(Color.WHITE);
        btnComun.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panFComun = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Espacio entre componentes
        panFComun.setBackground(new Color(23, 80, 172)); // PANEL DE LA BARRA DE BUSQUEDA DE AMIGOS EN COMUN
        panFComun.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espacio arriba y abajo
        panFComun.add(txtComun);
        panFComun.add(btnComun);
        panComun.add(panFComun, BorderLayout.NORTH);

        panLComun = new JPanel();
        panLComun.setLayout(new BoxLayout(panLComun, BoxLayout.Y_AXIS));
        panLComun.setBackground(new Color(84, 148, 218)); // PANEL DE MUESTRA DE RESULTADOS DE AMIGOS EN COMUN
        JScrollPane scrollComun = new JScrollPane(panLComun);
        scrollComun.getViewport().setBackground(new Color(255, 223, 0)); // Asegurar fondo uniforme
        panComun.add(scrollComun, BorderLayout.CENTER);

        // Configurar panel izquierdo y derecho
        panIzq.add(panTAmigos, BorderLayout.NORTH);
        panIzq.add(panAmigos, BorderLayout.CENTER);
        panDer.add(panTComun, BorderLayout.NORTH);
        panDer.add(panComun, BorderLayout.CENTER);

        // Agregar paneles al principal
        panPrincipal.add(panIzq);
        panPrincipal.add(panDer);
        

        // Eventos de los botones
        btnAmigos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panLAmigos.removeAll();
                if (txtAmigos.getText().equals("")) {
                    sacarAmigos(panLAmigos, obtenerPerfilID(usuario));
                } else {
                    buscarAmigos(txtAmigos.getText(), panLAmigos, obtenerPerfilID(usuario));
                }
                panLAmigos.revalidate();
                panLAmigos.repaint();
            }
        });

        btnComun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<PerfilUsuario> listA = listarAmigos(obtenerPerfilID(usuario));
                ArrayList<PerfilUsuario> listB = listarAmigos(obtenerPerfilIDN(txtComun.getText()));
                panLComun.removeAll();
                for (PerfilUsuario a : listA) {
                    for (PerfilUsuario b : listB) {
                        if (a.getUsuario().equals(b.getUsuario())) {
                            ImageIcon fotoPerfil = createScaledImageIcon(a.getFoto(), 150, 150);
                            JLabel fotoP = new JLabel();
                            fotoP.setIcon(fotoPerfil);
                            JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
                            panelUsuario.setBackground(new Color(84, 148, 218)); // fondo 3
                            JLabel lblNombre = new JLabel(a.getNombre() + " " + a.getApellido());
        	                lblNombre.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
                            panelUsuario.add(fotoP);
                            panelUsuario.add(lblNombre);
                            panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            panLComun.add(panelUsuario);
                        }
                    }
                }
                panLComun.revalidate();
                panLComun.repaint();
            }
        });

        setVisible(true);

	}
	
	private static ArrayList<PerfilUsuario> listarAmigos( int idu) {
		ArrayList<PerfilUsuario>lista=new ArrayList<>();
		String query = "SELECT pu.id_usuario, pu.pu_usuario,pu.pu_contraseña,pu.pu_nombre ,pu.pu_apellido , pu.pu_foto,pu.pu_estado FROM perfilusuario pu,amigos "
				+ "a WHERE pu.id_usuario = a.idusuario and a.idamigo like ? ";
        StringBuilder idU=new  StringBuilder();
        idU.append(idu);
        idU.append("A");
		try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(query)){
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + idU.toString() + "%");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	PerfilUsuario recuperado=new PerfilUsuario(rs.getString("pu_usuario"),rs.getString("pu_contraseña"),rs.getString("pu_nombre"),
                		rs.getString("pu_apellido"),rs.getString("pu_foto"),rs.getString("pu_estado"));
            	lista.add(recuperado);
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
		return lista;
    }
	
	
	
	private static void sacarAmigos( JPanel panelResultados,int idu) {
		String query = "SELECT pu.id_usuario, pu.pu_nombre ,pu.pu_apellido , pu.pu_foto FROM perfilusuario pu,amigos "
				+ "a WHERE pu.id_usuario = a.idusuario and a.idamigo like ? ";
        StringBuilder idU=new  StringBuilder();
        idU.append(idu);
        idU.append("A");
		try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(query)){
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + idU.toString() + "%");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("pu_nombre")+" "+rs.getString("pu_apellido");
                // Crear un panel para cada resultado
	        		ImageIcon fotoPerfil=createScaledImageIcon(rs.getString("pu_foto"),150,150);
	        		JLabel fotoP=new JLabel();
	        		fotoP.setIcon(fotoPerfil);
	                JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
	                panelUsuario.setBackground(new Color(115, 185, 238)); // FONDO AZUL DE AMIGOS QUE SE TIENE (LISTA COMPLETA)
	                JLabel lblNombre = new JLabel(nombre);
	                lblNombre.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));

	               
	
	                // Acción del botón de enviar solicitud
	               
	                panelUsuario.add(fotoP);
	                panelUsuario.add(lblNombre);
	                panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

	
	                // Agregar el panel del usuario al panel de resultados
	                panelResultados.add(panelUsuario);
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
    }
	
	
	
	private static void buscarAmigos(String busqueda, JPanel panelResultados,int idu) {
		String query = "SELECT pu.id_usuario, pu.pu_nombre ,pu.pu_apellido , pu.pu_foto FROM perfilusuario pu,amigos "
				+ "a WHERE pu.id_usuario = a.idusuario and a.idamigo like ? and pu.pu_nombre like ?";
        StringBuilder idU=new  StringBuilder();
        idU.append(idu);
        idU.append("A");
		try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(query)){
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + idU.toString() + "%");
            stmt.setString(2, "%" + busqueda + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("pu_nombre")+" "+rs.getString("pu_apellido");
                // Crear un panel para cada resultado
	        		ImageIcon fotoPerfil=createScaledImageIcon(rs.getString("pu_foto"),150,150);
	        		JLabel fotoP=new JLabel();
	        		fotoP.setIcon(fotoPerfil);
	                JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
	                panelUsuario.setBackground(new Color(115, 185, 238)); // FONDO AZUL (BUSCAR AMIGOS EN ESPECIFICO)

	                JLabel lblNombre = new JLabel(nombre);
	                lblNombre.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));

	               
	
	                // Acción del botón de enviar solicitud
	               
	                panelUsuario.add(fotoP);
	                panelUsuario.add(lblNombre);
	                panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

	
	                // Agregar el panel del usuario al panel de resultados
	                panelResultados.add(panelUsuario);
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
    }
	
	public static int obtenerPerfilIDN(String nombre) {
		try (Connection conn = Conexion.conectar()) {
	        if (conn != null) {
	        	String query = "SELECT id_usuario FROM perfilusuario WHERE pu_nombre = ?"; // Ajusta la consulta
	            try (PreparedStatement stmt = conn.prepareStatement(query)) {
	                stmt.setString(1, nombre); // Ajusta según tu necesidad
	
	                ResultSet rs = stmt.executeQuery();
	                if (rs.next()) {
	                	
	                    int obtenido =rs.getInt("id_usuario"); 
	                    
	                    return obtenido;
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            return 0; 
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.err.println("Error al obtener perfil: " + e.getMessage());
	    }
		return 0;
	}
	
	
	
	public static int obtenerPerfilID(String usuario) {
		try (Connection conn = Conexion.conectar()) {
	        if (conn != null) {
	        	String query = "SELECT id_usuario FROM perfilusuario WHERE pu_usuario = ?"; // Ajusta la consulta
	            try (PreparedStatement stmt = conn.prepareStatement(query)) {
	                stmt.setString(1, usuario); // Ajusta según tu necesidad
	
	                ResultSet rs = stmt.executeQuery();
	                if (rs.next()) {
	                	
	                    int obtenido =rs.getInt("id_usuario"); 
	                    
	                    return obtenido;
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            return 0; 
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.err.println("Error al obtener perfil: " + e.getMessage());
	    }
		return 0;
	}
	
	private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        
        // Escalar la imagen a la nueva dimensión
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        // Crear un ImageIcon a partir de la imagen escalada
        return new ImageIcon(imagenEscalada);
    }
	

}
