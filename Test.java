import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws SQLException {
        File fichero = new File("./recursos/seleccion_EEUU.dat");
        cargarFichero(fichero);
    }
    static java.sql.Connection con = DatabaseConnection.getInstance().getConnection();

    public Test() {

    }

    public void consultarCodPais() {

    }

    public void consultarJugadores(int codPais) {

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
