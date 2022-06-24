package com.mycompany.ecoclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;


public class Client {
     public static void main(String[] args) throws IOException{
         //definicion del puerto udp
         final int portServer =5005;
         //definidion del tamanio del buffer
         byte[] buffer = new byte[1024];
         //definicion de scanner para leer los datos tecleados por el usuario del cliente
         Scanner readText = new Scanner(System.in);
         
         try{
             System.out.println(">>> Starting Client UDP <<<\n");
            // asignacion de ip del server en nuestro caso es local
            InetAddress ipServer = InetAddress.getByName("localhost");
            //creacion del socket sin numero de puerto porque es quien va a enviar datagramas
            DatagramSocket socketUDP = new DatagramSocket();
            //peticion al usuario de que ingrese datos desde el teclado
            System.out.println("*** Ingresa el texto: \n ");
            //definicion de una variables string donde se va a almacenar lo ingresado por el usuario
            String clientMessage = new String();
            //almacenar el texto ingresdo 
            clientMessage = readText.nextLine();
            //asignando al buffer los bytes del string que contiene el mensaje 
            buffer = clientMessage.getBytes();
            //creando el datagrama a enviar con el buffer que contiene ls bytes, el tamanio de bytes, ip del servidor, puerto en el que escucha el server
            DatagramPacket requestToServer = new DatagramPacket(buffer, buffer.length, ipServer, portServer);
            //envio de los bytes de datos al servidor 
            System.out.println(">>> Sendindg message to Server UDP <<< \n ");
            socketUDP.send(requestToServer);
            //creando un datagrama donde almacenaremos la respuesta del servidor
            DatagramPacket responseToClient = new DatagramPacket(buffer, buffer.length);
            //recibimos los bytes de la respuesta del server
            socketUDP.receive(responseToClient);
            //extraemos el buffer de datos 
            String serverMessage = new String(responseToClient.getData());
            //imprimimos el mensaje que envia el servidor
            System.out.println("\n >>> Mensaje recibido del servidor <<< \n");
            System.out.println("> "+serverMessage);
            //cerramos el scanner de lectura 
            readText.close();
            //cerramos el socket
            socketUDP.close();
            
         }catch(SocketException ex){
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
