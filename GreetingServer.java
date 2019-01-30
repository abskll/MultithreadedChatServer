// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   String isr; //input string
   String osr; //output string
   Boolean listenflag;
   Boolean breakflag = false;
   
   public GreetingServer(int port, Boolean listenflag) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        this.listenflag = listenflag;
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("listenflag is: " + listenflag);
            //System.out.println(in.readUTF());
            if (listenflag) {
                while(true){
                    System.out.println("Now reading from client");
                    isr = in.readUTF();
                    System.out.println(isr);
                    if (isr.equals("quit")) {
                        System.out.println("output thread stopped");
                        break;
                    }
    
                }               
            } else {
                while(true) {
                    System.out.println("Now waiting to send to client");
                    osr = br.readLine();
                    out.writeUTF(osr);
                    if(osr.equals("quit")) {
                        System.out.println("input thread stopped");
                        break;
                    }
                }
            }
            

            //System.out.println(in.readUTF());
            //DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
               + "\nGoodbye!");
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread tlisten = new GreetingServer(port, true);
         tlisten.start();
         Thread tsend = new GreetingServer(port+1, false);
         tsend.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}