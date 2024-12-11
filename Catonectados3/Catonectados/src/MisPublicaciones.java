import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class MisPublicaciones extends JFrame {

    JPanel panPrincipal;
    JPanel panEncabezado; // Nuevo panel contenedor para el banner y el título
    JPanel panLogo;
    JPanel panTitulo;
    JPanel panScroll;

    JScrollPane scrollPubs;
    JLabel lblTitulo;

    public MisPublicaciones(String usuario) {
        // Constructor del frame mis publicaciones
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 1400, Toolkit.getDefaultToolkit().getScreenSize().height); // Maximizar alto
        setResizable(true); // Activar opción de minimizar tamaño
    	setExtendedState(JFrame.MAXIMIZED_BOTH); // Para pantalla completa


        panPrincipal = new JPanel(new BorderLayout());
        this.getContentPane().add(panPrincipal);

        // Panel contenedor para el banner y el título
        panEncabezado = new JPanel();
        panEncabezado.setLayout(new BoxLayout(panEncabezado, BoxLayout.Y_AXIS));

        // Agregar el banner
        panLogo = new JPanel();
        File logoFile = new File("src/imagenes/baner_catonectados.png");
        ImageIcon logoIcon = createScaledImageIcon(logoFile.getAbsolutePath(), 1400, 200);
        JLabel lblLogo = new JLabel(logoIcon);
        panLogo.add(lblLogo);
        panEncabezado.add(panLogo);

        // Agregar el título "Mis Publicaciones"
        lblTitulo = new JLabel("Mis Publicaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE); // Letra blanca
        panTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panTitulo.setBackground(new Color(51, 115, 196)); // Fondo 2
        panTitulo.add(lblTitulo);
        panEncabezado.add(panTitulo);

        // Agregar el panel contenedor al norte del layout principal
        panPrincipal.add(panEncabezado, BorderLayout.NORTH);

        // Panel para las publicaciones
        panScroll = new JPanel();
        panScroll.setLayout(new BoxLayout(panScroll, BoxLayout.Y_AXIS));

        scrollPubs = new JScrollPane(panScroll);
        StringBuilder aux = new StringBuilder();
        aux.append(obtenerPerfilID(usuario));
        aux.append("P");

        sacarPublicaciones(panScroll, aux.toString()); // Obtener las publicaciones del usuario en sesión
        panPrincipal.add(scrollPubs, BorderLayout.CENTER);

        setVisible(true);
    }

    // Función para obtener el id del usuario en sesión
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

    // Función para obtener las publicaciones y cargarlas al panel de publicaciones del usuario en sesión
    private static void sacarPublicaciones(JPanel panelResultados, String idP) {
        String query = "SELECT idpublicacion, pub_foto, pub_fecha FROM publicaciones WHERE idpublicacion LIKE ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + idP + "%");

            ResultSet rs = pstmt.executeQuery();
            int i = 1;
            while (rs.next()) {
                // Crear un panel para cada publicación
                JPanel panelUsuario = new JPanel(new BorderLayout());
                panelUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                // Panel para la etiqueta de publicación
                JPanel panPublicacion = new JPanel();
                panPublicacion.setBackground(new Color(84, 148, 218)); // Fondo 3
                JLabel publicacion = new JLabel("Publicación: " + i);
                publicacion.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
                panPublicacion.add(publicacion);

                // Panel para la imagen de la publicación
                JPanel panImagen = new JPanel();
                panImagen.setBackground(new Color(115, 185, 238)); // Fondo 4
                ImageIcon fotoPub = createScaledImageIcon(rs.getString("pub_foto"), 150, 150);
                JLabel fotoP = new JLabel();
                fotoP.setIcon(fotoPub);
                panImagen.add(fotoP);

                // Panel para la fecha
                JPanel panFecha = new JPanel(new GridLayout(2, 1));
                panFecha.setBackground(new Color(115, 185, 238)); // Fondo 4
                JLabel lblFechaTitulo = new JLabel("Fecha de publicación:");
                lblFechaTitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 25));
                lblFechaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
                lblFechaTitulo.setForeground(Color.WHITE); // TEXTO EN BLANCO
                JLabel lblFecha = new JLabel(rs.getString("pub_fecha"));
                lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
                lblFecha.setForeground(Color.WHITE); // TEXTO EN BLANCO
                panFecha.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Separación de 10px a los lados
                panFecha.add(lblFechaTitulo);
                panFecha.add(lblFecha);

                // Panel para el botón
                JPanel panBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panBoton.setBackground(new Color(51, 115, 196)); // Fondo 2
                JButton btnComentarios = new JButton("Ver comentarios");
                panBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Separación de 10px arriba y abajo
                panBoton.add(btnComentarios);
                btnComentarios.setBackground(new Color(23, 80, 172)); // AMARILLO
                btnComentarios.setForeground(Color.WHITE); // TEXTO AZUL
                btnComentarios.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));

                // Agregar los paneles al panel de la publicación
                panelUsuario.add(panPublicacion, BorderLayout.NORTH);
                panelUsuario.add(panImagen, BorderLayout.CENTER);
                panelUsuario.add(panFecha, BorderLayout.WEST);
                panelUsuario.add(panBoton, BorderLayout.SOUTH);

                // Acción del botón para ver comentarios de cada publicación
                String publi = rs.getString("idpublicacion");
                btnComentarios.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        VerComentarios coments = new VerComentarios(publi);
                    }
                });

                panelResultados.add(panelUsuario);
                i++;
            }

            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar publicaciones: " + e.getMessage());
        }
    }

    // Función para escalar una imagen a gusto
    private static ImageIcon createScaledImageIcon(String rutaImagen, int width, int height) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();

        // Escalar la imagen a la nueva dimensión
        Image imagenEscalada = imagenOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Crear un ImageIcon a partir de la imagen escalada
        return new ImageIcon(imagenEscalada);
    }
}
