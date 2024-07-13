import java.io.*;
import java.net.*;
public class Server {
    public static void main(String [] args) throws IOException{
       ServerSocket ss= new ServerSocket(2209);
       System.out.println("Waiting......");
        Socket s=ss.accept();
        //System.out.println("Connected....");
        InputStreamReader M=new InputStreamReader(s.getInputStream());
        BufferedReader K=new BufferedReader(M);
        String mm=K.readLine();
        System.out.println(mm);
        PrintWriter PR=new PrintWriter(s.getOutputStream());
        PR.println("I am fine %");
        PR.flush();
    }
}
