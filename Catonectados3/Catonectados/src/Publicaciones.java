import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Publicaciones extends JFrame {
    JPanel panPrincipal;
    JPanel panScrollP;
    JPanel panAux;
    JPanel panTitulo;
    JPanel panLogo;
    JPanel panSuperior;
    JLabel lblTitulo;

    JScrollPane scrollGeneral;

    public Publicaciones(String usuario) {
        // Constructor del frame de ver publicaciones de amigos
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa

        panPrincipal = new JPanel(new BorderLayout());
        this.getContentPane().add(panPrincipal);

        // Panel superior que contiene el banner y el título
        panSuperior = new JPanel(new BorderLayout());
        panPrincipal.add(panSuperior, BorderLayout.NORTH);

        // Agregar banner
        panLogo = new JPanel();
        File logoFile = new File("src/imagenes/baner_catonectados.png");
        ImageIcon logoIcon = createScaledImageIcon(logoFile.getAbsolutePath(), 1400, 200);
        JLabel lblLogo = new JLabel(logoIcon);
        panLogo.add(lblLogo);
        panSuperior.add(panLogo, BorderLayout.NORTH);

        // Agregar título
        panTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panTitulo.setPreferredSize(new Dimension(0, 50)); // Limitar la altura del título
        lblTitulo = new JLabel("Publicaciones de amigos");
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
        lblTitulo.setForeground(Color.WHITE); // TEXTO EN BLANCO

        panTitulo.add(lblTitulo);
        panTitulo.setBackground(new Color(51, 115, 196)); //FONDO 2
        panSuperior.add(panTitulo, BorderLayout.CENTER);

        // Panel principal para publicaciones
        panAux = new JPanel();
        panAux.setLayout(new BoxLayout(panAux, BoxLayout.Y_AXIS));
        panAux.setBackground(new Color(115, 185, 238)); 

        scrollGeneral = new JScrollPane(panAux);
        scrollGeneral.setBackground(new Color(115, 185, 238)); //FONDO 4


        // Obtener y mostrar publicaciones de amigos
        sacarPublicacionesA(panAux, obtenerPerfilID(usuario));

        panPrincipal.add(scrollGeneral, BorderLayout.CENTER);
        setVisible(true);
    }

    // Función para obtener la lista de amigos del usuario en sesión
    private static ArrayList<Integer> listaIDAmigos(int idu) {
        ArrayList<Integer> lista = new ArrayList<>();
        String query = "SELECT pu.id_usuario FROM perfilusuario pu, amigos "
                + "a WHERE pu.id_usuario = a.idusuario and a.idamigo like ? ";
        StringBuilder idU = new StringBuilder();
        idU.append(idu);
        idU.append("A");
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + idU.toString() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getInt("id_usuario"));
            }

            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // Función para obtener las publicaciones de los amigos
    private static void sacarPublicacionesA(JPanel panResultados, int idP) {
        ArrayList<Integer> amigos = listaIDAmigos(idP);
        String query = "SELECT pub.idpublicacion, pub.pub_foto, pub.pub_fecha, pu.pu_usuario "
                + "FROM publicaciones pub, perfilusuario pu WHERE pub.idpublicacion like ? "
                + "and pu.id_usuario = ? order by pub.pub_fecha asc";
        for (Integer y : amigos) {
            try (Connection conn = Conexion.conectar();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, "%" + y + "P%" );
                stmt.setInt(2, y);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    // Crear un panel contenedor para cada publicación
                    JPanel panContenedor = new JPanel(new BorderLayout());
                    panContenedor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    panContenedor.setBackground(new Color(115, 185, 238)); //FONDO 4


                    // Panel para el usuario, imagen y fecha
                    JPanel panelUsuario = new JPanel();
                    panelUsuario.setLayout(new BoxLayout(panelUsuario, BoxLayout.Y_AXIS));
                    panelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panelUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panelUsuario.setBackground(new Color(115, 185, 238)); //FONDO 4


                    // Usuario
                    JLabel lblUsuario = new JLabel(rs.getString("pu_usuario"));
                    lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 30));

                    // Imagen de la publicación
                    ImageIcon fotoPub = createScaledImageIcon(rs.getString("pub_foto"), 250, 250);
                    JLabel fotoP = new JLabel(fotoPub);
                    fotoP.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // Fecha de publicación
                    JLabel lblFecha = new JLabel(rs.getString("pub_fecha"));
                    lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 20));

                    // Añadir componentes al panelUsuario
                    panelUsuario.add(lblUsuario);
                    panelUsuario.add(Box.createVerticalStrut(10)); // Espaciado
                    panelUsuario.add(fotoP);
                    panelUsuario.add(Box.createVerticalStrut(10)); // Espaciado
                    panelUsuario.add(lblFecha);

                    // Panel para botones
                    JPanel panBotones = new JPanel(new GridLayout(1, 2, 10, 0));
                    panBotones.setBackground(new Color(115, 185, 238)); //FONDO 4
                    JButton btnComentarios = new JButton("Ver comentarios");
                    btnComentarios.setBackground(new Color(23, 80, 172)); // AMARILLO
                    btnComentarios.setForeground(Color.BLUE); // TEXTO AZUL
                    btnComentarios.setForeground(Color.WHITE); // TEXTO BLANCO
                    btnComentarios.setFont(new Font("Segoe UI", Font.BOLD, 25));
                    btnComentarios.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(1, 73, 128), 2), // COLOR Y TAMAÑO DEL BORDE
                            BorderFactory.createEmptyBorder(20, 1, 20, 1) // PADIGN DEL BOTON
                        ));
                    
                    JButton btnComentar = new JButton("Comentar");
                    btnComentar.setBackground(new Color(23, 80, 172)); // AMARILLO
                    btnComentar.setForeground(Color.BLUE); // TEXTO AZUL
                    btnComentar.setForeground(Color.WHITE); // TEXTO BLANCO
                    btnComentar.setFont(new Font("Segoe UI", Font.BOLD, 25));


                    // Acciones de los botones
                    String idPub = rs.getString("idpublicacion");
                    btnComentarios.addActionListener(e -> new VerComentarios(idPub));
                    btnComentar.addActionListener(e -> new AñadirComentario(idP, idPub));

                    panBotones.add(btnComentarios);
                    panBotones.add(btnComentar);

                    // Añadir panelUsuario y panBotones al contenedor
                    panContenedor.add(panelUsuario, BorderLayout.CENTER);
                    panContenedor.add(panBotones, BorderLayout.SOUTH);

                    panResultados.add(panContenedor);
                }

                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al buscar publicaciones: " + e.getMessage());
            }
        }
    }

    // Función para obtener el id del perfil en sesión
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
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener perfil: " + e.getMessage());
        }
        return 0;
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
