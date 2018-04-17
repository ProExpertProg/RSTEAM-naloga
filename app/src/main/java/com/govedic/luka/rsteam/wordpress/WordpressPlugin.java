package com.govedic.luka.rsteam.wordpress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WordpressPlugin implements Parcelable {

    public String name;
    public String description;
    public String screenshotURL;
    public String homepageURL;
    public String downloadURL;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public WordpressPlugin createFromParcel(Parcel in) {
            return new WordpressPlugin(in);
        }

        public WordpressPlugin[] newArray(int size) {
            return new WordpressPlugin[size];
        }
    };

    public WordpressPlugin(Plugin plugin) {
        name = plugin.getName();
        description = plugin.getShort_description();
        List<Screenshot> list = plugin.getScreenshots();
        screenshotURL = list.size() > 0 ? list.get(0).getSrc() : "";
        homepageURL = plugin.getHomepage();
        downloadURL = plugin.getDownloadLink();
    }

    private WordpressPlugin(Parcel parcel) {
        name = parcel.readString();
        description = parcel.readString();
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
        dest.writeString(description);
        dest.writeString(screenshotURL);
        dest.writeString(homepageURL);
        dest.writeString(downloadURL);
    }

    @Override
    public String toString() {
        return "WordpressPlugin{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", screenshotURL='" + screenshotURL + '\'' +
                ", homepageURL='" + homepageURL + '\'' +
                ", downloadURL='" + downloadURL + '\'' +
                '}';
    }
}
