package protocol;

import com.google.gson.Gson;
import message.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 用于扩展序列化、反序列化算法
 */
public interface Serializer {

    //反序列化
    <T> T deserialize(Class<T> clazz, byte[] bytes);


    //序列化
    <T> byte[] serialize(T object);

    enum Algorithm implements Serializer {
       Java {
           @Override
           public <T> T deserialize(Class<T> clazz, byte[] bytes) {
               ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
               ObjectInputStream objectInputStream = null;
               try {
                   objectInputStream = new ObjectInputStream(byteArrayInputStream);
               } catch (IOException e) {
                   throw new RuntimeException("序列化异常", e);
               }
               Message message;
               try {
                   message = (Message) objectInputStream.readObject();
               } catch (Exception e) {
                   throw new RuntimeException("序列化异常", e);
               }
               return (T) message;
           }

           @Override
           public <T> byte[] serialize(T object) {
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               ObjectOutputStream objectOutputStream = null;
               try {
                   objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
               } catch (IOException e) {
                   throw new RuntimeException("序列化异常", e);
               }
               try {
                   objectOutputStream.writeObject(object);
               } catch (IOException e) {
                   throw new RuntimeException("序列化异常", e);
               }
               return byteArrayOutputStream.toByteArray();
           }
       },
       Json {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Gson gson = new Gson();
                return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), clazz);
            }
            @Override
            public <T> byte[] serialize(T object) {
                Gson gson = new Gson();
                return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
            }
        }
    }
}
