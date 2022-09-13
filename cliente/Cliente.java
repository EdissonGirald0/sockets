package sockets.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import sockets.conexion.Conexion;

public class Cliente extends Conexion {

    public Cliente() throws IOException {
        super("cliente");
    } //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        try {
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Ya tenemos el "lector"

            System.out.println("Por favor ingrese su numero de cuenta");//Se pide un dato al usuario

            String cuenta = br.readLine(); //Se lee el nombre con readLine() que retorna un String con el dato

            System.out.println("cuenta " + cuenta + ". Por favor ingrese un valor");//Se pide otro dato al usuario

            String entrada = br.readLine(); //Se guarda la entrada en una variable

            //Nótese que readLine siempre retorna String y la clase BufferedReader...
            //no tiene un método para leer enteros, así que debemos convertirlo.
            int valor = Integer.parseInt(entrada);//Se transforma la entrada anterior en un entero
            //Si el usuario ingresó solo números funcionará bien, de lo contrario generará una excepción

            System.out.println(cuenta + ","+ valor); //Operacion numerica con la edad
            
                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF(cuenta +","+ valor);
       
                

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                cs.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
