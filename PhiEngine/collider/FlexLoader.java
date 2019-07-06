package PhiEngine.collider;

import PhiEngine.assets.AssetLoader;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Alex on 08/03/2018.
 */
public class FlexLoader implements AssetLoader{
    private static final String num= "\\s*\\d+[.]\\d+\\s*";
    private static Pattern graph = Pattern.compile("([a-z]{1,3})\\s*:"+num+"");
    public static FlexCollider loadFlex(InputStream input){
        Scanner sc = new Scanner(input);
        while (sc.hasNext()){
            String line = sc.nextLine();

        }
        return null;
    }
}
