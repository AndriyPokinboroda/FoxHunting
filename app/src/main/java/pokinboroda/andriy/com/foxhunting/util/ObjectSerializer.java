package pokinboroda.andriy.com.foxhunting.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer {
    private ObjectSerializer() { }

    public static <T> T deserializeFromFile(Context context, String fileName) {
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

    public static <T> boolean serializeToFile(Context context, String fileName,
                                              T object) {
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
