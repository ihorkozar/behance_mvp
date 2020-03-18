package i.kozar.behance_mvp.di;

import androidx.room.Room;
import com.google.gson.Gson;
import i.kozar.behance_mvp.AppDelegate;
import i.kozar.behance_mvp.BuildConfig;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.api.ApiKeyInterceptor;
import i.kozar.behance_mvp.data.api.BehanceApi;
import i.kozar.behance_mvp.data.database.BehanceDatabase;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class NetworkModule extends Module {

    private final Gson gson = provideGson();
    private final OkHttpClient okHttpClient = provideClient();
    private final Retrofit retrofit = provideRetrofit();

    public NetworkModule() {
        bind(Gson.class).toInstance(gson);
        bind(OkHttpClient.class).toInstance(okHttpClient);
        bind(Retrofit.class).toInstance(retrofit);
        bind(BehanceApi.class).toInstance(provideApiService());
    }

    OkHttpClient provideClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new ApiKeyInterceptor());
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    Gson provideGson() {
        return new Gson();
    }

    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // need for interceptors
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    BehanceApi provideApiService() {
       return retrofit.create(BehanceApi.class);
    }
}
