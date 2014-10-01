package Default;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Start {

    /**
     * Parametros de conexion
     */
    static String bd = "biblioteca2";
    static String usuario;
    static String contraseña;
    static String url = "jdbc:mysql://localhost/" + bd;
    Connection connection = null;
    ImageIcon ico = new ImageIcon("symbol_check.png");
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();

    /**
     * Constructor de DbConnection
     *
     * @param user
     * @param pass
     */
    public Start(String user, String pass) {
        usuario = user;
        contraseña = pass;
    }

    /**
     * Permite retornar la conexión
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
    /*
     Crea
     */

    public void insertarLibro(String a, String b, String b1, String c, String d, String e, String f, String h) {
        Statement stmt;
        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
            stmt = connection.createStatement();
            String sql = String.format("INSERT INTO libros value(\'%s\', \'%2s\', \'%3s\', \'%4s\', %5s, %6s, \'%7s\', %8s)", a, b, b1, c, d, e, f, h);
            System.out.println(sql);
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,
                    "",
                    "Operacion Exitosa",
                    JOptionPane.INFORMATION_MESSAGE,
                    ico);
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error en instrucción SQL: " + ex.getMessage(),
                    "Operacion Fallida",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void insertarUsuario(String a, String b, String c, String d, String e) {
        Statement stmt;
        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
            stmt = connection.createStatement();
            String sql = String.format("INSERT INTO usuarios value(%s, \'%2s\', \'%3s\', \'%4s\', %5s)", a, b, c, d, e);
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,
                    "",
                    "Operacion Exitosa",
                    JOptionPane.INFORMATION_MESSAGE,
                    ico);
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en instrucción SQL: " + ex.getMessage(),
                    "Operacion Fallida",
                    JOptionPane.ERROR_MESSAGE
            );
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    public void crearPrestamo(String a, String b) {
        Statement stmt;
        try {
            boolean libro = false, user = false;
            connection = DriverManager.getConnection(url, usuario, contraseña);
            stmt = connection.createStatement();
            String sql = String.format("SELECT cod_usuario FROM usuarios WHERE cod_usuario = %s", a);
            stmt.executeQuery(sql);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                user = true;
                System.out.println(rs.getString(1));
            };
            stmt.close();
            connection.close();
            connection = DriverManager.getConnection(url, usuario, contraseña);
            stmt = connection.createStatement();
            String sql2 = String.format("SELECT codigo FROM libros WHERE codigo = %s", b);
            stmt.executeQuery(sql2);
            ResultSet rs2 = stmt.getResultSet();
            while (rs2.next()) {
                libro = true;
                System.out.println(rs2.getString(1));
            };
            if (libro && user) {
                stmt.close();
                connection.close();
                connection = DriverManager.getConnection(url, usuario, contraseña);
                stmt = connection.createStatement();
                String sql3 = String.format("INSERT INTO prestamos (cod_usuario,codigo) values(%s , %2s);", a, b);
                stmt.executeUpdate(sql3);

                JOptionPane.showMessageDialog(null,
                        "",
                        "Operacion Exitosa",
                        JOptionPane.INFORMATION_MESSAGE,
                        ico);
                stmt.close();
                connection.close();
            } else {
                JOptionPane.showMessageDialog(null,
                        "El usuario o libro no se encuentra",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error en instrucción SQL: " + ex.getMessage(),
                    "Operacion Fallida",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void recibirLibro(String a, String b) {
        Statement stmt;
        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
            stmt = connection.createStatement();
            String sql = String.format("DELETE FROM prestamos WHERE cod_usuario = %s AND codigo = %2s", a, b);
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,
                    "Borrado Exitoso\n" + "Hora actual: " + dateFormat.format(cal.getTime()),
                    "Listo!\n",
                    JOptionPane.INFORMATION_MESSAGE);
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en instrucción SQL: " + ex.getMessage(),
                    "Operacion Fallida",
                    JOptionPane.ERROR_MESSAGE
            );
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    public void buscar(String a, int i) {
        if (i == 0) {
            Statement stmt;
            try {
                connection = DriverManager.getConnection(url, usuario, contraseña);
                stmt = connection.createStatement();
                String sql = ("Select * from libros where titulo LIKE \'%" + a + "%\' ;");
                stmt.executeQuery(sql);
                ResultSet rs = stmt.getResultSet();
                String concat = "";
                while (rs.next()) {
                    System.out.println("-------------------------------");
                    System.out.println("Titulo: " + rs.getString(1));
                    System.out.println("Nombre Autor: " + rs.getString(2));
                    System.out.println("Apellido Autor: " + rs.getString(3));
                    System.out.println("Editorial: " + rs.getString(4));
                    System.out.println("Dewey: " + rs.getString(7));
                    concat += "-------------------------------\n";
                    concat += ("Titulo: " + rs.getString(1) + "\n");
                    concat += ("Nombre Autor: " + rs.getString(2) + "\n");
                    concat += ("Apellido Autor: " + rs.getString(3) + "\n");
                    concat += ("Editorial: " + rs.getString(4) + "\n");
                    concat += ("Dewey: " + rs.getString(7) + "\n");

                }
                JOptionPane.showMessageDialog(null,
                        concat,
                        "Operacion Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                stmt.close();
                connection.close();

            } catch (SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Error en instrucción SQL: " + ex.getMessage(),
                        "Operacion Fallida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        if (i == 1) {
            Statement stmt;
            try {
                connection = DriverManager.getConnection(url, usuario, contraseña);
                stmt = connection.createStatement();
                String sql = "Select * from libros where nom_autor LIKE \'%" + a + "%\' ;";
                stmt.executeQuery(sql);
                ResultSet rs = stmt.getResultSet();
                String concat2 = "";
                while (rs.next()) {
                    System.out.println("-------------------------------");
                    System.out.println("Titulo: " + rs.getString(1));
                    System.out.println("Nombre Autor: " + rs.getString(2));
                    System.out.println("Apellido Autor: " + rs.getString(3));
                    System.out.println("Editorial: " + rs.getString(4));
                    System.out.println("Dewey: " + rs.getString(7));
                    concat2 += "-------------------------------\n";
                    concat2 += ("Titulo: " + rs.getString(1) + "\n");
                    concat2 += ("Nombre Autor: " + rs.getString(2) + "\n");
                    concat2 += ("Apellido Autor: " + rs.getString(3) + "\n");
                    concat2 += ("Editorial: " + rs.getString(4) + "\n");
                    concat2 += ("Dewey: " + rs.getString(7) + "\n");
                }
                JOptionPane.showMessageDialog(null,
                        concat2,
                        "Operacion Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
                stmt.close();
                connection.close();

            } catch (SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Error en instrucción SQL: " + ex.getMessage(),
                        "Operacion Fallida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        if (i == 2) {
            Statement stmt;
            try {
                connection = DriverManager.getConnection(url, usuario, contraseña);
                stmt = connection.createStatement();
                String sql = "Select * from libros where ape_autor LIKE \'%" + a + "%\' ;";
                stmt.executeQuery(sql);
                ResultSet rs = stmt.getResultSet();
                String concat3 = "";
                while (rs.next()) {
                    System.out.println("-------------------------------");
                    System.out.println("Titulo: " + rs.getString(1));
                    System.out.println("Nombre Autor: " + rs.getString(2));
                    System.out.println("Apellido Autor: " + rs.getString(3));
                    System.out.println("Editorial: " + rs.getString(4));
                    System.out.println("Dewey: " + rs.getString(7));
                    concat3 += "-------------------------------\n";
                    concat3 += ("Titulo: " + rs.getString(1) + "\n");
                    concat3 += ("Nombre Autor: " + rs.getString(2) + "\n");
                    concat3 += ("Apellido Autor: " + rs.getString(3) + "\n");
                    concat3 += ("Editorial: " + rs.getString(4) + "\n");
                    concat3 += ("Dewey: " + rs.getString(7) + "\n");
                }
                JOptionPane.showMessageDialog(null,
                        concat3,
                        "Operacion Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                stmt.close();
                connection.close();

            } catch (SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Error en instrucción SQL: " + ex.getMessage(),
                        "Operacion Fallida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        if (i == 3) {
            Statement stmt;
            try {
                connection = DriverManager.getConnection(url, usuario, contraseña);
                stmt = connection.createStatement();
                String sql = "Select * from libros where editorial LIKE \'%" + a + "%\' ;";
                stmt.executeQuery(sql);
                ResultSet rs = stmt.getResultSet();
                String concat4 = "";
                while (rs.next()) {
                    System.out.println("-------------------------------");
                    System.out.println("Titulo: " + rs.getString(1));
                    System.out.println("Nombre Autor: " + rs.getString(2));
                    System.out.println("Apellido Autor: " + rs.getString(3));
                    System.out.println("Editorial: " + rs.getString(4));
                    System.out.println("Dewey: " + rs.getString(7));
                    concat4 += "-------------------------------\n";
                    concat4 += ("Titulo: " + rs.getString(1) + "\n");
                    concat4 += ("Nombre Autor: " + rs.getString(2) + "\n");
                    concat4 += ("Apellido Autor: " + rs.getString(3) + "\n");
                    concat4 += ("Editorial: " + rs.getString(4) + "\n");
                    concat4 += ("Dewey: " + rs.getString(7) + "\n");
                }
                JOptionPane.showMessageDialog(null,
                        concat4,
                        "Operacion Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                stmt.close();
                connection.close();

            } catch (SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Error en instrucción SQL: " + ex.getMessage(),
                        "Operacion Fallida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.print("Exception: ");
            System.err.println(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

    }

    public void desconectar() {
        connection = null;
    }
}
