package twgj.ch4.modern;

/**
 * Created by dliu2 on 2015/5/26.
 */
import twgj.ch4.old.Update;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * Created by pramodh on 3/17/14.
 */
public class MicroBlogUpdateSorter extends RecursiveAction {
    private static final int SMALL_ENOUGH = 128;
    private final Update[] updates;
    private final int start, end;
    private final Update[] result;

    public MicroBlogUpdateSorter(Update[] updates){
        this(updates, 0, updates.length);
    }

    public MicroBlogUpdateSorter(Update[] updates, int start, int end){
        this.start = start;
        this.end = end;
        this.updates = updates;
        result = new Update[updates.length];
    }

    private void merge(MicroBlogUpdateSorter left, MicroBlogUpdateSorter right){
        int i = 0;
        int lCount = 0;
        int rCount = 0;
        while(lCount < left.size() && rCount < right.size()){
            result[i++] = (left.result[lCount].compareTo(right.result[rCount]) < 0) ? left.result[lCount++] : right.result[rCount++];
        }
        while(lCount < left.size()) result[i++] = left.result[lCount++];
        while(rCount < right.size()) result[i++] = right.result[rCount++];
    }

    public int size(){
        return end - start;
    }

    public Update[] getResult(){
        return result;
    }

    @Override
    protected void compute() {
        // RecursiveAction method
        if(size() < SMALL_ENOUGH){
            System.arraycopy(updates, start, result, 0, size());
            Arrays.sort(result, 0, size());
        } else {
            int mid = size()/2;
            MicroBlogUpdateSorter leftSorter = new MicroBlogUpdateSorter(updates, start, start+mid);
            MicroBlogUpdateSorter rightSorter = new MicroBlogUpdateSorter(updates, start+mid, end);
            invokeAll(leftSorter, rightSorter);
            merge(leftSorter, rightSorter);
        }
    }
}