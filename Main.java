import java.io.File;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while(!salir) {
            System.out.println(Colores.green+"Seleccione una opción:"+Colores.reset);
            System.out.println(Colores.blue+"1. Consultar países");
            System.out.println("2. Carga masiva de datos");
            System.out.println("3. Salir"+Colores.reset);

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    OperacionesDB.consultarPaises();
                    menuConsultarPaises(scanner);
                    break;
                case 2:

                    System.out.println(Colores.green+"Introduzca la ruta absoluta del fichero que desea consultar: "+Colores.reset);
                    File file = new File(scanner.nextLine());
                    if (file.exists()) {
                        OperacionesDB.cargarFichero(file);
                        System.out.println(Colores.green+"¡El contenido del fichero ha sido añadido a la base de datos!"+Colores.reset);
                    } else {
                        System.out.println(Colores.yellow+"El fichero seleccionado no existe."+Colores.reset);
                    }
                    break;
                default:
                    System.out.println(Colores.red+"Opción no válida, pruebe otra vez."+Colores.reset);
            }
        }
    }

    public static void menuConsultarPaises(Scanner scanner) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println(Colores.green+"Seleccione una opción:"+Colores.reset);
            System.out.println(Colores.blue+"1. Añadir país");
            System.out.println("2. Eliminar país");
            System.out.println("3. Consultar jugadores de un país");
            System.out.println("4. Volver"+Colores.reset);

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    Pais pais = OperacionesDB.datosPais(scanner);
                    OperacionesDB.nuevoPais(pais);
                    break;
                case 2:
                    OperacionesDB.consultarPaises();
                    System.out.println(Colores.green+"Introduzca el código del país:"+Colores.reset);
                    int codigoPais = leerOpcionMenu(scanner);
                    OperacionesDB.eliminarPais(codigoPais);
                    break;
                case 3:
                    OperacionesDB.consultarPaises();
                    System.out.println(Colores.green+"Introduzca el código del país:"+Colores.reset);
                    codigoPais = leerOpcionMenu(scanner);
                    if (OperacionesDB.paisExiste(codigoPais)){
                        OperacionesDB.consultarJugadoresPais(codigoPais);
                        menuConsultarJugadores(scanner, codigoPais);
                    }
                    else {
                        System.out.println(Colores.red+"País no existe!"+Colores.reset);
                    }

                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println(Colores.red+"Opción no válida, pruebe otra vez."+Colores.reset);
            }
        }
    }

    public static void menuConsultarJugadores(Scanner scanner, int codigoPais) throws SQLException {
        boolean volver = false;
        while (!volver) {
           // OperacionesDB.consultarJugadoresPais(codigoPais);
            System.out.println(Colores.green+"Seleccione una opción:"+Colores.reset);
            System.out.println(Colores.blue+"1. Añadir jugador");
            System.out.println("2. Eliminar jugador");
            System.out.println("3. Modificar jugador");
            System.out.println("4. Volver"+Colores.reset);

            int opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    Jugador jugador = OperacionesDB.datosJugador(codigoPais, scanner);
                    OperacionesDB.nuevoJugador(jugador);
                    break;
                case 2:
                    OperacionesDB.consultarJugadoresPais(codigoPais);
                    System.out.println(Colores.green+"Introduzca el nombre del jugador:"+Colores.reset);
                    String nombreJugador = scanner.nextLine();
                    OperacionesDB.eliminarJugador(nombreJugador);
                    break;
                case 3:
                    OperacionesDB.consultarJugadoresPais(codigoPais);
                    System.out.println(Colores.green+"Introduzca el codigo del jugador:"+Colores.reset);
                    int codJugador = scanner.nextInt();
                    if(OperacionesDB.jugadorExiste(codJugador)){
                        int num=-1;
                        do {
                            OperacionesDB.consultarJugador(codJugador);
                            System.out.println(Colores.green+"Elige el número del dato que deseas modificar \n"+Colores.blue+"(ingresa 0 para finalizar la modificación)"+Colores.reset+":"+Colores.reset);
                            num= leerOpcionMenu(scanner);
                            System.out.println(Colores.yellow+OperacionesDB.casosModificion(num,codJugador,scanner)+Colores.reset);

                        }while (num != 0);
                    }

                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println(Colores.red+"Opción no válida, pruebe otra vez."+Colores.reset);
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
               System.out.println(Colores.red+"Por favor, introduzca un número:"+Colores.reset);
           }
       }
    }

}
