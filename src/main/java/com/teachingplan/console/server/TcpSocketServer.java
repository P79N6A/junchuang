package com.teachingplan.console.server;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class TcpSocketServer {

    private Socket socket;

//    @PostConstruct
    public void init() {
        try {
            //创建一个ServerSocket,在端口号8000上监听客户端请求
            ServerSocket serverSocket = new ServerSocket(8000);
            socket = serverSocket.accept();

            //获取客户端套接字的输出流
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            //获取客户端输入流
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            //创建字符输入流接受键盘输入
            InputStreamReader isr= new InputStreamReader(System.in);
            BufferedReader br= new BufferedReader(isr);

            //消息
            String message;
            while (true) {
                //获取客户端输入流
                message = dis.readUTF();
                System.out.println("客户端发过来消息:"+message);
                if(message.equals("88"))
                    break;
                //读取键盘输入并写入到输出流
                message = br.readLine();
                dos.writeUTF(message);
            }

            dis.close();
            dos.close();
            serverSocket.close();
            socket.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }


    public static void main(String args[])
    {
        try{
            //创建客户端套接字
            Socket socket = new Socket("127.0.0.1", 8000);
            //获取套接字输入流
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            //获取套接字输出流
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            //从键盘输入
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            //聊天
            String message;
            while (true) {
                //读取键盘输入的字符，写到客户端输出流
                message = br.readLine();
                dos.writeUTF(message);
                if(message.equals("88"))
                    break;
                //读取服务器端的数据并打印到屏幕上
                message = dis.readUTF();
                System.out.println("服务器传回的消息:"+message);
            }
            //关闭流
            dis.close();
            dos.close();
            socket.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
