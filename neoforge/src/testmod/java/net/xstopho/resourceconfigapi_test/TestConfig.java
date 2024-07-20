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

    public static final ConfigEntry<Integer> NORMAL_INTEGER, RANGED_INTEGER;
    public static final ConfigEntry<Double> NORMAL_DOUBLE, RANGED_DOUBLE;
    public static final ConfigEntry<Boolean> BOOLEAN;
    public static final ConfigEntry<String> NORMAL_STRING, RANGED_STRING;
    public static final ConfigEntry<List<String>> LIST;
    public static final ConfigEntry<ArrayList<String>> ARRAY_LIST;

    private static final List<String> ITEM_LIST = Arrays.asList(Items.DIAMOND.toString(), Items.DIAMOND_BLOCK.toString());
    private static final ArrayList<String> BLOCK_LIST = new ArrayList<>(List.of(Blocks.ANDESITE.toString(), Blocks.DIAMOND_ORE.toString()));

    static {
        BUILDER.comment("Integer Category").push("Integer");
        NORMAL_INTEGER = BUILDER.comment("normal").define("normal", 100);
        RANGED_INTEGER = BUILDER.comment("ranged").defineInRange("ranged", 25, 0, 50);

        BUILDER.pop().comment("Double Category").comment("With Multiline Comment").push("Double");
        NORMAL_DOUBLE = BUILDER.comment("Normal Double").comment("with Multiline Comment").define("normal", 2.5);
        RANGED_DOUBLE = BUILDER.defineInRange("ranged", 0.5, 0.0, 1.0);

        BUILDER.pop().push("String Category");
        NORMAL_STRING = BUILDER.define("normal", "this is a String");
        RANGED_STRING = BUILDER.defineInRange("ranged", "This is a ranged String", 0, 25);

        BUILDER.pop().push("Boolean Category");
        BOOLEAN = BUILDER.define("boolean", true);

        BUILDER.pop().push("List Category");
        LIST = BUILDER.define("list", ITEM_LIST);

        BUILDER.pop().push("ArrayList Category");
        ARRAY_LIST = BUILDER.define("array_list", BLOCK_LIST);
    }
}
