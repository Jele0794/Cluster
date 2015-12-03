import java.awt.*;
import javax.swing.*;///////////////importa las librerias de la interfaz

import java.awt.event.*;
import java.io.*;

/*
 * Created by Abuelo on 2/9/2015.
 */
public class ViewVentana extends Frame {


    Component frame;
    Container ventanaContainer;

    Dialog miDialogo;
    JLabel usuario;

    JTextField busqueda , id ;

    JTextArea punto , cuadrados , segunda;

    JEditorPane palabra  ;

    JLabel noID;


    //////////////////////Botones






    public ViewVentana() {


        setTitle     ("Motor De Busqueda");
        setSize      (1250, 770);
        setResizable (false);
        setLocation  (430, 10);


        setLayout(null);
        setBackground(Color.darkGray);
        setVisible(false);
        //////////////////////////////////////////////////////////

        Font font1 = new Font("Helvetica" , Font.BOLD , 20);
        Font font2 = new Font("Helvetica" , Font.BOLD , 15);
        Font font3 = new Font("Helvetica" , Font.BOLD , 13);

        JLabel user;
        user = new JLabel("Buscar:" );
        user.setBounds(50, 100, 100, 50);
        user.setForeground(Color.LIGHT_GRAY);
        user.setFont(font2);

        add(user);


        JLabel idf;
        idf = new JLabel("IDF:" );
        idf.setBounds(50, 200, 100, 50);
        idf.setForeground(Color.LIGHT_GRAY);
        idf.setFont(font2);

        add(idf);


        JLabel iduser;
        iduser = new JLabel("ID:" );
        iduser.setBounds(650, 100, 50, 50);
        iduser.setForeground(Color.LIGHT_GRAY);
        iduser.setFont(font2);

        add(iduser);

        noID = new JLabel("NO HAY ID:" );
        noID.setBounds(130, 50, 100, 50);
        noID.setForeground(Color.LIGHT_GRAY);
        noID.setFont(font2);
        noID.setVisible(false);

        add(noID);

        JLabel tituloPunto;
        tituloPunto = new JLabel("PRODUCTO PUNTO:" );
        tituloPunto.setBounds(50, 250, 150, 50);
        tituloPunto.setForeground(Color.LIGHT_GRAY);
        tituloPunto.setFont(font2);

        add(tituloPunto);


        JLabel tituloCuadrado;
        tituloCuadrado = new JLabel("SUMA DE COSENOS:" );
        tituloCuadrado.setBounds(450, 250, 200, 50);
        tituloCuadrado.setForeground(Color.LIGHT_GRAY);
        tituloCuadrado.setFont(font2);


        add(tituloCuadrado);



        JTextField miTexto = new JTextField("");
        busqueda = miTexto;
        busqueda.setBounds(130, 100, 500, 35);
        busqueda.setVisible(true);
        busqueda.setFont(font1);
        add(busqueda);

        JTextArea miTextopunto = new JTextArea("");
        punto = miTextopunto;
        punto.setBounds(50, 300, 350, 400);
        punto.setVisible(true);
        punto.setFont(font3);

        JScrollPane scrollText= new JScrollPane(punto);
        scrollText.setBounds(50, 300, 350, 400);
        scrollText.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        add(scrollText);//agrega el boton al marco


        add(punto);

        JTextArea miTextocuadrado = new JTextArea("");
        cuadrados = miTextocuadrado;
        cuadrados.setBounds(450, 300, 350, 400);
        cuadrados.setVisible(true);
        cuadrados.setFont(font3);
        add(cuadrados);


        JTextArea miTextosegunda = new JTextArea("");
        segunda = miTextosegunda;
        segunda.setBounds(850, 300, 350, 400);
        segunda.setVisible(true);
        segunda.setFont(font3);

        JScrollPane scroll = new JScrollPane(segunda);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroll);
        add(segunda);


        JTextField elid = new JTextField("");
        id = elid;
        id.setBounds(700, 100, 50, 35);
        id.setVisible(true);
        id.setFont(font1);
        add(id);


        JTextArea miTextArea = new JTextArea();
        miTextArea.setBounds(100 , 200 , 700 , 500);
        miTextArea.setForeground(Color.darkGray);
        miTextArea.setFont(font1);
        //add(miTextArea);

        resultado();

        //setDefaultCloseOperation(EXIT_ON_CLOSE);


    }//end class view



    //@Override
    public void setActionListener(Controller miController) {

        //agregarDocumentos.addActionListener(miController);

    }

    public void resultado (){


        Font font1 = new Font("Helvetica" , Font.BOLD , 20);

        palabra = new JEditorPane();

        palabra.setVisible(false);
        palabra.setFont(font1);
        palabra.setBounds(130 , 200 , 500 , 30);


        add(palabra);







    }//end resultado


    public void nohayId(int i) {

        System.out.println("NO HAY ID letrero ");
        if(i == 1)
        noID.setVisible(true);
        if(i == 2)
        noID.setVisible(false);



    }
}//end class
