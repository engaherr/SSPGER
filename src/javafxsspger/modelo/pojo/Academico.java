/*
* Título del programa: Clase POJO de Académico
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 05/06/2023
* Descripción: Clase modelo para los Usuarios Académicos del sistema, la clase extiende a usuario
* para evitar la duplicidad de código entre Académico y Estudiante
*/
package javafxsspger.modelo.pojo;

import javafx.scene.control.CheckBox;

public class Academico extends Usuario {
    private static Academico instanciaSingleton;
    private int idAcademico;
    private int noPersonal;
    private boolean esAdmin;
    private boolean esResponsableCA;
    private int idCAResponsable;
    private boolean esDirector;
    private String grado;
    private CheckBox seleccionar;

    public Academico() {
    }

    public Academico(int idAcademico, int noPersonal, boolean esAdmin, boolean esResponsableCA, 
            int idCAResponsable, boolean esDirector, String grado, String email, String password, 
            String nombre, String apellidoPaterno, String apellidoMaterno, byte[] foto) {
        super(email, password, nombre, apellidoPaterno, apellidoMaterno, foto);
        this.idAcademico = idAcademico;
        this.noPersonal = noPersonal;
        this.esAdmin = esAdmin;
        this.esResponsableCA = esResponsableCA;
        this.idCAResponsable = idCAResponsable;
        this.esDirector = esDirector;
        this.grado = grado;
        this.seleccionar = new CheckBox();
    }

    public int getIdCAResponsable() {
        return idCAResponsable;
    }

    public void setIdCAResponsable(int idCAResponsable) {
        this.idCAResponsable = idCAResponsable;
    }

    public boolean isEsResponsableCA() {
        return esResponsableCA;
    }

    public void setEsResponsableCA(boolean esResponsableCA) {
        this.esResponsableCA = esResponsableCA;
    }

    public boolean isEsDirector() {
        return esDirector;
    }

    public void setEsDirector(boolean esDirector) {
        this.esDirector = esDirector;
    }

    public static Academico getInstanciaSingleton() {
        return instanciaSingleton;
    }

    public static void setInstanciaSingleton(Academico instanciaSingleton) {
        Academico.instanciaSingleton = instanciaSingleton;
    }

    public int getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(int noPersonal) {
        this.noPersonal = noPersonal;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public CheckBox getSeleccionar() {
        return seleccionar;
    }

    public void setSeleccionar(CheckBox seleccionar) {
        this.seleccionar = seleccionar;
    }
    
}
