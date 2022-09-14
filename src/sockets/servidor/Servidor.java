package sockets.servidor;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import sockets.conexion.Conexion;

public class Servidor extends Conexion //Se hereda de conexión para hacer uso de los sockets y demás
{

    public Servidor() throws IOException {
        super("servidor");
    } //Se usa el constructor para servidor de Conexion

    public void startServer()//Método para iniciar el servidor
    {
        while (true) {
            try {
                System.out.println("Esperando..."); //Esperando conexión
                cs = ss.accept();
                DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
                DataInputStream entrada = new DataInputStream(cs.getInputStream());
                String operacion = entrada.readUTF();
                if (operacion.equals("createAccount")){
                    String user_data = entrada.readUTF();
                    boolean resultado = scribeTxt(user_data);
                    if (resultado){
                        salida.writeUTF("OK");
                    } else {
                        salida.writeUTF("NO-OK");
                    }
                }else if (operacion.equals("queryAccount")){
                    String user_data = entrada.readUTF();
                    String resultado = searchAccount(user_data);
                    salida.writeUTF(resultado);
                }
                System.out.println("Fin de la conexion");
    
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    cs.close();//Se finaliza la conexión con el cliente
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public boolean scribeTxt(String inputData) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean resultado = false;
        try {
            File file = new File("base.csv");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(inputData);
            bw.write('\n');

        } catch (IOException e) {
            e.printStackTrace();
            return resultado;
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
                resultado = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public String searchAccount(String Account){
        String resultado = "";
        File base = new File("base.csv");
        try{
            Scanner lector = new Scanner(base);
            while(lector.hasNextLine()){
                String[] line = lector.nextLine().split(",", 2);
                if (line[0].equals(Account)){
                    resultado = line[1];
                }
            }
            lector.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
        return resultado;

    }
}
