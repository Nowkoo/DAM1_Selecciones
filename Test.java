import com.sun.source.tree.WhileLoopTree;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws SQLException {
        File fichero = new File("./Recursos/seleccion_Australia.dat");
        //cargarFichero(fichero);
        consultarPaises();
        consultarJugadoresPais(4);
    }
    static java.sql.Connection con = DatabaseConnection.getInstance().getConnection();

    public Test() {

    }

    public static void consultarPaises() throws SQLException {
        Statement st = con.createStatement();
        String sqlCodigosPais = "SELECT * FROM pais";
        ResultSet rs = st.executeQuery(sqlCodigosPais);
        System.out.println("--------------PAÍSES--------------");
        System.out.print("Código \t");
        System.out.println("Nombre\t");
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
        System.out.println("------------JUGADORES-------------");
        while (rs.next()) {
            System.out.printf("%-2d\t%-25s\t%-4d\t%-3d\t%-2d\t%s\n", rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6));
        }
        st.close();
        rs.close();
    }

    public void nuevoRegistro() {

    }

    public void nuevoPais() {

    }

    public void nuevoClub() {

    }

    public void nuevoJugador() {

    }

    public static void cargarFichero(File fichero) throws SQLException {
        Statement st = con.createStatement();
        ArrayList<Jugador> jugadores = arrayJugadores(fichero);

        String sqlPais = "INSERT INTO pais VALUES (" + jugadores.get(0).getCodPais() + ",'" + jugadores.get(0).getNombrePais() + "');";
        st.executeUpdate(sqlPais);

        for (int i = 0; i < jugadores.size(); i++) {
            String sqlJugador = "INSERT INTO jugador(nombre_jugador, año_nacimiento, altura, club, id_pais) VALUES ('" +
                    jugadores.get(i).getNombreJugador() + "'," + jugadores.get(i).getAñoNacimiento() + ","
                    + jugadores.get(i).getAltura() + ",'" + jugadores.get(i).getClub() + "',"
                    + jugadores.get(i).getCodPais() + ");";
            st.executeUpdate(sqlJugador);
        }
        st.close();
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
}
