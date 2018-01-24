package com.tanyayuferova.realestate.entity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.tanyayuferova.realestate.R;

/**
 * Created by Tanya Yuferova on 1/24/2018.
 */

public class RealEstate implements Parcelable {

    private String address;
    private double area;
    private double price;
    private int roomsCount;
    private int floor;
    private String photo;

    public RealEstate() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPriceShortDescription(Context context) {
        if(price%1000 == 0)
            // we don't want to display a lot of zeros
            return context.getString(R.string.price_kilo_description, price/1000);
        return context.getString(R.string.price_description, price);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<RealEstate> CREATOR = new Parcelable.Creator<RealEstate>() {
        public RealEstate createFromParcel(Parcel in) {
            return new RealEstate(in);
        }

        public RealEstate[] newArray(int size) {
            return new RealEstate[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        String[] strings = new String[2];
        strings[0] = address;
        strings[1] = photo;
        parcel.writeStringArray(strings);

        double[] doubles = new double[2];
        doubles[0] = area;
        doubles[1] = price;
        parcel.writeDoubleArray(doubles);

        int[] ints = new int[2];
        ints[0] = roomsCount;
        ints[1] = floor;
        parcel.writeIntArray(ints);
    }

    private RealEstate(Parcel parcel) {
        String[] strings = new String[2];
        parcel.readStringArray(strings);
        address = strings[0];
        photo = strings[1];

        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        area = doubles[0];
        price = doubles[1];

        int[] ints = new int[2];
        parcel.readIntArray(ints);
        roomsCount = ints[0];
        floor = ints[1];
    }

}
