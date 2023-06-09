/*
* Título del programa: Clase POJO de Modalidad
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo de las Modalidades del Anteproyecto que están registradas en la 
* persistencia del sistema
*/
package javafxsspger.modelo.pojo;

public class Modalidad {
    private int idModalidad;
    private String modalidad;

    public Modalidad() {
    }

    public Modalidad(int idModalidad, String modalidad) {
        this.idModalidad = idModalidad;
        this.modalidad = modalidad;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public String toString() {
        return modalidad;
    }
}
