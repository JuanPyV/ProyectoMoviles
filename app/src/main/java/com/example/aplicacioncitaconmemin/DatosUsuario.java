package com.example.aplicacioncitaconmemin;

public class DatosUsuario {

    private String username, cellphone, password;

    public DatosUsuario(String username, String cellphone, String password) {
        this.username = username;
        this.cellphone = cellphone;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
