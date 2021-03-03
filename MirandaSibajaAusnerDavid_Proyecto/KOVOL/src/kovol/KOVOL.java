package kovol;

import java.io.*;
import static java.lang.System.exit;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.omg.SendingContext.RunTime;

public class KOVOL {

    public static ArrayList milista = new ArrayList(); //Lista para guardar las lineas del archivo kovol.
    public static ArrayList datos_errores_txt = new ArrayList(); //Lista para guardar las lineas para el archivo de errores.
    public static ArrayList nombres_variables = new ArrayList(); //Lista para guardar los nombres de varibles definidas.
    public static boolean SinErrores = true; //Variable boleana que controla la creación del ejecutable .exe.
    //Recibe el nombre del arhchivo como parámetro

    public static void main(String[] nombrearchivo) throws FileNotFoundException, ArrayIndexOutOfBoundsException, IOException {

        if (nombrearchivo.length == 0) { //Si el nombre del archivo no tiene ningun tamaño.
            System.out.println(Error.FaltaNombre); //Muestra mensaje en la terminal CMD.
        } else { //Sino.

            boolean valido = true; //Variable de tipo boolean para controlar si el archivo es válido.
            String archivo = nombrearchivo[0]; //Convierte el nombre de archivo a cadena.

            if (!verificar_extension(nombrearchivo)) { //Si la función "verficar_extensión" devuelve false.

                File recuperarext = new File(nombrearchivo[0] + ".kovol"); //Crea archivo con el nombre y la extensión .kovol 

                if (!recuperarext.exists()) { //Si la función "recuperarext" devuelve un false.
                    System.out.println(Error.ExtensionNoEsKovol); //Muestra mensaje.
                    valido = false; //Asigna el valor de falso a valido.
                } else { //Sino.
                    archivo = nombrearchivo[0] + ".kovol"; //Agrega a la cadena archivo el nombre y la extensión .kovol.
                    valido = true; //Asigna true a valido.

                }

            }

            if (valido) { //Si valido es true.
                if (!verificar_nombre(nombrearchivo)) { //Si la función "verificar_nombre" devuelve false.
                    System.out.println(Error.NombreInvalido); //Muestra mensaje.
                } else { //Sino.
                    System.out.println("El archivo es válido\n"); //Muestra mensaje.
                    System.out.println("INICIA COMPILACIÓN ->\tCompilando el archivo " + nombrearchivo[0] + "...");  //Muestra mensaje.
                    Leer_Archivo(archivo); //Llama al método Leer_Archivo 
                }
            }

        }
    }

    //Método para verificar la extensión del archivo a leer.
    public static boolean verificar_extension(String[] nombrearchivo) {
        boolean extvalida = false; //Variable para controlar la validez de la extensión.
        StringTokenizer token = new StringTokenizer(nombrearchivo[0], "."); //Separa en token el nombre del archivo a partir del punto.

        while (token.hasMoreTokens()) { //Mientras hayan tokens.

            if ("kovol".equals(token.nextToken())) { //Si es igual a "kovol".
                extvalida = true;   //Asigna true a extvalida
            }
        }

        return extvalida;  //Retorna extvalida.
    }

    //Método para verificar el nombre del archivo.
    public static boolean verificar_nombre(String[] nombrearchivo) {
        boolean nombrevalido = false; //Variable para verificar el nombre del archivo.
        StringTokenizer token = new StringTokenizer(nombrearchivo[0], "."); //Separa el nombre en tokens.
        String name = token.nextToken(); //Aqui se guarda el nombre del archivo sin extension.

        if (!name.matches(Expresiones.misExpresiones.nombresvalidos.pattern)) { //Compara si este inicia con una letra y que solo tenga letras y números.
            nombrevalido = false; //Asigna falso a nombrevalido.
        } else { //Sino.
            nombrevalido = true; //Asigna true a nombrevalido.
        }

        return nombrevalido; //Retorna nombrevalido.
    }

