package com.pokinboroda.andriy.foxhunting.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class provide static method for saving object into file
 * and restoring from file in internal storage by serialization.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 */
public final class ObjectSerializer {
    private ObjectSerializer() { }

    /**
     * Deserialize from file.
     * Return object with type <T> or null if eny exception was thrown.
     *
     * @param <T>  the type parameter
     * @param context the application context
     * @param fileName the file name which store the object
     * @return the object with type T or null
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
     * @param context the application context
     * @param fileName the file name
     * @param object the object
     * @return true if object serialized, otherwise false
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
