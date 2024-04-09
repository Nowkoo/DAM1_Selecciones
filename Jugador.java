public class Jugador {
    public int codPais;
    public String nombrePais;
    public String nombreJugador;
    public int añoNacimiento;
    public float altura;
    public String club;

    public Jugador(int codPais, String nombrePais, String nombreJugador, int añoNacimiento, float altura, String club){
        this.codPais = codPais;
        this.nombrePais = nombrePais;
        this.nombreJugador = nombreJugador;
        this.añoNacimiento = añoNacimiento;
        this.altura = altura;
        this.club = club;
    }

    public int getCodPais() {
        return codPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public float getAltura() {
        return altura;
    }

    public String getClub() {
        return club;
    }
}
