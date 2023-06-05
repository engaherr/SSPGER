/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

/**
 *
 * @author kikga
 */
public class Estudiante extends Usuario {
    private int idEstudiante;
    private String matricula;

    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String matricula, String email, String password, String nombre, String apellidoPaterno, String apellidoMaterno, byte[] foto) {
        super(email, password, nombre, apellidoPaterno, apellidoMaterno, foto);
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
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
    
}
