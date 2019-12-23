package i.kozar.behance_mvp.di;

import androidx.room.Room;
import com.google.gson.Gson;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
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

@Module
public class NetworkModule {

    public NetworkModule() {
    }

    @Provides
    @Singleton
    OkHttpClient provideClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new ApiKeyInterceptor());
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // need for interceptors
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    BehanceApi provideApiService(Retrofit retrofit) {
       return retrofit.create(BehanceApi.class);
    }
}
