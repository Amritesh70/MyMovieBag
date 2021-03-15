package com.example.mymoviebag.data.remote.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {
    private static final String API_BASE_URL = "https://api.themoviedb.org/";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NTMzMzBmMDg4NTJjMzk5NTliNGVkNmYyYWU0ODRjMiIsInN1YiI6IjYwNDM3ZTZmYzRhZDU5MDAyYTQ3N2M4ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.tLtIlazCiBf44C22lqa_fkVia64uMwzatOLYudiKsgQ";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();
    @Inject
    public ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass,TOKEN);
    }

    private static <S> S createService(Class<S> serviceClass, String token) {
        return createService(serviceClass,token,"");
    }

    private static <S> S createService(Class<S> serviceClass, String token, String s) {
        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(token);

        if(!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    public static class AuthenticationInterceptor implements Interceptor {
        private String authToken;

        public AuthenticationInterceptor(String token){
            this.authToken =token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authentication",authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}
