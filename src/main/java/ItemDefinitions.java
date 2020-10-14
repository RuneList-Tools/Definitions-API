import com.runelist.defs.items.ItemDefinition;
import org.jetbrains.annotations.NotNull;

public class ItemDefinitions {

    @NotNull
    public static ItemDefinition forId(int item) {
        return ItemDefinition.Companion.forId(item);
    }

}
