package com.mycompany.ecoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) throws IOException {
        //definición de puerto
        final int port = 5005;
        //definicion tamanio de buffer
        byte[] buffer = new byte[1024];

        try {
            System.out.println(">>> Starting Server UDP in Port: " + port + " <<< \n");
            //creacion del socket udp en el puerto 5001
            DatagramSocket UDPsocket = new DatagramSocket(port);
            
            //ciclo infinito para recibir varias solicitudes de clientes
            while (true) {
                //creacion de datagrama de solicitud del cliente
                DatagramPacket requestOfClient = new DatagramPacket(buffer, buffer.length);
                //recibimos la solicitud del cliente en bytes
                UDPsocket.receive(requestOfClient);
                //extraemos el buffer de datos
                String clientMessageRequest = new String(requestOfClient.getData(),0,0,requestOfClient.getLength());
                //extraer el puerto del cliente
                int portClient = requestOfClient.getPort();
                //extraer la IP del cliente
                InetAddress ipClient = requestOfClient.getAddress();
                //imprimir el mensaje del cliente en la terminal del servidor 
                System.out.println("Ip Cliente UDP: " + ipClient + "Puerto Cliente UDP: " + portClient + " Mensaje: " + clientMessageRequest);
                //declaramos una nueva variable string
                @SuppressWarnings("UnusedAssignment")
                String serverMessageResponse = new String();
                //copiamos el mensaje enviado por el cliente en nuestro nuevo string
                serverMessageResponse = clientMessageRequest.toUpperCase();
                //asignamos los bytes del nuevo string que contiene el mensaje del cliente
                buffer = serverMessageResponse.getBytes();
                //preparamos el datagrama con el buffer, tamanio de buffer, ip del cliente a envier y el puerto del cliente.
                DatagramPacket responseToClient = new DatagramPacket(buffer, buffer.length, ipClient, portClient);
                //enviamos el datagrama
                UDPsocket.send(responseToClient);
                //reiniciamos el buffer
                buffer = null;
                buffer = new byte[1024];
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
