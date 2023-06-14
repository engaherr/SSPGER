/*
* Título del programa: Clase POJO de Dependencia
* Autor: Jasiel Emir Zavaleta García
* Fecha Creación: 09/06/2023
* Descripción: Clase modelo para las Dependencias y su información que proviene de la 
* persistencia del sistema
*/
package javafxsspger.modelo.pojo;

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
    


    @Override
    public String toString() {
        return nombre;
    }
    
}
