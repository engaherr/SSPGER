/*
* Título: Clase POJO de Materia
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase Modelo para las Experiencias educativas que se pueden seleccionar al crear un
* curso
*/
package javafxsspger.modelo.pojo;

public class Materia {
    private int idMateria;
    private String nombre;

    public Materia() {
    }

    public Materia(int idMateria, String nombre) {
        this.idMateria = idMateria;
        this.nombre = nombre;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  nombre;
    }
    
    
}
