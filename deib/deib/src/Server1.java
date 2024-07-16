import java.io.*;
import java.net.*;
public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2003);
        System.out.println("Server started. Waiting for client connection...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");
       // InputStream clientInput =new InputStreamReader(socket.getInputStream());
        //BufferedReader K=new BufferedReader(ClientInput);
        InputStreamReader M=new InputStreamReader(socket.getInputStream());
        BufferedReader K=new BufferedReader(M);
        PrintWriter PR=new PrintWriter(socket.getOutputStream());
        String userOption=K.readLine();
        System.out.println(userOption);
        if(userOption.equals("Register")){
            PR.println("Your information is being captured");
            PR.flush();
            PrintStream PS=new PrintStream(new FileOutputStream("Participant_data.bin", true));
            String un=K.readLine();
            System.out.println(un);
            PS.print("userName: ");
            PS.print(un);
            PS.print(", ");
            String fn=K.readLine();
            System.out.println(fn);
            PS.print("firstName:");
            PS.print(fn);
            PS.print(", ");
            String ln=K.readLine();
            System.out.println(ln);
            PS.print("lastName: ");
            PS.print(ln);
            PS.print(", ");
            String mail=K.readLine();
            System.out.println(mail);
            PS.print("emailAddress: ");
            PS.print(mail);
            PS.print(", ");
            String DOB=K.readLine();
            System.out.println(DOB);
            PS.print("dateOfBirth: ");
            PS.print(DOB);
            PS.print(", ");
            String SchholReg=K.readLine();
            System.out.println(SchholReg);
            PS.print("schoolRegistrationNumber: ");
            PS.print(SchholReg);
            PS.print(", ");
            String ep=K.readLine();
            System.out.println(ep);
            PS.print("password: ");
            PS.print(ep);
            PS.print(", ");
            InputStream imageInput=socket.getInputStream();
            FileOutputStream fop=new FileOutputStream("Participant_data.bin", true);
            byte[] buffer=new byte[5242880];
            int bytesRead;
            while((bytesRead=imageInput.read(buffer)) !=-1){
                fop.write(buffer, 0, bytesRead);
            }
            imageInput.close();
            fop.close();       
        }//Registration loop.
        else if(userOption.equals("Validation Status")){
            PR.println("Please enter your user name");
            PR.flush();
            String userName=K.readLine();
            System.out.println(userName);
            PR.println("Checking files. Please wait...");
            PR.flush();
        }//Validation loop ends here.
       /*  FileOutputStream fileOutputStream = new FileOutputStream("user_data.bin"); This kind of format overights the existing information in the file.

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        fileOutputStream.close();
        socket.close();
        serverSocket.close();

        System.out.println("Data stored in file: " + new File("user_data.bin").getAbsolutePath());
         FileOutputStream fileOutputStream = new FileOutputStream("user_data.bin", true); 
            ObjectOutputStream oos=new ObjectOutputStream(fileOutputStream);
            String un=K.readLine();
            System.out.println(un);
            oos.writeObject(un);
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("user_data.bin", true));*/
    }//Main ends here.
}
