package PhiEngine.assets;

import java.nio.file.Path;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Alex on 14/12/2017.
 */
public class AssetCache {
    private static WeakHashMap<Object,String> cashe = new WeakHashMap<>();
    private Map<Object, String> loaders;

}
