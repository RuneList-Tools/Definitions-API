import com.runelist.defs.items.ItemDefinitionLoader;

import java.io.File;
import java.util.Collections;

public class Test {


    public static void main(String[] args) {

        new ItemDefinitionLoader(true, new File("C:/Users/Home/Desktop/Programming/Runelist/TestDefDownload/"),true, Collections.emptyList()).init();
        System.out.println(ItemDefinitions.forId(995));

    }

}
