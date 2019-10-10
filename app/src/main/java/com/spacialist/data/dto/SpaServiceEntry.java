package com.spacialist.data.dto;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.spacialist.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpaServiceEntry implements Parcelable {

    private static final String TAG = SpaBusinessEntry.class.getSimpleName();

    public String serviceId;
    public String serviceName;
    public Date serviceDuration;
    public String serviceDescription;
    public String serviceType;
    public BigDecimal price;

    public SpaServiceEntry(String serviceId, String serviceName,
                           Date serviceDuration, String serviceDescription,
                           String serviceType, BigDecimal servicePrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceDuration = serviceDuration;
        this.serviceDescription = serviceDescription;
        this.serviceType = serviceType;
        this.price = servicePrice;
    }

    protected SpaServiceEntry(Parcel in) {
        serviceId = in.readString();
        serviceName = in.readString();
        serviceDescription = in.readString();
        serviceDuration = new Date(in.readLong());
        serviceType = in.readString();
        price = new BigDecimal(in.readLong());
    }

    public static final Creator<SpaServiceEntry> CREATOR = new Creator<SpaServiceEntry>() {
        @Override
        public SpaServiceEntry createFromParcel(Parcel in) {
            return new SpaServiceEntry(in);
        }

        @Override
        public SpaServiceEntry[] newArray(int size) {
            return new SpaServiceEntry[size];
        }
    };

    public static List<SpaServiceEntry> initProductEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.services);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Gson gsonBuilder = new GsonBuilder().setDateFormat("hh:mm")
                .create();
        Type productListType = new TypeToken<ArrayList<SpaServiceEntry>>() {
        }.getType();
        List<SpaServiceEntry> jsonToString = gsonBuilder.fromJson(jsonProductsString, productListType);
        System.out.println("JSONString: " + jsonToString.toString());
        System.out.println("size =" + jsonToString.size());
        return jsonToString;
    }

    @Override
    public String toString() {
        return "SpaServiceEntry{" +
                "serviceId='" + serviceId + '\'' +
                "serviceName='" + serviceName + '\'' +
//                ", serviceDuration=" + serviceDuration +
//                ", serviceDescription='" + serviceDescription + '\'' +
                ", serviceType=" + serviceType +
                ", servicePrice=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serviceId);
        parcel.writeString(serviceName);
        parcel.writeString(serviceDescription);
        parcel.writeString(serviceType);
        parcel.writeLong(serviceDuration.getTime());
        parcel.writeLong(price.longValue());
    }


}
