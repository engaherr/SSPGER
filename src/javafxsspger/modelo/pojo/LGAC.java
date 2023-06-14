/*
* Título del programa: Clase POJO de LGAC
* Autor: Enrique Gamboa Hernández, Jasiel Emir Zavaleta García
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para las LGACs del sistema y toda su información
*/
package javafxsspger.modelo.pojo;

public class Lgac {
    private int idLgac;
    private String nombre;
    private String descripcion;
    private int idCuerpoAcademico;
    private String nombreCuerpoAcademico;
    private int numero;

    public Lgac(int idLgac, String nombre,String descripcion, int idCuerpoAcademico,
            String nombreCuerpoAcademico, int numero) {
        this.idLgac = idLgac;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
        this.numero = numero;
    }

    public Lgac() {
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getNombreCuerpoAcademico() {
        return nombreCuerpoAcademico;
    }

    public void setNombreCuerpoAcademico(String nombreCuerpoAcademico) {
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
    }
    
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return numero + ". " + nombre;
    }
}
