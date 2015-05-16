import java.net.Socket;
import java.util.Hashtable;



public class Server {
	public static Hashtable<String,String> resultTable=new Hashtable<String,String>();
//	public static Hashtable<String,String> hashTable=new Hashtable<String,String>();
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		
		
		String jsonString = null;
		
		MultiThreadedServer server = new MultiThreadedServer(9000);
		System.out.println("server started on port 9000");
		new Thread(server).start();

		
		try {
			//sleep for 30 secs.
			System.out.println("Sleeping the main thread for 10 seconds to let clients connect to server");
		    Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		
		
		System.out.println("Stopping Server");
		server.stop();
		
		SDD1 strategy = new SDD1();
		
	}
}


