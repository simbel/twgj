package twgj.ch4;

/**
 * Created by dliu2 on 2015/5/26.
 */
public class Update {
    private final Author author;
    private final String updateText;
    final long timestamp;

    private Update(Builder b_) {
        author = b_.author;
        updateText = b_.updateText;
        timestamp = System.currentTimeMillis();
    }

    public static class Builder implements ObjBuilder<Update> {
        private Author author;
        private String updateText;

        public Builder author(Author author_) {
            author = author_;
            return this;
        }

        public Builder updateText(String updateText_) {
            updateText = updateText_;
            return this;
        }

        public Update build() {
            return new Update(this);
        }
    }

    public static void main(String[] args) {
        //usage, it's easier for understanding than pass lots parameter in constructors
        Update.Builder b = new Update.Builder();
        Author author = new Author();
        String updateText = "yyy";
        Update obj = b.author(author).updateText(updateText).build();

        System.out.println("Author: " + obj.author + ", UpdateText: " + obj.updateText);

    }
}


