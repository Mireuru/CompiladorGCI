/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:                  SEMESTRE: AGO-DIC/2021     HORA: 18-19 HRS
 *:                                   
 *:   
 *: Clase con la funcionalidad de Atributos
 *:
 *:
 *: Archivo         : Atributos.java
 *:                  Juan Miguel García Montes, Elí Uziel Montes Pérez,
 *:                  Erik Manuel Ramírez Vázquez
 *: Compilador : Java JDK 7
 *: Descripción : Esta clase permite el uso de los atributos Lugar, op, Verdadera, Falsa,
 Comienzo,
 *:               Siguiente y Codigo
 *: Ult.Modif. :
 *: Fecha       Modificó             Modificacion
 *:========================================================================
 =====
 *: 07/Dec/2021 Miguel,Erik,Elí Se agregaron todos los atributos, con excepción de
 *. Lugar y op, porque ya estaban.
 *:----------------------------------------------------------------------------
 */
package compilador;

public class Atributos {
    public String  Lugar;
    public String  op;
    public String  Verdadera;
    public String  Falsa;
    public String  Comienzo;
    public String  Siguiente;
    public String  Codigo;
    
    public Atributos () {
        Lugar = "";
        op = "";
        Verdadera = "";
        Falsa = "";
        Comienzo = "";
        Siguiente = "";
        Codigo = "";
    }
}
