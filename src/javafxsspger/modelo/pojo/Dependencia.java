/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

/**
 *
 * @author jasie
 */
public class Dependencia {
    private int idDependencia;
    private String nombre;

    public Dependencia() {
    }

    public Dependencia(int idDependencia, String nombre) {
        this.idDependencia = idDependencia;
        this.nombre = nombre;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String tostring(){
        return nombre;
    }
}
