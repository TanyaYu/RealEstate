package com.tanyayuferova.realestate.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tanya Yuferova on 1/24/2018.
 */

public class RealEstate implements Parcelable {

    private String address; //TODO Address should be diverse entity
    private double area;
    private double price;
    private int roomsCount;
    private int floor;
    private String photo;
    private String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RealEstate)
            return ((RealEstate) obj).getKey().equals(this.getKey());
        return super.equals(obj);
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
        String[] strings = new String[3];
        strings[0] = address;
        strings[1] = photo;
        strings[2] = key;
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
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        address = strings[0];
        photo = strings[1];
        key = strings[2];

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
