/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexperto;

import contenedor.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nelther
 */
public class Inferencia {
    public Queue <Integer> conjuntoConflicto = new LinkedList <Integer>();
    public FileManager manager = new FileManager();
    public RandomAccessFile BaseConocimientos,indice,BaseHechos;
    int tamañoAnt_Con=20;
    int tamañoLave=4;
    int numAnt=5;
    String Urlmaestro="archivos/maestro";
    String Urlindice="archivos/indice";
    String UrlBaseHechos="archivos/hechos";
    
    public Arbol tree = new Arbol();
    
    public Inferencia() throws IOException{
      try {
        indice=manager.Abrir(Urlindice);
        BaseHechos=manager.Abrir(UrlBaseHechos);
        BaseConocimientos=manager.Abrir(Urlmaestro);
      } catch (IOException ex) {
        Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
      }
        llenarArbol();
    }
     
    public void equiparacion () throws IOException{
        
    }
    
    public void resolucion(){
        
    }
    
    public void encadenamientoProgresivo(){
   
    }
    public void llenarArbol(){
        try { 
            while(indice.getFilePointer()<indice.length()){
                System.out.println("llenando arbol");
                tree.insertart(indice.readInt(), indice.readInt());
            }   
        } catch (IOException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void preOrden(Nodo raiz){
        Nodo l,d;
        if(raiz!=null){
           procesar(raiz);
           l=raiz.izq;
           preOrden(l);
           d=raiz.der;
           preOrden(d);
       }
}
    
    public void procesar(Nodo raiz){
        boolean flag=false;
        try {
            BaseHechos.seek(0);
            while(BaseHechos.getFilePointer()<BaseHechos.length()){
                
                       String fact=manager.formar_string(BaseHechos, tamañoAnt_Con);
                        
                       for(int i=0;i<numAnt;i++){
                           BaseConocimientos.seek(raiz.pos+tamañoLave+tamañoAnt_Con+(i*tamañoAnt_Con));
                           String ant=manager.formar_string(BaseConocimientos, 20);
                            if(ant!=""){
                                if(fact.equalsIgnoreCase(ant)){
                                      flag=true;
                                      break;
                                 }else{
                                      flag=false;
                                 }   
                            }
                       }
                       if(flag){
                           conjuntoConflicto.add(raiz.llave);
                       }
                                     
            }
        } catch (IOException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
