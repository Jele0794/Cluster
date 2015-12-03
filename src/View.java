import java.awt.*;
import javax.swing.*;///////////////importa las librerias de la interfaz

import java.awt.event.*;
import java.io.*;

/*
 * Created by Abuelo on 2/9/2015.
 */
public class View extends Frame {


    Component frame;
    Container examenesContainer;

    Dialog miDialogo;
    JLabel usuario;
    JLabel user;

    //////////////////////Botones

    JButton agregarDocumentos , separarDocumentos , obtenerIDF , realizarConsulta , obtnerPesoTermino , cerrar;





    public View() {

        agregarBotones();
        setTitle     ("Motor De Busqueda");
        setSize      (350, 770);
        setResizable (false);
        setLocation  (100, 10);


        setLayout(null);
        setBackground(Color.white);
        setVisible(true);
        //////////////////////////////////////////////////////////
        Font font1 = new Font("Helvetica" , Font.BOLD , 20);
        JLabel user;
        user = new JLabel("Motor de Busqueda:" );
        user.setBounds(50, 50, 200, 30);
        user.setFont(font1);

        add(user);






        //setDefaultCloseOperation(EXIT_ON_CLOSE);


    }//end class view



    //@Override
    public void setActionListener(Controller miController) {

        agregarDocumentos.addActionListener(miController);
        separarDocumentos.addActionListener(miController);
        obtenerIDF.addActionListener(miController);
        realizarConsulta.addActionListener(miController);
        obtnerPesoTermino.addActionListener(miController);
        cerrar.addActionListener(miController);

    }

    public void agregarBotones(){




        user = new JLabel("" );
        user.setBounds(25, 100, 60, 20);

        //add(user);

        JButton boton = new JButton();
        agregarDocumentos = boton;

        agregarDocumentos.setVisible(true);
        agregarDocumentos.setBounds(50, 120, 250, 60);
        agregarDocumentos.setText("Cargar Documentos");
        agregarDocumentos.setBackground(Color.darkGray);
        agregarDocumentos.setForeground(Color.LIGHT_GRAY);
        //agregarEstudiante.addActionListener( this );
        add(agregarDocumentos);

        JButton boton2 = new JButton();
        separarDocumentos = boton2;

        separarDocumentos.setVisible(true);
        separarDocumentos.setBounds(50, 220, 250, 60);
        separarDocumentos.setText("Segunda Consulta");
        separarDocumentos.setBackground(Color.darkGray);
        separarDocumentos.setForeground(Color.LIGHT_GRAY);
        //agregarProfesor.addActionListener( this );
        add(separarDocumentos);

        JButton boton5 = new JButton();
        obtenerIDF = boton5;

        obtenerIDF.setVisible(true);
        obtenerIDF.setBounds(50, 320, 250, 60);
        obtenerIDF.setText("Obtener IDF");
        obtenerIDF.setBackground(Color.darkGray);
        obtenerIDF.setForeground(Color.LIGHT_GRAY);
       // agregarExamen.addActionListener( this );
        add(obtenerIDF);


        JButton boton3 = new JButton();
        realizarConsulta = boton3;

        realizarConsulta.setVisible(true);
        realizarConsulta.setBounds(50, 420, 250, 60);
        realizarConsulta.setText("Realizar Busqueda");
        realizarConsulta.setBackground(Color.darkGray);
        realizarConsulta.setForeground(Color.LIGHT_GRAY);
        //contestarExamen.addActionListener( this );
        add(realizarConsulta);


        JButton boton4 = new JButton();
        obtnerPesoTermino = boton4;

        obtnerPesoTermino.setVisible(true);
        obtnerPesoTermino.setBounds(50, 520, 250, 60);
        obtnerPesoTermino.setText("Obtener Peso de Terminos");
        obtnerPesoTermino.setBackground(Color.darkGray);
        obtnerPesoTermino.setForeground(Color.LIGHT_GRAY);
        //calificarExamen.addActionListener( this );
        add(obtnerPesoTermino);


        JButton boton6 = new JButton();
        cerrar = boton6;

        cerrar.setVisible(true);
        cerrar.setBounds(50, 620, 250, 60);
        cerrar.setText("CERRAR APLICACION");
        cerrar.setBackground(Color.darkGray);
        cerrar.setForeground(Color.LIGHT_GRAY);
        //calificarExamen.addActionListener( this );
        add(cerrar);




    }



}//end class


/*I WOULD BE PLEASED TO RECEIVE ANY INFORMATION ON TELECOMMUNICATIONS
NETWORKS THIS COULD INCLUDE BOTH WIDE AREA NETWORKS MESH AND LOCAL
        AREA NETWORKS BUS RING TELECOMMUNICATIONS, LOCAL AREA NETWORKS
        WIDE AREA NETWORKS DATA TRANSMISSION DISTRIBUTED PROCESSING
        DISTRIBUTED ROUTING PACKET-SWITCHED NETWORKS MESH BUS RING

        */