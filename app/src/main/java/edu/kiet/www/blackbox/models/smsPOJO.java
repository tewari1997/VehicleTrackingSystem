package edu.kiet.www.blackbox.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sooraj on 12/9/17.
 */

public class smsPOJO {
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }
}
