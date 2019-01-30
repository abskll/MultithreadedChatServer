// File Name GreetingClient.java
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class GreetingClient extends Thread {
    Boolean listenflag = false;
    String sr;
    String serverName;
    int port;
    public GreetingClient(int port, Boolean listenflag, String serverName){
        this.listenflag = listenflag;
        this.serverName = serverName;
        this.port = port;
    }
    public void run(){
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            if (listenflag) {
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                while(true) {
                    System.out.println("Waiting for client input");
                    sr = in.readUTF();
                    System.out.println(sr);
                   if (sr.equals("quit")) {
                       break;
                   }
       
                }
            } else {
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                while(true) {
                    sr = br.readLine();
                    out.writeUTF(sr);
                   if (sr.equals("quit")) {
                       break;
                   }
       
                }
            }


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }
   public static void main(String [] args) {
      String serverName = args[0];
      int portsend = Integer.parseInt(args[1]);
      int portrecieve = portsend + 1;
      Thread tsend = new GreetingClient(portsend, false, serverName);
      tsend.start();
      Thread trecieve = new GreetingClient(portrecieve, true, serverName);
      trecieve.start();
            /**
      BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
      try {
         System.out.println("Sending to " + serverName + " on port " + portsend);
         System.out.println("Recieving from " + serverName + " on port " + portrecieve);
         Socket clientinput = new Socket(serverName, portrecieve);
         Socket clientoutput = new Socket(serverName, portsend);
         
         System.out.println("Just connected to " + clientinput.getRemoteSocketAddress());
         OutputStream outToServer = clientoutput.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         InputStream inFromServer = clientinput.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         //out.writeUTF("Hello from " + clientoutput.getLocalSocketAddress());
         while(true) {
             sr = br.readLine();
             out.writeUTF(sr);
             System.out.println("Waiting for client input");
             sr = in.readUTF();
             System.out.println(sr);
            if (sr.equals("quit")) {
                break;
            }

         }
         //out.writeUTF(br.readLine());
         //InputStream inFromServer = client.getInputStream();
         //DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         clientinput.close();
         clientoutput.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      **/
   }
}