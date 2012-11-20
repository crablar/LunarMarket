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

	public static double getMarketAverage(){
		if(MoonActivity.companyList.size() == 0)
			return 0;
		int time = MoonActivity.getTime();
		double total = 0;
		for(Company company : MoonActivity.companyList){
			total += company.getStock().getPrice(time);
		}
		return total / MoonActivity.companyList.size();
	}
	
    public static double roundCurrency(double amount) {
        final DecimalFormat twoDForm = new DecimalFormat("#.00");
        Double result = Double.valueOf(twoDForm.format(amount));
        return result.doubleValue();
    }

    public static float roundCurrencyToFloat(double value) {
        final DecimalFormat twoDForm = new DecimalFormat("#.00");
        Float result = Float.valueOf(twoDForm.format(value));
        return result.floatValue();
    }
    
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
