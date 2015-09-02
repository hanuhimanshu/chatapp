/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class handles the sending and  receiving of messages and  a file from and into a socket.
 * This class creates a new socket and uses it for sending or receiving a file and sends and receives 
 * file information over the given the socket.
 * @author Himanshu
 */
public abstract class MessageHandler {
    private Socket message_socket;
    private Socket file_socket;
    private IOStreamConnecter io_stream_connecter;
    private BufferedReader message_reader;
    private PrintWriter message_writer;

    /**
     * This method sends the given message in the format
     * msg:message that is after appending your message to String "msg:" 
     * over the given socket using PrintWriter.
     * This method is implemented  by printing the String, terminating the String then flushing the Writer.
     * @param message Message to be sent
     * @throws IOException 
     * @see java.io.PrintWriter
     */
    public void sendMessage(String message) throws IOException
    {
        message_writer.println("msg:"+message);
        message_writer.flush();
    }
    
    /**
     * This method reads a line from the InputStream of the Socket using BufferedReader.
     * @return the line read.
     * @throws IOException 
     * @see java.io.BufferedReader
     */
    public String receiveMessage() throws IOException
    {
        return message_reader.readLine();
    }
    
    /**
     * This method receives a file at the specified path by obtaining a new Socket from getFileSocket().
     * It is implemented by creating a new File at specified path and with the given name in file_info 
     * in the format file_name:file_size . It uses IOStreamConnector to connect FileOutputStream of the file 
     * and Socket's InputStream.
     * @param file_info String in format file_name:file_size.
     * @param path Path at which the file is to be stored.
     * @throws IOException 
     * @see IOStreamConnecter
     * @see java.io.File
     * @see java.net.Socket
     * @see java.io.FileOutputStream
     * @see java.io.InputStream
     * 
     */
    public void receiveFile(String file_info,File path) throws IOException
    {
        file_socket=getFileSocket();
        String info[]=file_info.split(":");
        File file=new File(path,info[0]);
        io_stream_connecter=new IOStreamConnecter(file_socket.getInputStream(), new FileOutputStream(file));
        io_stream_connecter.connect();
    }
    
    /**
     * This method tells whether the file transfer is in process or not.
     * This is implemented as whether IOStreamConnecter Thread is alive or not.
     * @return  true if the transfer is file tranfer is ongoing otherwise false.
     */
    public boolean isFileTransferOnline()
    {
        if(io_stream_connecter==null)
            return false;
        else
            return io_stream_connecter.isConnectionAlive();
    }

    protected abstract Socket getFileSocket() throws IOException;
    
    
    /**
     * This method sends the file by using the new Socket given by getFileSocket() and uses IOStreamConnector
     * to connect the FileInputStream of given file and Socket's OutputStream to flush the data from file to 
     * Socket. Before obtaining the Socket, this method sends the file information to the Socket which is used 
     * to send messages in the format file:file_name:file_size .
     * @param file file to be sent.
     * @throws IOException 
     * @see IOStreamConnecter
     * @see java.io.File
     * @see java.net.Socket
     * @see java.io.FileInputStream
     * @see java.io.OutputStream
     */
    public void sendFile(File file) throws IOException
    {
        message_writer.println("file:"+file.getName()+":"+file.length());
        file_socket=getFileSocket();
        io_stream_connecter=new IOStreamConnecter(new FileInputStream(file),file_socket.getOutputStream());
        io_stream_connecter.connect();
    }
    
    /**
     * This method halts the control and wait for IOStreamConnector to finish the file transfer.
     * This is implemented just by joining the IOStreamConnecter Thread.
     * @throws InterruptedException 
     * @see IOStreamConnecter
     */
    public void waitToCompleteFileTransfer() throws InterruptedException
    {
        io_stream_connecter.join();
    }

    /**
     * Constructs a MessageHandler which uses message_socket as primary socket to send messages.
     * @param message_socket
     * @throws IOException 
     */
    public MessageHandler(Socket message_socket) throws IOException {
        this.message_socket = message_socket;
        message_reader=new BufferedReader(new InputStreamReader(message_socket.getInputStream()));
        message_writer=new PrintWriter((message_socket.getOutputStream()));
    }

    /**
     * Give the primary socket using which this MessageHandler was created.
     * @return primary Socket
     */
    public Socket getMessage_socket() {
        return message_socket;
    }
    
    /**
     * Gives the IP address to which the MessageHandler's socket is bound to.
     * @return local IP address
     */
    public String getLocalIp()
    {
        return message_socket.getInetAddress().toString();
    }
}
