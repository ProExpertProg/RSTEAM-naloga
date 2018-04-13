package com.govedic.luka.rsteam.wordpress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WordpressPlugin implements Parcelable {

    private String name;
    private String screenshotURL;
    private String homepageURL;
    private String downloadURL;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public WordpressPlugin createFromParcel(Parcel in) {
            return new WordpressPlugin(in);
        }

        public WordpressPlugin[] newArray(int size) {
            return new WordpressPlugin[size];
        }
    };

    public WordpressPlugin(String name, String screenshotURL, String homepageURL, String downloadURL) {
        this.name = name;
        this.screenshotURL = screenshotURL;
        this.homepageURL = homepageURL;
        this.downloadURL = downloadURL;
    }

    public WordpressPlugin(Plugin plugin) {
        name = plugin.getName();
        screenshotURL = plugin.getScreenshots().get(0).getSrc();
        homepageURL = plugin.getHomepage();
        downloadURL = plugin.getDownloadLink();
    }

    public WordpressPlugin(Parcel parcel) {
        name = parcel.readString();
        screenshotURL = parcel.readString();
        homepageURL = parcel.readString();
        downloadURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(screenshotURL);
        dest.writeString(homepageURL);
        dest.writeString(downloadURL);
    }
}
