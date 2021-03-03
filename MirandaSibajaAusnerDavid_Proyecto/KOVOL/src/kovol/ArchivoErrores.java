package kovol;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ArchivoErrores {
    
    ArrayList datos = new ArrayList(); //Cra lista para alamcenar los datos que se pasan por parámetro.
    String cadena; //Crea cadena.
    File archivoerrores; //Crea atributo de tipo file.
    FileWriter fw; //Crea atributo de tipo FileWrtiter.
    BufferedWriter bw; //Crea atributo de tipo BufferedWriter.
    PrintWriter pw; //Crea atributo de tipo PrintWriter.
   
    //Constructor.
    public ArchivoErrores(String nombrearchivo, ArrayList datos){
        StringTokenizer st = new StringTokenizer(nombrearchivo,"."); //Separa en tokens el nombre del archivo.
        this.cadena = st.nextToken()+"-errores.txt"; //Asigna a la cadena el nuevo nombre.
        this.datos = datos; //Inicializa el ArrayList.
        CrearArchivo(); //Llama al método CrearArchivo.
    }
    
    //Método para crear el archivo.
    public void CrearArchivo(){
        
        try{ //Iniciar try.

            archivoerrores = new File(cadena);  //Crea archivo.
            fw = new FileWriter(archivoerrores); //Le asigna el archivoerrores.
            bw = new BufferedWriter(fw); //Le asigna el FileWriter.
            pw = new PrintWriter(bw); //Le asigna el BufferedWriter.
            
            for(int i=0; i<datos.size(); i++){ //Recorre la lista. 
                pw.write(datos.get(i).toString()+"\n"); //Escribe en el archivo.
            }
            
            //Cierra los atributos de escritura.
            pw.close();
            bw.close();
            fw.close();
            
            
        }catch(Exception e){ //Termina try - inicia catch.
            System.out.println("Ha ocurrido un error: "+e); //Muestra error.
        } //Termina catch.
         
        
    }
    
    
    
}
