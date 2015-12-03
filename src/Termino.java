

/**
 * @author Edmundo
 * @version 1.0
 * @package Objetos
 */

public class Termino {

    public String palabra;
    public int apaTotal = 0;
    public int numeroDeDocsEnQueAparece = 0;
    public double idf;
    public int aparacionEnDocumento;

    public Termino(String palabra) {

        this.palabra = palabra;
    } //end constructor

    public void setApaTotal(Termino elTermino){

        elTermino.apaTotal = elTermino.apaTotal + 1;
    } //end setApaTotal

    public void setNumeroDeDocsEnQueAparece(Termino elTermino){

        elTermino.numeroDeDocsEnQueAparece = elTermino.numeroDeDocsEnQueAparece + 1;
    } // end setNumeroDeDocsEnQueAparece


    public void calcularIDF (Termino elTermino){}//end calcularIDF


}//end class
