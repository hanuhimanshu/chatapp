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
 *
 * @author Himanshu
 */
public class ServerHandler extends MessageHandler{
    private ServerSocket server;

    public ServerHandler(ServerSocket server) throws IOException {
        super(server.accept());
        this.server = server;
    }    
    
    @Override
    protected Socket getFileSocket() throws IOException {
       return server.accept();
    }
    
   
}
