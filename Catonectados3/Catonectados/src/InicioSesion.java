import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;



public class InicioSesion extends JFrame {

    JPanel panPrincipal;
    JPanel panLogo;
    JPanel panFormulario;
    JLabel lblUsuario;
    JLabel lblIUsuario;
    JLabel lblLogo;
    JLabel lblContra;
    JLabel lblIContra;
    JPasswordField pasContra;
    JTextField txtUsuario;
    JButton btnIngresar;
    JButton btnRegistro;
    

    public InicioSesion() {
        // Configuración principal del frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setTitle("Inicio de Sesión");

        panPrincipal = new JPanel(new BorderLayout());
        panPrincipal.setBackground(new Color(247, 219, 69)); // Fondo amarillo 

        // BANNER PRINCIPAL
        panLogo = new JPanel();
        File logoFile = new File("C:\\Users\\diego\\eclipse-workspace\\Catonectados3\\Catonectados\\src\\Imagenes\\baner_catonectados.png");
        ImageIcon logoIcon = createScaledImageIcon(logoFile.getAbsolutePath(), 1400, 200);
        lblLogo = new JLabel();
        lblLogo.setIcon(logoIcon);
        panLogo.add(lblLogo);
        panLogo.setBackground(new Color(115, 185, 238)); 
        panPrincipal.add(panLogo, BorderLayout.NORTH);

        // PANEL AZUL
        panFormulario = new JPanel();
        panFormulario.setLayout(new BoxLayout(panFormulario, BoxLayout.Y_AXIS));
        panFormulario.setBackground(new Color(84, 148, 218)); // FONDO AZUL
        panFormulario.setBorder(BorderFactory.createEmptyBorder(20, 55, 40, 55)); // TAMAÑO DEL PANEL
        

        // CAMPO USUARIO
        JPanel panUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panUsuario.setOpaque(false);

        File userIconFile = new File("C:\\Users\\diego\\eclipse-workspace\\Catonectados3\\Catonectados\\src\\imagenes\\user-3297.png");
        ImageIcon userIcon = createScaledImageIcon(userIconFile.getAbsolutePath(), 30, 30);
        lblIUsuario = new JLabel(userIcon);
        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblUsuario.setForeground(Color.WHITE); // PONER TEXTO EN BLANCO
        panUsuario.add(lblIUsuario);
        panUsuario.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        txtUsuario.setPreferredSize(new Dimension(300, 30));
        panUsuario.add(txtUsuario);

        panFormulario.add(Box.createVerticalStrut(20)); // ESPACIO ENCIMA DEL CAMPO USUARIO
        panFormulario.add(panUsuario);

        // CAMPO CONTRASEÑA
        JPanel panContra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panContra.setOpaque(false);

        File lockIconFile = new File("C:\\Users\\diego\\eclipse-workspace\\Catonectados3\\Catonectados\\src\\imagenes\\lock.png");
        ImageIcon lockIcon = createScaledImageIcon(lockIconFile.getAbsolutePath(), 30, 30);
        lblIContra = new JLabel(lockIcon);
        lblContra = new JLabel("Contraseña:");
        lblContra.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblContra.setForeground(Color.WHITE); // TEXTO EN BLANCO
        panContra.add(lblIContra);
        panContra.add(lblContra);

        pasContra = new JPasswordField();
        pasContra.setPreferredSize(new Dimension(300, 30));
        panContra.add(pasContra);

        panFormulario.add(Box.createVerticalStrut(50)); // ESPACIO ENCIMA DEL CAMPO DE CONTRASEÑA
        panFormulario.add(panContra);

        // BOTON DE INICIO DE SESION
        btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setBackground(new Color(23, 80, 172)); // AMARILLO
        btnIngresar.setForeground(Color.BLUE); // TEXTO AZUL
        btnIngresar.setForeground(Color.WHITE); // TEXTO BLANCO
        btnIngresar.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        panFormulario.add(Box.createVerticalStrut(50)); // ESPACIO ENCIMA DEL BOTON DE INICIO DE SESION
        panFormulario.add(btnIngresar);

        // BOTON DE REGISTRO
        btnRegistro = new JButton("Crear Perfil");
        btnRegistro.setBackground(new Color(23, 80, 172)); // AZUL CLARO
        btnRegistro.setForeground(Color.WHITE); // TEXTO BLANCO
        btnRegistro.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        btnRegistro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 51, 102), 1), // COLOR Y TAMAÑO DEL BORDE
            BorderFactory.createEmptyBorder(5, 20, 5, 20) // PADIGN DEL BOTON
        ));

        panFormulario.add(Box.createVerticalStrut(50)); // ESPACIO ENCIMA DEL BOTON DE REGISTRO
        panFormulario.add(btnRegistro);



        // CENTRAR TODO EL FORMULARIO
        JPanel panCenter = new JPanel(new GridBagLayout());
        panCenter.setBackground(new Color(115, 185, 238)); // FONDO DEL FORMULARIO AMARILLO
        panCenter.add(panFormulario);
        panPrincipal.add(panCenter, BorderLayout.CENTER);

        this.getContentPane().add(panPrincipal);

      //accion del boton ingresar
      	btnIngresar.addActionListener(new ActionListener() {

      		@Override
      		public void actionPerformed(ActionEvent e) {
      			String contraseña= new String(pasContra.getPassword());
      			if(autenticarUsuario(txtUsuario.getText(),contraseña)){
      				PrincipalMenu menu=new PrincipalMenu(txtUsuario.getText());
      				txtUsuario.setText("");
      				pasContra.setText("");
      				
      			}else {
      				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
      				txtUsuario.setText("");
      				pasContra.setText("");
      			}
      		}
      			
      	});

      //boton para registrar nuevo usuario
      	btnRegistro.addActionListener(new ActionListener() {

      		@Override
      		public void actionPerformed(ActionEvent e) {
      			// TODO Auto-generated method stub
      			RegistrarUsuario nuevo=new RegistrarUsuario();
      		}
      			
      	});
      		
      		
      		
      	setVisible(true);
    }

  //funcion para autenticar el usuario y contraseña, retorna true si el query funciona correctamente
  	public static boolean autenticarUsuario(String nombreUsuario, String contraseña) {
          String sql = "SELECT * FROM perfilusuario WHERE pu_usuario = ? ";
          
          try (Connection conn = Conexion.conectar();
               PreparedStatement stmt = conn.prepareStatement(sql)) {
               
              stmt.setString(1, nombreUsuario);
              ResultSet rs = stmt.executeQuery();
              
              if (rs.next()) {
                  String contraseñaBD = rs.getString("pu_contraseña");
                  
                  // Comparar la contraseña ingresada con la de la base de datos
                  // Si usas hashing, compara el hash
                  if(contraseña.equals(contraseñaBD)) {
                  	System.out.println("Inicio completo");
                  	return true;
                  }
                  
              }
              
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return false;
      }

    // Escalar imágenes
    private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public static void main(String[] args) {
        new InicioSesion();
    }
}
