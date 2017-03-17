package courses.ch_12_nio;

import java.nio.channels.SocketChannel;

public class ChangeRequest
{
    public SocketChannel socket;
    public int ops;
    private int CHANGEGEOPS;

    public ChangeRequest(SocketChannel socketChannel, int CHANGEGEOPS, int ops) {
        this.socket = socketChannel;
        this.ops = ops;
        this.CHANGEGEOPS = CHANGEGEOPS;
    }

    int type() {
        return CHANGEGEOPS;
    }
}
