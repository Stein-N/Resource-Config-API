package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.builder.ResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

import java.util.List;

public class TestConfig {

    public static final ResourceConfigBuilder BUILDER = new ResourceConfigBuilder();

    private static final List<Integer> integerList = List.of(1, 2, 3, 4, 5);

    public static final ConfigEntry<Boolean> BOOLEAN, CATEGORY_BOOLEAN;
    public static final ConfigEntry<Byte> BYTE, CATEGORY_BYTE;
    public static final ConfigEntry<Character> CHARACTER, CATEGORY_CHARACTER;
    public static final ConfigEntry<Double> DOUBLE, CATEGORY_DOUBLE;
    public static final ConfigEntry<Float> FLOAT, CATEGORY_FLOAT;
    public static final ConfigEntry<Integer> INTEGER, CATEGORY_INTEGER;
    public static final ConfigEntry<Long> LONG, CATEGORY_LONG;
    public static final ConfigEntry<Short> SHORT, CATEGORY_SHORT;
    public static final ConfigEntry<String> STRING, CATEGORY_STRING;
    public static final ConfigEntry<ConfigType> ENUM, CATEGORY_ENUM;

    public static final ConfigEntry<Byte[]> BYTE_ARRAY;
    public static final ConfigEntry<Character[]> CHARACTER_ARRAY;
    public static final ConfigEntry<Double[]> DOUBLE_ARRAY;
    public static final ConfigEntry<Float[]> FLOAT_ARRAY;
    public static final ConfigEntry<Integer[]> INTEGER_ARRAY;
    public static final ConfigEntry<Long[]> LONG_ARRAY;
    public static final ConfigEntry<Short[]> SHORT_ARRAY;
    public static final ConfigEntry<String[]> STRING_ARRAY;
    public static final ConfigEntry<ConfigType[]> ENUM_ARRAY;

    public static final ConfigEntry<List<Integer>> INTEGER_LIST, CATEGORY_INTEGER_LIST;

    static {
        BOOLEAN = BUILDER.define("boolean", true);
        BYTE = BUILDER.define("byte", (byte) 12);
        CHARACTER = BUILDER.define("character", 'j');
        DOUBLE = BUILDER.define("double", 2.9);
        FLOAT = BUILDER.define("float", 35.5874f);
        INTEGER = BUILDER.define("integer", 100);
        LONG = BUILDER.define("long", 983649816749124L);
        SHORT = BUILDER.define("short", (short) 345);
        STRING = BUILDER.define("string", "Hello World!");
        INTEGER_LIST = BUILDER.define("integerList", integerList);
        ENUM = BUILDER.define("enum", ConfigType.CLIENT);

        BUILDER.push("Category");

        CATEGORY_BOOLEAN = BUILDER.define("boolean", false);
        CATEGORY_BYTE = BUILDER.define("byte", (byte) 34);
        CATEGORY_CHARACTER = BUILDER.define("character", 'l');
        CATEGORY_DOUBLE = BUILDER.define("double", 3.8);
        CATEGORY_FLOAT = BUILDER.define("float", 5.39874f);
        CATEGORY_INTEGER = BUILDER.define("integer", 388);
        CATEGORY_LONG = BUILDER.define("long", 238745L);
        CATEGORY_SHORT = BUILDER.define("short", (short) 457);
        CATEGORY_STRING = BUILDER.define("string", "Category String");
        CATEGORY_ENUM = BUILDER.define("enum", ConfigType.COMMON);
        CATEGORY_INTEGER_LIST = BUILDER.define("integerList", integerList);

        BUILDER.pop();

        BYTE_ARRAY = BUILDER.define("byteArray", new Byte[]{23, 65, 78});
        CHARACTER_ARRAY = BUILDER.define("characterArray", new Character[]{'l', 'ä', 'f'});
        DOUBLE_ARRAY = BUILDER.define("doubleArray", new Double[]{354.435, 3.9, 348.0});
        FLOAT_ARRAY = BUILDER.define("floatArray", new Float[]{35.35f, 213124.356f, 0.14896124f});
        INTEGER_ARRAY = BUILDER.define("integerArray", new Integer[]{1, 2, 3, 4, 5});
        LONG_ARRAY = BUILDER.define("longArray", new Long[]{1234532L, 1249612L, 314612L});
        SHORT_ARRAY = BUILDER.define("shortArray", new Short[]{(short) 213, (short) 124, (short) 2355});
        STRING_ARRAY = BUILDER.define("stringArray", new String[]{"Audi", "Porsche", "Mercedes"});
        ENUM_ARRAY = BUILDER.define("enumArray", new ConfigType[]{ConfigType.COMMON, ConfigType.CLIENT, ConfigType.SERVER});
    }
}
