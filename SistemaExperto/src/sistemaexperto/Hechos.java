/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexperto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author EVAADRIANA
 */
public class Hechos {
    
    File archivo;
    FileWriter escribir;
    public Hechos(){

        try {
            //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
            archivo = new File("hechos.txt");

            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            escribir = new FileWriter(archivo, true);

            //Escribimos en el archivo con el metodo write 
          // escribir.write(saludo);

            //Cerramos la conexion
           escribir.close();
        } //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            System.out.println("Error al escribir");
        }

    }
    
    public void Escribir(String dato, String valor) throws IOException{
        escribir = new FileWriter(archivo, true);
        escribir.write(dato);
        escribir.write(",");
        escribir.write(valor);
        escribir.write("\r\n");
        escribir.close();
    }
    
     public void eliminarArchivo(){
        archivo.delete();
    }
}
