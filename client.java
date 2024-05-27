import java.net.*;
import java.io.*;


public class client {
     ServerSocket server;
     Socket socket;
     BufferedReader br ;
     PrintWriter out;
    public client(){
        try {
            System.out.println("sending request to client");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection done.");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startReading(){
        Runnable r1=()->{
          System.out.println("reading started...");
          while(true ){
             try {
              String msg;
              msg = br.readLine();
             if(msg.equals("exit")){
                 System.out.println("Server terminated the chat");
                 break;
             }
             System.out.println("Server:" +msg);
             } catch (Exception e) {
              e.printStackTrace();
             }
          }
        
      };
      new Thread(r1).start();
      }
      public void startWriting(){
          Runnable r2=()->{
              System.out.println("writer stsarted..");
            while(true){
              try {
                  BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                  String content = br1.readLine();
                  out.println(content);
                  out.flush();
              } catch (Exception e) {
                  e.printStackTrace();
              }
            }
          };
          new Thread(r2).start();
      }
    public static void main(String[] args) {
        System.out.println("this is client...");
        new client();
    }
    
}
