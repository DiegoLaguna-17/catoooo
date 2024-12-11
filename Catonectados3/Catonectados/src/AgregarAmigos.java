import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import javax.swing.border.LineBorder;
public class AgregarAmigos extends JFrame {

    JPanel panPrincipal;
    JPanel panSolicitudes;
    JLabel lblTituloSol;

    JPanel panAgregar;
    JLabel lblTituloAgregar;
    JTextField txtBuscar;
    JButton btnBuscar;
    JPanel panResultados;

    public AgregarAmigos(String usuario) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(800, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH); //PARA PANTALLA COMPLETA

        // Panel principal con dos secciones
        panPrincipal = new JPanel(new GridLayout(2, 1));
        
        this.getContentPane().add(panPrincipal);

        // Configuración del panel de solicitudes
        panSolicitudes = new JPanel(new BorderLayout());
        panSolicitudes.setBackground(new Color(51, 115, 196)); // fondo 2
        panSolicitudes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblTituloSol = new JLabel("Solicitudes de amistad");
        lblTituloSol.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
        lblTituloSol.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloSol.setForeground(Color.WHITE); // TEXTO EN BLANCO


        JPanel panTSol = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panTSol.setBackground(new Color(44, 78, 205)); // Azul
        panTSol.add(lblTituloSol);
        panSolicitudes.add(panTSol, BorderLayout.NORTH);

        // Panel para mostrar la lista de solicitudes centradas
        JPanel panCola = new JPanel();
        panCola.setLayout(new BoxLayout(panCola, BoxLayout.Y_AXIS));
        panCola.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panCola.setBackground(new Color(84, 148, 218)); //FONDO 3

        JScrollPane scrollPaneSolicitudes = new JScrollPane(panCola);
        panSolicitudes.add(scrollPaneSolicitudes, BorderLayout.CENTER);

        // Configuración del panel para agregar amigos
        panAgregar = new JPanel(new BorderLayout());
        panAgregar.setBackground(new Color(51, 115, 196)); // FONDO 2
        panAgregar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        lblTituloAgregar = new JLabel("Agregar amigos");
        lblTituloAgregar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
        lblTituloAgregar.setForeground(Color.WHITE); // TEXTO EN BLANCO


        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        btnBuscar.setBackground(new Color(115, 185, 238)); // fondo 4


        JPanel panAgregarB = new JPanel(new FlowLayout());
        panAgregarB.setBackground(new Color(44, 78, 205)); // Azul
        panAgregarB.add(lblTituloAgregar);
        panAgregarB.add(txtBuscar);
        panAgregarB.add(btnBuscar);
        panAgregar.add(panAgregarB, BorderLayout.NORTH);

        // Panel para resultados
        panResultados = new JPanel();
        panResultados.setLayout(new BoxLayout(panResultados, BoxLayout.Y_AXIS));
        panResultados.setBackground(new Color(84, 148, 218)); // FONDO 3
        panResultados.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPaneResultados = new JScrollPane(panResultados);
        panAgregar.add(scrollPaneResultados, BorderLayout.CENTER);

        // Acción para el botón de búsqueda
        btnBuscar.addActionListener(e -> {
            panResultados.removeAll(); // Limpiar resultados anteriores
            buscarUsuarios(txtBuscar.getText(), panResultados, obtenerPerfilID(usuario));
            panResultados.revalidate();
            panResultados.repaint();
        });

        // Procesar y mostrar solicitudes
        Queue<PerfilUsuario> solicitudes = colaSolicitudes(obtenerPerfilID(usuario));
        procesarYMostrarSolicitudes(panCola, solicitudes, obtenerPerfilID(usuario));

        // Agregar paneles al principal
        panPrincipal.add(panSolicitudes);
        panPrincipal.add(panAgregar);

