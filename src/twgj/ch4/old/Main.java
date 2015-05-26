package twgj.ch4.old;

/**
 * Created by dliu2 on 2015/5/26.
 */
public class Main {
    public static void testBuilderAuthor() {
        Author author = new Author("Dummy");
        String updateText = "Hello Dummy";
        // Creating a immutable update object
        Update update = (new Update.Builder()).author(author).updateText(updateText).build();
    }

    public static void main(String[] args) {
        // Demonstrates a deadlock situation
        final MicroBlogNode local = new MicroBlogNode("localhost:8080");
        final MicroBlogNode other = new MicroBlogNode("localhost:9080");
        final Update first = (new Update.Builder()).author(new Author("joe")).updateText("updateText 1").build();
        final Update second = (new Update.Builder()).author(first.getAuthor()).updateText("updateText 2").build();

        for (int i = 0; i < 1; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    local.propagateUpdate(first, other);
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    other.propagateUpdate(second, local);
                }
            }).start();
        }
    }
}