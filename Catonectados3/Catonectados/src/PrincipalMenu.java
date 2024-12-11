import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.Hashtable;

public class PrincipalMenu extends JFrame {
    JPanel panPrincipal, panPerfil, panDetallesIntereses, panFoto, panInfoBasica, panDetalles, panIntereses;
    JLabel lblFoto, lblUsuario, lblNombre, lblApellido, lblEstado, lblDetalles, lblMDetalles, lblIntereses, lblMIntereses;
    JMenuBar mbBarra;

    public PrincipalMenu(String usuario) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Inicio");

        // Panel principal
        panPrincipal = new JPanel(new BorderLayout());
        panPrincipal.setBackground(new Color(70, 130, 180)); // Fondo azul

     // MENU
        mbBarra = new JMenuBar();
        mbBarra.setLayout(new FlowLayout(FlowLayout.CENTER)); // Para centrar
        mbBarra.setBackground(new Color(51, 115, 196)); // Fondo del menú

        // Publicaciones
        JMenu menuPublicaciones = new JMenu("Publicaciones");
        menuPublicaciones.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        menuPublicaciones.setForeground(Color.WHITE); // Color de texto de los botones principales

        JMenuItem agregarF = new JMenuItem("Agregar");
        JMenuItem misP = new JMenuItem("Mis Publicaciones");
        JMenuItem masP = new JMenuItem("Ver Publicaciones");

        agregarF.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        misP.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        masP.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

        // Cambiar colores de los items de menú
        agregarF.setBackground(new Color(23, 80, 172)); // Fondo del botón
        agregarF.setForeground(Color.WHITE); // Texto blanco
        misP.setBackground(new Color(23, 80, 172));
        misP.setForeground(Color.WHITE);
        masP.setBackground(new Color(23, 80, 172));
        masP.setForeground(Color.WHITE);

        menuPublicaciones.add(agregarF);
        menuPublicaciones.add(misP);
        menuPublicaciones.add(masP);

        // Amigos
        JMenu menuAgregar = new JMenu("Amigos");
        menuAgregar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        menuAgregar.setForeground(Color.WHITE); // Color de texto de los botones principales

        JMenuItem agregarAmigo = new JMenuItem("Agregar");
        JMenuItem listaAmigos = new JMenuItem("Ver Amigos");

        agregarAmigo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        listaAmigos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

        // Cambiar colores de los items de menú
        agregarAmigo.setBackground(new Color(23, 80, 172)); // Fondo del botón
        agregarAmigo.setForeground(Color.WHITE); // Texto blanco
        listaAmigos.setBackground(new Color(23, 80, 172));
        listaAmigos.setForeground(Color.WHITE);

        menuAgregar.add(agregarAmigo);
        menuAgregar.add(listaAmigos);

        mbBarra.add(menuPublicaciones);
        mbBarra.add(menuAgregar);


        // Estilo del menú
        for (Component comp : mbBarra.getComponents()) {
            JMenu menu = (JMenu) comp;
            menu.setOpaque(true);
            menu.setBackground(new Color(23, 80, 172)); // Fondo amarillo
            menu.setForeground(Color.WHITE); // Letras negras
            menu.addChangeListener(e -> {
                if (menu.isSelected()) {
                    menu.setBackground(new Color(0, 0, 139)); // Azul oscuro
                    menu.setForeground(Color.WHITE); // Letras blancas
                } else {
                    menu.setBackground(new Color(23, 80, 172)); // Fondo 1
                    menu.setForeground(Color.WHITE); // Letras negras
                }
            });
        }
        panPrincipal.add(mbBarra, BorderLayout.NORTH);

        // Perfil del usuario
        PerfilUsuario perfilActual = Perfil(usuario);

        panPerfil = new JPanel(new BorderLayout());
        panPerfil.setBackground(new Color(115, 185, 238)); // Fondo azul
        panPerfil.setBorder(BorderFactory.createEmptyBorder(1, 70, 1, 50)); // Tamaño del panel

