/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

/**
 *
 * @author jasie
 */
public class Lgac {
    private int idLgac;
    private String nombre;
    private String descripcion;

    public Lgac() {
    }

    public Lgac(int idLgac, String nombre, String descripcion) {
        this.idLgac = idLgac;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    
    
    public int getIdLgac() {
        return idLgac;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String toString(){
        return nombre;
    }    
}
