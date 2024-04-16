import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class OperacionesDB {
    static java.sql.Connection con = DatabaseConnection.getInstance().getConnection();

    public OperacionesDB() {

    }

    public static void consultarPaises() throws SQLException {
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT * FROM pais";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        System.out.println( Colores.purple+"--------------PAÍSES--------------"+Colores.reset);
        System.out.print(Colores.cyan+"Código \t");
        System.out.println("Nombre\t"+Colores.reset);
        while (rs.next()) {
            System.out.printf("%-6d\t%s\n", rs.getInt(1), rs.getString(2));
        }
        st.close();
        rs.close();
    }

    public static void consultarJugadoresPais(int codPais) throws SQLException {
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT * FROM jugador WHERE id_pais = " + codPais + ";";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        System.out.println(Colores.purple+"------------JUGADORES-------------"+Colores.reset);
        System.out.printf(Colores.blue+"%-12s\t%-25s\t%-17s\t%-13s\t%-12s\t%s\n", "Código", "Nombre","Año de nacimiento","Altura", "Pais","Club"+Colores.reset);

        while (rs.next()) {
            System.out.printf("%-12d\t%-25s\t%-17d\t%-13.2f\t%-12d\t%s\n", rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getInt(5), rs.getString(6));
        }
        st.close();
        rs.close();
    }

    public static void nuevoPais(Pais pais) throws SQLException {
        if (!paisExiste(pais.getId())) {
            Statement st = con.createStatement();
            String sqlPais = "INSERT INTO pais VALUES (" + pais.getId() + ",'" + pais.getNombrePais() + "');";
            st.executeUpdate(sqlPais);
            st.close();
            System.out.println(Colores.yellow+"El país ha sido añadido."+Colores.reset);
        } else {
            System.out.println(Colores.red+"El país que está intentando introducir ya existe en la base de datos."+Colores.reset);
        }
    }

    public static void nuevoJugador(Jugador jugador) throws SQLException {
        if (!jugadorExiste(jugador.getNombreJugador())) {
            Statement st = con.createStatement();
            String sqlJugador = "INSERT INTO jugador(nombre_jugador, año_nacimiento, altura, club, id_pais) VALUES ('" +
                    jugador.getNombreJugador() + "'," + jugador.getAñoNacimiento() + ","
                    + jugador.getAltura() + ",'" + jugador.getClub() + "',"
                    + jugador.getCodPais() + ");";
            st.executeUpdate(sqlJugador);
            st.close();
            System.out.println(Colores.yellow+"El jugador ha sido añadido."+Colores.reset);
        } else {
            System.out.println(Colores.red+"El jugador que está intentando introducir ya existe en la base de datos."+Colores.reset);
        }
    }

    public static void eliminarPais(int codigoPais) throws SQLException {
        if (paisExiste(codigoPais)) {
            Statement st = con.createStatement();
            String sqlPais = "DELETE FROM pais WHERE id_pais = " + codigoPais + ";";
            st.executeUpdate(sqlPais);
            st.close();
            System.out.println(Colores.yellow+"El país ha sido eliminado."+Colores.reset);
        } else {
            System.out.println(Colores.red+"El país que está intentando eliminar no existe."+Colores.reset);
        }
    }

    public static void eliminarJugador(String nombreJugador) throws SQLException {
        if (jugadorExiste(nombreJugador)) {
            Statement st = con.createStatement();
            String sqlJugador = "DELETE FROM jugador WHERE nombre_jugador = '" + nombreJugador + "';";
            st.executeUpdate(sqlJugador);
            st.close();
            System.out.println(Colores.yellow+"El jugador ha sido eliminado."+Colores.reset);
        } else {
            System.out.println(Colores.red+"El jugador que está intentando eliminar no existe."+Colores.reset);
        }
    }

    public static void cargarFichero(File fichero) throws SQLException {
        Statement st = con.createStatement();
        ArrayList<Jugador> jugadores = arrayJugadores(fichero);

        for (int i = 0; i < jugadores.size(); i++) {
            if (!paisExiste(jugadores.get(i).getCodPais())) {
                String sqlPais = "INSERT INTO pais VALUES (" + jugadores.get(i).getCodPais() + ",'" + jugadores.get(i).getNombrePais() + "');";
                st.executeUpdate(sqlPais);
            }

            if (!jugadorExiste(jugadores.get(i).getNombreJugador())) {
                String sqlJugador = "INSERT INTO jugador(nombre_jugador, año_nacimiento, altura, club, id_pais) VALUES ('" +
                        jugadores.get(i).getNombreJugador() + "'," + jugadores.get(i).getAñoNacimiento() + ","
                        + jugadores.get(i).getAltura() + ",'" + jugadores.get(i).getClub() + "',"
                        + jugadores.get(i).getCodPais() + ");";
                st.executeUpdate(sqlJugador);
            }
        }
        st.close();
    }

    public static boolean paisExiste(int codPais) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT id_pais FROM pais;";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        while(rs.next()) {
            ids.add(rs.getInt(1));
        }
        rs.close();
        st.close();
        return ids.contains(codPais);
    }

    //TODO buscar jugador por id
    public static boolean jugadorExiste(String nombreJugador) throws SQLException {
        ArrayList<String> ids = new ArrayList<>();
        Statement st = con.createStatement();
        String sqlJugador = "SELECT nombre_jugador FROM jugador;";
        ResultSet rs = st.executeQuery(sqlJugador);
        while(rs.next()) {
            ids.add(rs.getString(1));
        }
        rs.close();
        st.close();
        return ids.contains(nombreJugador);
    }
    public static boolean jugadorExiste(int codJugador) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        Statement st = con.createStatement();
        String sqlJugador = "SELECT id_jugador FROM jugador;";
        ResultSet rs = st.executeQuery(sqlJugador);
        while(rs.next()) {
            ids.add(rs.getInt(1));
        }
        rs.close();
        st.close();
        return ids.contains(codJugador);
    }
    public static ArrayList arrayJugadores(File f) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try {
            DataInputStream f_in = new DataInputStream((new FileInputStream(f)));
            while(f_in.available() > 0) {
                jugadores.add(new Jugador(f_in.readInt(), f_in.readUTF(), f_in.readUTF(), f_in.readInt(), f_in.readFloat(), f_in.readUTF()));
            }
            f_in.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jugadores;
    }
    public static Jugador datosJugador(int codpais, Scanner scan) {
        System.out.println(Colores.green+"Tntroduzca el nombre del jugador:"+Colores.reset);
        String nombre = scan.nextLine();
        System.out.println(Colores.green+"Tntroduzca su año de nacimiento :"+Colores.reset);
        int año = scan.nextInt();
        System.out.println(Colores.green+"Tntroduzca su altura:"+Colores.reset);
        float altura = scan.nextFloat();
        scan.nextLine();
        System.out.println(Colores.green+"Tntroduzca su club"+Colores.reset);
        String club = scan.nextLine();
        return new Jugador(codpais,nombre,año,altura,club);
    }

    public static Pais datosPais(Scanner scan) {
        System.out.println(Colores.green+"Tntroduzca el codigo del país:"+Colores.reset);
        int codigo = scan.nextInt();
        scan.nextLine();
        System.out.println(Colores.green+"Tntroduzca el nombre del país:"+Colores.reset);
        String pais = scan.nextLine();
        return new Pais(codigo,pais);
    }
    public static void modificarNombreJugador(int codJugador, Scanner scan) throws SQLException {
        System.out.println(Colores.green+"Introduzca el nuevo nombre:"+Colores.reset);
        String nombre = scan.nextLine();
        Statement st = con.createStatement();
        String sqlCambiarNombre = "update jugador set nombre_jugador ='"+nombre+"' where id_jugador ="+codJugador+";";
        st.executeUpdate(sqlCambiarNombre);
        st.close();

    }
    public static void modificarAnoJugador(int codJugador, Scanner scan) throws SQLException {
        System.out.println(Colores.green+"Tntroduzca el nuevo año:"+Colores.reset);
        int ano = scan.nextInt();
        scan.nextLine();
        Statement st = con.createStatement();
        String sqlCambiarAño = "update jugador set año_nacimiento ="+ano+" where id_jugador ="+codJugador+";";
        st.executeUpdate(sqlCambiarAño);
        st.close();

    }
    public static void modificarAlturaJugador(int codJugador, Scanner scan) throws SQLException {
        System.out.println(Colores.green+"Tntroduzca la nueva altura:"+Colores.reset);
        float altura = scan.nextFloat();
        Statement st = con.createStatement();
        String sqlCambiarAltura = "update jugador set altura ="+altura+" where id_jugador ="+codJugador+";";
        st.executeUpdate(sqlCambiarAltura);
        st.close();

    }
    public static void modificarClubJugador(int codJugador, Scanner scan) throws SQLException {

        System.out.println(Colores.green+"Tntroduzca el nuevo club:"+Colores.reset);
        String club = scan.nextLine();
        Statement st = con.createStatement();
        String sqlCambiarAltura = "update jugador set club ='"+club+"' where id_jugador ="+codJugador+";";
        st.executeUpdate(sqlCambiarAltura);
        st.close();
        System.out.println(Colores.yellow+"Datos modificados."+Colores.reset);

    }
    public static void modificarPaisJugador(int codJugador, Scanner scan) throws SQLException {
       otrosPaises(codJugador);
        System.out.println(Colores.green+"Eliga el código de un país:"+Colores.reset);
        int code= scan.nextInt();
        if(paisExiste(code)){
            Statement st = con.createStatement();
            String sqlCambiarAltura = "update jugador set id_pais ="+code+" where id_jugador ="+codJugador+";";
            st.executeUpdate(sqlCambiarAltura);
            st.close();

        }
        else {
            System.out.println(Colores.red+"Pais no existe!"+Colores.reset);
        }

    }
    public static void otrosPaises(int codJugador) throws SQLException {
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT * FROM pais where id_pais != (select id_pais from jugador where id_jugador ="+codJugador+");";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        System.out.println(Colores.purple+"--------------PAÍSES--------------"+Colores.reset);
        System.out.print(Colores.cyan+"Código \t");
        System.out.println("Nombre\t"+Colores.reset);
        while (rs.next()) {
            System.out.printf("%-6d\t%s\n", rs.getInt(1), rs.getString(2));
        }
        st.close();
        rs.close();
    }
    public static void consultarJugador(int codJugador) throws SQLException {
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT nombre_jugador, nombre_pais, año_nacimiento, altura, club FROM jugador inner join pais using(id_pais) where id_jugador = " + codJugador + ";";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        rs.next();
        System.out.println(
                  Colores.blue + "1- Nombre:           \t" + Colores.reset + rs.getString(1) + "\n"
                + Colores.blue + "2- Pais:             \t" + Colores.reset + rs.getString(2) + "\n"
                + Colores.blue + "3- Año de nacimiento:\t" + Colores.reset + rs.getInt(3) + "\n"
                + Colores.blue + "4- Altura:           \t" + Colores.reset + rs.getFloat(4) + "\n"
                + Colores.blue + "5- Club:             \t" + Colores.reset + rs.getString(5)

        );
    }

    public static String casosModificion(int num, int codJugador, Scanner scan) throws SQLException {
        switch (num){
            case 0:
                return "Modificación finalizada.";
            case 1:
                modificarNombreJugador(codJugador,scan);
                return "El nombre del jugador "+codJugador+" ha sido modificado con exito!" ;
            case 2:
                modificarPaisJugador(codJugador,scan);
                return "El pais del jugador "+codJugador+" ha sido modificado con exito!" ;

            case 3:
                modificarAnoJugador(codJugador,scan);
                return "El año del nacimiento del jugador "+codJugador+" ha sido modificado con exito!" ;
            case 4:
                modificarAlturaJugador(codJugador,scan);
                return "La altura del jugador "+codJugador+" ha sido modificada con exito!" ;

            case 5:
                modificarClubJugador(codJugador,scan);
                return "El club del jugador "+codJugador+" ha sido modificado con exito!" ;

            default:
            return "numero de datos no existe.";
        }
    }
    }
