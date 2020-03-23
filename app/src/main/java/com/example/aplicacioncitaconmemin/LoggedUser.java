package com.example.aplicacioncitaconmemin;

public class LoggedUser {

    private String nombreUsuario, nombrePersonal, edad, biografia;
    private boolean firstLogIn;


    public LoggedUser(String nombreUsuario, String nombrePersonal, String edad, String biografia, boolean firstLogIn) {
        this.nombreUsuario = nombreUsuario;
        this.nombrePersonal = nombrePersonal;
        this.edad = edad;
        this.biografia = biografia;
        this.firstLogIn = firstLogIn;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public boolean isFirstLogIn() {
        return firstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }
}
