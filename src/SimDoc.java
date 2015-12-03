/**
 * Created by Abuelo on 10/10/2015.
 */
public class SimDoc {

    int id , id_DocH, id_DocV , cluster;
    double sim;


    public SimDoc(int id_DocH, int id_DocV) {
        this.id_DocH = id_DocH;
        this.id_DocV = id_DocV;
        this.sim = sim;
    }

    public void setSim(double valor){

        this.sim = valor;


    }//end setSim


    public void setCluster(int valor){

        this.cluster = valor;


    }//end setSim

    public void setid(int valor){

        this.id = valor;


    }//end setSim

}//end class SimDoc

