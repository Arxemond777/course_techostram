package courses.ch_12_nio;

import java.nio.channels.SocketChannel;

public class ServerDataEvent
{
    NIOServer server;
    public SocketChannel socket;
    public byte[] data;

    ServerDataEvent(NIOServer server, SocketChannel socketChannel, byte[] data) {
        this.server = server;
        this.socket = socketChannel;
        this.data = data;
    }
}
