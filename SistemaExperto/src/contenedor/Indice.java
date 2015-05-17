
package contenedor;


import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author nelther
 */
public class Indice {
   int llave;
   int pos;
    FileManager manager = new FileManager();
    RandomAccessFile indice;
    String URLArchivoIndice="archivos/indice";
    Arbol arbolito=new Arbol();
    
    void leerIndice() throws IOException{
        indice=manager.Abrir(URLArchivoIndice);
        indice.seek(0);
        if (indice.length()!=0) {
            while (indice.getFilePointer()<indice.length()) { 
                llave=indice.readInt();
                pos=indice.readInt();
                System.out.println("llave:  "+llave+"   pos:   "+pos);
                arbolito.insertart(llave, pos);
            }
        }
        indice.close();
    }
    
}
