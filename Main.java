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
                    if (file.exists()) {
                        OperacionesDB.cargarFichero(file);
                        System.out.println("¡El contenido del fichero ha sido añadido a la base de datos!");
                    } else {
                        System.out.println("El fichero seleccionado no existe.");
                    }
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
                    Pais pais = OperacionesDB.datosPais(scanner);
                    OperacionesDB.nuevoPais(pais);
                    break;
                case 2:
                    System.out.println("Introduzca el código del país:");
                    int codigoPais = leerOpcionMenu(scanner);
                    OperacionesDB.eliminarPais(codigoPais);
                    break;
                case 3:
                    OperacionesDB.consultarPaises();
                    System.out.println("Introduzca el código del país:");
                    codigoPais = leerOpcionMenu(scanner);
                    if (OperacionesDB.paisExiste(codigoPais)){
                        OperacionesDB.consultarJugadoresPais(codigoPais);
                        menuConsultarJugadores(scanner, codigoPais);
                    }
                    else {
                        System.out.println("País no existe!");
                    }

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
            OperacionesDB.consultarJugadoresPais(codigoPais);
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir jugador");
            System.out.println("2. Eliminar jugador");
            System.out.println("3. Modificar jugador");
            System.out.println("4. Volver");

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    Jugador jugador = OperacionesDB.datosJugador(codigoPais, scanner);
                    OperacionesDB.nuevoJugador(jugador);
                    break;
                case 2:
                    OperacionesDB.consultarJugadoresPais(codigoPais);
                    System.out.println("Introduzca el nombre del jugador:");
                    String nombreJugador = scanner.nextLine();
                    OperacionesDB.eliminarJugador(nombreJugador);
                    break;
                case 3:
                    OperacionesDB.consultarJugadoresPais(codigoPais);
                    System.out.println("Introduzca el codigo del jugador:");
                    int codJugador = scanner.nextInt();
                    if(OperacionesDB.jugadorExiste(codJugador)){
                        int num=-1;
                        do {
                            OperacionesDB.consultarJugador(codJugador);
                            System.out.println("Elige el número del dato que deseas modificar \n(ingresa 0 para finalizar la modificación):");
                            num= leerOpcionMenu(scanner);
                            OperacionesDB.casosModificion(num,codJugador,scanner);
                        }while (num != 0);
                    }

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
               int resultado = scanner.nextInt();
               scanner.nextLine();
               return resultado;
           } catch (InputMismatchException e) {
               scanner.next();
               System.out.println("Por favor, introduzca un número:");
           }
       }
    }

}
