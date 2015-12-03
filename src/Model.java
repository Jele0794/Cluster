//package sample;

/*
 *
 * Created by Diego on 25/11/14.
 */


import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.math.*;


//la base de datos se llama proyectofinal
//show database
//create database
//mysql-connector-java-3.0.17-ga-bin.jar

public class Model {

    Connection conn = null;
    Statement stmt = null;
    String tupla = "";

    //variables de archivos

    private File documentos;


    //vectores

    private Vector<Documento> losDocumentos;
    private Vector<Termino> losTerminos;
    private Vector<String> palabranEnDocumento;
    public  Vector<Resultado> losResultado;
    public  Vector<Resultado> losResultadoCocenos;


    public Vector<SimDoc> losSimDoc;

    int tamDocumentos = 20;


    private int totalArchivos , totalTerminos;



    public Model() throws Exception {

        /*
        double idf1 , idf2 , div ;
        div = (double) 3/2;
        idf1 = Math.log10( div);
        idf2 = Math.log(div);
        System.out.println(idf1);
        System.out.println(idf2);

        */




        ConectarDataBase();
        stmt = conn.createStatement();


        //direccion de los archivos
        documentos = new File("C:\\Users\\Abuelo\\Documents\\UDLAP\\septimo semestre\\Recuperacion de Informacion\\Motor De Busqueda\\lisaprueba.txt");

        //leer archivo




        losDocumentos = new Vector<Documento>();
        losTerminos = new Vector<Termino>();
        losSimDoc = new Vector<SimDoc>();

    }//end constructor

    public void ConectarDataBase() throws Exception {

        try {
            /*
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/motordebusqueda";
            String usuarioDB = "root";
            String passwordDB = "123456789";
            */


            Class.forName("com.mysql.jdbc.Driver");
             String servidor = "jdbc:mysql://localhost/motordebusquedaprueba";
            String usuarioDB = "root";
            String passwordDB = "123456789";


            /*
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/motordebusquedacompleto";
            String usuarioDB = "root";
            String passwordDB = "123456789";
            */



            conn = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("Conexion Exitosa");

            conn.setAutoCommit(false);
            System.out.println("Autocomit FALSE");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }


    }






    public void leerDocumentos(File documentos) throws FileNotFoundException {

        String nombreDocumento , findoc , nombreDocumentorenglon , textDoc;
        int numeroDocumento;

        textDoc = "";

        BufferedReader br;
        FileReader fr;

        fr = new FileReader(documentos);
        br = new BufferedReader(fr);

        String lineaLeida = leerLinea(br);

        while (lineaLeida != null) {

            System.out.println("LINEA1"+lineaLeida);
            String titulo="";
            textDoc = "";


            numeroDocumento = getIdDocumento(lineaLeida);

            lineaLeida = leerLinea(br);
            //System.out.println("whilee"+ lineaLeida);
            while (lineaLeida.length() > 0 && lineaLeida.compareTo("     ")!=0  ){

                titulo = titulo + lineaLeida;

                lineaLeida = leerLinea(br);
                System.out.println("while"+ lineaLeida);
            }

            textDoc = "";
            textDoc = textDoc +""+ lineaLeida ;




            lineaLeida=leerLinea(br);
            findoc = lineaLeida.substring(0,1);
            textDoc = textDoc + lineaLeida ;

            System.out.println("LINEA2"+lineaLeida + "-" +findoc);


            while (0!=findoc.compareTo("*"))

            {
                if(lineaLeida.length()>0 ) {
                    findoc = lineaLeida.substring(0, 1);
                }
                System.out.println( "LINEA3" +lineaLeida + "-" +findoc);
                lineaLeida =  leerLinea(br);
                textDoc = textDoc +""+ lineaLeida ;
            }
            textDoc = purificarTexto(textDoc);
            agregaraVectorDocumentos(numeroDocumento, titulo , textDoc);
            System.out.println(numeroDocumento + titulo + textDoc);
            textDoc = "";


        }//end while

    }//end cargar documento


    public void leerTerminos(File documentos) throws FileNotFoundException{

        String nombreDocumento , nombreDocumentorenglon , findoc;
        String palabra;
        int numeroDocumento;
        String titulo="";



        BufferedReader br;
        FileReader fr;

        fr = new FileReader(documentos);
        br = new BufferedReader(fr);


        String lineaLeida = leerLinea(br); //el primer titulo de un archivo

        while (lineaLeida != null   ){ //si aun hay lineas de texto en el archivo

            System.out.println("LINEA1"+lineaLeida);

            numeroDocumento = getIdDocumento(lineaLeida);

            //titulo
            // salta el nombre del documento
            lineaLeida = leerLinea(br);
            while (lineaLeida.length() > 0 && lineaLeida.compareTo("     ")!=0) {
                titulo = titulo + lineaLeida;
                lineaLeida = leerLinea(br);
            }



//-----------------------------------------------------------------------------


            lineaLeida=leerLinea(br);
            findoc = lineaLeida.substring(0,1);

            System.out.println("....................LINEA2"+lineaLeida + "-" +findoc);

            while (0!=findoc.compareTo("*"))

            {
                if(lineaLeida != null )
                    findoc = lineaLeida.substring(0 , 1);

                ///// incio con la separacion de las palabras


                StringTokenizer lineaTokens = new StringTokenizer(lineaLeida);

                while (lineaTokens.hasMoreTokens() == true) {

                    palabra = lineaTokens.nextToken();

                    int size = palabra.length();
                    String ultimo = palabra.substring(size-1 , size);


                    if(ultimo.compareTo(".") == 0 || ultimo.compareTo(",") ==0)
                    palabra = palabra.substring(0 , size-1);


                    System.out.print("LINEA3" + palabra);

                    Termino otroTermino = new Termino(palabra);



                    otroTermino.palabra = purificarTexto(otroTermino.palabra);


                    agregaraVectorTerminos(otroTermino);

                }

                lineaLeida =  leerLinea(br);
            }



//*****************************************************************************
        }//end while

    }//end leer terminos



