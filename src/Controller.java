import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import java.util.Vector;

/**
 * Created by Abuelo on 8/27/2015.
 */
public class Controller implements ActionListener {


    View miView;
    ViewVentana miViewVentana;

    Model miModelo;
    File prueba;

    public Controller(Model elModelo , View esteEsElView , ViewVentana esteesmiViewVentana) {


         miView = esteEsElView;
         miModelo = elModelo;
         miViewVentana = esteesmiViewVentana;


        prueba = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\Motor De Busqueda\\lisaprueba.txt");

    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {


        JButton accion;

        accion =(JButton)  e.getSource();

        if(accion == miView.cerrar )
        {
            System.out.println("Cerrar");//////////////////////////////////
            miView.dispose();
            miViewVentana.dispose();
        }//end if

        if(accion == miView.agregarDocumentos ) {

            crawler("http://arstechnica.com/");
            crawler("http://acrasystems.com.mx/");
           /* try {


                /////////////////////////////////////////cargar all
                //todoLosDocumentos();
                //////////////////////////////////////////documento prueba


                cargarDocumentos(prueba);
                miModelo.palabraporDocumentos(prueba);


            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
*/
        }

        if(accion == miView.separarDocumentos )
            segundaConsultaPreparar();
            //segundaConsultaPrepararBien();
            //documentosBaseDeDatos();
           // terminoBaseDeDatos();

        if(accion == miView.obtenerIDF ) {
            try {
                obtenerIDF();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }//end if obtener idf
        if(accion == miView.realizarConsulta ) {


           // procesarConsulta();

          //  miModelo.obtenerSim_Doc();
          //  miModelo.mayorSimilitud();
            miModelo.construirCluster();

        }


        if(accion == miView.obtnerPesoTermino )
            pesodeTerminos();


    }//end actionPerformed

    //----------------------------------------------------------------------
    public void cargarDocumentos(File documentos) throws FileNotFoundException {
        System.out.println("Cargar Documentos");

        //cargarArchivos leer guardar los objetos en un vector






        miModelo.leerDocumentos(documentos);
        miModelo.leerTerminos(documentos);
        miModelo.leerTerminosEnDocumentos(documentos);

        miModelo.totalArchivos();
        miModelo.calcularIDF();

       // System.out.println("########################################################");
        miModelo.resultados();

        try {
            miModelo.enviarDocumentosBaseDeDatos();
            miModelo.enviarTerminoBaseDeDatos();

            System.out.println("CARGA EXITOSA ");
        } catch (SQLException e) {

            System.out.println("ALGO SALIO MAL");
            e.printStackTrace();
        }

        //guardar las nuevas tuplas en una base de datos



        //volver a leer los archivos

        //guardar las palabras base de datos





    }//cargarDocumentos
    //----------------------------------------------------------------------
    public void separarDocumentos(){

        System.out.println("separarDocumentos");
    }//separarDocumentos
    //----------------------------------------------------------------------
    public void obtenerIDF() throws SQLException {
        System.out.println("obtenerIDF");

        String buscar;
        double idfencontrado = 0;



        buscar = miViewVentana.busqueda.getText();
        buscar = buscar.toUpperCase();
        System.out.println("QUIERO BUSCAR:"+buscar);

        idfencontrado =  miModelo.obtenerIDFBaseDeDatos(buscar);



        actualizarResultado(buscar,idfencontrado);



    }//obtenerIDF
    //----------------------------------------------------------------------
    public void realizarBusqueda(){
        System.out.println("realizarBusqueda");
    }//realizarBusqueda
    //----------------------------------------------------------------------
    public void pesodeTerminos(){
        System.out.println("pesodeTerminos");
    }//pesodeTerminos
    //----------------------------------------------------------------------

    public void actualizarResultado(String palabra , double resultado){


        if (resultado != -99)
        miViewVentana.palabra.setText(palabra+":"+resultado);
        if (resultado == -99)
            miViewVentana.palabra.setText(palabra + ":NO EXISTE");

        miViewVentana.palabra.setVisible(true);

    }//end actualizarResultado

    public  void todoLosDocumentos() throws FileNotFoundException {

        File documento1 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA1.txt");
        File documento2 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA2.txt");
        File documento3 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA3.txt");
        File documento4 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA4.txt");
        File documento5 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA5.txt");
        File documento6 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA6.txt");
        File documento7 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA7.txt");
        File documento8 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA8.txt");
        File documento9 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA9.txt");
        File documento10 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA10.txt");
        File documento11 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA11.txt");
        File documento12 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA12.txt");
        File documento13 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA13.txt");
        File documento14 = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\lisa\\LISA14.txt");


        System.out.println("-----------------------------------------------------------documentos");

        /*

        cargarDocumentosCompleto(documento1);
        cargarTerminosCompleto(documento1);
        cargarTerminosenDocumentosCompleto(documento1);
        totalArchivos();
        calcularIDFS();


        //terminoBaseDeDatos();
        //documentosBaseDeDatos();


        miModelo.palabraporDocumentos(documento1);
      //  terminoBaseDeDatos();
        */


        //////////////////////////////////////////////////////////pruba completa

        cargarDocumentosCompleto(documento1);
        cargarDocumentosCompleto(documento2);
        cargarDocumentosCompleto(documento3);
        cargarDocumentosCompleto(documento4);
        cargarDocumentosCompleto(documento5);
        cargarDocumentosCompleto(documento6);
        cargarDocumentosCompleto(documento7);
        cargarDocumentosCompleto(documento8);
        cargarDocumentosCompleto(documento9);
        cargarDocumentosCompleto(documento10);
        cargarDocumentosCompleto(documento11);
        cargarDocumentosCompleto(documento12);
        cargarDocumentosCompleto(documento13);
        cargarDocumentosCompleto(documento14);


        System.out.println("-----------------------------------------------------------terminos");

        cargarTerminosCompleto(documento1);
        cargarTerminosCompleto(documento2);
        cargarTerminosCompleto(documento3);
        cargarTerminosCompleto(documento4);
        cargarTerminosCompleto(documento5);
        cargarTerminosCompleto(documento6);
        cargarTerminosCompleto(documento7);
        cargarTerminosCompleto(documento8);
        cargarTerminosCompleto(documento9);
        cargarTerminosCompleto(documento10);
        cargarTerminosCompleto(documento11);
        cargarTerminosCompleto(documento12);
        cargarTerminosCompleto(documento13);
        cargarTerminosCompleto(documento14);


        System.out.println("-----------------------------------------------------------numero de veces en documeto");

        cargarTerminosenDocumentosCompleto(documento1);
        cargarTerminosenDocumentosCompleto(documento2);
        cargarTerminosenDocumentosCompleto(documento3);
        cargarTerminosenDocumentosCompleto(documento4);
        cargarTerminosenDocumentosCompleto(documento5);
        cargarTerminosenDocumentosCompleto(documento6);
        cargarTerminosenDocumentosCompleto(documento7);
        cargarTerminosenDocumentosCompleto(documento8);
        cargarTerminosenDocumentosCompleto(documento9);
        cargarTerminosenDocumentosCompleto(documento10);
        cargarTerminosenDocumentosCompleto(documento11);
        cargarTerminosenDocumentosCompleto(documento12);
        cargarTerminosenDocumentosCompleto(documento13);
        cargarTerminosenDocumentosCompleto(documento14);

        System.out.println("-----------------------------------------------------------numero de doc en los que aparece");


        totalArchivos();

        System.out.println("-----------------------------------------------------------IDF");

        calcularIDFS();

        System.out.println("-----------------------------------------------------------BASEDATOSdocumentos");


        documentosBaseDeDatos();


        System.out.println("-----------------------------------------------------------Terminos");


        terminoBaseDeDatos();

        System.out.println("-----------------------------------------------------------TF");

        miModelo.palabraporDocumentos(documento1);
        miModelo.palabraporDocumentos(documento2);
        miModelo.palabraporDocumentos(documento3);
        miModelo.palabraporDocumentos(documento4);
        miModelo.palabraporDocumentos(documento5);
        miModelo.palabraporDocumentos(documento6);
        miModelo.palabraporDocumentos(documento7);
        miModelo.palabraporDocumentos(documento8);
        miModelo.palabraporDocumentos(documento9);
        miModelo.palabraporDocumentos(documento10);
        miModelo.palabraporDocumentos(documento11);
        miModelo.palabraporDocumentos(documento12);
        miModelo.palabraporDocumentos(documento13);
        miModelo.palabraporDocumentos(documento13);





    }//end todosLosDocumentos

    public void cargarDocumentosCompleto(File documento) throws FileNotFoundException {

        miModelo.leerDocumentos(documento);

    }// end cargarDocumentos


    //carga los terminos
    public void cargarTerminosCompleto(File documento) throws FileNotFoundException {

        miModelo.leerTerminos(documento);




    } // end cargarTerminos

    //calcual en cuantos documetnos parace un mismo termino
    public void cargarTerminosenDocumentosCompleto(File documento) throws FileNotFoundException {

        miModelo.leerTerminosEnDocumentos(documento);


    }// end cargarTerminosDocumentos

    //
    public void totalArchivos(){

        miModelo.totalArchivos();

    }//end  totalArchivos

    public void calcularIDFS(){


        miModelo.calcularIDF();

    }//end calcularIDFS


    public void terminoBaseDeDatos(){

        try {
            miModelo.enviarTerminoBaseDeDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//end terminoBaseDeDatos

    public void documentosBaseDeDatos(){


        try {
            miModelo.enviarDocumentosBaseDeDatos();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//end documentosBaseDeDatos

    public void procesarConsulta(){

        System.out.println("procesarConsulta");

        int id;
        String consulta;
        String idtexto;

        idtexto = miViewVentana.id.getText();

        if(idtexto.length()>0) {

            miViewVentana.cuadrados.setText("");
            miViewVentana.punto.setText("");

            miViewVentana.nohayId(2);

            consulta = miViewVentana.busqueda.getText();
            consulta = consulta.toUpperCase();


            id = Integer.parseInt(idtexto);


           // System.out.println("Consulta:" + consulta + ", ID:" + id );
            miModelo.procesarConsulta(consulta , miModelo.idConsultaMayor()+1);
            try {
                miModelo.obtenerDocumentosRelevantes(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }




            miModelo.obtenerDocumentosRelevantesCocenos(id);





        }

        else

            miViewVentana.nohayId(1);


       // System.out.println(miModelo.resultadoCOnsultasCocenos());




        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");
        miViewVentana.cuadrados.setText(miModelo.resultadoCOnsultasCocenos());
        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");

        miViewVentana.punto.setText(miModelo.resultadoCOnsultas());

    }// end procesarConsulta

    public void segundaConsultaPreparar(){


        //id
        String idtexto = miViewVentana.id.getText();
        int id = Integer.parseInt(idtexto + 1);


        //primer consulta

        procesarConsulta();

        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");

        //pasos para preparar la segunda consulta

        int arreglo[] = new int[1];

        //introdusco los documentos relevantes
        String entrada = JOptionPane.showInputDialog(null, "Documento Relevante");
        int tam = Integer.parseInt(entrada);



        arreglo [ 0] = tam ;

        //obtengo las palabras importantes

        String consultaPalabras = miModelo.segundaConsulta(arreglo);

        String consulta = miViewVentana.busqueda.getText();
        consulta = consulta.toUpperCase();

        String consultaNueva = consulta + consultaPalabras;



        miModelo.procesarConsulta(consultaNueva, miModelo.idConsultaMayor()+1);



        //seleccionar el tipo de consulta

        //el vector de resultados es el que se genera como el resultado de la consulta
        //solo seleccionamos cual utilizamos si el de concenos o el de vectorial




        String[] opciones = { "Vectorial", "Cocenos"  };

        JComboBox elCombo = new JComboBox(opciones);

        JOptionPane.showMessageDialog( null, elCombo, "Seleccione el tipo de la segunda bisqueda", JOptionPane.QUESTION_MESSAGE);


        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");



        if( elCombo.getSelectedIndex()==0)
        {
        //vectorial

            try {
                miModelo.obtenerDocumentosRelevantes(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //imprimo en la nueva ventana

            miViewVentana.segunda.setText(miModelo.resultadoCOnsultas());



        }//end if vectorial
        else if (elCombo.getSelectedIndex()==1)
        {
        //cocenos

            miModelo.obtenerDocumentosRelevantesCocenos(id);

            //imprimo en la nueva venta
            miViewVentana.segunda.setText(miModelo.resultadoCOnsultasCocenos());

        }//end if cocenos





    }//end segunda consulta

    public void segundaConsultaPrepararBien(){

        //guardo el id de la consulta

        String idtexto = miViewVentana.id.getText();
        int id = Integer.parseInt(idtexto  ) + 1;

        //realizo la primer consulta

        procesarConsulta();

        //recupero las palabras que incrementaron su idf

        String nuevo = miModelo.recuperarPalabrasBien(id-1);


        //guardo en una variable la consulta original para despues unirla

        String consulta = miViewVentana.busqueda.getText();
        consulta = consulta.toUpperCase();

        //concateno la consulta vieja y las nuevas palabras

        System.out.println(nuevo);

        String consultaNueva =  nuevo;

        //va de nuevo

        //limpio vectores
        miModelo.limpiarVectores();

        miModelo.procesarConsulta(consultaNueva , miModelo.idConsultaMayor()+1);

        //que tipo

        String[] opciones = { "Vectorial", "Cocenos"  };

        JComboBox elCombo = new JComboBox(opciones);

        JOptionPane.showMessageDialog( null, elCombo, "Seleccione el tipo de la segunda bisqueda", JOptionPane.QUESTION_MESSAGE);


        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");



        if( elCombo.getSelectedIndex()==0)
        {
            //vectorial

            try {
                miModelo.obtenerDocumentosRelevantes(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //imprimo en la nueva ventana

            miViewVentana.segunda.setText(miModelo.resultadoCOnsultas());



        }//end if vectorial
        else if (elCombo.getSelectedIndex()==1)
        {
            //cocenos

            miModelo.obtenerDocumentosRelevantesCocenos(id);

            //imprimo en la nueva venta
            miViewVentana.segunda.setText(miModelo.resultadoCOnsultasCocenos());

        }//end if cocenos








    }//

    public void lematizacion(){}//end lematizacion



    public void crawler(String URL){



        SpiderLeg spider = new SpiderLeg();
        spider.crawl(URL);
        spider.printList();






    }//end crawler



}//end class


/*

 try {
            comando = conn.createStatement();



        } catch (SQLException e) {
            e.printStackTrace();
        }
 */
