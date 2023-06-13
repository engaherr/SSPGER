/*
* Título del programa: Interfaz para la Notificacion de Operaciones
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 06/06/2023
* Descripción: Interfaz multifuncional para indicar cambios en una colección como tablas, listas
* o algún otro componente de la tecnología que al ser añadido o modificado un elemento tenga que
* actualizar sus elementos
*/
package javafxsspger.interfaz;

public interface INotificacionOperacion {
    
    public void notificarOperacionGuardar(String estado);
    
    public void notificarOperacionActualizar(String estado);
}
