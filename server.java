import java.net.*;
import java.io.*;
public class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br ;
    PrintWriter out;
    public server(){
        try {
            server = new ServerSocket(7777); 
            System.out.println("server is ready to accept connection ");
            System.out.println("waiting...");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
    public void startReading(){
      //to perform reading and writing simultaneoously we use threading
      Runnable r1=()->{
        System.out.println("reading started...");
        try {
            while(true ){
                String msg;
                msg = br.readLine();
               if(msg.equals("exit")){
                   System.out.println("Client terminated the chat");
                   socket.close();
                   break;
               }
               System.out.println("Client:" +msg);
               } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2=()->{
            System.out.println("writer stsarted..");
            try {
                while(true){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }
              }   
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

            
        
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is server");
        new server();
    }
}