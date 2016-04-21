import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class DuoServer {
	public static void main(String []ags){
		boolean connected=true;
		try {
			ServerSocket ss=new ServerSocket(6000);
			
			int clientnum=0;
			while(connected){
				ServerThread sss=new ServerThread(ss.accept(),clientnum);
				sss.start();
				clientnum++;
				System.out.println(clientnum);
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 static class ServerThread extends Thread{
		private Socket s;
		int clientnum=0;
		public ServerThread(Socket s,int num){
			this.s=s;
			clientnum++;
		}
		public void run(){
			try {
				InputStream is=s.getInputStream();
				OutputStream os=s.getOutputStream();
				DataInputStream dis=new DataInputStream(is);
				String request=dis.readLine();
				System.out.println(request);
				PrintStream ps=new PrintStream(os);
				ps.println(request);
				s.close();} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
