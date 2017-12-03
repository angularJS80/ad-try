
package com.example.jcompia.tutoralnavi3.govweather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("item")
    @Expose
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
