/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:                  SEMESTRE: AGO-DIC/2013     HORA: 12-13 HRS
 *:                                   
 *:               
 *:    # Clase con la funcionalidad del Generador de COdigo Intermedio
 *                 
 *:                           
 *: Archivo       : GenCodigoInt.java
 *: Autor         : Fernando Gil  
 *: Fecha         : 03/Octubre/2013
 *: Compilador    : Java JDK 7
 *: Descripción   :  
 *:                  
 *:           	     
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *:                                 P -> V C
 *:                                 V -> id : T  V | empty
 *:                                 T -> caracter | entero | real 
 *:                                 C -> inicio S fin
 *:                                 S -> id := E  S  |  empty
 *:                                 E -> num  E' | num.num  E' |  id  E'
 *:                                 E'-> oparit E  |  empty 
 *:-----------------------------------------------------------------------------
 */

package compilador;


public class GenCodigoInt {
 
    private Compilador cmp;
    private int consecutivoTemp;
    public static final int NIL = 0;
    
    //--------------------------------------------------------------------------
    // Constructor de la clase, recibe la referencia de la clase principal del 
    // compilador.
    //
    public GenCodigoInt ( Compilador c ) {
        cmp = c;
    }
    // Fin del Constructor
    //--------------------------------------------------------------------------
    
    public void generar () {
        consecutivoTemp = 1;
        P ();
    }    

    //--------------------------------------------------------------------------

    private void emite ( String c3d ) {
        cmp.erroresListener.mostrarCodInt ( c3d );
    }
    
    private String tempnuevo(  ) {
        return "t" + consecutivoTemp++;
    }
    
    //--------------------------------------------------------------------------
    //************EMPAREJAR**************//
    private void emparejar ( String t ) {
	if (cmp.be.preAnalisis.complex.equals ( t ) )
		cmp.be.siguiente ();
	else
		errorEmparejar ( "Se esperaba " + t + " se encontró " +
                                 cmp.be.preAnalisis.lexema );
    }	
	
    //--------------------------------------------------------------------------
    // Metodo para devolver un error al emparejar
    //--------------------------------------------------------------------------

    private void errorEmparejar ( String _token ) {
        String msjError = "ERROR SINTACTICO: ";
              
        if ( _token.equals ( "id" ) )
            msjError += "Se esperaba un identificador" ;
        else if ( _token.equals ( "num" ) )
            msjError += "Se esperaba una constante entera" ;
        else if ( _token.equals ( "num.num" ) )
            msjError += "Se esperaba una constante real";
        else if ( _token.equals ( "literal" ) )
            msjError += "Se esperaba una literal";
        else if ( _token.equals ( "oparit" ) )
            msjError += "Se esperaba un Operador Aritmetico";
        else if ( _token.equals ( "oprel" ) )
            msjError += "Se esperaba un Operador Relacional";
        else 
            msjError += "Se esperaba " + _token;
                
        cmp.me.error ( Compilador.ERR_SINTACTICO, msjError );    
    }            

    // Fin de ErrorEmparejar
    //--------------------------------------------------------------------------
	
    //--------------------------------------------------------------------------
    // Metodo para mostrar un error sintactico
 
    private void error ( String _token ) {
        cmp.me.error ( cmp.ERR_SINTACTICO,
         "ERROR SINTACTICO: en la produccion del simbolo  " + _token );
    }
 
