import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;


public class myserversocket {
	 static ArrayList<Socket> Allsocket=new ArrayList<Socket>();
	 
	public static void main(String[]args)throws IOException{
		boolean connected=true;
	
	ServerSocket ss=new ServerSocket(6001);
	while(connected){
		Socket s=ss.accept();
		Allsocket.add(s);         //只要有客户端连接就在Allsocket中增加
		new serverthread(s).start();
	}
	}
	static class serverthread extends Thread{
		private  Socket s=null;
		BufferedReader br=null;
		serverthread(Socket s){
			this.s=s;
			try {
				br=new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run(){
			try{
				String content=null;
				while((content=readFromClient())!=null){		//收到消息是发给每个客户端
					for(Iterator<Socket> it=Allsocket.iterator();it.hasNext();)
					{
						Socket s=it.next();
					
						try{
						OutputStream os=s.getOutputStream();
						os.write((content+"\n").getBytes("utf-8"));
					}
						catch(SocketException e){    //处理客户端关闭时的情况
							e.printStackTrace();
							it.remove();
							System.out.println(Allsocket);
						}
				}}}
				
				
			
				catch(IOException E){
			E.printStackTrace();
		}
			
		
}
private String readFromClient(){		//检测是否有消息发来
	try{
		return br.readLine();
		
	}
	catch(IOException e){
		e.printStackTrace();
		Allsocket.remove(s);
	}
	return null;
}
}}