        // Panel foto de perfil
        lblFoto = new JLabel();
        ImageIcon fotoPerfil = createScaledImageIcon1(perfilActual.getFoto(), 300, 300); // Reducir altura
        lblFoto.setIcon(fotoPerfil);
        lblFoto.setHorizontalAlignment(JLabel.CENTER);
        panFoto = new JPanel(new BorderLayout());
        panFoto.setBackground(new Color(115, 185, 238)); // Fondo azul claro
        panFoto.add(lblFoto, BorderLayout.CENTER);
        panFoto.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 50)); // Reducir altura del borde

        // Panel de información básica
        panInfoBasica = new JPanel(new GridBagLayout());
        panInfoBasica.setBackground(new Color(115, 185, 238)); // Fondo azul claro
        panInfoBasica.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Más angosto

        lblUsuario = new JLabel("Usuario: " + perfilActual.getUsuario());
        lblNombre = new JLabel("Nombre: " + perfilActual.getNombre());
        lblApellido = new JLabel("Apellido: " + perfilActual.getApellido());
        lblEstado = new JLabel("Estado: " + perfilActual.getEstado());
        
        lblUsuario.setForeground(Color.WHITE); // TEXTO EN BLANCO
        lblNombre.setForeground(Color.WHITE); // TEXTO EN BLANCO
        lblApellido.setForeground(Color.WHITE); // TEXTO EN BLANCO
        lblEstado.setForeground(Color.WHITE); // TEXTO EN BLANCO

        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(20, 0, 20, 0); // Espaciado entre líneas

        panInfoBasica.add(lblUsuario, gbc);
        panInfoBasica.add(lblNombre, gbc);
        panInfoBasica.add(lblApellido, gbc);
        panInfoBasica.add(lblEstado, gbc);

        panPerfil.add(panFoto, BorderLayout.WEST);
        panPerfil.add(panInfoBasica, BorderLayout.CENTER);

     // Panel de detalles e intereses
        panDetallesIntereses = new JPanel(new GridLayout(1, 2, 10, 10));
        panDetallesIntereses.setBackground(new Color(51, 115, 196)); // Fondo 2
        panDetallesIntereses.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20)); // Aumentar altura

        // Panel Detalles
        panDetalles = new JPanel(new BorderLayout(10, 10)); // Espaciado vertical de 10 px
        panDetalles.setBackground(new Color(84, 148, 218)); // Fondo 3

        lblDetalles = new JLabel("Detalles:", SwingConstants.CENTER); // Centrar texto
        lblDetalles.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblDetalles.setForeground(Color.WHITE); // TEXTO EN BLANCO


        ArrayList<String> detalles = obtenerDetalles(obtenerPerfilID(perfilActual.getUsuario()));
        lblMDetalles = new JLabel(String.join(" | ", detalles), SwingConstants.CENTER); // Centrar texto
        lblMDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Aumentar tamaño de letra de ítems

        panDetalles.add(lblDetalles, BorderLayout.NORTH);
        panDetalles.add(lblMDetalles, BorderLayout.CENTER);

        // Espaciado superior e inferior
        panDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Intereses
        panIntereses = new JPanel(new BorderLayout(10, 10)); // Espaciado vertical de 10 px
        panIntereses.setBackground(new Color(84, 148, 218)); // Fondo 3

        lblIntereses = new JLabel("Intereses:", SwingConstants.CENTER); // Centrar texto
        lblIntereses.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblIntereses.setForeground(Color.WHITE); // TEXTO EN BLANCO


        Hashtable<String, String> intereses = obtenerIntereses(obtenerPerfilID(perfilActual.getUsuario()));
        lblMIntereses = new JLabel(String.join(" | ", intereses.values()), SwingConstants.CENTER); // Centrar texto
        lblMIntereses.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Aumentar tamaño de letra de ítems

        panIntereses.add(lblIntereses, BorderLayout.NORTH);
        panIntereses.add(lblMIntereses, BorderLayout.CENTER);

        // Espaciado superior e inferior
        panIntereses.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panDetallesIntereses.add(panDetalles); // Detalles a la izquierda
        panDetallesIntereses.add(panIntereses); // Intereses a la derecha

        // Agregar paneles al principal
        panPrincipal.add(panPerfil, BorderLayout.CENTER);
        panPrincipal.add(panDetallesIntereses, BorderLayout.SOUTH);

        this.getContentPane().add(panPrincipal);
        setVisible(true);
        
		//accion del item mis publicaciones
		misP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MisPublicaciones mp=new MisPublicaciones(usuario);
			}
			
		});
		//accion del item ver publicaciones de amigos
		masP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Publicaciones pubs=new Publicaciones(usuario);
			}
			
		});
		//accion del item agregar publicacion
		agregarF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AgregarPublicacion ap=new AgregarPublicacion(usuario);
			}
			
		});
		//accion del item agregar amigos
		agregarAmigo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AgregarAmigos agregarA=new AgregarAmigos(usuario);
			}
			
		});
		//accion del item ver amigos
		listaAmigos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AmigosInterfaz panAmigos=new AmigosInterfaz(usuario);
			}
			
		});
		
		
		
		
		setVisible(true);

    }

    private ImageIcon createScaledImageIcon1(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }




    // Métodos de obtención de datos (Perfil, obtenerDetalles, obtenerIntereses) se mantienen igual.
    
  //funcion que devuelve un hashtable con los intereses del usuario
  	public static Hashtable<String,String> obtenerIntereses(int id) {
  		Hashtable<String,String> ints=new Hashtable<>();
  		try (Connection conn = Conexion.conectar()) {
  			String sql = "SELECT * FROM intereses WHERE idinteres LIKE ?";
  		    
  		    try (PreparedStatement statement = conn.prepareStatement(sql)) {
  		    	StringBuilder idi =new StringBuilder();
  		    	idi.append(id);
  		    	idi.append("I");
  		        // Configurar el parámetro de búsqueda con la palabra clave
  		        statement.setString(1, "%" + idi.toString() + "%");
  		        
  		        // Ejecutar la consulta
  		        ResultSet resultSet = statement.executeQuery();
  		        
  		        // Procesar los resultados
  		        while (resultSet.next()) {
  		            // Asumiendo que tienes columnas 'id' y 'nombre' en tu tabla
  		            String id1 = resultSet.getString("idinteres");
  		            String valor = resultSet.getString("i_interes");
  		            ints.put(id1, valor);
  		            
  		        }
  		        return ints;
  		    } catch (SQLException e) {
  		        e.printStackTrace();
  		        return null;
  		    }
          } catch (SQLException e) {
              System.err.println("Error al obtener perfil: " + e.getMessage());
          }
  		return null;
  		
  	}
  	//funcion que obtiene los datos del usuario en sesion
  public static PerfilUsuario Perfil(String usuario) {
  		
  		try (Connection conn = Conexion.conectar()) {
              if (conn != null) {
              	String query = "SELECT * FROM perfilusuario WHERE pu_usuario = ?"; // Ajusta la consulta
                  try (PreparedStatement stmt = conn.prepareStatement(query)) {
                      stmt.setString(1, usuario); // Ajusta según tu necesidad

                      ResultSet rs = stmt.executeQuery();
                      if (rs.next()) {
                      	
                          PerfilUsuario obtenido =new PerfilUsuario(rs.getString("pu_usuario"),rs.getString("pu_contraseña"),rs.getString("pu_nombre"),
                          		rs.getString("pu_apellido"),rs.getString("pu_foto"),rs.getString("pu_estado")); 
                          
                          return obtenido;
                      }
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  return null; 
              }
              conn.close();
          } catch (SQLException e) {
              System.err.println("Error al obtener perfil: " + e.getMessage());
          }
  		return null;
  	}


  	//funcion que obtiene el ID del usuario en sesion
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
  	//funcion que devuelve un arraylist con los detalles del usuario
  	public static ArrayList<String> obtenerDetalles(int id) {
  		ArrayList<String> dets=new ArrayList<>();
  		try (Connection conn = Conexion.conectar()) {
  			String sql = "SELECT d_detalle FROM detalles WHERE iddetalle LIKE ?";
  		    
  		    try (PreparedStatement statement = conn.prepareStatement(sql)) {
  		    	StringBuilder idi =new StringBuilder();
  		    	idi.append(id);
  		    	idi.append("D");
  		        // Configurar el parámetro de búsqueda con la palabra clave
  		        statement.setString(1, "%" + idi.toString() + "%");
  		        
  		        // Ejecutar la consulta
  		        ResultSet resultSet = statement.executeQuery();
  		        
  		        // Procesar los resultados
  		        while (resultSet.next()) {
  		            // Asumiendo que tienes columnas 'id' y 'nombre' en tu tabla
  		            String detalle = resultSet.getString("d_detalle");
  		            dets.add(detalle);
  		            
  		        }
  		        return dets;
  		    } catch (SQLException e) {
  		        e.printStackTrace();
  		        return null;
  		    }
          } catch (SQLException e) {
              System.err.println("Error al obtener perfil: " + e.getMessage());
          }
  		return null;
  		
  	}
  	//funcion que permite el escalado de una imagen a gusto
  	private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
          ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
          Image imagenOriginal = iconoOriginal.getImage();
          
          // Escalar la imagen a la nueva dimensión
          Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
          
          // Crear un ImageIcon a partir de la imagen escalada
          return new ImageIcon(imagenEscalada);
      }
}
