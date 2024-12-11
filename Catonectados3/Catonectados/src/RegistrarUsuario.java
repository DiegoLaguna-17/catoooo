import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RegistrarUsuario extends JFrame {
	JPanel panPrincipal;
	JPanel panLogo;
	JPanel panTitulo;
	JPanel panFormulario;
	JPanel panDatos1;
	JPanel panDatos2;
	JPanel panDatos3;
	JPanel panDatos4;
	JPanel panNombre;
	JPanel panApellido;
	JPanel panUsuario;
	JPanel panContra;
	JPanel panFoto;
	JPanel panDetalle;
	JPanel panInteres;
	JPanel panEstado;
	JPanel panRegistrar;
	
	JLabel lblTitulo;
	JLabel lblLogo;
	JLabel lblNombre;
	JLabel lblApellido;
	JLabel lblUsuario;
	JLabel lblContra;
	JLabel lblFoto;
	JLabel lblDetalle;
	JLabel lblInteres;
	JLabel lblLDetalles;
	JLabel lblLIntereses;
	JLabel lblEstado;
	
	JTextField txtNombre;
	JTextField txtApellido;
	JTextField txtUsuario;
	JTextField txtContra;
	JTextField txtFoto;
	JTextField txtDetalle;
	JTextField txtInteres;
	
	JButton btnDetalle;
	JButton btnInteres;
	JButton btnRegistrar;
	JButton btnFoto;
	
	JFileChooser rutaFoto;
	JComboBox <String>cbEstado;
	
	
public RegistrarUsuario() {
		
	// Construcción del frame de inicio de sesión
	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	setSize(800, 700);
	setExtendedState(JFrame.MAXIMIZED_BOTH); // Para pantalla completa

	panPrincipal = new JPanel(new BorderLayout());
	this.getContentPane().add(panPrincipal);

	// Panel de título
	panTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	panTitulo.setForeground(new Color(15, 20, 208));
	panTitulo.setPreferredSize(new Dimension(100, 100));
	panTitulo.setBackground(new Color(51, 115, 196)); // Fondo 2
	panTitulo.add(Box.createVerticalStrut(80)); // Espacio encima del campo usuario

	// Banner principal
	panLogo = new JPanel();
	File logoFile = new File("src/imagenes/baner_catonectados.png");
	ImageIcon logoIcon = createScaledImageIcon(logoFile.getAbsolutePath(), 1400, 150);
	lblLogo = new JLabel(logoIcon);
	panLogo.add(lblLogo);
	panLogo.setBackground(new Color(7, 22, 203)); 
	panPrincipal.add(panLogo, BorderLayout.NORTH);

	// Panel del formulario
	panFormulario = new JPanel(new GridLayout(5, 1));
	lblTitulo = new JLabel("Registrar nuevo usuario");
	lblTitulo.setVerticalAlignment(SwingConstants.BOTTOM);
	lblTitulo.setBackground(new Color(115, 185, 238)); //fondo 4
	lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
	lblTitulo.setForeground(Color.WHITE); // Texto en blanco
	panTitulo.add(lblTitulo);
	panFormulario.add(panTitulo);

	// Panel de nombre y apellido
	panDatos1 = new JPanel(new GridLayout(1, 2));

	// Panel de nombre
	panNombre = new JPanel(new FlowLayout());
	lblNombre = new JLabel("Nombre:");
	lblNombre.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblNombre.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO
	txtNombre = new JTextField(10);
	panNombre.add(lblNombre);
	panNombre.add(txtNombre);
	panNombre.setBackground(new Color(115, 185, 238)); // Fondo 4
	panNombre.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO USUARIO
	panDatos1.add(panNombre);

	// Panel de apellido
	panApellido = new JPanel(new FlowLayout());
	lblApellido = new JLabel("Apellido:");
	lblApellido.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblApellido.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO

	panApellido.add(lblApellido);
	txtApellido = new JTextField(10);
	panApellido.add(txtApellido);
	panApellido.setBackground(new Color(115, 185, 238)); // Fondo 4
	panApellido.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO USUARIO
	panDatos1.add(panApellido);

	panFormulario.add(panDatos1);

	// Panel de usuario y contraseña
	panDatos2 = new JPanel(new GridLayout(1, 2));

	// Panel de usuario
	panUsuario = new JPanel(new FlowLayout());
	lblUsuario = new JLabel("Usuario:");
	lblUsuario.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
    lblUsuario.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO
	txtUsuario = new JTextField(10);
	panUsuario.add(lblUsuario);
	panUsuario.add(txtUsuario);
	panUsuario.setBackground(new Color(84, 148, 218)); // Fondo 3
	panUsuario.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO USUARIO
	panDatos2.add(panUsuario);

	// Panel de contraseña
	panContra = new JPanel(new FlowLayout());
	lblContra = new JLabel("Contraseña:");
	lblContra.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblContra.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO
	txtContra = new JTextField(10);
	panContra.add(lblContra);
	panContra.add(txtContra);
	panContra.setBackground(new Color(84, 148, 218)); // Fondo 3
	panContra.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO CONTRA
	panDatos2.add(panContra);

	panFormulario.add(panDatos2);

	// Panel de detalles, intereses y estado
	panDatos3 = new JPanel(new GridLayout(1, 3));
	ArrayList<String> detalles=new ArrayList<>();
	Hashtable<String,String> intereses=new Hashtable<>();

	// Panel de detalle
	panDetalle = new JPanel(new FlowLayout(FlowLayout.CENTER));
	lblDetalle = new JLabel("Detalle:");
	lblDetalle.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblDetalle.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO

	txtDetalle = new JTextField(10);
	btnDetalle = new JButton("Agregar");
	btnDetalle.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
	btnDetalle.setBackground(new Color(23, 80, 172)); // fondo 1
	btnDetalle.setForeground(Color.WHITE); // TEXTO BLANCO

	lblLDetalles = new JLabel();
	panDetalle.add(lblDetalle);
	panDetalle.add(txtDetalle);
	panDetalle.add(btnDetalle);
	panDetalle.add(lblLDetalles);
	panDetalle.setBackground(new Color(115, 185, 238)); // Fondo 4
	panDetalle.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO DETALLE
	panDatos3.add(panDetalle);

	// Panel de interés
	panInteres = new JPanel(new FlowLayout(FlowLayout.CENTER));
	lblInteres = new JLabel("Interés:");
	lblInteres.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblInteres.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO

	txtInteres = new JTextField(10);
	btnInteres = new JButton("Agregar");
	btnInteres.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
	btnInteres.setBackground(new Color(23, 80, 172)); // fondo 1
	btnInteres.setForeground(Color.WHITE); // TEXTO BLANCO

	lblLIntereses = new JLabel();
	panInteres.add(lblInteres);
	panInteres.add(txtInteres);
	panInteres.add(btnInteres);
	panInteres.add(lblLIntereses);
	panInteres.setBackground(new Color(115, 185, 238)); // Fondo 4
	panInteres.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO INTERESES
	panDatos3.add(panInteres);

	// Panel de estado
	panEstado = new JPanel(new FlowLayout());
	lblEstado = new JLabel("Estado:");
	lblEstado.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	lblEstado.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO

	cbEstado = new JComboBox();
	cbEstado.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
	cbEstado.addItem("Soltero");
	cbEstado.addItem("En una Relación");
	panEstado.add(lblEstado);
	panEstado.add(cbEstado);
	panEstado.setBackground(new Color(115, 185, 238)); // Fondo 4
	panEstado.add(Box.createVerticalStrut(80)); // ESPACIO ENCIMA DEL CAMPO ESTADO
	panDatos3.add(panEstado);

	panFormulario.add(panDatos3);

	// Panel de la foto
	panDatos4 = new JPanel(new GridLayout(1, 1));
	panFoto = new JPanel(new FlowLayout());
	lblFoto = new JLabel();
	lblFoto.setBounds(150, 150, 150, 150);
	lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	txtFoto = new JTextField(10);
	// Si no suben imagen, se pone una por default
	ImageIcon imagenFoto = createScaledImageIcon("C:\\Users\\diego\\Downloads\\default.jpg", 150, 150);
	txtFoto.setText("C:\\Users\\diego\\Downloads\\default.jpg");
	lblFoto.setIcon(imagenFoto);
	btnFoto = new JButton("Seleccionar");
	btnFoto.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
	btnFoto.setBackground(new Color(23, 80, 172)); // fondo 1
	btnFoto.setForeground(Color.WHITE); // TEXTO BLANCO

	panFoto.add(lblFoto);
	panFoto.add(btnFoto);
	panFoto.setBackground(new Color(84, 148, 218)); // Fondo 3
	panDatos4.add(panFoto);
	panFormulario.add(panDatos4);


	// Panel para el botón de registrar
	btnRegistrar = new JButton("Crear cuenta");
	btnRegistrar.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
	btnRegistrar.setBackground(new Color(23, 80, 172)); // fondo 1
	btnRegistrar.setForeground(Color.WHITE); // TEXTO BLANCO

	panRegistrar = new JPanel(new FlowLayout());
	panRegistrar.add(btnRegistrar);
	panRegistrar.setBackground(new Color(115, 185, 238)); // Fondo 4
	panPrincipal.add(panRegistrar, BorderLayout.SOUTH);

	panPrincipal.add(panFormulario, BorderLayout.CENTER);
    int idNuevo=obtenerPerfilID()+1;
		
		
		btnDetalle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				detalles.add(txtDetalle.getText());
				
				txtDetalle.setText("");
				
				lblLDetalles.setText(detalles.toString());
			}
			
		});
		ArrayList<String>listaInteres=new ArrayList<>();
		btnInteres.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String interes=txtInteres.getText();
				StringBuilder interesID=new StringBuilder();
				interesID.append(idNuevo);
				interesID.append("I");
				interesID.append(intereses.size()+1);
				intereses.put(interesID.toString(), interes);
				listaInteres.add(txtInteres.getText());
				txtInteres.setText("");
				lblLIntereses.setText(listaInteres.toString());
				
			}
			
		});
		
		//accion del boton foto para elegir la imagen de perfil
		btnFoto.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rutaFoto = new JFileChooser();
				rutaFoto.setFileSelectionMode(JFileChooser.FILES_ONLY);
				rutaFoto.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

                int result = rutaFoto.showOpenDialog(panFormulario);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = rutaFoto.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    txtFoto.setText(imagePath); // Mostrar la ruta en el campo de texto

                    // Mostrar la imagen seleccionada
                    ImageIcon imagenFoto = createScaledImageIcon(imagePath,90,90);;
           
                    lblFoto.setIcon(imagenFoto);
                }
				
			}
			
		});
		//accion del boton registrar, crea un usuario nuevo
		btnRegistrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtUsuario.getText().equals("")&&!txtContra.getText().equals("")&&!txtNombre.getText().equals("")&&
						!txtApellido.getText().equals("")&&!txtFoto.getText().equals("")&& !detalles.isEmpty()&&!listaInteres.isEmpty()) {
					PerfilUsuario nuevo=new PerfilUsuario(txtUsuario.getText(),txtContra.getText(),txtNombre.getText(),txtApellido.getText(),
							txtFoto.getText(),cbEstado.getSelectedItem().toString());
					if(nuevo.insertarPerfilUsuario()) {
						Detalles(detalles);
						intereses(intereses);
						
					}
					JOptionPane.showMessageDialog(null, "Usuario ingresado");
					txtUsuario.setText("");
					txtContra.setText("");
					txtNombre.setText("");
					txtApellido.setText("");
					lblFoto.setIcon(null);
					txtFoto.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Campos requeridos no completados");
				}
				
			}
			
		});
		
		
		setVisible(true);
	}
	//funcion para almacenar los detalles del usuario en la tabla detalles
	//utiliza una clave primaria unica conformada por el id del usuario y la letra D seguido del conteo de detalles
	public static void Detalles(ArrayList<String>detalles) {
		int idper=obtenerPerfilID();
		int i=1;
		for(String s:detalles) {
			StringBuilder cod=new StringBuilder();
			cod.append(idper);
			cod.append("D");
			
			cod.append(i);
			ingresarDetalles(cod.toString(),s);
			i++;
		}
	}
	//funcinon que permite la insercion de los detalles a la base de datos
	public static void ingresarDetalles(String idd,String det) {
		String sql = "INSERT INTO detalles (iddetalle,d_detalle) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,idd);
            pstmt.setString(2,det);
            
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("detalle ingresado");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar el detalle: " + e.getMessage());
        }
	}
	
	//funcion que permite la insercion de los interese del usuario
	//recorre el hashtable para almacenarlos datos uno por uno, usa una clave primaria 
	//con el id del usuario y la letra I seguido del
	//conteo de intereses
	public static void intereses(Hashtable<String,String>intereses) {
		
		for(String in:intereses.keySet()) {
			String valor=intereses.get(in);
			ingresarIntereses(in,valor);
		}
	}
	
	//funcion que almacena los intereses en la base de datos
	public static void ingresarIntereses(String idi,String in) {
		String sql = "INSERT INTO intereses (idinteres,i_interes) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna valores a los parámetros de la consulta
            pstmt.setString(1,idi);
            pstmt.setString(2,in);
            
            // Ejecuta la consulta
            int filasInsertadas = pstmt.executeUpdate();
            System.out.println("Interes ingresado");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar el interes: " + e.getMessage());
        }
	}
	 
	//funcion para obtener elid del nuevo usuario 
	public static int obtenerPerfilID() {
		try (Connection conn = Conexion.conectar()) {
            if (conn != null) {
            	String query = "SELECT id_usuario FROM perfilusuario order by id_usuario desc limit 1"; // Ajusta la consulta
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    ; // Ajusta según tu necesidad

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("id_usuario"); // Cambia "tu_columna" por el nombre de tu columna
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return 0; 
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener datos: " + e.getMessage());
        }
		return 0;
	}
	
	//funcion para escalar imagenes a gusto
	private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        
        // Escalar la imagen a la nueva dimensión
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        // Crear un ImageIcon a partir de la imagen escalada
        return new ImageIcon(imagenEscalada);
    }

}
