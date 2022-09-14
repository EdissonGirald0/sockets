package sockets.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
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
            System.out.println("Por favor seleccione una operación");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Consultar Saldo");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String opcion = br.readLine();
            if(opcion.equals("1")){
                this.createAccount();
            }else if(opcion.equals("2")){
                
                this.queryAccount();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void createAccount() //Método para iniciar el cliente
    {
        try {
                           
            DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            salida.writeUTF("createAccount");
            System.out.println("Por favor ingrese la cuenta");
            String cuenta = lector.readLine();
            System.out.println("Por favor ingrese el saldo");
            String valor = lector.readLine();
            salida.writeUTF(cuenta + ',' + valor);
            String resultado = entrada.readUTF();
            System.out.println(resultado);

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

    public void queryAccount() //Método para iniciar el cliente
    {
        try {
            DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            salida.writeUTF("queryAccount");
            System.out.println("Por favor ingrese la cuenta");
            String cuenta = lector.readLine();
            salida.writeUTF(cuenta);
            String resultado = entrada.readUTF();
            if (resultado.isEmpty()){
                System.out.println("Cuenta no encontrada");
            }else{
                System.out.println(resultado);
            }
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
