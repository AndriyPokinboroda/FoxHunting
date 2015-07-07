package com.pokinboroda.andriy.foxhunting.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The type Object serializer.
 */
public final class ObjectSerializer {
    private ObjectSerializer() { }

    /**
     * Deserialize from file.
     *
     * @param <T>  the type parameter
     * @param context the context
     * @param fileName the file name
     * @return the t
     */
    public static <T> T deserializeFromFile(final Context context,
                                            final String fileName) {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(
                    new File(context.getFilesDir(), fileName)));
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                /* Do nothing */
            }
        }
    }

    /**
     * Serialize to file.
     *
     * @param <T>  the type parameter
     * @param context the context
     * @param fileName the file name
     * @param object the object
     * @return the boolean
     */
    public static <T> boolean serializeToFile(final Context context,
                                              final String fileName,
                                              final T object) {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(
                    new File(context.getFilesDir(), fileName)));
            output.writeObject(object);
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                /* Do nothing */
            }
        }

        return true;
    }

}
