package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            String message = new String(buffer, 0, bytesRead);
            System.out.println("Получено сообщение: " + message);

            String response = "Вы отправили: " + message;
            output.write(response.getBytes());

        } catch (IOException e) {
            System.err.println("Ошибка при обработке клиента: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии сокета клиента: " + e.getMessage());
            }
        }
    }
}