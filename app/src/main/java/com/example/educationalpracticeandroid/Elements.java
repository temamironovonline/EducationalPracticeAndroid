package com.example.educationalpracticeandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Elements implements Parcelable {

    public static final Creator<Elements> CREATOR = new Creator<Elements>() {
        @Override
        public Elements createFromParcel(Parcel in) {
            return new Elements(in);
        }

        @Override
        public Elements[] newArray(int size) {
            return new Elements[size];
        }
    };
    private int id;
    private String title;
    private String image;
    private String description;

    public Elements(int id, String title, String image, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    protected Elements(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
    }
}
