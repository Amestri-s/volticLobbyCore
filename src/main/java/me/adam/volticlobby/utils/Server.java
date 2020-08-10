package me.adam.volticlobby.utils;

import org.bukkit.Bukkit;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server  {

    private String host;
    private int port;

    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    public Server(String host) {
        this.host = host;
        this.port = 25565;
    }

    public Server(int port) {
        this.host = "127.0.0.1";
        this.port = port;
    }

    public String parseData(Connection connection) {
        try{
            Socket socket = new Socket();
            OutputStream os;
            DataOutputStream dos;
            InputStream is;
            InputStreamReader isr;

            socket.setSoTimeout(0);
            socket.connect(new InetSocketAddress(host, port));

            os = socket.getOutputStream();
            dos = new DataOutputStream(os);

            is = socket.getInputStream();
            isr = new InputStreamReader(is, Charset.forName("UTF-16BE"));

            dos.write(new byte[] {(byte)(0xFE), (byte)0x01});

            int packetId = is.read();

            if(packetId == -1) {
                Bukkit.getConsoleSender().sendMessage("§c(EOS)");
            }

            if(packetId != 0xFF) {
                Bukkit.getConsoleSender().sendMessage("§cInvalid ID! " + packetId);
            }

            int length = isr.read();

            if(length == -1) {
                Bukkit.getConsoleSender().sendMessage("§c(EOS)");
            }

            if(length == 0) {
                Bukkit.getConsoleSender().sendMessage("§cInvalid length!");
            }

            char[] chars = new char[length];

            if(isr.read(chars, 0, length) != length) {
                Bukkit.getConsoleSender().sendMessage("§c(EOS)");
            }

            String string = new String(chars);
            String[] data = string.split("\0");

            if(connection == Connection.PLAYERS_ONLINE) {
                return Integer.parseInt(data[4])+"/" + Integer.parseInt(data[5]);
            }else if(connection == Connection.MOTD) {
                return data[3];
            }else if(connection == Connection.SERVER_VERSION) {
                return data[2];
            }else {
                return "Invalid request";
            }

        }catch(Exception e) {
            return "Offline";
        }
    }

    public enum Connection {
        PLAYERS_ONLINE, SERVER_VERSION, MOTD;
    }
}