    //Método para leer el archivo .kovol
    public static void Leer_Archivo(String nombrearchivo) throws FileNotFoundException, IOException {

        File miarchivo = new File(nombrearchivo); //Obtengo el archivo.
        Scanner entrada = new Scanner(miarchivo); //Para leer cada una de las lineas del archivo.
        entrada.useDelimiter(System.getProperty("line.separator")); //Deleimita la lectura por linea.
        while (entrada.hasNext()) { //Mientras hayan más lineas.
            milista.add(entrada.nextLine()); //Agrega la linea a la lista.
        }

        Analizar_Linea(nombrearchivo); //Llama al método Analizar_Linea.

    }

    //Método para analizar las lineas de la lista.
    public static void Analizar_Linea(String nombrearchivo) throws IOException {

        for (int i = 0; i < milista.size(); i++) { //Recorre la lista.
            String linea = milista.get(i).toString(); //Obtiene la linea en una cadena.
            String linea2 = linea.trim();
            if (linea2.equals("")) {
                //Se ignora la linea
            } else {
                if (i < 9) { //Si el número de linea está entre 0 y 8.
                    datos_errores_txt.add("0000" + (i + 1) + linea); //Arega a la lista.
                    System.out.println("Compilando linea: 0000" + (i + 1));
                } else if (i >= 9 && i < 99) { //Si esta entre 9 o 98.
                    datos_errores_txt.add("000" + (i + 1) + linea); //Agrega a la lista.
                    System.out.println("Compilando linea: 000" + (i + 1));
                } else if (i >= 99 && i < 999) { //Si esta entre 99 o 998.
                    datos_errores_txt.add("00" + (i + 1) + linea); //Agrega a la lista.
                    System.out.println("Compilando linea: 00" + (i + 1));
                } else if (i >= 999 && i < 9999) { //Si esta entre 999 y 9998.
                    datos_errores_txt.add("0" + (i + 1) + linea); //Agrega a la lista.
                    System.out.println("Compilando linea: 0" + (i + 1));
                }

                if (linea.length() > 0) {
                    for (int col = 0; col < 6; col++) {  //Recorre la linea desde la columna 1 a la 7 

                        char letra = linea.charAt(col); //Obtiene cada uno de los caracteres.

                        if (!Character.isSpaceChar(letra)) {//Verifica los 6 espacios en blanco.
                            SinErrores = false; //Asigna falso a sin errores.
                            datos_errores_txt.add(Error.NoTiene6EspaciosVacios); //Agrega un error a la lista.
                            break; //Rompe el bucle.
                        }

                    }
                }

                //Se verifica si no es un comentario
                if (linea.charAt(6) != '*') {
                    if (linea.length() >= 71) {  //Si la linea tiene más carácteres que 72.
                        if (linea.charAt(71) != '*' || linea.charAt(71) != ' ') { //Verifica si no es un comentario en la columna 72 de cobol.

                        } else if (linea.charAt(71) == ' ') {

                        } else {
                            datos_errores_txt.add(Error.ErrorColumna72);
                        }
                    }

                    if (linea.charAt(6) == ' ' || linea.charAt(6) == '-') { //Si no hay nada es una linea normal sin comentario.

                        //Verificar liena con 80 columnas ->  Apartir de la col 8 Separar en tokens para quitat los espacios
                        VerificarExtensionLinea(linea); //LISTO
                        VerificarPuntoFinal(linea);
                        //Evalua si hay errores en el Margen A
                        if (VerificarMargenA(linea)) {
                            datos_errores_txt.add(Error.ErrorMargenA); //Agrega el error a la lista.
                        }

                        //Evalua si hay errores en margen B
                        if (VerificarMargenB(linea)) { //Si devuelve true.
                            //Si entra en este if quiere decir que estaba el comando STOP RUN, el cual detiene la ejecución del programa.
                            break;
                        }

                    } else { //Sino.
                        SinErrores = false; //Asigna a sinerrores el valor de false.
                        datos_errores_txt.add(Error.ErrorColumna7); //Agrega el error a la lista.
                    }

                }
            }
        }

        ArchivoErrores obj = new ArchivoErrores(nombrearchivo, datos_errores_txt); //Instancia un objeto de la clase ArchivoErrores y le manda la lista de errores.

        if (SinErrores) {
            //Si no hay errrores.
            //Llamar al IDE Cobol.
            CrearArchivoCOBOL(nombrearchivo);    //Llama al método para crear el archivo con extensión .cob
            CrearEjecutable(nombrearchivo); //Llama al método para crear el ejecutable .exe. 

        } else { //Sino.
            System.out.println("VERIFICAR ARCHIVO DE ERRORES:\nNo se generó el ejecutable .exe porque el archivo contiene errores"); //Muestra mensaje.
        }

    }

