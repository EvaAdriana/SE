
package contenedor;

import java.util.LinkedList;

/**
 *
 * @author nelther
 */
public class Arbol {
    public Nodo raiz,recorre,anterior;
   
    
    public Arbol(){
        raiz=null;
    }
    
    public void insertart(int llave, int pos){
        if(raiz==null){
            raiz=new Nodo(llave,pos);
        }else{
            recorre=raiz;
            while(true){
                if(recorre.llave>llave){
                    if(recorre.izq!=null){
                        recorre=recorre.izq;
                    }else{
                        break;
                    }
                }else{
                    if(recorre.der!=null){
                        recorre=recorre.der;
                    }else{
                        break;
                    }
                }
            }
            if(recorre.llave>llave){
                recorre.izq=new Nodo(llave,pos);
            }else{
                recorre.der = new Nodo(llave,pos);
            }
        }
        
        
    }
    
//    public void eliminar(int llave){
//        if(raiz!=null){
//            if(raiz.llave==llave){
//                if(raiz.izq==null&&raiz.der==null){
//                    raiz=null;
//                }else{
//                    if(raiz.izq==null){
//                        raiz=raiz.der;
//                    }else{
//                        raiz=raiz.izq;
//                    }
//                }
//            }
//        }
//    }
    
public void buscarProfundidad(int llave){
    int peso=0;
    if(raiz.llave!=llave){
        recorre=raiz;
        while(recorre.llave!=llave&&(recorre.izq!=null||recorre.der!=null)){
            if(recorre.llave>llave&&recorre.izq==null){
                break;
            }else{
                if(recorre.llave>llave&&recorre.izq!=null){
                    peso+=recorre.llave;
                    recorre=recorre.izq;
                }else{
                    if(recorre.llave<llave&&recorre.der==null){
                        break;
                    }else{
                        if(recorre.llave<llave&&recorre.der!=null){
                            peso+=recorre.llave;
                             recorre=recorre.der;
                        }
                    }
                }
            }    
        }
        if(recorre.llave!=llave){
            System.out.println("valor no encontrado");
        }else{
            System.out.println("valor encontrado, recorrido: "+peso);
        }
    }
}

public void busquedaAnchura(int llave){
    int peso=0;
    boolean bandera=false;
    LinkedList<Nodo> cola=new LinkedList();
    cola.add(raiz);
    do{
        if (cola.getFirst().llave!=llave){
            if (cola.getFirst().izq!=null) {
                 cola.add(cola.getFirst().izq);
            }
            if (cola.getFirst().der!=null) {
                 cola.add(cola.getFirst().der);
            }
            cola.poll();
            peso++;
        }
        else{
         System.out.println("LLave encontrada \nPeso: "+peso);
         bandera=true;
         break;
        }
    }while(!cola.isEmpty());
    if (!bandera) {
        System.out.println("Llave no encontrada!");
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
public void entreOrden(Nodo raiz){
    Nodo l,d;
    if(raiz!=null){
       l=raiz.izq;
       entreOrden(l);
       procesar(raiz);
       d=raiz.der;
       entreOrden(d);
   }
}
public void postOrden(Nodo raiz){
    Nodo l,d;
    if(raiz!=null){
       l=raiz.izq;
       postOrden(l);
       
       d=raiz.der;
       postOrden(d);
       procesar(raiz);
   }
}
public int procesar(Nodo raiz){
    return raiz.llave;
}
}
