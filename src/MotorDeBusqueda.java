/**
 * Created by Abuelo on 8/27/2015.
 */
public class MotorDeBusqueda {





    public static void main(String[] args)  {
        try {

        View elView;
        ViewVentana elViewVentana;

        Controller elController;
        Model miModelo;


        elView = new View();
        elView.setVisible(true);

        elViewVentana = new ViewVentana();
        elViewVentana.setVisible(true);



            miModelo = new Model();

        elController = new Controller(miModelo , elView , elViewVentana);




        elView.setActionListener(elController);


        } catch (Exception e) {
            e.printStackTrace();
        }


        ////////////////////////////////////////////////////////////conexion bvase de datos









    }//end main

}//end  class