    //Método para Verificar que la linea no tenga más de 80 columnas.
    public static void VerificarExtensionLinea(String linea) {
        int contadorcolumnas = 0; //Contador.
        for (int i = 0; i < linea.length(); i++) { //Recorre la linea a partir de la columna 8 de cobol.
            contadorcolumnas++; //Aumenta el contador.
        }
        if (contadorcolumnas > 80) { //Si es mayor a 80.
            datos_errores_txt.add(Error.ErrorLineaConMasDe80Columnas); //Agrega el error a la lista.
        }
    }

    //Método para verificar que la linea tiene un punto final de la instrucción.
    public static void VerificarPuntoFinal(String linea) {
        if (!linea.isEmpty()) { //Si la linea no está vacía.

            if (linea.contains(".")) { //Si la linea tiene un punto
                //Ignora
            } else { //Sino.
                datos_errores_txt.add(Error.ErrorNoTerminaConPunto); //Agrega el error a la lista.
            }
        }
    }

    //Método para verificar errores en el margen A.
    public static boolean VerificarMargenA(String linea) {
        boolean error = false; //Variable para controlar los errores del margen A.

        /**
         * ***************PROCEDURE DIVISION************
         */
        //Si la linea contiene las palabras de las secciones del archivo en el margen A o si el margen A está vacío o es una continucación de linea.
        if (linea.contains("IDENTIFICATION DIVISION.") || linea.contains("PROGRAM-ID.") || linea.contains("AUTHOR.") || linea.contains("ENVIRONMENT DIVISION.") || linea.contains("DATA DIVISION.") || linea.contains("WORKING-STORAGE SECTION.") || linea.contains("PROCEDURE DIVISION.") || linea.contains("INICIO") || linea.contains("       ") || linea.contains("      -")) {
            if (linea.contains("77")) { //Si tiene el nivel 77.

                String MargenA = ""; //Crea cadena.
                for (int i = 7; i <= 10; i++) { //Verifica de la columna 8 a la 11.
                    MargenA += linea.charAt(i); //Obtiene los datos de esa sección.
                }

                if (MargenA.contains("77")) { //Si el margen A tiene el nivel 77.
                    comandoPICTURE(linea);
                } else { //Sino.
                    datos_errores_txt.add(Error.ErrorNivel77); //El Nivel 77 no se encuentra en el margen A.
                    error = true; //Asigna verdadero a error.
                }

            }
            error = false; //Asigna falso a error.
        } else { //Sino.
            error = true; //Asigna verdadero a error.
        }

        return error; //Retorna error.
    }

