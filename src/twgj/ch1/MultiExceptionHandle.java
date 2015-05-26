package twgj.ch1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

/**
 * Created by dliu2 on 2015/5/21.
 */
public class MultiExceptionHandle {

    public static void test() throws ParseException, IOException {

        Map<Integer, Map<String, String>> userLists = new HashMap<>();

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            df.parse("x20110731");
            new FileReader("file.txt").read();
        } catch (final Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        MultiExceptionHandle.test();
    }
}

