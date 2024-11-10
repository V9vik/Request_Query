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
       try(ServerSocket serverSocket = new ServerSocket(port)){
           System.out.println("Сервер запущен на порту: "+ port );
           while (true){
               Socket socket = serverSocket.accept();
               System.out.println("Новое подключение!");
                hendlerClientConnected(socket);


           }
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }
   private void hendlerClientConnected(Socket socket) {
       Runnable task = () -> {
           try {
               processRequest(socket);

           } catch (IOException e) {
               System.err.println("Ошибка при обработке соединения: " + e.getMessage());
           }finally {
               try {

                   if (socket == null && !socket.isClosed()) {
                       socket.close();
                   }
               } catch (IOException e) {
                   System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
               }
           }
       };



       threadPool.execute(task);
   }

    private void processRequest(Socket socket) throws IOException {
        System.out.println("Обрабатываем запрос от клиента...");

        byte[] buffer = new byte[1024];
        int readBytes = socket.getInputStream().read(buffer);
        String request = new String(buffer, 0, readBytes).trim();

        System.out.println("Полученный запрос: " + request);

        String response = "Ответ сервера: Получено сообщение '" + request + "'\n";
        socket.getOutputStream().write(response.getBytes());
        socket.getOutputStream().flush();
    }


}
