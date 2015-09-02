/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles the all the server side required operations of a chatapp
 * @author Himanshu
 */
public class ServerHandler extends MessageHandler{
    private ServerSocket socketserver;
    
    /**
     * Constructs ServerHandler with given ServerSocket
     * @param socketserver ServerSocket at which a request will be accepted and the returned Socket will be used to send messages.
     * @throws IOException
     * @see java.net.ServerSocket
     * @see java.net.Socket
     */
    public ServerHandler(ServerSocket socketserver) throws IOException {
        super(socketserver.accept());
        this.socketserver = socketserver;
    }    
    
    @Override
    protected Socket getFileSocket() throws IOException {
       return socketserver.accept();
    }
    
   
}
