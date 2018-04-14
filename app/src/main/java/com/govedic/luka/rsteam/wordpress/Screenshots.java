package com.govedic.luka.rsteam.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Screenshots {
    @SerializedName("0")
    @Expose
    private Screenshot first;

    public Screenshot getFirst() {
        return first;
    }

    public void setFirst(Screenshot first) {
        this.first = first;
    }
}
