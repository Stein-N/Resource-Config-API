package net.xstopho.resourceconfigapi_test;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.xstopho.resourceconfigapi.builder.ResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TestConfig {

    public static final ResourceConfigBuilder BUILDER = new ResourceConfigBuilder();

    public static Supplier<Integer> NORMAL_INTEGER, RANGED_INTEGER, SYNCED_INTEGER;
    public static Supplier<Double> NORMAL_DOUBLE, RANGED_DOUBLE, SYNCED_DOUBLE;
    public static Supplier<Boolean> BOOLEAN, SYNCED_BOOLEAN;
    public static Supplier<String> NORMAL_STRING, RANGED_STRING, SYNCED_STRING;
    public static Supplier<List<String>> LIST;
    public static Supplier<ArrayList<String>> ARRAY_LIST;

    private static final List<String> ITEM_LIST = Arrays.asList(Items.DIAMOND.toString(), Items.DIAMOND_BLOCK.toString());
    private static final ArrayList<String> BLOCK_LIST = new ArrayList<>(List.of(Blocks.ANDESITE.toString(), Blocks.DIAMOND_ORE.toString()));

    static {
        BUILDER.comment("Integer Category").push("Integer");
        NORMAL_INTEGER = BUILDER.comment("normal").define("normal", 100);
        RANGED_INTEGER = BUILDER.comment("ranged").defineInRange("ranged", 25, 0, 50);
        SYNCED_INTEGER = BUILDER.comment("syncable").sync().define("synced", 100);

        BUILDER.pop().comment("Double Category").comment("With Multiline Comment").push("Double");
        NORMAL_DOUBLE = BUILDER.comment("Normal Double").comment("with Multiline Comment").define("normal", 2.5);
        RANGED_DOUBLE = BUILDER.defineInRange("ranged", 0.5, 0.0, 1.0);
        SYNCED_DOUBLE = BUILDER.sync().define("synced", 2.5);

        BUILDER.pop().push("String Category");
        NORMAL_STRING = BUILDER.define("normal", "this is a String");
        RANGED_STRING = BUILDER.defineInRange("ranged", "This is a ranged String", 0, 25);
        SYNCED_STRING = BUILDER.sync().define("synced", "String should get synced.");

        BUILDER.pop().push("Boolean Category");
        BOOLEAN = BUILDER.define("normal", true);
        SYNCED_BOOLEAN = BUILDER.sync().define("synced", true);

        BUILDER.pop().push("List Category");
        LIST = BUILDER.define("list", ITEM_LIST);

        BUILDER.pop().push("ArrayList Category");
        ARRAY_LIST = BUILDER.define("array_list", BLOCK_LIST);
    }
}
