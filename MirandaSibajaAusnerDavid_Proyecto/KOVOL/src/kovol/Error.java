
package kovol;


public class Error {

    //Variables de texto estáticas con los errores.
    public static String FaltaNombre = "Error 001   No colocó el nombre del archivo";
    public static String ExtensionNoEsKovol = "Error 002   El archivo no tiene la extensión .kovol";
    public static String NombreInvalido = "Error 003   El nombre del archivo es inválido";
    public static String NoTiene6EspaciosVacios = "Error 0004   No tiene 6 espacios en blanco";
    public static String ErrorColumna7 = "Error 0005   En la columna 7 hay un carater inválido";
    public static String ErrorColumna72 = "Error 0006   En la columna 72 hay un caracter inválido";
    public static String ErrorLineaConMasDe80Columnas = "Error 0007   La linea tiene más de 80 columnas";
    public static String ErrorMargenA = "Error 0008   Hay un error en el margen A";
    public static String ErrorMargenB = "Error 0009   Hay un error en el margen B";
    public static String ErrorEnElWorkingStorage = "Error 0010   Hay un error en el WORKING-STORAGE";
    public static String ErrorNoTerminaConPunto = "Error 0011    La linea no termina con punto";
    public static String ErrorNivel77 = "Error 0012   EL nivel 77 no se encuentra en el margen A";
    //Método para cuando la variable no está definida.
    public static String VariableNoDefinida(String variable){
        return "Error 0013   La variable "+variable+" no esta definida.";
    }
    public static String ErrorVariableFormatoIncorrecto(String variable){
        return "Error 0014   La variable "+variable+" no tiene un formato correcto.";
    }
    public static String ErrorVariableMuyLarga(String variable){
        return "Error 0015   La variable "+variable+" tiene más de 30 caracteres.";
    }
    public static String ErrorInstrucciónDISPLAY = "Error 0016   Hay un error en la instrucción DISPLAY";
    public static String ErrorInstrucciónCOMPUTE = "Error 0017   Hay un error en la instrucción COMPUTE";
    public static String ErrorExpresionIncorrecta = "Error 0018   Hay un error en la expresión matemática";
    public static String ErrorConParentesis = "Error 0019    Hay un error con los paretesis";
    public static String ErrorInstrucciónPICTURE = "Error 0020    Hay un error en el comando PICTURE o PIC";
    public static String ErrorInstruccionACCEPT = "Error 0021   Hay un error en la instrucción ACCEPT";
    public static String ErrorFaltanComillas = "Error 0022   Faltan comillas en la instrucción DISPLAY";
    public static String ErrorFaltaComa = "Error 0023   Falta la coma en la instrucción DISPLAY";
    public static String ErrorOperadoresSeguidos = "Error 0024   Hay dos operadores seguidos";
    //Método estático para la advertencia de palabras no reconocidas.
    public static String AdvertenciaPalabraNoReconocidaxKOVOL (String palabra){
        
        return "Advertencia: instrucción "+palabra+" no es soportada por esta versión.";
    }
    

    
  
    
    
    
    
}
