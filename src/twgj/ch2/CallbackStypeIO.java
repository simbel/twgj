package twgj.ch2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dliu2 on 2015/5/22.
 */
public class CallbackStypeIO {

    public static void main(String[] args) {
        try {
            Path file = Paths.get("C:\\Users\\dliu2\\Downloads\\kindle");

            if (Files.isReadable(file)) {
                AsynchronousFileChannel channel = AsynchronousFileChannel.open(file);

                ByteBuffer buffer = ByteBuffer.allocate(100_000);

                channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("Bytes read [" + result + "]");
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println(exc.getMessage());
                    }
                });
            } else {
                System.out.println("File does not exist.");
            }

            // If sub-thread don't have time to return before the main thread ends
            // we will lost the result from sub-thread
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
