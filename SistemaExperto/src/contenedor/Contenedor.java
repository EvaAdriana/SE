
package contenedor;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author nelther
 */
public class Contenedor {

   Scanner A_leer=new Scanner(System.in);
   Maestro master = new Maestro();
   static Arbol tree = new Arbol();
   static Indice indice = new Indice();
   
    public static void main(String[] args) throws IOException {
       
        //MENU 
        int opc=0;
        int k,o;
        Contenedor c = new Contenedor();
        do{
            System.out.println("<<ARCHIVOS>>");
            System.out.println("1.-Insertar");
            System.out.println("2.-Eliminar");
            System.out.println("3.-Modificar");
            System.out.println("<<ARBOL>>");
            System.out.println("4.-Crear Árbol");
            System.out.println("5.-Buscar Anchura");
            System.out.println("6.-Buscar Profundidad");
            System.out.println("7.-Preorden");
            System.out.println("8.-Entreoden");
            System.out.println("9.-Postorden");
            opc=c.A_leer.nextInt();
            switch(opc){
                case 1: System.out.println("Llave de la regla");
                        k = c.A_leer.nextInt();
                        System.out.println("Definir Regla:");
                        c.master.insertar(k,c.A_leer.next());
                        break;
                case 2: System.out.println("Llave de la regla a eliminar");
                        k = c.A_leer.nextInt();
                        c.master.eliminar(k);
                        break;
                case 3:System.out.println("Llave de la regla a modificar");
                       k = c.A_leer.nextInt();
                       System.out.println("Clave del campo");
                       String campo=c.A_leer.next();
                       System.out.println("Nueva informacion");
                       String campoNuevo=c.A_leer.next();
                       c.master.modificar(k, campo, campoNuevo);
                       break;
                case 5: System.out.println("Llave a buscar en el arbol: ");     
                        k=c.A_leer.nextInt();
                        c.indice.arbolito.busquedaAnchura(k);
                         break;
                case 4: c.indice.leerIndice();
                        break;      
                case 6: System.out.println("Llave a buscar en el arbol: ");     
                        k=c.A_leer.nextInt();
                        c.indice.arbolito.buscarProfundidad(k);
                        break;
                case 7: 
                        c.indice.arbolito.preOrden(indice.arbolito.raiz);
                    break;
                case 8: 
                        c.indice.arbolito.entreOrden(indice.arbolito.raiz);
                    break;
                case 9: 
                        c.indice.arbolito.postOrden(indice.arbolito.raiz);
                    break;
                 
            }
        }while(opc!=0);
        
    }
    
}
