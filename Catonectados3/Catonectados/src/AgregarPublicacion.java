import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class AgregarPublicacion extends JFrame {
    JPanel panPrincipal;
    JPanel panLogo;
    JPanel panCentro;
    JPanel panTitulo;
    JPanel panFoto;
    JPanel panBoton;

    JLabel lblTitulo;
    JLabel lblArchivo;
    JLabel lblLogo;

    JTextField txtRuta;
    JFileChooser rutaFoto;
    JButton btnBuscar;
    JButton btnSubir;

    public AgregarPublicacion(String usuario) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(600, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel principal con BorderLayout
        panPrincipal = new JPanel(new BorderLayout());
        this.getContentPane().add(panPrincipal);

        // BANER PRINCIPAL
        panLogo = new JPanel();
        File logoFile = new File("src/imagenes/baner_catonectados.png");
        ImageIcon logoIcon = createScaledImageIcon(logoFile.getAbsolutePath(), 1400, 200);
        lblLogo = new JLabel(logoIcon);
        panLogo.add(lblLogo);
        panPrincipal.add(panLogo, BorderLayout.NORTH);

        // Panel central con BorderLayout para ajustar proporciones
        panCentro = new JPanel(new BorderLayout());
        panPrincipal.add(panCentro, BorderLayout.CENTER);

        // Panel del título
        panTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTitulo = new JLabel("Agregar Publicación");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 40)); // Fuente más grande
        lblTitulo.setForeground(Color.WHITE); // TEXTO EN BLANCO
        panTitulo.add(lblTitulo);
        panTitulo.setPreferredSize(new Dimension(0, 80)); // Altura ajustada del título
        panTitulo.setBackground(new Color(84, 148, 218)); // fondo 3
        panCentro.add(panTitulo, BorderLayout.NORTH);

        // Panel para la foto y botón de seleccionar imagen
        panFoto = new JPanel();
        panFoto.setLayout(new BoxLayout(panFoto, BoxLayout.Y_AXIS)); // Disposición en columnas
        lblArchivo = new JLabel();
        lblArchivo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar la imagen
        txtRuta = new JTextField(20);
        txtRuta.setMaximumSize(new Dimension(400, 30)); // Ancho máximo para el campo de texto
        txtRuta.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el campo de texto
        btnBuscar = new JButton("Seleccionar imagen");
        btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el botón
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Fuente del botón
        btnBuscar.setBackground(new Color(23, 80, 172)); // AMARILLO
        btnBuscar.setForeground(Color.YELLOW); // TEXTO AZUL
        btnBuscar.setForeground(Color.WHITE); // TEXTO BLANCO
        btnBuscar.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));

        // Añadir componentes al panel
        panFoto.add(lblArchivo);
        panFoto.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado entre la imagen y el botón
        panFoto.add(btnBuscar);
        panFoto.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado entre el botón y el campo de texto
        
        panFoto.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaciado alrededor del panel
        panFoto.setBackground(new Color(115, 185, 238)); // Fondo 4
        panCentro.add(panFoto, BorderLayout.CENTER);

        // Botón de subir publicación en la parte inferior
        panBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSubir = new JButton("Subir Publicación");
        btnSubir.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Fuente del botón
        btnSubir.setBackground(new Color(51, 115, 196)); // AMARILLO
        btnSubir.setForeground(Color.WHITE); // TEXTO AZUL
        btnSubir.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        panBoton.add(btnSubir);

        // Espaciado superior e inferior entre el botón y el panel
        panBoton.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Margen superior e inferior
        panBoton.setBackground(new Color(84, 148, 218)); // fondo 3
        panPrincipal.add(panBoton, BorderLayout.SOUTH);

        // Acción del botón buscar para seleccionar la imagen
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rutaFoto = new JFileChooser();
                rutaFoto.setFileSelectionMode(JFileChooser.FILES_ONLY);
                rutaFoto.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

                int result = rutaFoto.showOpenDialog(panFoto);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = rutaFoto.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    txtRuta.setText(imagePath); // Mostrar la ruta en el campo de texto

                    // Mostrar la imagen seleccionada
                    ImageIcon imagenFoto = createScaledImageIcon(imagePath, 450, 450);
                    lblArchivo.setIcon(imagenFoto);
                }
            }
        });

        // Acción del botón subir para subir una publicación
        btnSubir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtRuta.getText().equals("")) {
                    StringBuilder aux = new StringBuilder();
                    int id = obtenerPerfilID(usuario);
                    aux.append(id);
                    aux.append("P");
                    int nPub = cantPublicacion(aux.toString()) + 1;
                    aux.append(nPub);
                    Publicacion nueva = new Publicacion(aux.toString(), txtRuta.getText());
                    nueva.subirPublicacion();
                } else {
                    JOptionPane.showMessageDialog(null, "Agregue una foto");
                }
            }
        });

        setVisible(true);
    }

    // Función para obtener el ID del usuario en sesión
    public static int obtenerPerfilID(String usuario) {
        try (Connection conn = Conexion.conectar()) {
            if (conn != null) {
                String query = "SELECT id_usuario FROM perfilusuario WHERE pu_usuario = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, usuario);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("id_usuario");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener perfil: " + e.getMessage());
        }
        return 0;
    }

    // Función para obtener la cantidad de publicaciones del usuario en sesión
    private static int cantPublicacion(String idPub) {
        String query = "SELECT COUNT(*) AS total FROM publicaciones WHERE idpublicacion LIKE ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + idPub + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al enviar la solicitud: " + e.getMessage());
        }
        return 0;
    }

    // Función para escalar una imagen
    private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();

        // Escalar la imagen a la nueva dimensión
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Crear un ImageIcon a partir de la imagen escalada
        return new ImageIcon(imagenEscalada);
    }
}
