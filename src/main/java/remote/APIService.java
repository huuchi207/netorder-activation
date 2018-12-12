package remote;

import dto.sample.Item;
import remote.sample.SampleResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
  //Sample
  @GET("data/2.5/box/city")
  Call<SampleResponseDTO<Item>> getSample(@Query("bbox") String bbox, @Query("appid") String appid);

  @POST("machine/active")
  Call<ResponseDTO<Object>> activeServer(@Body String key);
}