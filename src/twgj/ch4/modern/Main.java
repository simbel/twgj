package twgj.ch4.modern;

import twgj.ch4.old.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pramodh on 3/13/14.
 */
public class Main {
    public static void main(String[] args) {
        testForkJoin();
    }

    public static void testForkJoin(){
        List<Update> lu = new ArrayList<Update>();
        String text = "";
        final Update.Builder up = new Update.Builder();
        final Author author = new Author("Joel");
        for (int i = 0; i < 256; i++) {
            text += "X";
            long now = System.currentTimeMillis();
            lu.add(up.author(author).updateText(text).createTime(now).build());
            try{
                Thread.sleep(1);
            } catch(InterruptedException e){}
        }
        Collections.shuffle(lu);
        Update[] updates = lu.toArray(new Update[0]);
        MicroBlogUpdateSorter sorter = new MicroBlogUpdateSorter(updates);
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(sorter);
        for(Update u: sorter.getResult()){
            System.out.println(u);
        }
    }

    public static void testCallable(){
        final Author author = new Author("JoeM");
        Callable<String> cb = new Callable() {
            @Override
            public String call() throws Exception {
                return author.getName();
            }
        };
        try {
            String s = cb.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testFuture(){
        Future<Long> fut = getNthPrime(1_000_000_000);
        Long result = null;
        while(result == null){
            try{
                result = fut.get(60, TimeUnit.MILLISECONDS);

            } catch(TimeoutException | ExecutionException | InterruptedException ex){
                System.out.println("Haven't found the prime yet");
            }
        }
        System.out.println("Found it " + result.longValue());
    }
    public static Future<Long>  getNthPrime(int number){
        return new Future<Long>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Long get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public Long get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
    }
    public static void testMicroBlogThread(){
        final Update.Builder up = new Update.Builder();
        final BlockingQueue<Update> lbq = new LinkedBlockingQueue<>(100);
        MicroBlogExample t1 = new MicroBlogExample(lbq, 10) {
            @Override
            public void doAction() {
                text = text + "X";
                Update u = up.author(new Author("claire")).updateText(text).build();
                boolean handed = false;
                try {
                    handed = updates.offer(u, 100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {

                }
                if(!handed){
                    System.out.println("Unable hand off to queue due to timeout");
                }

            }
        };

        MicroBlogExample t2 = new MicroBlogExample(lbq, 1000) {
            @Override
            public void doAction() {
                Update u = null;
                try {
                    u = lbq.take();
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        t1.start();
        t2.start();
    }
    public static void testMicroBlogThread1(){
        final Update.Builder up = new Update.Builder();
        final TransferQueue<Update> lbq = new LinkedTransferQueue<Update>();
        MicroBlogExampleThread t1 = new MicroBlogExampleThread(lbq, 10) {
            @Override
            public void doAction() {
                text = text + "X";
                Update u = up.author(new Author("claire")).updateText(text).build();
                boolean handed = false;
                try {
                    handed = updates.tryTransfer(u, 100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {

                }
                if(!handed){
                    System.out.println("Unable hand off to queue due to timeout");
                }

            }
        };

        MicroBlogExampleThread t2 = new MicroBlogExampleThread(lbq, 1000) {
            @Override
            public void doAction() {
                Update u = null;
                try {
                    u = lbq.take();
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        t1.start();
        t2.start();
    }
    public static void testDeadLock(){
        // Demonstrates a deadlock situation
        final MicroBlogNode local = new MicroBlogNode("localhost:8080");
        final MicroBlogNode other = new MicroBlogNode("localhost:9080");
        final Update first = (new Update.Builder()).author(new Author("joe")).updateText("updateText 1").build();
        final Update second = (new Update.Builder()).author(first.getAuthor()).updateText("updateText 2").build();

        for (int i = 0; i < 6; i++) {


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

    public static void testMicroBlogTimeline(){
        final CountDownLatch firstLatch = new CountDownLatch(1);
        final CountDownLatch secondLatch = new CountDownLatch(1);
        final Update.Builder ub = new Update.Builder();

        final CopyOnWriteArrayList<Update> l = new CopyOnWriteArrayList<>();
        l.add(ub.author(new Author("joe")).updateText("hello joe").build());
        l.add(ub.author(new Author("manga")).updateText("hello manga").build());
        l.add(ub.author(new Author("koti")).updateText("hello koti").build());

        ReentrantLock lock = new ReentrantLock();
        final MicroBlogTimeline tl1 = new MicroBlogTimeline(l, lock, "TL1");
        final MicroBlogTimeline tl2 = new MicroBlogTimeline(l, lock, "TL2");

        Thread t1 = new Thread(){
            public void run(){
                l.add(ub.author(new Author("gollam")).updateText("heloo gollam").build());
                tl1.prep();
                firstLatch.countDown();
                try{
                    secondLatch.await();
                }catch(InterruptedException ex){

                }
                tl1.printTimeline();
            }
        };

        Thread t2 = new Thread(){
            public void run(){
                try{
                    firstLatch.await();
                    l.add(ub.author(new Author("kevin")).updateText("heloo kevin").build());
                    tl2.prep();
                    secondLatch.countDown();
                }catch(InterruptedException ex){

                }
                tl2.printTimeline();
            }
        };
        t1.start();
        t2.start();

    }

    public static void main1(String[] args) {
        final int MAX_THREADS = 10;
        final int quorum = 1 + (int)(MAX_THREADS/2);
        final CountDownLatch cdl = new CountDownLatch(quorum);
        final Set<ProcessingThread> nodes = new HashSet<>();
        try {
            for (int i = 0; i < MAX_THREADS ; i++) {
                ProcessingThread local = new ProcessingThread("localhost:" + (9000 + i), cdl);
                nodes.add(local);
                local.start();
            }
            cdl.await();
        } catch (InterruptedException ex){}
        finally {

        }
    }

}