    //Método para verificar el margen B.
    public static boolean VerificarMargenB(String linea) {
        boolean continuar = false;
        String MargenB = "";

        for (int i = 11; i < linea.length(); i++) {
            MargenB += linea.charAt(i);
        }
        if (MargenB.isEmpty()) { //Si esta vacío

        } else {
            //Se separa la linea en tokens:
            StringTokenizer token = new StringTokenizer(MargenB);
            String txtline = new String(); //Crea un string para guardar la linea completa.
            //String miexpresion = new String(); //Crea un string para guardar el token.

            while (token.hasMoreTokens()) { //Mientras hayan más tokens.
                boolean KOVOLreservadaencontrada = false; //Crea variable boleana para saber si se encuentra una palabra de KOVOL.
                boolean COBOLreservadaencontrada = false; //Crea variable boleana para saber si se encuentra una palabra de COBOL.
                String TOKEN = token.nextToken(); //Obtiene el token.
                txtline += TOKEN; //Guarda el token en la lista.

                for (Simbolos.misSimbolos simbolo : Simbolos.misSimbolos.values()) { //Recorre el enum de simbolos.
                    if (TOKEN.toUpperCase().matches(simbolo.pattern)) { //Verifica si hay un match.

                        switch (simbolo) { //Evalua el simbolo.
                            case palabrasreservadasKOVOL: //Verifico si es una de KOVOL 
                                KOVOLreservadaencontrada = true; //Asigna el valor de true.
                                continuar = PalabraReservadaKOVOL(TOKEN, linea); //Obtiene el valor true si el comando era STOP RUN.
                                break; //Rompe el switch.
                            case palabrasreservadasCOBOL:  //Verfico si es una de COBOL
                                datos_errores_txt.add(Error.AdvertenciaPalabraNoReconocidaxKOVOL(TOKEN)); //Agrega datos a la lista.
                                COBOLreservadaencontrada = true; //Asigna true a la variable.
                                break; //Rompe el switch.
                            default:
                                datos_errores_txt.add(Error.ErrorMargenB);
                        }
                    }

                    if (COBOLreservadaencontrada || KOVOLreservadaencontrada) { //Si se encontro una palabra de COBOL o de KOVOL
                        break; //Rompe el for.
                    }

                }

                if (COBOLreservadaencontrada || KOVOLreservadaencontrada) { //Si se encontro una palabra de COBOL o de KOVOL se ignora el resto de la linea.
                    break; //Rompe el while.
                }
            }
        }
        return continuar; //Retorna continuar.
    }

    //Método para el comando PIC o PICTURE.
    public static void comandoPICTURE(String linea) {
        StringTokenizer token = new StringTokenizer(linea); //Crea token.
        token.nextToken(); //Nivel 77
        String mivariable = token.nextToken(); //Nombre de la variable.
        nombres_variables.add(mivariable); //Guarda el nomobre de la variable.
        //Verifica el correcto formato de la variable.
        if (!mivariable.matches(Expresiones.misExpresiones.nombresvalidos.pattern)) { //Verifica el nombre de la variable con  la expresión regular.
            if (mivariable.length() > 30) { //Si el nombre es mayor a 30.
                datos_errores_txt.add(Error.ErrorVariableMuyLarga(mivariable)); //Escribe errores.
            }
            datos_errores_txt.add(Error.ErrorVariableFormatoIncorrecto(mivariable)); //Escribe errores.
        }
        String expresion = ""; //Crea cadena vacía.
        while (token.hasMoreTokens()) { //Mientras hayan más tokens.
            expresion += token.nextToken(); //Instrucción PIC o PICTURE despues del nombre de la variable.
        }
        if (!expresion.matches(Expresiones.misExpresiones.PIC.pattern)) { //Si no coincide con la expresión regular.
            datos_errores_txt.add(Error.ErrorInstrucciónPICTURE); //Agrega el error.
        }

    }

    //Método para ver el comando que es quivalente al TOKEN.
    public static boolean PalabraReservadaKOVOL(String TOKEN, String linea) {
        boolean STOPRUN = false; //Crea variable booleana para saber cuando es el comando STOP RUN.
        switch (TOKEN) { //Evalua el TOKEN recibido.
            case "ACCEPT": //Si es el comando ACCEPT.
                //Llama a la función comandoACCEPT.
                comandoACCEPT(linea);
                break;
            case "DISPLAY": //Si es el comando DISPLAY.
                //Llama a la función comandoDISPLAY.
                comandoDISPLAY(linea);
                break;
            case "COMPUTE": //Si es el comando COMPUTE.
                //Llama a la función comandoCOMPUTE.
                comandoCOMPUTE(linea);
                break;
            case "STOP": //SI ES EL COMANDO STOP RUN.
                System.out.println("TERMINA COMPILACIÓN -> SE ENCONTRÓ EL COMANDO STOP RUN, POR LO QUE ES LA ÚLIMA LINEA."); //Muestra mensaje.
                STOPRUN = true;
                // exit(1); //Finaliza el programa.
                break;
        }
        return STOPRUN; //Retorna el valor de STOPRUN.
    }

