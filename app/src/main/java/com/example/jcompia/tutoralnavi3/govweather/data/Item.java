
package com.example.jcompia.tutoralnavi3.govweather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("stnId")
    @Expose
    private String stnId;
    @SerializedName("tmFc")
    @Expose
    private String tmFc;
    @SerializedName("tmSeq")
    @Expose
    private String tmSeq;
    @SerializedName("t1")
    @Expose
    private String t1;

    public String getStnId() {
        return stnId;
    }

    public void setStnId(String stnId) {
        this.stnId = stnId;
    }

    public String getTmFc() {
        return tmFc;
    }

    public void setTmFc(String tmFc) {
        this.tmFc = tmFc;
    }

    public String getTmSeq() {
        return tmSeq;
    }

    public void setTmSeq(String tmSeq) {
        this.tmSeq = tmSeq;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

}
