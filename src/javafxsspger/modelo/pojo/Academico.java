/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

/**
 *
 * @author kikga
 */
public class Academico extends Usuario {
    private static Academico instanciaSingleton;
    private int idAcademico;
    private int noPersonal;
    private boolean esAdmin;
    private boolean esResponsableCA;
    private int idCAResponsable;
    private boolean esDirector;
    private String grado;

    public Academico() {
    }

    public Academico(int idAcademico, int noPersonal, boolean esAdmin, boolean esResponsableCA, int idCAResponsable, boolean esDirector, String grado, String email, String password, String nombre, String apellidoPaterno, String apellidoMaterno, byte[] foto) {
        super(email, password, nombre, apellidoPaterno, apellidoMaterno, foto);
        this.idAcademico = idAcademico;
        this.noPersonal = noPersonal;
        this.esAdmin = esAdmin;
        this.esResponsableCA = esResponsableCA;
        this.idCAResponsable = idCAResponsable;
        this.esDirector = esDirector;
        this.grado = grado;
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
    
}
