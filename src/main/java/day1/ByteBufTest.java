package day1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest {
    public static void main(String[] args) {
        //自动扩容 默认是直接内存
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        //堆内存
        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        log.info(buffer.toString());
        for (int i = 0; i < 300; i++) {
            stringBuilder.append("a");
        }
        buffer.writeBytes(stringBuilder.toString().getBytes());
        log.info(buffer.toString());
    }
}
