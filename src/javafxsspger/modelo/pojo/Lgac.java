/*
* Título del programa: Clase POJO de LGAC
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para las LGACs del sistema y toda su información
*/
package javafxsspger.modelo.pojo;

public class LGAC {
    private int idLgac;
    private String nombre;
    private int idCuerpoAcademico;
    private int numero;

    public LGAC(int idLgac, String nombre, int idCuerpoAcademico, int numero) {
        this.idLgac = idLgac;
        this.nombre = nombre;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.numero = numero;
    }

    public LGAC() {
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

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
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
