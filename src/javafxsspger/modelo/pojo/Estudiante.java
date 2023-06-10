/*
* Título del programa: Clase POJO de Estudiante
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 05/06/2023
* Descripción: Clase modelo para Usuario Estudiante y la información que lo integre
*/
package javafxsspger.modelo.pojo;

public class Estudiante extends Usuario {
    private static Estudiante instanciaSingleton;
    private int idEstudiante;
    private String matricula;
    private int telefono;

    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String matricula, String email, String password, String nombre, String apellidoPaterno, String apellidoMaterno, byte[] foto, int telefono) {
        super(email, password, nombre, apellidoPaterno, apellidoMaterno, foto);
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.telefono = telefono;
    }

    
    
    public static Estudiante getInstanciaSingleton() {
        return instanciaSingleton;
    }

    public static void setInstanciaSingleton(Estudiante instanciaSingleton) {
        Estudiante.instanciaSingleton = instanciaSingleton;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    
}
