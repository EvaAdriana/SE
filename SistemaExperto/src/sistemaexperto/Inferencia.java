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
    int tamañoAnt_Con=40;
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
        equiparacion();
        BaseHechos.close();
    }
     
    public void equiparacion () throws IOException{
     preOrden(tree.raiz);
        if(conjuntoConflicto.isEmpty()){
           System.out.println("CC vacio");
       }else{
        
        while(!conjuntoConflicto.isEmpty()){
            System.out.println(conjuntoConflicto.poll());
        }
       }
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
        System.out.println("Equiparando Regla: "+raiz.llave+","+raiz.pos);
        try {
            BaseHechos.seek(0);
            while(BaseHechos.getFilePointer()<BaseHechos.length()){
                
                       String fact=manager.formar_string(BaseHechos, 20);
                       System.out.println("Hecho Eq= "+fact);
                       for(int i=0;i<numAnt;i++){
                           if(i==0){
                              BaseConocimientos.seek(raiz.pos+tamañoLave+((tamañoAnt_Con*5)+8)+(i*tamañoAnt_Con));
                           }else{
                               BaseConocimientos.seek(raiz.pos+tamañoLave+((tamañoAnt_Con*5)+8)+(i*tamañoAnt_Con)+2);
                           }
                           //System.out.println("apuntador BC: "+BaseConocimientos.getFilePointer());
                           String ant=manager.formar_string(BaseConocimientos, 20);
                           
                            if(ant.length()!=0){
                                System.out.println("Antecedente Eq= "+ant+","+ant.length());
                                if(fact.equalsIgnoreCase(ant)){
                                      flag=true;
                                     //System.out.println(flag);
                                  
                                 }else{
                                    //System.out.println(flag);
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
