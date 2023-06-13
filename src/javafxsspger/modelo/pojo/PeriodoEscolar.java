/*
* Título: Clase POJO de Periodo Escolar
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase modelo para los periodos escolares que se muestran en la creación de cursos
*/
package javafxsspger.modelo.pojo;

public class PeriodoEscolar {
    private int idPeriodoEscolar;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(int idPeriodoEscolar, String nombre, String fechaInicio,
            String fechaFin) {
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