        setVisible(true);
    }
	
	//funcion para ingresar un nuevo amigo por parte de usuario
	public static void ingresarAmigo(String idA,int id) {
		String sql = "INSERT INTO amigos (idamigo,idusuario) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,idA);
            pstmt.setInt(2,id);
            
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("Amigo ingresado");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar el amigo: " + e.getMessage());
        }
	}
	
	//ingresar amigo por parte del solicitante
	public static void ingresarAmigo2(String idA,int id) {
		String sql = "INSERT INTO amigos (idamigo,idusuario) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,idA);
            pstmt.setInt(2,id);
            
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("Amigo ingresado");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar el amigo: " + e.getMessage());
        }
	}
	
	//funcion para quitar una una solicitud despues de haber sido aceptado o rechazado
	public static void quitarSolicitud(String idS) {
		String sql = "delete from solicitudes  where idsolicitud= ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,idS);
            
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("Solicitud quitada");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al quitar SOlicitud: " + e.getMessage());
        }
	}
	
	//fuincion para mostrar las solicitudes de amistad en el panel de solicitudes
	private static void procesarYMostrarSolicitudes(JPanel panSolicitudes,Queue<PerfilUsuario>cola,int id) {
        while (!cola.isEmpty()) {
            PerfilUsuario solicitud = cola.poll(); // Sacar de la cola

            // Crear un panel para mostrar la solicitud procesada
            JPanel panelSolicitud = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelSolicitud.setBackground(new Color(115, 185, 238)); // FONDO 4
            JLabel lblNombre = new JLabel(solicitud.getNombre()+" "+solicitud.getApellido());
            lblNombre.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
            JButton btnAgreg=new JButton("Agregar");
            JButton btnElim=new JButton("Eliminar");
            btnAgreg.setBackground(new Color(23, 80, 172)); // FONDO 1
            btnAgreg.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
            btnAgreg.setForeground(Color.WHITE); // TEXTO BLANCO
            btnElim.setBackground(new Color(23, 80, 172)); // FONDO 1
            btnElim.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
            btnElim.setForeground(Color.WHITE); // TEXTO BLANCO

            
    		ImageIcon fotoPerfil=createScaledImageIcon(solicitud.getFoto(),150,150);
    		JLabel lblFoto=new JLabel();
    		lblFoto.setIcon(fotoPerfil);
    		int idS=obtenerPerfilID(solicitud.getUsuario());
    		StringBuilder auxS=new StringBuilder();
    		auxS.append(id);
    		auxS.append("S");
    		auxS.append(idS);
    		
    		StringBuilder auxA=new StringBuilder();
    		auxA.append(id);
    		auxA.append("A");
    		auxA.append(idS);
    		
    		StringBuilder auxA2=new StringBuilder();
    		auxA2.append(idS);
    		auxA2.append("A");
    		auxA2.append(id);
    		
    		//accion de agregar un amigo de la lista de solicitudes
    		btnAgreg.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ingresarAmigo(auxA.toString(),idS);
					ingresarAmigo2(auxA2.toString(),id);
					quitarSolicitud(auxS.toString());
					panSolicitudes.remove(panelSolicitud);
					JOptionPane.showMessageDialog(null, "amigo agregado: "+solicitud.getNombre()+" "+solicitud.getApellido());
					panSolicitudes.revalidate();
					panSolicitudes.repaint();
				}
    			
    		});
    		
    		btnElim.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					quitarSolicitud(auxS.toString());
					panSolicitudes.remove(panelSolicitud);
					JOptionPane.showMessageDialog(null, "Solicitud eliminada");
					panSolicitudes.revalidate();
					panSolicitudes.repaint();
				}
    			
    		});
    		panelSolicitud.add(lblFoto);
            panelSolicitud.add(lblNombre);
            panelSolicitud.add(btnAgreg);
            panelSolicitud.add(btnElim);
            panelSolicitud.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

            // Agregar el panel de la solicitud al contenedor principal
            panSolicitudes.add(panelSolicitud);

            // Opcional: Simular acción (p. ej., guardar estado en base de datos)
        }

        // Actualizar el panel para reflejar los cambios
        panSolicitudes.revalidate();
        panSolicitudes.repaint();
    }
	
	
	
	//funcion para obtener la cola de solicitudes de amistad
	public Queue<PerfilUsuario>colaSolicitudes(int id){
		Queue<PerfilUsuario> cola=new LinkedList<PerfilUsuario>();
		String query = "SELECT pu.id_usuario,pu.pu_usuario,pu.pu_contraseña,pu.pu_nombre,pu.pu_apellido,pu.pu_foto,pu.pu_estado"
				+ "  FROM perfilusuario  pu , solicitudes s WHERE pu.id_usuario =s.idsolicitante and s.idsolicitud like ?";
		try (Connection conn = Conexion.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query)){
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + id+"S" + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                PerfilUsuario recuperado=new PerfilUsuario(rs.getString("pu_usuario"),rs.getString("pu_contraseña"),rs.getString("pu_nombre"),
                		rs.getString("pu_apellido"),rs.getString("pu_foto"),rs.getString("pu_estado"));
                cola.offer(recuperado);
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar solicitudes: " + e.getMessage());
        }
		System.out.println("tamaño"+cola.size());
		return  cola;
	}
	
	
	
	//funcion para buscar los usuarios para mandar solicitud de amistad
	private static void buscarUsuarios(String busqueda, JPanel panelResultados,int idSol) {
		String query = "SELECT id_usuario, pu_nombre ,pu_apellido , pu_foto FROM perfilusuario WHERE pu_nombre LIKE ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(query)){
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + busqueda + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("pu_nombre")+" "+rs.getString("pu_apellido");
                ArrayList<Integer>idsAmigos=listaIDAmigos(idSol);
	                if(id!=idSol && !idsAmigos.contains(id)) {
	                // Crear un panel para cada resultado
		        		ImageIcon fotoPerfil=createScaledImageIcon(rs.getString("pu_foto"),240,240);
		        		JLabel fotoP=new JLabel();
		        		fotoP.setIcon(fotoPerfil);
		                JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER));
		                panelUsuario.setBackground(new Color(115, 185, 238)); // FONDO 4
		                JLabel lblNombre = new JLabel(nombre);
		                lblNombre.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 25));
		                JButton btnEnviarSolicitud = new JButton("Enviar Solicitud");
		                btnEnviarSolicitud.setBackground(new Color(23, 80, 172)); //fondo 1
		                btnEnviarSolicitud.setForeground(Color.WHITE); // TEXTO BLANCO
		                btnEnviarSolicitud.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		
		                // Acción del botón de enviar solicitud
		                btnEnviarSolicitud.addActionListener(e -> enviarSolicitud(id, idSol));
		                panelUsuario.add(fotoP);
		                panelUsuario.add(lblNombre);
		                panelUsuario.add(btnEnviarSolicitud);
		                panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

		                // Agregar el panel del usuario al panel de resultados
		                panelResultados.add(panelUsuario);
	                }
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
    }
	
	//funcion para obtener la lista de amigos del usuario en sesion para no mandar de nuevo solicitud
	private static ArrayList<Integer> listaIDAmigos( int idu) {
		ArrayList<Integer>lista=new ArrayList<>();
		String query = "SELECT pu.id_usuario  FROM perfilusuario pu,amigos "
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
            	
            	lista.add(rs.getInt("id_usuario"));
                
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
		return lista;
    }
	
	//funcion para listar los amigos del usuario
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
	
	//funcion para insertar una solicitud de amistad 
	 private static void enviarSolicitud(int idUsuario, int idSol) {
		 String query = "INSERT INTO solicitudes (idsolicitud, idsolicitante) VALUES (?, ?)";
	        try (Connection conn = Conexion.conectar();
	                PreparedStatement pstmt = conn.prepareStatement(query)){
	        	
	        	StringBuilder idSoli=new StringBuilder();
	        	idSoli.append(idUsuario);
	        	idSoli.append("S");
	        	
	        	idSoli.append(idSol);
	        	System.out.println(idSoli);
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, idSoli.toString());
	            stmt.setInt(2, idSol);
	            stmt.executeUpdate();
	            stmt.close();

	            JOptionPane.showMessageDialog(null, "Solicitud enviada ");
	        } catch (SQLException e) {
	        }
	    }
	 //funcion para obtener la cantidad de solicitudes de un usuario para realizar la insercion
	 private static int cantSolicitud(String idSReceptor) {
		    String query = "SELECT COUNT(*) AS total FROM solicitudes WHERE idsolicitud LIKE ?";
		    try (Connection conn = Conexion.conectar();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        // Configura el parámetro en el PreparedStatement correcto
		        pstmt.setString(1, "%" + idSReceptor + "%");

		        // Ejecuta la consulta
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                int total = rs.getInt("total");
		                System.out.println("solicitudes r" + total);
		                return total;
		            }
		        }
		    } catch (SQLException e) {
		        JOptionPane.showMessageDialog(null, "Error al enviar la solicitud: " + e.getMessage());
		    }
		    return 0;
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

}
