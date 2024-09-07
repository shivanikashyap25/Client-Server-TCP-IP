import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ServerSocket serverSocket = new ServerSocket(9000);
		System.out.println("Server is up and running!");
		
		Socket socket = serverSocket.accept();
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String fileName = bReader.readLine();
		System.out.println("Searching for file: " + fileName); 
				 
		File file = new File("client.txt");
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
		String existingFile = file.getName();
		if(fileName.equals(existingFile)) {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
							
			System.out.println("File found! Streaming data from file to client...");
					
			String string = "";
			while((string = fileReader.readLine()) != null) {
				out.println(string);
				Thread.sleep(2000);
			}
					
			System.out.println("Streaming done");
			fileReader.close();
		}
		else {
				System.out.println("There is no file " + fileName + " in server!");
				out.println("Sorry, No such file exists!");
		}
		serverSocket.close();
	}
			
}