    // Fin de error
    //--------------------------------------------------------------------------
    private void P()
    {
        if ( cmp.be.preAnalisis.complex.equals ( "id"     ) ||
             cmp.be.preAnalisis.complex.equals ( "inicio" )     )
        {
            //P -> V C 
            V ( ) ;
            C ( ) ;
        }
        else {                    
            error("Error de P");
        }
    }
	//-------------------------------------------------------------------
	private void V( ) 
	 {
			if ( cmp.be.preAnalisis.complex.equals ( "id" ) ) {
				//V -> id : T V 
				emparejar ( "id" ) ;
				emparejar ( ":" ) ;
				T ( ) ;
				V ( ) ;
			}
			else {
				//V -> EMPTY
			}
	 }
	//------------------------------------------------------------------------
	private void T () {

			if ( cmp.be.preAnalisis.complex.equals ( "caracter" ) ){
			 //T -> caracter
				emparejar ( "caracter" ) ;
		}
			else if (cmp.be.preAnalisis.complex.equals ( "entero" ) ){
				//T -> entero
				emparejar ( "entero" ) ;
		}
			else if ( cmp.be.preAnalisis.complex.equals ( "real" ) ){
				//T -> real
				emparejar( "real" );
		}
			else{
				error("Error de T");
			 }
	}  
	private void C()
	{
		if (cmp.be.preAnalisis.complex.equals ( "inicio" ) ) {
				//C -> inicio S fin
				emparejar ( "inicio" ) ;
				S (new Atributos());
				emparejar ( "fin" );
			}
			else{
				error("Error de C");
			 }
	}
	//________________________________________________________________________
	private void S(Atributos S)
	{
            Atributos E = new Atributos();
            Atributos K = new Atributos();
            Linea_BE id = new Linea_BE();
            int p = 0;
		if (cmp.be.preAnalisis.complex.equals ( "id" ) ) {
                    //S -> id := E {1} S
                    id = cmp.be.preAnalisis;
                    emparejar ( "id" );

                    emparejar ( ":=" );
                    E (E);
                    //Accion semantica 1
                    p = cmp.ts.buscar(id.lexema);
                    if(p != NIL){
                        emite(p+" := "+ E.Lugar);
                    }
                    else{
                        cmp.me.error(Compilador.ERR_CODINT, "[S] Error");
                    }
                    //Fin de accion semantica 1
                    S (S);
                }
                else if(cmp.be.preAnalisis.complex.equals ( "si" )) {
                    //S -> si K entonces inicio S fin S
                    emparejar("si");
                    K(K);
                    emparejar("entonces");
                    emparejar("inicio");
                    S(S);
                    emparejar("fin");
                    S(S);
                }
                else if(cmp.be.preAnalisis.complex.equals ( "mientras" )){
                    //S -> mientras K hacer inicio S fin S
                    emparejar("mientras");
                    K(K);
                    emparejar("hacer");
                    emparejar("inicio");
                    S(S);
                    emparejar("fin");
                    S(S);
                }
                else {
                              //S -> EMPTY
                }
	}
        //---------------------------------------------------
        private void K(Atributos K){
        Atributos E1 = new Atributos();
        Atributos E2 = new Atributos();
        Linea_BE oprel = new Linea_BE();
	if( cmp.be.preAnalisis.complex.equals ( "num" ) ||
            cmp.be.preAnalisis.complex.equals ( "num.num" ) ||
            cmp.be.preAnalisis.complex.equals ( "id" ) )
        {
            E(E1);
            oprel = cmp.be.preAnalisis;
            emparejar("oprel");
            E(E2);
        }
        else{
            error("Error de K");
        }
}
	//------------------------------------------------------------------------
	private void E(Atributos E)
	{
            Atributos Ep = new Atributos();
            Linea_BE num = new Linea_BE();
            Linea_BE numnum = new Linea_BE();
            Linea_BE id = new Linea_BE();
		if( cmp.be.preAnalisis.complex.equals ( "num" ) ){
				 //E -> num E'
                                 num = cmp.be.preAnalisis;
				 emparejar ( "num" ) ;
				 Ep (Ep);             
                                 //Accion semantica 5
                                 if(Ep.op.equals("")){
                                     E.Lugar = num.entrada+"";
                                 }
                                 else{
                                     E.Lugar = tempnuevo();
                                     emite(E.Lugar + " := "+num.entrada+Ep.op+Ep.Lugar);
                                 }
                                 //Fin accion semantica 5
		}
			 else if ( cmp.be.preAnalisis.complex.equals ( "num.num" ) ){
				 //E -> num.num  E'
                                 numnum = cmp.be.preAnalisis;
				 emparejar ( "num.num" ) ;
				 Ep (Ep);      
                                 //Accion semantica 6
                                 if(Ep.op.equals("")){
                                     E.Lugar = numnum.entrada+"";
                                 }
                                 else{
                                     E.Lugar = tempnuevo();
                                     emite(E.Lugar + " := "+numnum.entrada+Ep.op+Ep.Lugar);
                                 }
                                 //Fin accion semantica 6
                                 
		}
			 else if ( cmp.be.preAnalisis.complex.equals ( "id" ) ){
				 //E -> id E'
                                 id = cmp.be.preAnalisis;
				 emparejar ( "id" ) ;
				 Ep (Ep);
                                 //Accion semantica 7
                                 if(Ep.op.equals("")){
                                     E.Lugar = id.entrada+"";
                                 }
                                 else{
                                     E.Lugar = tempnuevo();
                                     emite(E.Lugar + " := "+id.entrada+Ep.op+Ep.Lugar);
                                 }
                                 //Fin accion semantica 6
		}         
			 else{
				cmp.me.error(Compilador.ERR_CODINT,"Error de E");
			 }
	}

	//------------------------------------------------------------------------

	private void Ep (Atributos Ep) {
            Atributos E = new Atributos();
            Linea_BE oparit = new Linea_BE();
		if ( cmp.be.preAnalisis.complex.equals ( "oparit" ) ) {
			// E' -> oparit E
                        oparit = cmp.be.preAnalisis;
			emparejar ( "oparit" );
			E (E);
                        //Accion semantica 8
                        Ep.op = oparit.lexema;
                        Ep.Lugar = E.Lugar;
                        //Fin de accion semantica 8
		} else {
			// E' -> empty
                        //Accion semantica 9
                        Ep.op = "";
                        //Fin de accion semantica 9
		}
	}
		 
	//------------------------------------------------------------------------

    
}
