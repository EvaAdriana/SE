
package sistemaexperto;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Interfaz {
    
    JFrame Ventana=new JFrame("SISTEMA EXPERTO");
    JLabel Etiquetas[];
    String Etiqueta[];
    JLabel EtiquetaVacia=new JLabel("");
    JSpinner Spinners[];
    JPanel PanelDatos=new JPanel();
    JPanel PanelBoton = new JPanel();
    JRadioButton sexo[]= new JRadioButton[2];
    ButtonGroup grupo=new ButtonGroup();
    JButton inferir=new JButton("Inferir");
  //  JScrollBar barra=new JScrollBar(Scrollbar.VERTICAL);
        public Interfaz (){
            CrearVentana();
        }
    
    public void CrearVentana(){
        CrearPanel();
        Ventana.setLayout( new FlowLayout() );  
        Ventana.add(PanelDatos);
        Ventana.add(PanelBoton);
    //    Ventana.add(barra);
        Ventana.pack();
        Ventana.setVisible(true);
                
    }
    
    public void CrearPanel(){
        CrearEtiquetas();
        CrearSpinners();
      
        PanelDatos.setLayout(new GridLayout (23,1,10,10));
        for (int i = 0; i < Etiqueta.length; i++) {
            PanelDatos.add(Etiquetas[i]);
            if(i<2){
                PanelDatos.add(sexo[i]);
            }else{
                if(i==12){
                    PanelDatos.add(EtiquetaVacia);
                }else{
                 PanelDatos.add(Spinners[i]);
                }
            }
        }//Fin ciclo For
        inferir.addActionListener (new hecho());
        PanelBoton.add(inferir);
    }
    
    public void CrearEtiquetas(){
        Etiqueta = new String[]
        {"Hombre","Mujer","Hematíes","Hemoglobina","Valor hematócrito",
            "Leucocitos","Basófilos","Eosinófolos","Neutrófilos","Linfocitos",
            "Monocitos","Plaquetas","Velocidad de sedimentación","1ra Hora",
            "2da Hora","Urea","Ácido úrico","Glucosa","Colesterol","Triglicéridos",
            "Albúmina","Bilirrubina","Transaminasas"};
        
        Etiquetas=new JLabel [Etiqueta.length];
        for (int i = 0; i < Etiqueta.length; i++) {
            Etiquetas[i]=new JLabel(Etiqueta[i]);
        }
        
    }//Fin metodo CrearEtiquetas()
    
    public void CrearSpinners(){
        Spinners=new JSpinner[Etiqueta.length];
         for (int i = 0; i < Etiqueta.length; i++) {
            Spinners[i]=new JSpinner();
        }
        for (int i = 0; i < 2; i++) {
            sexo[i]=new JRadioButton();
              grupo.add(sexo[i]);
        }
    
     
    }//Fin metodo CrearSpinners()
    
    
    public class hecho implements ActionListener
{ 
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Hechos datos=  new Hechos();
                datos.eliminarArchivo();
                if(sexo[0].isSelected())
                { datos.Escribir("Sexo",Etiqueta[0]);}
                if(sexo[1].isSelected())
                { datos.Escribir("Sexo",Etiqueta[1]);}
                for (int i = 2; i < Etiqueta.length; i++) {
                     if (i!=12){
                   datos.Escribir(Etiqueta[i], String.valueOf(Spinners[i].getValue()));}
                }
                 
               
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (Exception ex) {
            }
        }
    }

    
}