    public void leerTerminosEnDocumentos(File documentos) throws FileNotFoundException{

        String nombreDocumento , findoc , nombreDocumentorenglon ,palabra;
        int numeroDocumento;
        String titulo="";

        BufferedReader br;
        FileReader fr;

        fr = new FileReader(documentos);
        br = new BufferedReader(fr);

        String lineaLeida = leerLinea(br);

        while (lineaLeida != null) {

            System.out.println("LINEA1"+lineaLeida);

            nuevoVector();
            numeroDocumento = getIdDocumento(lineaLeida);
            lineaLeida = leerLinea(br);
            while (lineaLeida.length() > 0 && lineaLeida.compareTo("     ")!=0  ){
                titulo = titulo + lineaLeida;
                lineaLeida = leerLinea(br);
            }
//-----------------------------------------------------------------------------

            lineaLeida=leerLinea(br);
            findoc = lineaLeida.substring(0,1);

            System.out.println("LINEA2"+lineaLeida + "-" +findoc);

            while (0!=findoc.compareTo("*"))

            {
                if(lineaLeida != null )
                    findoc = lineaLeida.substring(0 , 1);

                ///// incio con la separacion de las palabras


                StringTokenizer lineaTokens = new StringTokenizer(lineaLeida);

                while (lineaTokens.hasMoreTokens() == true) {

                    palabra = lineaTokens.nextToken();

                    int size = palabra.length();
                    String ultimo = palabra.substring(size-1 , size);

                    if(ultimo.compareTo(".") == 0 || ultimo.compareTo(",") ==0)
                        palabra = palabra.substring(0 , size-1);

                    System.out.println("LINEA3" + palabra);

                    Termino otroTermino = new Termino(palabra);

                    agregaraVectorTerminosDocumentos(otroTermino, numeroDocumento);

                }

                lineaLeida =  leerLinea(br);

            }



//*****************************************************************************
        }//end while

    }//end leer terminos

    private void agregaraVectorDocumentos(int numeroDocumento, String nombreDocumento , String texto) {

     Documento elDocumento = new Documento(numeroDocumento , nombreDocumento );
        elDocumento.texto = texto;

        losDocumentos.add(elDocumento);

    }


