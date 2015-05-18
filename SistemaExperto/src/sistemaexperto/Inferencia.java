package sistemaexperto;

import contenedor.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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

    public Queue<Integer> conjuntoConflicto = new LinkedList<Integer>();
    public ArrayList <Integer> reglasVisitadas= new ArrayList<Integer>();
    public FileManager manager = new FileManager();
    public RandomAccessFile BaseConocimientos, indice, BaseHechos;
    int tamañoAnt_Con = 40;
    int tamañoLave = 4;
    int numAnt = 5;
    String Urlmaestro = "archivos/maestro";
    String Urlindice = "archivos/indice";
    String UrlBaseHechos = "archivos/hechos";

    public Arbol tree = new Arbol();

    public Inferencia() throws IOException {
        try {
            indice = manager.Abrir(Urlindice);
            BaseHechos = manager.Abrir(UrlBaseHechos);
            BaseConocimientos = manager.Abrir(Urlmaestro);
        } catch (IOException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        llenarArbol();
        equiparacion();
        BaseHechos.close();
    }

    public void equiparacion() throws IOException {
        preOrden(tree.raiz);
        if (conjuntoConflicto.isEmpty()) {
            System.out.println("CC vacio");
        } else {
            while (!conjuntoConflicto.isEmpty()) {
             System.out.println(conjuntoConflicto.poll());
    }
        }
    }

    public boolean resolucion(Queue <Integer> cc) {
        boolean alcanzado = false;
        if(!cc.isEmpty()){
           
            try {
                BaseConocimientos.seek(cc.peek());
                reglasVisitadas.add(cc.peek());
                String nuevoHecho=manager.formar_string(BaseConocimientos, tamañoAnt_Con);
                BaseHechos.seek(0);
                while(BaseHechos.getFilePointer()<BaseHechos.length()){
                    if(nuevoHecho.equalsIgnoreCase(manager.formar_string(BaseHechos, 20))){
                        alcanzado=true;
                    }else{
                        alcanzado=false;
                    }
                }
                if(!alcanzado){
                    BaseHechos.seek(BaseHechos.length());
                    manager.m_EscribirString(nuevoHecho, numAnt, indice);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        return alcanzado;
    }

    public void encadenamientoProgresivo() {
       
      
   
    }

    public void llenarArbol() {
        try {
            while (indice.getFilePointer() < indice.length()) {
                System.out.println("llenando arbol");
                tree.insertart(indice.readInt(), indice.readInt());
            }
        } catch (IOException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preOrden(Nodo raiz) {
        Nodo l, d;
        
        if (raiz != null) {
            procesar(raiz);
            l = raiz.izq;
            preOrden(l);
            d = raiz.der;
            preOrden(d);
        }
    }

    public void procesar(Nodo raiz) {
        boolean flag = false;
        System.out.println("Equiparando Regla: " + raiz.llave + "," + raiz.pos);
        try {
            for (int i = 0; i < 5; i++) {
                
                BaseConocimientos.seek(raiz.pos + 4 + ((40)*5) + (40*i));
                String ant = manager.formar_string(BaseConocimientos, 20);
                BaseHechos.seek(0);//coloca el apuntador en el inicio de la base de hechos
                                   //Para cada antecedente debe leerse toda la base de hechos
                while (BaseHechos.getFilePointer() < BaseHechos.length()) {
                    String fact = manager.formar_string(BaseHechos, 20);
                    if (ant.length() != 0) {
                        System.out.println("Hecho: "+fact+" Ant: "+ant);
                        if (fact.equalsIgnoreCase(ant)) {
                            flag = true;
                            break;
                            //System.out.println(flag);
                        } else {
                            //System.out.println(flag);
                            flag = false;
                        }
                    }
                }
                if(!flag){
                   break;
                }
            }if(flag){
                if(!reglasVisitadas.contains(raiz.pos)){
                    conjuntoConflicto.add(raiz.llave);
                }     
             }
        } catch (IOException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
