/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Himanshu
 */
public class IOStreamConnecter implements Runnable {
    private InputStream inputstream;
    private OutputStream outputstream;
    private Thread thread;
    private int buffer_size;
    
    public IOStreamConnecter(InputStream inputstream, OutputStream outputstream, int buffer_size) {
        this.inputstream = inputstream;
        this.outputstream = outputstream;
        this.buffer_size = buffer_size;
    }

    public IOStreamConnecter(InputStream inputstream, OutputStream outputstream) {
        this.inputstream = inputstream;
        this.outputstream = outputstream;
        buffer_size=1<<17;
    }
       
    public void connect()
    {
       thread=new Thread(this);
       thread.start();
    }
    
    public void join() throws InterruptedException
    {
        thread.join();
    }
    
    @Override
    public void run()
    {
        try {
            int available;
            byte buffer[]=new byte[buffer_size];
            while((available=inputstream.read(buffer,0,buffer_size))!=-1)
            {
                outputstream.write(buffer,0, available);
                outputstream.flush();
            }
            outputstream.close();
            inputstream.close();
        } catch (IOException ex) {
            Logger.getLogger(IOStreamConnecter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isConnectionAlive()
    {
        if(thread==null)
            return false;
        else
            return thread.isAlive();
    }
}
