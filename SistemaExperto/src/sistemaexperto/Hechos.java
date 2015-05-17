/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexperto;

import contenedor.FileManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EVAADRIANA
 */
public class Hechos {
    /*
    File archivo;
    FileWriter escribir;*/
    
    FileManager manager = new FileManager();
    RandomAccessFile hechos;
    String UrlArchivoHechos="archivos/hechos";
    
    int tamañoHecho=20;
    
    public Hechos(){
        try {
            hechos=manager.Abrir(UrlArchivoHechos);
        } catch (IOException ex) {
            Logger.getLogger(Hechos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void Escribir(String valor) throws IOException{
        //Escribe un registro de 2 campos cada uno de tamaño 20
        // 20 caracteres + 20 caracteres           .
        hechos.seek(hechos.length());
        manager.m_EscribirString(valor, tamañoHecho, hechos);
     
    }
    
    public void Escribir(String dato, double valor) throws IOException{
        //escribe el nombre de un dato en un registro de 20 caracteres y su valor 
        //como tipo de dato double 8 bytes
        //20 caracteres + 8 bytes
        hechos.seek(hechos.length());
        manager.m_EscribirString(dato, tamañoHecho, hechos);
        hechos.writeDouble(valor);
        System.out.println("se escrivio un dato con valor double");
    }
    
    
     public void eliminarArchivo() throws IOException{
       hechos.close();
       System.out.println("cerrado");
       new File(UrlArchivoHechos).delete();
         System.out.println("eliminado");
    }
}
