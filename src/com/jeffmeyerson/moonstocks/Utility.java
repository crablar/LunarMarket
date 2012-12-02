package com.jeffmeyerson.moonstocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import android.util.Log;

import com.jeffmeyerson.moonstocks.activities.MoonActivity;
import com.jeffmeyerson.moonstocks.pojos.Company;

// Let's try not to make this a kitchen sink.
public class Utility {

    private static final DecimalFormat twoDForm = new DecimalFormat("#.00");

	public static double getMarketAverage() {

		if (MoonActivity.companyList.size() == 0) {
			return 0;
		}

		final int time = MoonActivity.getTime();
		double total = 0;

		for (Company company : MoonActivity.companyList) {
			total += company.getStock().getPrice(time);
		}

		return total / MoonActivity.companyList.size();
	}

    public static double roundCurrency(double amount) {
        return Double.valueOf(twoDForm.format(amount)).doubleValue();
    }

    public static float roundCurrencyToFloat(double value) {
        return Float.valueOf(twoDForm.format(value)).floatValue();
    }

    public static byte[] serialize(Object o) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            final ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();

            return buf;
        } catch (IOException ioe) {
            Log.e("serializeObject", "IOException error", ioe);
            return null;
        }
    }

    public static Object deserialize(byte[] b) {
        if (b == null) {
            Log.e("Utility.deserialize", "byte array was null");
            return null;
        }

        try {
            final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            final Object object = in.readObject();
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
