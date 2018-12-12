package remote;

import config.ConstantConfig;
import config.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServiceBuilder {
  private static Retrofit retrofit = null;
  private static final String BASEURL= ConstantConfig.FAKE ? "http://api.openweathermap.org/" : "http://localhost:5000/";
  private static APIService sApiService;
  public static synchronized APIService getApiService() {
    sApiService = getRetrofit(BASEURL).create(APIService.class);

    return sApiService;
  }

  public static Retrofit getRetrofit(String baseUrl) {
    if (retrofit==null) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      if (ConstantConfig.DEBUG) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      }
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .readTimeout(20, TimeUnit.SECONDS)
          .connectTimeout(20, TimeUnit.SECONDS)
          .addInterceptor(new Interceptor() {
            // User agent default
            @Override
            public Response intercept(Chain chain) throws IOException {
              // Set original User agent
              Request original = chain.request();
              Request.Builder builder = original.newBuilder();
              for (Map.Entry<String, String> header: getHeader().entrySet()){
                builder.header(header.getKey(), header.getValue() == null ? "" : header.getValue());
              }
              Request request = builder
                  .method(original.method(), original.body())
                  .build();

              return chain.proceed(request);
            }
          }).build();
      retrofit = new Retrofit.Builder()
          .baseUrl(baseUrl)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }
  public static HashMap<String, String> getHeader(){
    HashMap<String, String> headers = new HashMap<>();

    headers.put(Constants.USER_TOKEN, !ConstantConfig.FAKE ? "" : "");
    headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

    return headers;
  }

    public static String getBASEURL() {
        return BASEURL;
    }
}
