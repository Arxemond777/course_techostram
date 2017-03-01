package courses.Testing_TenLearning;

import java.io.*;
import java.nio.ByteBuffer;

public class SerializebleExamoke
{
}

class Message implements Serializable
{}

class Serializible123
{
    public byte[] encode(Message msg) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {

            out.writeObject(msg);
            byte[] objData = bos.toByteArray();
            int size = objData.length;

            ByteBuffer buf = ByteBuffer.allocate(size + 4);
            buf.putInt(size);
            buf.put(objData);

            return buf.array();
        } catch (IOException e) {
            e.getMessage();
        }
        return new byte[0];
    }

    public Message decode(byte[] data) throws Exception{
        ByteBuffer buf = ByteBuffer.wrap(data);
        int size = buf.getInt();
        if (size != data.length - 4)
            throw new Exception("Invalid Data");

        byte[] objData = new byte[size];
        buf.get(objData);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(objData);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (Message) in.readObject();
        } catch (IOException e) {
            throw new Exception("Failed decode object", e);
        } catch (ClassNotFoundException e) {
            throw new Exception("No class found", e);
        }
    }
}