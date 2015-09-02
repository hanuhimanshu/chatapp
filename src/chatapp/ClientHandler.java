/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.IOException;
import java.net.Socket;

/**
 * This class handles the all the client side required operations of a chatapp
 * @author Himanshu
 */
public class ClientHandler extends MessageHandler{

    /**
     * constructs a ClientHandler with given Socket
     * @param message_socket Socket which will be used to send messages
     * @throws IOException 
     * @see java.net.Socket
     */
    public ClientHandler(Socket message_socket) throws IOException {
        super(message_socket);
    }

    @Override
    protected Socket getFileSocket() throws IOException {
        return new Socket(getMessage_socket().getInetAddress(), getMessage_socket().getPort());
    }
    
}
