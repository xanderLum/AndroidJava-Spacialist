package com.spacialist.data.dto;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spacialist.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SpaBusinessEntry implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpaBusinessDTO createFromParcel(Parcel in) {
            return new SpaBusinessDTO(in);
        }

        public SpaBusinessDTO[] newArray(int size) {
            return new SpaBusinessDTO[size];
        }
    };

    public SpaBusinessEntry(Parcel in) {
        this.busId = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.contactNo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.busId);
        parcel.writeString(this.name);
        parcel.writeString(this.url);
        parcel.writeString(this.address);
        parcel.writeString(this.description);
        parcel.writeString(this.contactNo);
    }

    private static final String TAG = SpaBusinessEntry.class.getSimpleName();

    public final int busId;
    public final String name;
    public final String contactNo;
    public final String url;
    public final String address;
    public final String description;

    public SpaBusinessEntry(int busId, String name, String contactNo, String url, String address, String description) {
        this.busId = busId;
        this.name = name;
        this.contactNo = contactNo;
        this.url = url;
        this.address = address;
        this.description = description;
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */
    /*public static List<SpaBusinessEntry> initProductEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.spa);
        StringWriter writer = new StringWriter();
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
        Type productListType = new TypeToken<ArrayList<SpaBusinessEntry>>() {
        }.getType();
        List<SpaBusinessEntry> jsonToString = gson.fromJson(jsonProductsString, productListType);
        System.out.println("JSONString: " + jsonToString.toString());
        System.out.println("size =" + jsonToString.size());
        return jsonToString;
    }*/

    @Override
    public String toString() {
        return "SpaBusinessEntry{" +
                "busId=" + busId +
                ", name='" + name + '\'' +
                ", contactNo=" + contactNo +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