    //Método del comando ACCEPT.
    public static void comandoACCEPT(String linea) {
        StringTokenizer token = new StringTokenizer(linea, " ."); //Crea token.
        token.nextToken(); //Siguiente token.
        if (token.hasMoreTokens()) {
            String variable = token.nextToken(); //Guarda el nombre de la variable.
            boolean encontrado = false; //Crea variable booleana para buscar si la variable esta definida.

            for (int i = 0; i < nombres_variables.size(); i++) { //Recorre la lista de variables.
                if (nombres_variables.get(i).equals(variable)) { //Si coinciden.
                    encontrado = true; //Asigna verdadero a encontrado.
                }
            }

            if (!encontrado) { //Si no se encontró.
                datos_errores_txt.add(Error.VariableNoDefinida(variable)); //Agrega error.
            }
        } else {
            datos_errores_txt.add(Error.ErrorInstruccionACCEPT);//Agrega error.
        }

    }

    //Método para el comando DISPLAY.
    public static void comandoDISPLAY(String linea) {
        boolean convariable = false; //Variable booleana para diferenciar si el comando va con variable o solo.
        StringTokenizer tk = new StringTokenizer(linea); //Crea token.
        while (tk.hasMoreTokens()) { //Mientras hayan tokens.
            String t = tk.nextToken(); //Obtiene el token.
            if (t.contains(".")) { //Si tiene punto.
                t = t.replace(".", ""); //Lo quita.
            }
            for (int m = 0; m < nombres_variables.size(); m++) { //Recorre la lista de variables.
                if (nombres_variables.get(m).equals(t)) { //Si alguno coincide con el token.
                    convariable = true; //Asigna true a convariable.
                    break; //Rompe el ciclo.
                }
            }
        }

        if (convariable) { //Si el DISPLAY va con variable.
            int contadorcomas = 0; //Contador de comas.
            for (int c = 0; c < linea.length(); c++) { //Recorre la linea.
                if (linea.charAt(c) == ',') { //Si es una coma.
                    contadorcomas++; //Suma en 1 al contador de comas.
                }
            }
            if (contadorcomas == 0) { //Si es igual a 0.
                datos_errores_txt.add(Error.ErrorFaltaComa); //Agrega el error.
            }

            StringTokenizer token = new StringTokenizer(linea, "\",."); //Crea token.
            token.nextToken(); //DISPLAY.

            boolean variable_encontrada = false; //Crea variable booleana para buscar la variable en la lista de variables.
            //Verifico el nombre de la variable.
            token.nextToken(); //TEXTO.

            if (token.hasMoreTokens()) {
                String variable = token.nextToken(); //VARIABLE.
                variable = variable.replace(" ", ""); //Quira los espacios en blanco.
                for (int i = 0; i < nombres_variables.size(); i++) { //Recorre la lista de variables.
                    if (nombres_variables.get(i).equals(variable)) { //Si coinciden.
                        variable_encontrada = true; //Asigna verdadero a variable_encontrada.
                    }
                }

                if (!variable_encontrada) { //Si no se encontró.
                    datos_errores_txt.add(Error.VariableNoDefinida(variable)); //Agrega el error.
                }

                //Verificar si la instrucción coincida con la expresion regular.
                if (!linea.matches(Expresiones.misExpresiones.DISPLAY.pattern)) { //Si la linea no tiene el formato correcto.
                    datos_errores_txt.add(Error.ErrorInstrucciónDISPLAY); //Agrega el error.
                }
            }

        } else { //El DISPLAY va sin variable.
            if (!linea.matches(Expresiones.misExpresiones.DISPLAY.pattern)) { //Si la linea no tiene el formato correcto.
                datos_errores_txt.add(Error.ErrorInstrucciónDISPLAY); //Agrega el error.
            }

        }

        int contadorcomillas = 0; //Contador de comillas.
        for (int n = 0; n < linea.length(); n++) { //Recorre la linea.
            if (linea.charAt(n) == '"') { //Si hay comillas.
                contadorcomillas++; //Suma en 1 al contador.
            }

        }
        if (contadorcomillas != 2) { //Si el contador es diferente de 2.
            datos_errores_txt.add(Error.ErrorFaltanComillas); //Agrega el error.
        }

    }

