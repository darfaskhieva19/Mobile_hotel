package com.example.mobile_hotel;

import android.os.Parcel;
import android.os.Parcelable;

public class Hotel implements Parcelable {

    private int ID;
    private String Country;
    private String City;
    private String Title;
    private int NumberOfStars;
    private String Image;

    protected Hotel(Parcel in) {
        ID = in.readInt();
        Country = in.readString();
        City = in.readString();
        Title = in.readString();
        NumberOfStars = in.readInt();
        Image = in.readString();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size)
        {
            return new Hotel[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setNumberOfStars(int numberofstars) {
        NumberOfStars = numberofstars;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    public String getTitle() {
        return Title;
    }

    public int getNumberOfStars() {
        return NumberOfStars;
    }

    public String getImage() {
        return Image;
    }

    public Hotel(int ID, String country, String city, String title, int numberofstars, String image) {
        this.ID = ID;
        Country = country;
        City = city;
        Title = title;
        NumberOfStars = numberofstars;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Country);
        dest.writeString(City);
        dest.writeString(Title);
        dest.writeInt(NumberOfStars);
        dest.writeString(Image);
    }
}