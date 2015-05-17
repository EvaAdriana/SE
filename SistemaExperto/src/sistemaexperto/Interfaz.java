
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
    JLabel EtiquetaVacia[]=new JLabel[2];
    JRadioButton MayorMenor[];
    JPanel PanelDatos=new JPanel();
    JPanel PanelBoton = new JPanel();
    JRadioButton sexo[]= new JRadioButton[2];
    ButtonGroup GrupoDatos[];
    ButtonGroup GrupoSexo=new ButtonGroup();
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
        Ventana.setSize(700, 720);
        Ventana.setVisible(true);
                
    }
    
    public void CrearPanel(){
        CrearEtiquetas();
        CrearRadioButton();
        int j=0;
        PanelDatos.setLayout(new GridLayout (23,2,10,10));
        for (int i = 0; i < Etiquetas.length; i++) {
            PanelDatos.add(Etiquetas[i]);
            if(i<2){
                 PanelDatos.add(sexo[i]);
                 PanelDatos.add(EtiquetaVacia[i]);
            }else{
                 PanelDatos.add(MayorMenor[j]);
                 if(j<MayorMenor.length-2)
                 PanelDatos.add(MayorMenor[j+1]);
                 j=j+2;
            }
        }//Fin ciclo For
        inferir.addActionListener (new hecho());
        inferir.setVisible(true);
        PanelBoton.add(inferir);
    }
    
    public void CrearEtiquetas(){
        Etiqueta = new String[]
        {"Hombre","Mujer","Hematíes","Hemoglobina","Valor hematócrito",
            "Leucocitos","Basófilos","Eosinófolos","Neutrófilos","Linfocitos",
            "Monocitos","Plaquetas","Urea","Ácido úrico","Glucosa","Colesterol","Triglicéridos",
            "Albúmina","Bilirrubina","Transaminasas"};
        EtiquetaVacia[0]=new JLabel("");
        EtiquetaVacia[1]=new JLabel("");
        Etiquetas=new JLabel [Etiqueta.length];
        for (int i = 0; i < Etiqueta.length; i++) {
            Etiquetas[i]=new JLabel(Etiqueta[i]);
        }
        
    }//Fin metodo CrearEtiquetas()
    
    public void CrearRadioButton(){
        String Valores[]=new String[]
        {"","",
            "","",
            "","",
            "Menor a 5 mil","Mayor a 10 mil",
            "Menor a 0%","Mayor a 1%",
            "Menor a 1%","Mayor a 3%",
            "Menor a 40%","Mayor a 60%",
            "Menor a 20%","Mayor a 40%",
            "Menor a 4%","Mayor a 8%",
            "Menor a 150 mil","Mayor a 300 mil",
            "Menor a 15 mg/cc","Mayor a 40 mg/cc",
            "Menor a 2 mg/cc","Mayor a 7 mg/cc",
            "Menor a 80 mg/cc","Mayor a 120 mg/cc",
            "Menor a 140 mg/cc","Mayor a 250 mg/cc",
            "Menor a 74 mg/cc","Mayor a 150 mg/cc",
            "Menor a 3'5 g/cc","Mayor a 5'5 g/cc",
            "Menor a 0'3 mg/cc","Mayor a 1 mg/cc",
            "Mayor a 35 u/l",};
        MayorMenor=new JRadioButton[Valores.length];
        GrupoDatos=new ButtonGroup[(Valores.length-1)/2];
        for (int i = 0; i < GrupoDatos.length; i++) {
            GrupoDatos[i]=new ButtonGroup();
        }
        int j=0, cont=1;
         for (int i = 0; i < MayorMenor.length; i++) {
            MayorMenor[i]=new JRadioButton(Valores[i]);
            if (j<GrupoDatos.length) {
            GrupoDatos[j].add(MayorMenor[i]);
            cont++;
            if(cont==3){
                j++;
                cont=1;
            }
            }
        }
        for (int i = 0; i < 2; i++) {
            sexo[i]=new JRadioButton();
            sexo[i].addActionListener(new datos());
              GrupoSexo.add(sexo[i]);
        }
    
     
    }//Fin metodo CrearSpinners()
    
    public void mostrarHombre(){
        String DatosHombre[]=new String[]
        {"Menor a 4.7 millones","Mayor a 5.3 millones",
       "Menor a 14 g/cc", "Mayor a 18 g/cc",
        "Menor a 38% de celulas","Mayor a 54% de celulas",};
        cambiarDatos(DatosHombre);
        }
    
    public void cambiarDatos(String valores[]){
        for (int j = 0; j <6; j++) {
            MayorMenor[j].setText(valores[j]);
            }//Fin ciclo For
    }
    
     public void mostrarMujer(){
          String DatosMujer[]=new String[]
        {"Menor a 4.2 millones","Mayor a 4.8 millones",
       "Menor a 12 g/cc", "Mayor a 16 g/cc",
        "Menor a 34% de celulas","Mayor a 47% de celulas",};
          cambiarDatos(DatosMujer);
        }
     
    
    public class hecho implements ActionListener
{ 
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Hechos datos=  new Hechos();
                
                if(sexo[0].isSelected())
                { datos.Escribir(Etiqueta[0]);}
                if(sexo[1].isSelected())
                { datos.Escribir(Etiqueta[1]);}
                for (int i = 2, j=0; i < Etiqueta.length; i++) {
                    if(MayorMenor[j].isSelected()){
                         datos.Escribir(Etiqueta[i]);
                         datos.Escribir(MayorMenor[j].getText());
                         j=j+2;
                     }else{
                        j++;
                        if(MayorMenor[j].isSelected()){
                             datos.Escribir(Etiqueta[i]);
                             datos.Escribir(MayorMenor[j].getText());
                         }
                        j++;
                     }
                } //fin del cliclo for
              //  datos.Escribir("b");
              //  datos.Escribir("c");
           
            //    Inferencia doInferencia=new Inferencia();
               
               // datos.eliminarArchivo();
                 
               
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (Exception ex) {
            }
        }
    }
    
    public class datos implements ActionListener
{ 
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (sexo[0].isSelected())
                    mostrarHombre();
                if(sexo[1].isSelected())
                    mostrarMujer();
                 
               
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (Exception ex) {
            }
        }
    }

    
}