    //Método del comando COMPUTE.
    public static void comandoCOMPUTE(String linea) {
        StringTokenizer token = new StringTokenizer(linea, " ."); //Crea token.
        String expresion = ""; //Crea cadena para ir guardando la expresión.

        while (token.hasMoreTokens()) { //Mientras existan más tokens.
            String TOKEN = token.nextToken(); //Obtiene el siguiente token.
            if (TOKEN.equals("COMPUTE")) { //Si es igual a COMPUTE.
                expresion += "COMPUTE"; //Agrega a la cadena el texo: " COMPUTE".
                //Ignora ya que es la palabra reservada.
            } else { //Sino.
                if (EsUnNumero(TOKEN)) { //Verifica si es un número.
                    expresion += " " + TOKEN; //Agrega el número a la cadena.
                } else { //Sino.
                    //Verfica que es una variable previamente definida.
                    boolean variable_valida = false; //Crea variable booleana para buscar en la lista de variables.
                    for (int i = 0; i < nombres_variables.size(); i++) { //Recorre la lista.
                        if (nombres_variables.get(i).equals(TOKEN)) {  //Si coinciden.
                            variable_valida = true; //Asigna verdadero a variable_valida.
                        }
                    }

                    if (!variable_valida) { //Si no se encontró.
                        if (TOKEN.equals("=") || TOKEN.equals("+") || TOKEN.equals("-") || TOKEN.equals("*") || TOKEN.equals("/")) { //Si el token es un operador aritmético.
                            expresion += " " + TOKEN; //Agrega el token a la expresión.
                            if (token.hasMoreTokens()) { //Si hay más tokens.
                                String nuevoToken = token.nextToken(); //Obtiene el siguiente token.
                                if (nuevoToken.equals("=") || nuevoToken.equals("+") || nuevoToken.equals("-") || nuevoToken.equals("*") || nuevoToken.equals("/")) { //Si el nuevo token es un operador.
                                    datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error ya que dos operadores juntos es un error.
                                    datos_errores_txt.add(Error.ErrorOperadoresSeguidos);
                                } else { //Sino.
                                    boolean find = false; //Crea variable para buscar la variable en la lista.
                                    for (int x = 0; x < nombres_variables.size(); x++) { //Recorre la lista.
                                        if (nombres_variables.get(x).equals(nuevoToken)) { //Si coinciden.
                                            find = true; //Asigna true a find.
                                        }
                                    }
                                    if (!find) { //Si find es falso.
                                        if (EsUnNumero(nuevoToken)) { //Si es un número.
                                            expresion += " " + nuevoToken; //Agrega el token a la cadena.
                                        } else { //Sino.
                                            if (nuevoToken.contains("(")) {
                                                //1. Verificar si es un número.
                                                String TokenNum = nuevoToken.replace("(", ""); //Quita el parentesis de apertura.
                                                if (EsUnNumero(TokenNum)) { //Si es un número.
                                                    expresion += " " + nuevoToken; //Agrega el token a la expresion.
                                                } else { //Sino.
                                                    boolean valido = false; //Variable booleana para buscar en la lista.
                                                    //2. Verificar si es una variable
                                                    for (int k = 0; k < nombres_variables.size(); k++) { //Recorre la lista.
                                                        if (nuevoToken.equals("(" + nombres_variables.get(k))) { //Si es igual a la lista.
                                                            valido = true; //Asigna true a valido.
                                                        }
                                                    }
                                                    if (valido) { //Si valido es true.
                                                        expresion += " " + nuevoToken; //Asigna el token a la expresion.
                                                    } else { //Sino.
                                                        datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                                                    }
                                                }
                                            } else if (nuevoToken.contains(")")) { //Si el token tiene un parentesis de cierre.
                                                //1. Verificar si es un número.
                                                String TokenNum = nuevoToken.replace(")", ""); //Quita el parentesis.
                                                if (EsUnNumero(TokenNum)) { //Si es un número.
                                                    expresion += " " + nuevoToken; //Agrega el token a la expresion.
                                                } else { //Sino.
                                                    boolean valido = false; //Variable booleana para buscar en la lista de variables.
                                                    //2. Verificar si es una variable
                                                    for (int k = 0; k < nombres_variables.size(); k++) { //Recorre la lista.
                                                        if (nuevoToken.equals(nombres_variables.get(k) + ")")) { //Si coincide.
                                                            valido = true; //Asigna true a valido.
                                                        }
                                                    }
                                                    if (valido) { //Si valido es true.
                                                        expresion += " " + nuevoToken; //Agrega el token a la expresion.
                                                    } else { //Sino.
                                                        datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                                                    }
                                                }
                                            } else { //Sino.
                                                datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                                            }
                                        }
                                    } else { //Sino.
                                        expresion += " " + nuevoToken; //Agrega el token a la cadena expresion.
                                    }
                                }
                            } else { //Sino.
                                datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                            }
                        } else { //Sino.
                            if (TOKEN.contains("(")) { //Si el token contiene un parentesis de apertura.
                                //1. Verificar si es un número.
                                String TokenNum = TOKEN.replace("(", ""); //Lo quita.
                                if (EsUnNumero(TokenNum)) { //Si es un número.
                                    expresion += " " + TOKEN; //Agrega el token a la expresion.
                                } else { //Sino.
                                    boolean valido = false; //Variable booleana para buscar en la lista de variables.
                                    //2. Verificar si es una variable
                                    for (int k = 0; k < nombres_variables.size(); k++) { //Recorre la lista.
                                        if (TOKEN.equals("(" + nombres_variables.get(k))) { //Si coincide.
                                            valido = true; //Asigna true a valido.
                                        }
                                    }
                                    if (valido) { //Si valido es true.
                                        expresion += " " + TOKEN; //Agrega el token a la expresion.
                                    } else { //Sino.
                                        datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Arega el error.
                                    }
                                }
                            } else if (TOKEN.contains(")")) { //Si el error tiene un parentesis de cierre.
                                //1. Verificar si es un número.
                                String TokenNum = TOKEN.replace(")", ""); //Lo quita.
                                if (EsUnNumero(TokenNum)) { //Si es un número.
                                    expresion += " " + TOKEN; //Agrega el token a la expresion.
                                } else { //Sino.
                                    boolean valido = false; //Variable booleana para buscar en la lista de variables.
                                    //2. Verificar si es una variable
                                    for (int k = 0; k < nombres_variables.size(); k++) { //Recorre la lista.
                                        if (TOKEN.equals(nombres_variables.get(k) + ")")) { //Si coincide.
                                            valido = true; //Asigna true a valido.
                                        }
                                    }
                                    if (valido) { //Si valido es true.
                                        expresion += " " + TOKEN; //Agrega el token a la lista.
                                    } else { //Sino.
                                        datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                                    }
                                }
                            } else { //Sino.
                                datos_errores_txt.add(Error.ErrorExpresionIncorrecta); //Agrega el error.
                            }
                        }
                    } else { //Sino.

                        expresion += " " + TOKEN; //Agrega el token a la cadena expresión.
                    }

                }
            }
        }
        int contadorAbreParentesis = 0; //Contador para los parentesis de apertura.
        int contadorCierraParentesis = 0; //Contador para los parentesis de cierre.
        for (int indice = 0; indice < linea.length(); indice++) { //Recorrre la linea.
            if (linea.charAt(indice) == '(') { //Si encuentra un parentesis de apertue.
                contadorAbreParentesis++; //Aumenta en 1.
            } else if (linea.charAt(indice) == ')') { //Si encuentra un parentesis de cierre.
                contadorCierraParentesis++; //Aumenta en 1.
            }
        }
        if (contadorAbreParentesis != contadorCierraParentesis) { //Si la expresión tiene un parentesis de apertura pero falta el de cierre.
            datos_errores_txt.add(Error.ErrorConParentesis); //Agrega el error.
        }
    }

