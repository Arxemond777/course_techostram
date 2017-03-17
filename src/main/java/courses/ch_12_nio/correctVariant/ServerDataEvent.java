package courses.ch_12_nio.correctVariant;

import java.nio.channels.SocketChannel;

class ServerDataEvent {
    NioServer server;
    public SocketChannel socket;
    public byte[] data;

    ServerDataEvent(NioServer server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}
