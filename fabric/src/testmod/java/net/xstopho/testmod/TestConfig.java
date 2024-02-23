package net.xstopho.testmod;

import net.xstopho.resource_config_api.builder.ConfigBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TestConfig {

    static final ConfigBuilder BUILDER = new ConfigBuilder();

    static Supplier<Integer> int_normal, int_ranged, int_comment, int_multiple_comments;
    static Supplier<Double> double_normal, double_ranged, double_comment, double_multiple_comments;
    static Supplier<String> string_normal, string_ranged, string_comment, string_multiple_comments;
    static Supplier<Boolean> boolean_normal, boolean_multipleComments, comment_category;

    static Supplier<List<String>> list_normal, list_comment, list_multiple_comments;

    static List<String> test_list = Arrays.asList("minecraft:diamond", "minecraft:charcoal", "minecraft:emerald");


    static {

        BUILDER.comment("Integer Category Comment").push("Integer");
        int_normal = BUILDER.define("normal", 100);
        int_ranged = BUILDER.defineInRange("ranged", 25, 0, 50);
        int_comment = BUILDER.comment("This is a commented Integer Value").define("comment", 40);
        int_multiple_comments = BUILDER.comment("First line comment")
                .comment("Second line comment").defineInRange("mulitple_comment", 10, 0, 25);

                BUILDER.pop().comment("Double Category Comment").push("Double");
        double_normal = BUILDER.define("normal", 2.5);
        double_ranged = BUILDER.defineInRange("ranged", 4.5, 0.0, 10.0);
        double_comment = BUILDER.comment("This is a commented Double Value").define("comment", 0.25);
        double_multiple_comments = BUILDER.comment("First line comment")
                .comment("Second line comment").defineInRange("mulitple_comment", 10.0, 0.0, 25.0);

        BUILDER.pop().comment("String Category Comment").push("String");
        string_normal = BUILDER.define("normal", "This is a String Value");
        string_ranged = BUILDER.defineInRange("ranged", "This String can only be 42 characters long", 0, 42);
        string_comment = BUILDER.comment("This is a commented String Value").define("comment", "One of many Strings");
        string_multiple_comments = BUILDER.comment("First line comment")
                .comment("Second line comment").defineInRange("mulitple_comment", "This is also a ranged Value", 0, 50);

        BUILDER.pop().comment("Boolean Category Comment").push("Boolean");
        boolean_normal = BUILDER.define("normal", true);
        boolean_multipleComments = BUILDER.comment("First line comment")
                .comment("Second line comment").define("multiple_comment", false);

        BUILDER.pop().comment("List Category Comment").push("List");
        list_normal = BUILDER.define("normal", test_list);
        list_comment = BUILDER.comment("This is a commented List").define("comment", test_list);
        list_multiple_comments = BUILDER.comment("First Line comment").comment("Second Line comment")
                .define("multiple_comments", test_list);

        BUILDER.pop().comment("Categories can also have").comment("multiple comment lines").push("Comment Category");
        comment_category = BUILDER.define("needed", true);

        BUILDER.pop();
    }
}
