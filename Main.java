import java.io.File;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while(!salir) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Consultar países");
            System.out.println("2. Carga masiva de datos");
            System.out.println("3. Salir");

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    OperacionesDB.consultarPaises();
                    menuConsultarPaises(scanner);
                    break;
                case 2:
                    System.out.println("Introduzca la ruta absoluta del fichero que desea consultar: ");
                    File file = new File(scanner.nextLine());
                    OperacionesDB.cargarFichero(file);
                    System.out.println("¡El contenido del fichero ha sido añadido a la base de datos!");
                    break;
                default:
                    System.out.println("Opción no válida, pruebe otra vez.");
            }
        }
    }

    public static void menuConsultarPaises(Scanner scanner) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir país");
            System.out.println("2. Eliminar país");
            System.out.println("3. Consultar jugadores de un país");
            System.out.println("4. Volver");

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    OperacionesDB.nuevoPais();
                    break;
                case 2:
                    System.out.println("Introduzca el código del país:");
                    int codigoPais = leerOpcionMenu(scanner);
                    OperacionesDB.eliminarPais(codigoPais);
                case 3:
                    System.out.println("Introduzca el código del país:");
                    codigoPais = leerOpcionMenu(scanner);
                    OperacionesDB.consultarJugadoresPais(codigoPais);
                    menuConsultarJugadores(scanner, codigoPais);
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida, pruebe otra vez.");
            }
        }
    }

    public static void menuConsultarJugadores(Scanner scanner, int codigoPais) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir jugador");
            System.out.println("2. Eliminar jugador");
            System.out.println("3. Modificar jugador");
            System.out.println("4. Volver");

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    OperacionesDB.nuevoJugador();
                    break;
                case 2:
                    System.out.println("Introduzca el código del jugador:");
                    int codigoJugador = leerOpcionMenu(scanner);
                    OperacionesDB.eliminarJugador(codigoJugador);
                    break;
                case 3:

                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida, pruebe otra vez.");
            }
        }
    }


    public static int leerOpcionMenu(Scanner scanner) {
       while (true) {
           try {
               return scanner.nextInt();
           } catch (InputMismatchException e) {
               scanner.next();
               System.out.println("Por favor, introduzca un número:");
           }
       }
    }
}
