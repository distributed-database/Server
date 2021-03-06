//Downbloads the initial statistics from the clients to server and saves it in a hash map
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.ws.Closeable;

/**

 */
public class WorkerRunnable implements Runnable{

    public Socket clientSocket = null;
    public String relationName   = null;
    

    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            
            
        	
        	InputStream input  = clientSocket.getInputStream();
            
            DataInputStream dis=new DataInputStream(input);  
            String responseJson = (String)dis.readUTF();
            System.out.println("Transfering statistics");
            System.out.println(responseJson);
            JSONArray array = new JSONArray(responseJson);
            
            ServerDatabase serverStatsDatabase = new ServerDatabase();
            System.out.println("Updating the db");
            serverStatsDatabase.updateDB(array);
            
            dis.close();
            input.close();
            
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

