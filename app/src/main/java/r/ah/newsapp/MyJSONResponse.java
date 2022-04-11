package r.ah.newsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyJSONResponse {

    @SerializedName("articles")
    @Expose
    private ModelClass[] dataarray;

    public ModelClass[] getDataarray() {
        return dataarray;
    }

    public void setDataarray(ModelClass[] dataarray) {
        this.dataarray = dataarray;
    }
}
