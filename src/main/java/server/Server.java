package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private ExecutorService threadPool;
    public Server(int port){
       this.port = port;
        this.threadPool = Executors.newFixedThreadPool(64);
    }
   public void start(){
       try {
           ServerSocket serverSocket = new ServerSocket(port);
           System.out.println("Сервер запущен на порту: "+ port );
           while (true){
               Socket socket = serverSocket.accept();
               System.out.println("Новое подключение!");
               threadPool.submit(new ClientHandler(socket));
               try {
                   socket.close();
               } catch (IOException e) {
                   System.err.println("Ошибка при закрытии сокета клиента: " + e.getMessage());
               }

           }
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

}
