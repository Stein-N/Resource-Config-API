package net.xstopho.resourceconfigapi.util;

import java.io.*;

public class ConfigUtils {

    public static byte[] serializeConfig(Class<?> clazz) {
        try(ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();) {
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);

            objectOutput.writeObject(clazz);
            return byteOutput.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize Config class!", e);
        }
    }

    public static Class<?> deserializeConfig(byte[] bytes) {
        try(ByteArrayInputStream byteInput = new ByteArrayInputStream(bytes)) {
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);

            return (Class<?>) objectInput.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize Config class!", e);
        }
    }

    public static boolean isSerializable(Class<?> clazz) {
        return Serializable.class.isAssignableFrom(clazz);
    }
}
