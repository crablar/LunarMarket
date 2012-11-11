package com.jeffmeyerson.moonstocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.util.Log;

// Let's try not to make this a kitchen sink.
public class Utility {

    public static byte[] serialize(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();

            return buf;
        } catch (IOException ioe) {
            Log.e("serializeObject", "error", ioe);

            return null;
        }
    }

    public static Object deserialize(byte[] b) {
        if (b == null) {
            Log.e("Utility.deserialize", "byte array was null");
            return null;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            Object object = in.readObject();
            in.close();
            return object;
        } catch (ClassNotFoundException cnfe) {
            Log.e("Utility.deserialize", "class not found error", cnfe);
            return null;
        } catch (IOException ioe) {
            Log.e("Utility.deserialize", "io error", ioe);
            return null;
        }
    }
}