    public String leerLinea( BufferedReader bufferReader ){
        String nuevaLinea=null;

        try {
            nuevaLinea = bufferReader.readLine();



            if(nuevaLinea ==null)return null;
            if (nuevaLinea != null ) return nuevaLinea;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return nuevaLinea;
    }

    public int getIdDocumento(String linea){
        int id;
        String cararcter , numero = "";

        int tam = linea.length();

        for(int i = 8 ; i < 13 ; i++ ){
            cararcter =  linea.substring(i , i+1);

            if(0!=cararcter.compareTo(" "))

            numero = numero + cararcter;

            //System.out.println("-"+numero);
        }

        id = Integer.parseInt(numero);
        return id;
    }//end getIdDocumento

    public void enviarDocumentosBaseDeDatos() throws SQLException {

        int i , tam;
        String texto;

        i = 0;
        tam = losDocumentos.size();

        while (i < tam){

            int id;
            String titulo;

            Documento elDocumento = losDocumentos.get(i);

            id = elDocumento.id;
            titulo = elDocumento.titulo;
            texto  = elDocumento.texto;



            agregarDocumentosBasedeDatos(id , titulo , texto);


            i++;
        }//end while i < tam





    }//end enviarDocumentosBaseDeDatos

    public void enviarTerminoBaseDeDatos() throws SQLException {

        int i , tam;

        totalTerminos = losTerminos.size();

        i = 0;
        tam = losTerminos.size();

        while (i<tam){
            int   numerodermino , numeroDocumentos;
            double idf;
            String palabra;

            Termino elTermino = losTerminos.get(i);

            palabra = elTermino.palabra;
            numerodermino = elTermino.apaTotal;
            numeroDocumentos = elTermino.numeroDeDocsEnQueAparece;
            idf = elTermino.idf;

            agregarTerminosBasedeDatos(palabra ,numerodermino , numeroDocumentos , idf , totalTerminos , i);



            i++;
        }//end while i < tam



    }//end enviarTerminoBaseDeDatos


    public void agregarDocumentosBasedeDatos (int id , String titulo , String texto)  {

        //get documento
        String statement, tituloPurificado , textoPurificado;

        //purificadora

        //texto.substring(0 , 2000);

        tituloPurificado = purificarTexto(titulo);
        textoPurificado = purificarTexto(texto);


        System.out.println(id + ","+ titulo);
        //INSERT INTO `motordebusqueda`.`documento` (`id`, `titulo`) VALUES ('45', 'elnombrte');
        //INSERT INTO `motordebusquedaprueba`.`documento` (`id`, `titulo`, `texto`) VALUES ('1', 't', 'te');
        statement = "INSERT INTO motordebusquedaprueba.documento VALUES ('"+id+"', '"+tituloPurificado+"', '"+texto+"');";

        try {
            stmt.executeUpdate( statement );
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //scrip SQL

    } //end agregarDocumentosBasedeDatos

    public void agregarTerminosBasedeDatos(String palabraa, int nuemeroVeces, int numeroDocumentos, double idf, int totalTerminos , int voyEnTermino)   {

        String statement , palabraPurificada;


        palabraPurificada = purificarTexto(palabraa);

        System.out.println(palabraa + ","+ numeroDocumentos+ "," + idf+"," + "purificada"+ palabraPurificada +"--------"+ totalTerminos +"-"+ voyEnTermino);
        statement = "INSERT INTO termino VALUES ('"+palabraPurificada+"', '"+nuemeroVeces+"', '"+numeroDocumentos+ "', '"+idf+"');;";

        try {
            stmt.executeUpdate( statement );
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }




    } //end agregarDocumentosBasedeDatos




    public void agregaraVectorTerminos(Termino nuevoTermino){

        String laPalabra;
        int tamVector , index = -2;
        boolean encontrado = false;



        //obtener la palabra del nuevo termino

        laPalabra = nuevoTermino.palabra;


        // tam del vector

        tamVector = losTerminos.size();

        //buscar el termino en el vector
        int i=0;
        if(tamVector > 0) {

            while (i < tamVector && encontrado==false)


            {
                //System.out.println("BUSCANDO");
                if(laPalabra.compareTo(losTerminos.get(i).palabra) == 0) {
                    encontrado = true;
                    index = i;
                }
                i++;
            }//end for i < tam vector

        }//end if isEmpty


        ////////
        if( encontrado==true )
        {
            //aumento el contador


            Termino plabraEncontrada = losTerminos.get(index);

           plabraEncontrada.apaTotal = plabraEncontrada.apaTotal + 1;

            System.out.println("+++" + plabraEncontrada.palabra + ",,,"+ plabraEncontrada.apaTotal);


        }
            if(encontrado == false){
            //agrego un nuevo elemento al vector
            losTerminos.addElement(nuevoTermino);

            Termino plabraEncontrada = losTerminos.get(buscarPalabra(nuevoTermino.palabra , losTerminos));

            plabraEncontrada.apaTotal = 1;
            System.out.println("NUEVO" + nuevoTermino.palabra + plabraEncontrada.apaTotal);
           // System.out.println("nueva" );
        }

       //abregar un nuevo






    }//end nuevo termino


    public void agregaraVectorTerminosDocumentos(Termino nuevoTermino , int numeroDocumento){

        String laPalabra;
        int tamVector , index = -2;
        boolean encontrado = false;
        boolean encontradoenDocumento = false;




        //obtener la palabra del nuevo termino
        laPalabra = nuevoTermino.palabra;
        // tam del vector
        tamVector = losTerminos.size();

        //////// busqueda documento

        int u=0;
        int tampalabras = palabranEnDocumento.size();

        System.out.println("tam" + tampalabras);


        if(palabranEnDocumento.size()>0) {

            while (u < tampalabras && encontradoenDocumento==false)


            {
                //System.out.println("BUSCANDO");
                String palabraComparar = palabranEnDocumento.get(u);

                if(laPalabra.compareTo(palabraComparar) == 0) {
                    System.out.println("@@@"+ palabraComparar +""+ laPalabra);
                    encontradoenDocumento = true;
                    index = u;
                }
                u++;
            }//end for i < tam vector

        }//end if isEmpty






        if(encontradoenDocumento == false) {
            palabranEnDocumento.addElement(laPalabra);



            index = buscarPalabra(nuevoTermino.palabra  , losTerminos);

            Termino plabraEncontrada = losTerminos.get(index);

            plabraEncontrada.numeroDeDocsEnQueAparece = plabraEncontrada.numeroDeDocsEnQueAparece + 1;
            System.out.println("((" + index);

        }
        ////////////////////////////////////////////////////
        if(  encontradoenDocumento == true)
        {
            //aumento el contador
            index = buscarPalabra(nuevoTermino.palabra , losTerminos);
            System.out.println("))))))))" + index);

            Termino plabraEncontrada = losTerminos.get(index);

           // plabraEncontrada.numeroDeDocsEnQueAparece = plabraEncontrada.numeroDeDocsEnQueAparece + 1;

            //  System.out.println("+++" + plabraEncontrada.palabra + ",,,"+ plabraEncontrada.apaTotal);


        }






    }//end nuevo termino

    private int buscarPalabra(String laPalabra , Vector<Termino> losTerminos ) {
        int index = 0;
        boolean encontrado = false;



       int  tamVector = losTerminos.size();

        //buscar el termino en el vector
        int i=0;
        if(losTerminos.isEmpty()==false) {

            while (i < tamVector && encontrado==false)


            {
                //System.out.println("BUSCANDO");
                if(laPalabra.compareTo(losTerminos.get(i).palabra) == 0) {
                    encontrado = true;
                    index = i;
                }
                i++;
            }//end for i < tam vector

        }//end if isEmpty



        return index;
    }


    public void nuevoVector(){

        palabranEnDocumento = new Vector<String>();

    }//end nuevo vector


    public void resultados(){

        int tamTerminos = losTerminos.size();
        int tamDocumentos = losDocumentos.size();


        for(int i = 0 ; i < tamDocumentos ; i++) {

            System.out.println("idDocumento : " + losDocumentos.get(i).id);
            System.out.println("Nombre: " + losDocumentos.get(i).titulo);

        }

        for(int u = 0 ; u < tamTerminos ; u++) {

            System.out.println("termino:" + losTerminos.get(u).palabra
                    +"- Total:"+  losTerminos.get(u).apaTotal
                        + "- Documentos " + losTerminos.get(u).numeroDeDocsEnQueAparece
                            + "--IDF" + losTerminos.get(u).idf);


        }


    }


    public void calcularIDF(){

        int tam;
        int i;
        double idf , div;



        tam = losTerminos.size();

        i = 0;

        while (i < tam) {


            Termino elTermino = losTerminos.get(i);
            System.out.println("CALULANDO-" + elTermino.palabra + totalArchivos +"/" + elTermino.numeroDeDocsEnQueAparece) ;
            //Calcular idf

            //log(aparece / losqueson)

            div = (double)totalArchivos / elTermino.numeroDeDocsEnQueAparece  ;

            idf =Math.log10( div);

            elTermino.idf = idf;

            i++;
        }//end while i< tam
    }//end calcular idf

    public void totalArchivos(){

        totalArchivos = losDocumentos.size();


    }//end total de archivos


    public double obtenerIDFBaseDeDatos(String palabra) throws SQLException {

        double idfencontrado = -99;
       tupla = "";

        //purificadora

        String laPalabraPurificada;

        //purificadora
        laPalabraPurificada = purificarTexto(palabra);


        Statement comando = conn.createStatement();

        ResultSet registro  =

                comando.executeQuery("select idf from termino where termino.palabra='"+laPalabraPurificada+"';");


            if (registro.next()==true){

                idfencontrado  = registro.getDouble("idf");

                System.out.println("IDF = "+ idfencontrado);
            }


            else
                System.out.println("NO EXISTE LA PALABRA");





        return idfencontrado;
    }//end  obtenerIDFBaseDeDatos


    public String purificarTexto (String texto){

        String textoPurificado = "";

        int tamTexto , i ;

        tamTexto = texto.length();
        i = 0;

        while (i < tamTexto) {
            String cararter;

            cararter = texto.substring(i , i+1);
            if(cararter.compareTo("'")==0)
            cararter = "";

            textoPurificado = textoPurificado + cararter;

            i++;
        }//end while i < tamTexto
        return textoPurificado;
    }//end purificarTexto



    public String despurificarTexto (String texto){

        String textoPurificado = "";

        int tamTexto , i ;

        tamTexto = texto.length();
        i = 0;

        while (i < tamTexto) {
            String cararter;

            cararter = texto.substring(i , i+1);
            if(cararter.compareTo("@")==0)
                cararter = "'";

            textoPurificado = textoPurificado + cararter;

            i++;
        }//end while i < tamTexto
        return textoPurificado;
    }//end purificarTexto



    public void palabraporDocumentos(File documentos) throws FileNotFoundException{

        String nombreDocumento , findoc , nombreDocumentorenglon ,palabra;
        int numeroDocumento;
        String titulo="";


        Vector<Termino> terminosDelDocumento;

        BufferedReader br;
        FileReader fr;

        fr = new FileReader(documentos);
        br = new BufferedReader(fr);

        String lineaLeida = leerLinea(br);

        while (lineaLeida != null) {
            terminosDelDocumento = new Vector<Termino>();

            System.out.println("LINEA1"+lineaLeida);

            nuevoVector();
            numeroDocumento = getIdDocumento(lineaLeida);
            lineaLeida = leerLinea(br);
            while (lineaLeida.length() > 0 && lineaLeida.compareTo("     ")!=0  ){
                titulo = titulo + lineaLeida;
                lineaLeida = leerLinea(br);
            }
//-----------------------------------------------------------------------------

            lineaLeida=leerLinea(br);
            findoc = lineaLeida.substring(0,1);

            System.out.println("LINEA2"+lineaLeida + "-" +findoc);

            while (0!=findoc.compareTo("*"))

            {
                if(lineaLeida != null )
                    findoc = lineaLeida.substring(0 , 1);

                ///// incio con la separacion de las palabras


                StringTokenizer lineaTokens = new StringTokenizer(lineaLeida);

                while (lineaTokens.hasMoreTokens() == true) {

                    palabra = lineaTokens.nextToken();

                    int size = palabra.length();
                    String ultimo = palabra.substring(size-1 , size);

                    if(ultimo.compareTo(".") == 0 || ultimo.compareTo(",") ==0)
                        palabra = palabra.substring(0 , size-1);

                    System.out.println("LINEA3" + palabra);

                    Termino otroTermino = new Termino(palabra);

                    otroTermino.palabra = purificarTexto(otroTermino.palabra);

                    palabraenDocumentosVectorTerminos(otroTermino, terminosDelDocumento);

                }

                lineaLeida =  leerLinea(br);

                //conteo de palabras

                conteoPalbrasporDocumento(numeroDocumento , terminosDelDocumento);

                ////// envio base de datos





            }

            try {
                enviarBaseDeDatosConteoPalabrasPorDocumento(numeroDocumento , terminosDelDocumento   );
            } catch (SQLException e) {
                e.printStackTrace();
            }



//*****************************************************************************
        }//end while

    }//end leer terminos

    private void conteoPalbrasporDocumento(int numeroDocumento, Vector<Termino> terminosDelDocumento) {    }


    public void palabraenDocumentosVectorTerminos(Termino nuevoTermino , Vector<Termino> terminosDelDocumento){

        String laPalabra;
        int tamVector , index = -2;
        boolean encontrado = false;



        //obtener la palabra del nuevo termino

        laPalabra = nuevoTermino.palabra;


        // tam del vector

        tamVector = terminosDelDocumento.size();

        //buscar el termino en el vector
        int i=0;
        if(tamVector > 0) {

            while (i < tamVector && encontrado==false)


            {
                //System.out.println("BUSCANDO");
                if(laPalabra.compareTo(terminosDelDocumento.get(i).palabra) == 0) {
                    encontrado = true;
                    index = i;
                }
                i++;
            }//end for i < tam vector

        }//end if isEmpty


        ////////
        if( encontrado==true )
        {
            //aumento el contador


            Termino plabraEncontrada = terminosDelDocumento.get(index);

            plabraEncontrada.aparacionEnDocumento = plabraEncontrada.aparacionEnDocumento + 1;

            System.out.println("####" + plabraEncontrada.palabra + "///"+ plabraEncontrada.aparacionEnDocumento);


        }
        if(encontrado == false){
            //agrego un nuevo elemento al vector
            terminosDelDocumento.addElement(nuevoTermino);

            Termino plabraEncontrada = terminosDelDocumento.get(buscarPalabra(nuevoTermino.palabra , terminosDelDocumento));

            plabraEncontrada.aparacionEnDocumento = 1;
            System.out.println("NUEVO" + nuevoTermino.palabra + plabraEncontrada.aparacionEnDocumento);
            // System.out.println("nueva" );
        }

        //abregar un nuevo






    }//end nuevo termino


    public  void enviarBaseDeDatosConteoPalabrasPorDocumento(int numeroDocumento ,Vector<Termino> terminosDelDocumento) throws SQLException {


        int i , tam;

        i = 0;
        tam = terminosDelDocumento.size();

        while (i < tam){

            int frecuancia , idDocumento ;
            String palbra;

            Termino elTermino = terminosDelDocumento.get(i);


            idDocumento = numeroDocumento;
            palbra = elTermino.palabra;
            frecuancia = elTermino.aparacionEnDocumento;




            agregarConteoPalabrasPorDocumentoBasedeDatos(idDocumento , palbra , frecuancia , terminosDelDocumento.size() , i);


            i++;
        }//end while i < tam


    }

    private void agregarConteoPalabrasPorDocumentoBasedeDatos(int idDocumento, String palbra, int frecuancia , int totalPalbras , int i)  {



        String statement , palabraPurificada;
        palabraPurificada = purificarTexto(palbra);

        int tf = -10;
        double idf = -10;
        double resultado ;

        //idf pre

        try {
             tf = frecuancia;
            idf = obtenerIDFBaseDeDatos(palabraPurificada);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultado = tf * idf;


        if(resultado >  0) {

            System.out.println("idDocumento:" + idDocumento + ", Palbra:" + palbra + ", PURI:" + palabraPurificada + "frecuancia:" + frecuancia + "///" + totalPalbras + "-" + i);
            statement = "INSERT INTO frecuencia VALUES ('" + palabraPurificada + "', '" + idDocumento + "', '" + frecuancia + "','" + resultado + "');";

            try {
                stmt.executeUpdate(statement);
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }//end if

    }//end metodo

    public void procesarConsulta(String consulta , int id){

         Vector<Termino> losTerminosConsulta;
        losTerminosConsulta = new Vector<Termino>();


        StringTokenizer lineaTokens = new StringTokenizer(consulta);

        while (lineaTokens.hasMoreTokens() == true) {

            String palabra = lineaTokens.nextToken();

            int size = palabra.length();
            String ultimo = palabra.substring(size - 1, size);


            if (ultimo.compareTo(".") == 0 || ultimo.compareTo(",") == 0)
                palabra = palabra.substring(0, size - 1);


            System.out.print("LINEA3" + palabra);

            Termino otroTermino = new Termino(palabra);

            agregarVectorPalabra(losTerminosConsulta , palabra);


        }//end while

        enviarTerminosConsultaBaseDeDatos(losTerminosConsulta , id);

    }//end procesar consulta


    public void agregarVectorPalabra(Vector<Termino> losTerminosConsulta , String palabra){

        String laPalabra;
        int tamVector , index = -2;
        boolean encontrado = false;

        Termino nuevoTermino = new Termino(palabra);



        //obtener la palabra del nuevo termino

        laPalabra = palabra;


        // tam del vector

        tamVector = losTerminosConsulta.size();

        //buscar el termino en el vector
        int i=0;
        if(tamVector > 0) {

            while (i < tamVector && encontrado==false)


            {
                //System.out.println("BUSCANDO");
                if(laPalabra.compareTo(losTerminosConsulta.get(i).palabra) == 0) {
                    encontrado = true;
                    index = i;
                }
                i++;
            }//end for i < tam vector

        }//end if isEmpty


        ////////
        if( encontrado==true )
        {
            //aumento el contador


            Termino plabraEncontrada = losTerminosConsulta.get(index);

            plabraEncontrada.aparacionEnDocumento = plabraEncontrada.aparacionEnDocumento + 1;

            System.out.println("####" + plabraEncontrada.palabra + "///"+ plabraEncontrada.aparacionEnDocumento);


        }
        if(encontrado == false){
            //agrego un nuevo elemento al vector
            losTerminosConsulta.addElement(nuevoTermino);

            Termino plabraEncontrada = losTerminosConsulta.get(buscarPalabra(nuevoTermino.palabra , losTerminosConsulta));

            plabraEncontrada.aparacionEnDocumento = 1;
            System.out.println("NUEVO" + nuevoTermino.palabra + plabraEncontrada.aparacionEnDocumento);
            // System.out.println("nueva" );
        }

        //abregar un nuevo
    }//end agregar vector palabra

    public void enviarTerminosConsultaBaseDeDatos(Vector<Termino> losTerminosConsulta , int id){

        int i , tam;

        totalTerminos = losTerminosConsulta.size();

        i = 0;
        tam = losTerminosConsulta.size();

        while (i<tam){
            int   tf ,idEnviar;
            double idf;
            String palabra;

            Termino elTermino = losTerminosConsulta.get(i);

            palabra = elTermino.palabra;
            idEnviar = id;
            tf = elTermino.aparacionEnDocumento;

            almacentarTerminoConsultaBaseDeDatos(palabra, idEnviar, tf);



            i++;
        }//end while i < tam


    }// enviarTerminosConsultaBaseDeDatos

    public void almacentarTerminoConsultaBaseDeDatos(String palabra , int id , int tf){


        //get documento
        String statement, palabraPurificada;

        //purificadora

        palabraPurificada = purificarTexto(palabra);

       // System.out.println(id + ","+ palabraPurificada);
        //INSERT INTO `motordebusqueda`.`documento` (`id`, `titulo`) VALUES ('45', 'elnombrte');
        statement = "INSERT INTO consulta VALUES ('"+id+"', '"+palabraPurificada+"','"+tf+"');";


        try {
            stmt.executeUpdate( statement );
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //scrip SQL




    } // almacentarTerminoConsultaBaseDeDatos


    public double obtenerDocumentosRelevantes(int id ) throws SQLException {

        double idfencontrado = -99;
        tupla = "";


        //purificadora

        String laPalabraPurificada;

        //purificadora



        Statement comando = conn.createStatement();

        ResultSet registro  =

                comando.executeQuery("select i.id, sum(q.tf * t.idf * i.repeticionesEnDocumento * t.idf)  "+
                        "from consulta q, frecuencia i, termino t "+
                        "where q.palabra = t.palabra AND i.palabra = t.palabra  "+
                        "AND q.idconsulta = '"+id+"' group by i.id Order by 2 desc ");


            losResultado = new Vector<Resultado>();


            while (registro.next()){

                int idResultado ;
                double producto;

                idResultado = registro.getInt(1);
                producto = registro.getDouble(2);



                Resultado elResultado = new Resultado(idResultado , producto);

                losResultado.addElement(elResultado);


            }//end while registro.next()




        return idfencontrado;
    }//end  obtenerIDFBaseDeDatos


    public  String resultadoCOnsultas(){

        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");

        String devolver="";

        int tam = losResultado.size();
        int i = 0;
        while(i < tam) {

            int id = losResultado.get(i).documentoID;
            double producto = losResultado.get(i).productoPuntoi;

            devolver = devolver + "Vectorial ID:" + id+ "\n";



            i++;
        }

        System.out.println(devolver);
        return  devolver;

    }//end resultados


    public void obtenerDocumentosRelevantesCocenos(int idConsulta){

        double idfencontrado = -99;
        tupla = "";

        System.out.println("ID CONSULTA:"+ idConsulta);


        //purificadora

        String laPalabraPurificada;

        //purificadora


        Statement comando = null;
        try {
            comando = conn.createStatement();

            String statementA;

                    statementA = "insert into pesos_docs select id, sqrt(sum(i.repeticionesEnDocumento * t.idf * i.repeticionesEnDocumento * t.idf)) , "+idConsulta+"  from frecuencia i, termino t where i.palabra = t.palabra group by i.id;";

            stmt.executeUpdate( statementA );
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        Statement coamndo2 = null;
        try {
            coamndo2 = conn.createStatement();

            String statementB;

            statementB = "insert into peso_q select sqrt(sum(q.tf * t.idf * q.tf * t.idf )) , "+idConsulta+"  from consulta q, termino t where q.palabra = t.palabra ;";


            stmt.executeUpdate( statementB );
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement coamndo3 = null;
        try {
            coamndo3 = conn.createStatement();

            ResultSet registroC  =

                    coamndo3.executeQuery("select i.id, sum(q.tf * t.idf * i.repeticionesEnDocumento * t.idf) / (dw.pesos * qw.peso) from consulta q, frecuencia i, termino t, pesos_docs dw, peso_q qw  where q.palabra = t.palabra AND  i.palabra = t.palabra AND i.id = dw.idDocumento  AND  q.idconsulta = '"+idConsulta+"' AND dw.idConsulta = qw.idconsulta group by i.id, dw.pesos, qw.peso   order by 2 desc");


            losResultadoCocenos = new Vector<Resultado>();



            while (registroC.next()){

                int idResultado ;
                double producto;

                idResultado = registroC.getInt(1);
                producto = registroC.getDouble(2);



                Resultado elResultado = new Resultado(idResultado , producto);

                losResultadoCocenos.addElement(elResultado);




            }//end while registro.next()





        } catch (SQLException e) {
            e.printStackTrace();
        }





    }// end obtenerDocumentosRelevantesCocenos


    public  String resultadoCOnsultasCocenos(){

        System.out.println("///////////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////////");

        String devolver="";

        int tam = losResultadoCocenos.size();
        int i = 0;
        while(i < tam) {

            int id = losResultadoCocenos.get(i).documentoID;
            double producto = losResultadoCocenos.get(i).productoPuntoi;


            devolver = devolver +"COCENOS"+ "ID:" + id +"\n";



            i++;
        }

        System.out.println(devolver);
        return  devolver;

    }//end resultados



    public String segundaConsulta(  int documentosImportantes[] ){

        //obtener el vector de resultados


        String palabras[][];

            //el vector de resultados es el que se genera como el resultado de la consulta
            //solo seleccionamos cual utilizamos si el de concenos o el de vectorial





        //buscar los o el documento importante
        //obtner el numero de palabras importantes

        String entrada = JOptionPane.showInputDialog(null, "Numero de Palabras Importantes");
        int numeroDePalabras = Integer.parseInt(entrada);

        //int numeroDePalabras = 10;

        palabras = new String[numeroDePalabras][documentosImportantes.length];

        //almacenar las palabras de importancia

        palabras = recuperarPalabras(palabras , numeroDePalabras, documentosImportantes);



        //concatenar la paabras con la consu principal

            String nuevaConsulta = "";


            for(int j = 0 ; j < documentosImportantes.length ; j++)
            for(int i = 0 ; i < numeroDePalabras; i++)
            {

                nuevaConsulta = nuevaConsulta + palabras[i][j] + "\n";

            }//end for


            return nuevaConsulta;


    }//end segundaConsulta


    public String[][] recuperarPalabras(String palabras[][] , int numeroPalabras , int documentos[]){


        for(int i = 0 ; i < documentos.length ; i++ ){

            //consulta base de datos


            Statement comando = null;
            try {

                int numeroDocumento = documentos[i];
                comando = conn.createStatement();


            ResultSet registro  =

                    comando.executeQuery("SELECT termino.palabra  "+
                            " FROM motordebusquedacompleto.termino , "+
                            "motordebusquedacompleto.documento, "+
                            "motordebusquedacompleto.frecuencia "+
                            "where documento.id = '"+ numeroDocumento+"' "+
                            "AND frecuencia.id = 10 "+
                            "AND termino.palabra = frecuencia.palabra "+
                            "Order by  termino.idf DESC  ");

                        int j = 0 ;
                while (registro.next() && j<numeroPalabras){

                    palabras[j][i] = String.valueOf(registro.getInt(1));
                    System.out.println(palabras[j][i]);

                  j++;
                }//end while registro.next




            } catch (SQLException e) {
                e.printStackTrace();
            }

            //recorrer la palabras

            //almacenar en el vector





        }//ene for i < documentos.leght







        return palabras;



    }//end recuperarPalabras



    public String recuperarPalabrasBien(int id){

            String palabras="";


            //consulta base de datos


            Statement comando = null;
            try {


                comando = conn.createStatement();


                ResultSet registro  =

                        comando.executeQuery("SELECT ter.palabra , ter.idf ,sum(tter.idf + tt.idf )" +
                                "from  documento d , termino ter , consulta cc , termino  tt" +
                                "where cc.idconsulta = '"+id+"'" +
                                "and c.palabra = ter.palabra  = tt.palabra" +
                                " in (select i.id, sum(q.tf * t.idf * i.repeticionesEnDocumento * t.idf)" +
                                "from consulta q, frecuencia i, termino t" +
                                "where q.palabra = t.palabra AND i.palabra = t.palabra " +
                                "AND q.idconsulta = '"+id +"'+ group by i.id Order by 2 desc " );


                while (registro.next() ){

                    System.out.print(registro.getInt(1));

                    palabras = palabras + String.valueOf(registro.getInt(1));


                }//end while registro.next




            } catch (SQLException e) {
                e.printStackTrace();
            }

            //recorrer la palabras

            //almacenar en el vector













        return palabras;



    }//end recuperarPalabras


    public void limpiarVectores(){

         Vector<Documento> losDocumentos = new Vector<Documento>();
         Vector<Termino> losTerminos = new Vector<Termino>();
        Vector<String> palabranEnDocumento = new Vector<String>();
          Vector<Resultado> losResultado = new Vector<Resultado>();
          Vector<Resultado> losResultadoCocenos = new Vector<Resultado>();






    }//end limpiar vectores


    public void obtenerSim_Doc(){

        //var

        int tamDocumentos;

        //ir por todos los documentos base de datos vector de documentos

       // llenarVectorPrueba(); //con este metodo lleno el vector solo temporal


        //desmadre de llenar matrz


            //pruebas vale
          // tamDocumentos = 2;

                    //completo
                tamDocumentos = losDocumentos.size();

                for(int i = 0 ; i < tamDocumentos ; i++){

                    double valorSimilitudVertical = -10;
                    String consultaTextoVertical = losDocumentos.get(i).texto;

                    procesarConsulta(consultaTextoVertical , idConsultaMayor()+1);

                    //consulta me debe de regresar  un double


                    try {
                        valorSimilitudVertical = obtenerDocumentosRelevantesSimilitud( idConsultaMayor() );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


/////////////////////////////////////////////////////////////////////////////para el otro documento y yap
                    for(int j = 0 ; j < tamDocumentos ; j++)

                    {

                        //un objeto temporal
                        SimDoc elSimDoc = new SimDoc(losDocumentos.get(i).id , losDocumentos.get(j).id);



                        if(elSimDoc.id_DocH == elSimDoc.id_DocV){

                            //solo asigno el valor -1 al documento
                            System.out.println("---------------------------------------------------------Igaules" + i +","+ j);

                            elSimDoc.setSim(-1);

                            losSimDoc.addElement(elSimDoc);


                        }//end if i == j

                        else if(elSimDoc.id_DocH != elSimDoc.id_DocV){

                            System.out.println("------------------------------------------------------Diferentes"+i+","+j);

                            //tengo que realizar la consulta
                            // obtener el valor del texto

                            String consultaTextoHorizontal = losDocumentos.get(j).texto;

                            procesarConsulta(consultaTextoHorizontal , idConsultaMayor()+1);

                                //consulta me debe de regresar  un double

                            double valorSimilitudHorizontal = -10;


                            try {
                                valorSimilitudHorizontal = obtenerDocumentosRelevantesSimilitud( idConsultaMayor() );
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //comparas

                            //asignar valor
                            double valorSimilitud = valorSimilitudHorizontal + valorSimilitudVertical;

                            elSimDoc.setSim(valorSimilitud);

                            losSimDoc.addElement(elSimDoc);





                        } // end else if i != j

                    }//int j = 0 ; j < tamDocumentos ; j++
                }//end for int i = 0 ; i < tamDocumentos ; i++



        //guardas todobase de datos
        documentosSimilitudBasedeDatosAlmacenar();



    }//end obtenerSim_Doc


    public void llenarVectorPrueba(){

     //   public Vector<SimDoc> losSimDoc;


            for(int j = 0 ; j < 3 ; j++) {



                Documento elDocumento = new Documento(j , "");
                losDocumentos.addElement(elDocumento);




            }//edn fors


    }//end llenarVectorPrueba


    public int idConsultaMayor(){

        int id = -1;

        Statement comando = null;
        try {
            comando = conn.createStatement();

            ResultSet registro  =

                    comando.executeQuery("SELECT max(consulta.idconsulta)FROM consulta;");


            while (registro.next()) {

                id = registro.getInt(1);

            }//end while
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return id;
    }//end idConsultaMayor


    public int idConsultaMayorMatriz(){

        int id = -1;

        Statement comando = null;
        try {
            comando = conn.createStatement();

            ResultSet registro  =

                    comando.executeQuery("SELECT max(cluster.id)FROM cluster;");


            while (registro.next()) {

                id = registro.getInt(1);

            }//end while
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return id;
    }//end idConsultaMayor


    public double obtenerDocumentosRelevantesSimilitud(int id ) throws SQLException {

        double idfencontrado = -99;
        tupla = "";


        //purificadora

        String laPalabraPurificada;

        //purificadora



        Statement comando = conn.createStatement();

        ResultSet registro  =

                comando.executeQuery("select i.id, sum(q.tf * t.idf * i.repeticionesEnDocumento * t.idf)  "+
                        "from consulta q, frecuencia i, termino t "+
                        "where q.palabra = t.palabra AND i.palabra = t.palabra  "+
                        "AND q.idconsulta = '"+id+"' group by i.id Order by 2 desc ");


        losResultado = new Vector<Resultado>();


        while (registro.next()){

            int idResultado ;
            double producto;

            idResultado = registro.getInt(1);
            producto = registro.getDouble(2);



            Resultado elResultado = new Resultado(idResultado , producto);

            return producto;






        }//end while registro.next()




        return idfencontrado;
    }//end  obtenerIDFBaseDeDatos

    public  void documentosSimilitudBasedeDatosAlmacenar(){


        String statement;

        System.out.println("tam" + losSimDoc.size());


        for(int i = 0 ; i < losSimDoc.size() ; i++)
        {


            int horizontal = losSimDoc.get(i).id_DocH;
            int vertical = losSimDoc.get(i).id_DocV;
            double similitud = losSimDoc.get(i).sim;
            int id = idConsultaMayorMatriz() +1;





            System.out.println("SIMILITUD___"+ similitud +"h"+horizontal +"v"+vertical);


            //statement = "INSERT INTO matriz  VALUES ('"+id+"','"+horizontal+"', '"+vertical+"', '"+similitud+"');";

            statement = "INSERT INTO cluster (id , documentoHorizontal, documentoVertical, similitud) VALUES ('"+id+"','"+horizontal+"', '"+vertical+"', '"+similitud+"');";



            try {
                stmt.executeUpdate(statement);
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //scrip SQL

        }//end for



    }//end documentosSimilitudBasedeDatosAlmacenar


    public int obtenerTFBaseDeDatos(String palabra , int id) throws SQLException {

        int idfencontrado = -99;
        tupla = "";

        //purificadora

        String laPalabraPurificada;

        //purificadora
        laPalabraPurificada = purificarTexto(palabra);


        Statement comando = conn.createStatement();

        ResultSet registro  =

                comando.executeQuery("select repeticionesEnDocumento from frecuencia where frecuencia.id = '"+id+"'  AND frecuencia.palabra='"+laPalabraPurificada+"';");


        if (registro.next()==true){

            idfencontrado  = registro.getInt("repeticionesEnDocumento");

            System.out.println("IDF = "+ idfencontrado);
        }


        else
            System.out.println("NO EXISTE LA PALABRA TF");





        return idfencontrado;
    }//end  obtenerIDFBaseDeDatos


    public SimDoc mayorSimilitud(){

        int id , dochorizontal , docvertical ,  cluster;
        double similitud;



        Statement comando = null;
        try {
            comando = conn.createStatement();

            ResultSet registro  =

                    comando.executeQuery("SELECT   id , documentoHorizontal , documentoVertical , similitud , cluster FROM cluster order by similitud desc limit 1;");


            while (registro.next()) {

                id = registro.getInt(1);
                dochorizontal = registro.getInt(2);
                docvertical   = registro.getInt(3);
                similitud     = registro.getDouble(4);
                cluster       = registro.getInt(5);

                System.out.println("id:" + id + ",doh:" + dochorizontal + ",docv:" + docvertical + ",sim:" + similitud + ",cluster:" + cluster);

                SimDoc miSimDoc = new SimDoc(dochorizontal , docvertical);
                miSimDoc.setSim(similitud);
                miSimDoc.setCluster(cluster);
                miSimDoc.setid(id);


                return miSimDoc;
            }//end while
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return null;
    }//end idConsultaMayor

    public void construirCluster(){


        //el mayor de todos

        SimDoc elMayor = mayorSimilitud();

        if(elMayor != null){

            //deben de realizar las nuevas consultas

            nuevaComparacion(elMayor);


        }//end el mayor null


    }//end construirCluster


    public void nuevaComparacion(SimDoc elMayor){



       Vector<SimDoc> nuevosSimDoc = new Vector<SimDoc>();


        for(int j = 0 ; j < tamDocumentos ; j++){



            //similitud que tengo

            //similitud del documento con el que comparo
            String comparoSimilitud = losDocumentos.get(j).texto;

            procesarConsulta(comparoSimilitud , idConsultaMayor()+1);

            //consulta me debe de regresar  un double

            double similitudconAlmacenado = -10;


            try {
                similitudconAlmacenado = obtenerDocumentosRelevantesSimilitud( idConsultaMayor() );
            } catch (SQLException e) {
                e.printStackTrace();
            }


            double valorSimilitud = similitudconAlmacenado + elMayor.sim;

            SimDoc elNuevoSimDoc = new SimDoc(   elMayor.id_DocV , losDocumentos.get(j).id);
            elNuevoSimDoc.setSim(similitudconAlmacenado);
            elNuevoSimDoc.setCluster(elMayor.id);



            nuevosSimDoc.addElement(elNuevoSimDoc);



        }//end for de recorrido

        SimDoc elUltimo = new SimDoc(elMayor.id_DocV , tamDocumentos+1);


        elUltimo.setSim(-1);
        elUltimo.setCluster(elMayor.cluster);
        elUltimo.setCluster(elMayor.id);
        nuevosSimDoc.addElement(elUltimo);



        //todoa la base de datos pero primero lo quiero ver corriendo

        for(int i = 0 ; i < tamDocumentos + 1 ; i++){



           int  id            = nuevosSimDoc.get(i).id;
           int  dochorizontal = nuevosSimDoc.get(i).id_DocH;
           int  docvertical   = nuevosSimDoc.get(i).id_DocV;
           double  similitud  = nuevosSimDoc.get(i).sim;
           int  cluster       = nuevosSimDoc.get(i).cluster;


            System.out.println("id:" + id + ",doh:" + dochorizontal + ",docv:" + docvertical + ",sim:" + similitud + ",cluster:" + cluster);




        }//end for para impriir en lugar de guardar


        //crear el nuevo documeto


        //base de datos

       documentosSimilitudBasedeDatosAlmacenarNuevos(nuevosSimDoc);




        tamDocumentos = tamDocumentos + 1; //////////////////////hay que canmbiarlo despues
    }//nuevaComparacion


    public  void documentosSimilitudBasedeDatosAlmacenarNuevos(Vector<SimDoc> nuevosSimDoc){


        String statement;

        System.out.println("tam" + nuevosSimDoc.size());


        for(int i = 0 ; i < nuevosSimDoc.size() ; i++)
        {


            int horizontal = nuevosSimDoc.get(i).id_DocH;
            int vertical = nuevosSimDoc.get(i).id_DocV;
            double similitud = nuevosSimDoc.get(i).sim;
            int cluster = nuevosSimDoc.get(i).cluster;
            int id = idConsultaMayorMatriz() +1;





            System.out.println("SIMILITUD___"+ similitud +"h"+horizontal +"v"+vertical);


            //statement = "INSERT INTO matriz  VALUES ('"+id+"','"+horizontal+"', '"+vertical+"', '"+similitud+"');";

            statement = "INSERT INTO cluster (id , documentoHorizontal, documentoVertical, similitud , cluster) VALUES ('"+id+"','"+horizontal+"', '"+vertical+"', '"+similitud+"','"+cluster+"');";



            try {
                stmt.executeUpdate(statement);
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //scrip SQL

        }//end for



    }//end documentosSimilitudBasedeDatosAlmacenar


    public void crawler(String url){}//end crawler



}//end class model