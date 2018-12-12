package dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import model.BaseChartItem;

public class ChartPoint extends BaseChartItem {

    @SerializedName("viewValue")
    @Expose
    private String viewValue;


    public String getViewValue() {
        return viewValue;
    }

    public void setViewValue(String viewValue) {
        this.viewValue = viewValue;
    }

}