package util;

import app.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dto.ChartData;
import dto.ChartPoint;
import dto.GroupChartPoint;
import dto.Product;
import remote.ResponseDTO;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FakeDataUtils {

  public static ChartData getFakeGroupBarChart() {
    try {
      JsonReader reader = readFile("groupbarchart.json");
      if (reader != null) {
        ResponseDTO<ChartData> responseDTO = new Gson()
          .fromJson(reader, new TypeToken<ResponseDTO<ChartData>>() {
          }.getType());
        if (responseDTO != null) {
          ChartData chartData = responseDTO.getResult();
          for (GroupChartPoint groupChartPoint : chartData.getGroupChartPoints()) {
            for (ChartPoint chartPoint : groupChartPoint.getChartPoints()) {
              chartPoint.setValue(1000000 + (Math.random() * ((100000 - 1000) + 1)));
            }
          }
          return chartData;
        }
      }
    } catch (Exception e) {
      Messenger.erro(e.getMessage());
    }
    return null;
  }
  public static List<Product> getFakeProductList(){
    try{
      JsonReader reader = readFile("products.json");
      if (reader != null){
        ResponseDTO<List<Product>> responseDTO = new Gson()
          .fromJson(reader, new TypeToken<ResponseDTO<List<Product>>>() {
          }.getType());
        if (responseDTO!= null){
//          ProductListDTO productListDTO =responseDTO.getResult();
          return responseDTO.getResult();
        }
      }
    }catch (Exception e){
      Messenger.erro(e.getMessage());
    }

    return new ArrayList<>();
  }
  public static JsonReader readFile(String fileDir) {
    JsonReader reader = null;
    try {
      reader = new JsonReader(new InputStreamReader(
        App.class.getClass().getResourceAsStream("/json/fake/" +
          fileDir), "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return reader;
  }
}
