package contenedor;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author nelther
 */
public class Maestro {
     int llave;
    FileManager manager = new FileManager();
    public RandomAccessFile maestro,indice;
    String URLArchivoMaestro="archivos/maestro";
    String URLArchivoIndice="archivos/indice";
    
    int tamañoAnt_Con=20;
    int numAnt_Con=5; //variable para definir maximo de antecedentes y consecuentes.
    int tamanoAntecedente=((tamañoAnt_Con*numAnt_Con))*2;
    int tamañoConsecuente=tamanoAntecedente;
    int tamañoRegistro=tamanoAntecedente+tamañoAnt_Con+6;
            
    String antecedente  [] = new String[numAnt_Con];
    String operadoresAnt [] = new String[numAnt_Con-1];
    String consecuente [] = new String[numAnt_Con];
    String operadoresCons[]= new String[numAnt_Con-1] ;

    
    void insertar (int llave, String registro ) throws IOException{
        for (int i = 0; i < numAnt_Con; i++) {
            antecedente[i]="";
            consecuente[i]="";
        }
        for (int i = 0; i < numAnt_Con-1; i++) {
            operadoresAnt[i]="";
            operadoresCons[i]="";
        }
        procesarRegistro(registro);

        
        //Considerar Eliminados        
        maestro=manager.Abrir(URLArchivoMaestro);
        indice=manager.Abrir(URLArchivoIndice);
        int posicion=0, seek=0;
        boolean eliminado=false;
        indice.seek(0);
        if(indice.length()!=0){
        do {    
            int l=indice.readInt();
            if (l<0) {
                 seek=(int)indice.getFilePointer()-2;
                 posicion=indice.readInt();
                 eliminado=true;
            }
        } while (indice.getFilePointer()<indice.length()-2);
        }
        if (eliminado) {
             indice.seek(seek);
             maestro.seek(posicion);
             indice.writeInt(llave);
        }else{
             maestro.seek(maestro.length());
             indice.seek(indice.length());
             indice.writeInt(llave);
             indice.writeInt((int)(maestro.length()));
        }
        
        maestro.writeInt(llave);
        for (int i = 0; i < antecedente.length; i++) {
            manager.m_EscribirString(antecedente[i], tamañoAnt_Con, maestro);
           /* if(i<antecedente.length-1){
                manager.m_EscribirString(operadoresAnt[i], 1, maestro);
            }*/
            
        }
        
        for (int i = 0; i < consecuente.length; i++) {
            manager.m_EscribirString(consecuente[i], tamañoAnt_Con, maestro);
          /*  if(i<consecuente.length-1){
                manager.m_EscribirString(operadoresCons[i], 1, maestro);
            }*/
            
        }
        
        
        
        maestro.close();
        indice.close();
        
    }
    
    void procesarRegistro(String cadena){//SEPARAR LA CADENA 
        String campo="";
        int i=0, indice=0;
        do{
            if(cadena.charAt(i)!='&')
                if(cadena.charAt(i)!='|' ){
                     campo+=cadena.charAt(i);
                }else{
                     antecedente[indice]=campo;
                     operadoresAnt[indice]=String.valueOf(cadena.charAt(i));
                     campo="";
                     indice++;
            }else{
                     antecedente[indice]=campo;
                     operadoresAnt[indice]=String.valueOf(cadena.charAt(i));
                     campo="";
                     indice++;
            }
            i++;
        }while (cadena.charAt(i)!='-');
        antecedente[indice]=campo;
        campo="";
        indice=0;
        
        do{
            i++;
            if(cadena.charAt(i)!='&')
                if(cadena.charAt(i)!='|' ){
                     campo+=cadena.charAt(i);
                }else{
                     consecuente[indice]=campo;
                     operadoresCons[indice]=String.valueOf(cadena.charAt(i));
                     campo="";
                     indice++;
                }
            else{
                     consecuente[indice]=campo;
                     operadoresCons[indice]=String.valueOf(cadena.charAt(i));
                     campo="";
                     indice++;
            }
        }while (i<cadena.length()-1);
         consecuente[indice]=campo;
    }
    
    void modificar(int llave, String campo, String nuevoCampo) throws IOException{
        
        maestro=manager.Abrir(URLArchivoMaestro);
        indice=manager.Abrir(URLArchivoIndice);
        int Ant_Con=0;
        int posMaestro=0;
        int tamaño=0;
        boolean buscar=false;
        indice.seek(0);
        do {            
            if (indice.readInt()==llave) {
                posMaestro=indice.readInt();
                buscar=true;
            }
            indice.seek(indice.getFilePointer()+4);
            
        } while (indice.getFilePointer()<indice.length() || buscar!=true);
      
        
        if (buscar) {
            switch (campo.charAt(0)) {
                case 'A': Ant_Con=0;
                    break;
                case 'C': Ant_Con=tamanoAntecedente;
                    break;
                case 'O': switch (campo.charAt(1)) {
                        case 'A': Ant_Con=0;
                            break;
                        case 'C': Ant_Con=tamañoConsecuente;
                            break;
                        default:
                            System.out.println("Campo invalido");
                    }
                    break;
                default:
                    System.out.println("Campo invalido");
            }
         
            int posicion=0;
            int posCampo=Integer.parseInt(String.valueOf(campo.charAt(2)));
           // System.out.println("caxmp  "+posCampo);
            switch (campo.charAt(0)) {
                case 'A': 
                case 'C': posicion=((posCampo-1)*(tamañoAnt_Con*2))+((posCampo-1)*2)+Ant_Con;
                          tamaño=tamañoAnt_Con;
                    break;
                case 'O': posicion=(posCampo*(tamañoAnt_Con*2))+((posCampo-1)*2)+Ant_Con;
                          tamaño=1;
                    break;
                default:
                    throw new AssertionError();
            }
            posMaestro+=posicion+4;
            maestro.seek(posMaestro);
            manager.m_EscribirString(nuevoCampo, tamaño, maestro);   
        }else{
            System.out.println("La llave no existe");
        }
        
        maestro.close();
        indice.close();
    }
    
    void eliminar(int llave) throws IOException{
        indice=manager.Abrir(URLArchivoIndice);
        indice.seek(0);
        do {            
            if (indice.readInt()==llave) {
                indice.seek(indice.getFilePointer()-4);
                int valor=llave*(-1);
                indice.writeInt(valor);
            }
            indice.readInt();
        } while (indice.getFilePointer()<indice.length());
        indice.close();
    }
    
    
    
    void mostrarMaestro(){
        
    }
    
    void mostrarIndice(){
        
    }
}
