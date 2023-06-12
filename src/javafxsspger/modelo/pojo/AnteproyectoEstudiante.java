/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

/**
 *
 * @author jasie
 */
public class AnteproyectoEstudiante {
    private int idAnteproyecto;
    private int idEstudiante;
    private int codigoRespuesta;
    private String estado;

    public AnteproyectoEstudiante() {
    }

    public AnteproyectoEstudiante(int idAnteproyecto, int idEstudiante,int codigoRespuesta, String 
            estado) {
        this.idAnteproyecto = idAnteproyecto;
        this.idEstudiante = idEstudiante;
        this.codigoRespuesta = codigoRespuesta;
        this.estado = estado;
    }
    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
