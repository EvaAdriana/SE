/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contenedor;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
    
    
    public RandomAccessFile Abrir(String Archivo)throws IOException{
    return new RandomAccessFile(Archivo,"rw");
    }
    
    public void m_EscribirString (String Escribe, int largo, RandomAccessFile Archivo)throws IOException
        {   
            StringBuffer buffer=new StringBuffer(Escribe);
            buffer.setLength(largo);
            Archivo.writeChars(buffer.toString());  
        }
    
    public String formar_string(RandomAccessFile file, int num)throws IOException{
        //FORMA UN STRING A PARTIR DE LEER UN CONJUNGO DE CARACTERES EN EL ARCHIVO
        char s [] = new char[num];
        char aux;
        String strg="";
        for(int i=0; i<s.length; i++){
            aux=file.readChar();
            s[i]=aux; 
        }
       
        return  new String(s).replace("\0","");
    } 
}