    //Método para ver si el TOKEN es un número.
    public static boolean EsUnNumero(String cadena) {
        try { //Inicia try.
            Integer.parseInt(cadena); //Convierte el texto en entero.
            return true; //Retorna true.
        } catch (NumberFormatException e) {
            return false; //En caso de error, retorna false.
        }
    }

    //Método para crear e ejecutable del programa.
    public static void CrearEjecutable(String nombrearchivo) throws IOException {

        try { //Inicia try.

            //Se compila el archivo para generar uno con extension.exe
            String comando = "cobc -x -j " + nombrearchivo + ".cob";
            Process Proceso = Runtime.getRuntime().exec(comando); //Procede a ejecutar el comando.
            System.out.println("Ejecutando el comando: " + comando); //Muetsra mensaje.
            System.out.println("Espere...."); //Muestra mensaje.
            Thread.sleep(20000); //Se espera a que se cree el ejecutable para evitar fallas al intentar abrirlo cuando no existe.
        } catch (Exception e) { //Termina try - inicia catch.
            System.out.println("Excepción encontrada: " + e); //Muestra mensaje.
        } //Termina catch.

        try { //Inicia try.
            File verificarejecutable = new File(nombrearchivo + ".exe"); //Obtiene un archivo con el nombre del ejecutable.

            if (verificarejecutable.exists()) { //Verifica si el ejecutable existe.
                Runtime.getRuntime().exec("cmd /c \"start " + nombrearchivo + ".exe"); //Abre el ejecutable.
            } else { //Sino.
                //Muestra mensaje.

                System.out.println("ATENCIÓN:  El archivo compilado contiene errores, por lo que el ejecutable no se pudo crear");
            }

        } catch (IOException e) { //Termina try - inicia catch.
            System.out.println("Ha ocurrido un error: " + e.toString()); //Muestra mensaje.
        }

    }

    //Método para crear el arhivo COBOL.
    public static void CrearArchivoCOBOL(String nombrearchivo) {
        File archivocobol; //Crea variable de tipo file.
        //Atributos para escribir en el archivo.
        FileWriter fw;
        BufferedWriter bw;
        PrintWriter pw;

        try { //Inicia try.

            archivocobol = new File(nombrearchivo + ".cob"); //Crea archivo con la extensión .cob.
            //Lo prepara para leer.
            fw = new FileWriter(archivocobol); //Le asigna el archivocobol.
            bw = new BufferedWriter(fw); //Le asigna el FileWriter.
            pw = new PrintWriter(bw); //Le asigna el BufferedWriter.

            for (int i = 0; i < milista.size(); i++) { //Recorre la lista.
                pw.write(milista.get(i).toString() + "\n"); //Escribe las lineas al archivo.
            }

            //Cierra los atributos de la escritura.
            pw.close();
            bw.close();
            fw.close();

        } catch (Exception e) { //Termina try - inicia catch.
            System.out.println("Ha ocurrido un error: " + e); //Muestra mensaje.
        } //Termina catch.

    }

}
