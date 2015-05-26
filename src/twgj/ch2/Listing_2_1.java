package twgj.ch2;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dliu2 on 2015/5/22.
 */
public class Listing_2_1 {

    public static void main(String[] args) {
        Path listing = Paths.get("/usr/bin/zip");
        System.out.println("File name [" + listing.getFileName() + "]");
        System.out.println("Number of Name Elements in the path [" + listing.getNameCount() + "]");
        System.out.println("Parent Path [" + listing.getParent() + "]");
        System.out.println("Root of Path [" + listing.getRoot() + "]");
        System.out.println("Subpath from Root, 2 elements deep [" + listing.subpath(0,2) + "]");
    }
}
