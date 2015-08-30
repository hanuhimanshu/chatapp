/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mychatapp;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Himanshu
 */
public class ClientHandler extends MessageHandler{

    public ClientHandler(Socket message_socket) throws IOException {
        super(message_socket);
    }

    @Override
    protected Socket getFileSocket() throws IOException {
        return new Socket(getMessage_socket().getInetAddress(), getMessage_socket().getPort());
    }
    
}
