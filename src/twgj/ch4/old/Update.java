package twgj.ch4.old;

/**
 * Created by dliu2 on 2015/5/26.
 */
public class Update implements Comparable {
    private final Author author;
    private final String updateText;
    private final long time;
    private Update(Builder builder){
        this.author = builder.author;
        this.updateText = builder.updateText;
        this.time = builder.time;
    }

    public Author getAuthor() {
        return author;
    }

    public String getUpdateText() {
        return updateText;
    }

    public long getTime(){
        return this.time;
    }


    public static class Builder {
        private Author author;
        private String updateText;
        private long time;
        public Builder author(Author author){
            this.author = author;
            return this;
        }
        public Builder updateText(String updateText){
            this.updateText = updateText;
            return this;
        }
        public Builder createTime(long time){
            this.time = time;
            return this;
        }
        public Update build(){
            return new Update(this);
        }
    }
    @Override
    public int compareTo(Object other){
        if (this.getTime() > ((Update)other).getTime())
            return 1;
        else if(this.getTime() < ((Update)other).getTime())
            return -1;
        else
            return 0;

    }

    @Override
    public String toString(){
        return "millis "+ time + " text " +getUpdateText();
    }
}