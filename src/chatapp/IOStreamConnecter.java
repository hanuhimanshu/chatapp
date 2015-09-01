
package chatapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class connects the provided InputStream and OutputStream by continuously reading data from
 * InputStrea and flushing data into OutputStream into a new Thread.
 * When all the data is transfered that is if no byte is available into 
 * InputStream because the stream is at end of file, both InputStream and OutputStream are closed.
 * @see java.io.InputStream
 * @see java.io.OutputStream
 * @see java.lang.Thread
 * @author Himanshu
 */
public class IOStreamConnecter extends Thread {
    private InputStream inputstream;
    private OutputStream outputstream;
    private int buffer_size;
    private long totalbytessent;
    private boolean errors;
    /**
     * This Constructor creates a IOStreamConnector with given InputStream, OutputStream and buffer size
     * which is the maximum number of bytes of data that can be transfered from InputStream to OutputStream
     * at one attempt.
     * @param inputstream InputStream to be connected
     * @param outputstream OutputStream to be connected
     * @param buffer_size Buffer Size
     * @see java.io.InputStream
     * @see java.io.OutputStream
     * @see java.lang.Thread
     * 
     */
    public IOStreamConnecter(InputStream inputstream, OutputStream outputstream, int buffer_size) {
        this.inputstream = inputstream;
        this.outputstream = outputstream;
        this.buffer_size = buffer_size;
        this.errors=false;
    }
    /**
     * This Constructor creates a IOStreamConnector with given InputStream and OutputStream with default buffer size
     *  of 16 MB which is the maximum number of bytes of data that can be transfered from InputStream to OutputStream
     * at one attempt.
     * @param inputstream InputStream to be connected
     * @param outputstream OutputStream to be connected
     * @param buffer_size Buffer Size
     * @see java.io.InputStream
     * @see java.io.OutputStream
     * @see java.lang.Thread
     */
    public IOStreamConnecter(InputStream inputstream, OutputStream outputstream) {
        this.inputstream = inputstream;
        this.outputstream = outputstream;
        this.buffer_size=(1<<14);
        this.errors=false;
    }
    
    /**
     * This method connects the InputStream and OutputStream just by starting the
     * Thread. it is simply implemented by starting the Thread.
     * @see java.lang.Thread
     */
    public void connect()
    {
       start();
    }
    
    @Override
    public void run()
    {
        totalbytessent=0;
        try {
            int available;
            byte buffer[]=new byte[buffer_size];
            while((available=inputstream.read(buffer,0,buffer_size))!=-1)
            {
                outputstream.write(buffer,0, available);
                outputstream.flush();
                totalbytessent+=available;
            }
            outputstream.close();
            inputstream.close();
        } catch (IOException ex) {
            //Logger.getLogger(IOStreamConnecter.class.getName()).log(Level.SEVERE, null, ex);
            errors=true;
        }
    }
    
    /**
     * This method checks whether the transfer of data from InputStream to OutputStream is ongoing or not.
     * @return true if transfer is ongoing or false if any occurred or if transfer is not ongoing
     * 
     */
    public boolean isConnectionAlive()
    {
            return isAlive()&&(!errors);
    }
    
    /**
     * This method gives the total number of bytes transfered from InputStream to OutputSream.
     * @return Total number of bytes transfered.
     */
    public long getTotalBytesSent() {
        return totalbytessent;
    }
}
