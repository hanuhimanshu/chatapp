/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author Himanshu
 */
public abstract class MessageHandler {
    private Socket message_socket;
    private Socket file_socket;
    private IOStreamConnecter io_stream_connecter;
    private BufferedReader message_reader;
    private PrintWriter message_writer;
    public static final int TYPE_MESSAGE=0;
    public static final int TYPE_FILE=1;
    public void sendMessage(String message) throws IOException
    {
        message_writer.println("msg:"+message);
        message_writer.flush();
    }
    
    public String receiveMessage() throws IOException
    {
        return message_reader.readLine();
        
    }
    
    public void receiveFile(String file_info,File path) throws IOException
    {
        file_socket=getFileSocket();
        String info[]=file_info.split(":");
        File file=new File(path,info[0]);
        io_stream_connecter=new IOStreamConnecter(file_socket.getInputStream(), new FileOutputStream(file));
        io_stream_connecter.connect();
    }
    
    public boolean isFileTransferOnline()
    {
        if(io_stream_connecter==null)
            return false;
        else
            return io_stream_connecter.isConnectionAlive();
    }

    protected abstract Socket getFileSocket() throws IOException;
    
    public void sendFile(File file) throws IOException
    {
        message_writer.println("file:"+file.getName()+":"+file.length());
        file_socket=getFileSocket();
        io_stream_connecter=new IOStreamConnecter(new FileInputStream(file),file_socket.getOutputStream());
        io_stream_connecter.connect();
    }
    
    public void waitToCompleteFileTransfer() throws InterruptedException
    {
        io_stream_connecter.join();
    }

    public MessageHandler(Socket message_socket) throws IOException {
        this.message_socket = message_socket;
        message_reader=new BufferedReader(new InputStreamReader(message_socket.getInputStream()));
        message_writer=new PrintWriter((message_socket.getOutputStream()));
    }

    public Socket getMessage_socket() {
        return message_socket;
    }
    
    public String getLocalIp()
    {
        return message_socket.getInetAddress().toString();
    }
}
