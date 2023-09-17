package com.example.androidclient;//package com.example.androidclient;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import java.io.*;
//import java.net.*;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button myButton = findViewById(R.id.myButton);
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Сервер и порт, к которым мы хотим подключиться
//                String serverAddress = "192.168.1.105"; // Адрес сервера
//                int serverPort = 8000; // Порт, на котором работает сервер
//
//                // Строка с именем файла, который вы хотите запросить
//                String requestedFileName = "babka.png";
//
//                // Путь к папке, в которую сохранить полученное изображение
//                File internalStorageDir = getFilesDir();
//                String saveFolderPath = internalStorageDir.getAbsolutePath() + "/ReceivedImages/";
//
//                int i = 0;
//                try {
//
//                    // Устанавливаем соединение с сервером
//                    DatagramSocket socket = new DatagramSocket(serverPort);
//                    String command = "RECEIVE";
//                    // Открываем потоки для отправки и получения данных с сервера
//                    byte[] commandData = command.getBytes();
//                    DatagramPacket commandPacket = new DatagramPacket(commandData, commandData.length, InetAddress.getByName(serverAddress), serverPort);
//                    socket.send(commandPacket);
//
//
//                    String filename = "babka.png";
//                    byte[] filenameData = filename.getBytes();
//                    DatagramPacket filenamePacket = new DatagramPacket(filenameData , filenameData.length, InetAddress.getByName(serverAddress), serverPort);
//                    socket.send(filenamePacket);
//
//                    Log.d("AndroidClientTag","Asked for file " + filename);
//
//
//                    i++;
//                    String fileName = "A.txt";
//                    String filePath = saveFolderPath + fileName;
//
//                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//
//                    byte[] receiveData = new byte[4096];
//                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//
//                    while (true) {
//                        socket.receive(receivePacket);
//                        byte[] data = receivePacket.getData();
//                        int dataLength = receivePacket.getLength();
//                        if (dataLength <= 0) {
//                            break; // End of file
//
//                        }
//                        fileOutputStream.write(data, 0, dataLength);
//                        // Write the binary data to the file
//
//                    }
//
//                    fileOutputStream.close();
//                    Log.d("AndroidClientTag", "Received PNG image saved as: " + fileName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
//}

//////////


import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.R;

import java.io.*;
        import java.net.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FileDownloadTask().execute(); // Запуск AsyncTask для выполнения фоновой работы
            }
        });
    }

    // Внутренний класс AsyncTask для выполнения операции чтения пакетов в фоновом режиме
    private class FileDownloadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Здесь поместите вашу операцию чтения пакетов, как в вашем текущем коде
            // Не обновляйте интерфейс в этом методе
            // Путь к папке, в которую сохранить полученное изображение
            File internalStorageDir = getFilesDir();
            String saveFolderPath = internalStorageDir.getAbsolutePath() + "/ReceivedImages/";

            String serverAddress = "192.168.1.105";
            int serverPort = 8000;
            String requestedFileName = "babka.png";
            // Остальной код для чтения и сохранения файла

            int i = 0;
            try {

                // Устанавливаем соединение с сервером
                DatagramSocket socket = new DatagramSocket(serverPort);
                String command = "RECEIVE";
                // Открываем потоки для отправки и получения данных с сервера
                byte[] commandData = command.getBytes();
                DatagramPacket commandPacket = new DatagramPacket(commandData, commandData.length, InetAddress.getByName(serverAddress), serverPort);
                socket.send(commandPacket);


                String filename = "babka.png";
                byte[] filenameData = filename.getBytes();
                DatagramPacket filenamePacket = new DatagramPacket(filenameData , filenameData.length, InetAddress.getByName(serverAddress), serverPort);
                socket.send(filenamePacket);

                Log.d("AndroidClientTag","Asked for file " + filename);


                i++;
                String fileName = "A.txt";
                String filePath = saveFolderPath + fileName;

                FileOutputStream fileOutputStream = new FileOutputStream(filePath);

                byte[] receiveData = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                while (true) {
                    socket.receive(receivePacket);
                    byte[] data = receivePacket.getData();
                    int dataLength = receivePacket.getLength();
                    if (dataLength <= 0) {
                        break; // End of file

                    }
                    fileOutputStream.write(data, 0, dataLength);
                    // Write the binary data to the file

                }

                fileOutputStream.close();
                Log.d("AndroidClientTag", "Received PNG image saved as: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Этот метод будет выполнен в главном потоке после завершения фоновой задачи
            // Здесь вы можете выполнить обновление пользовательского интерфейса или другие действия
            // на основе результата операции чтения пакетов.
            Log.d("AndroidClientTag", "File download completed.");
        }
    }
}
