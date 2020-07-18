package ae.chamber.mvcapplication.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/"

interface NYAPI {

    @GET("mostviewed/all-sections/{period}/.json?")
    fun getMostViewd(@Path("period")  period:Int, @Query("api-key") apiKey :String): Call<JsonObject>

    @GET("mostviewed/{section}/{time-period}.json")
    fun getArticles(
        @Path("section") section: String?,
        @Path("time-period") timePeriod: Int,
        @Query("api-key") apiKey: String?
    ): Call<JsonObject>

}

object Api {

    fun createWithHeader(context: Context):NYAPI{
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "YWRtaW46MTIz")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(NYAPI::class.java)
    }


    val urlApiService : NYAPI by lazy {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(NYAPI::class.java)
    }

    fun createWithHeader2(context: Context): NYAPI {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                /* .addHeader("Authorization", context.getString(R.string.authorization))*/
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            /*.connectionSpecs(Collections.singletonList(spec))*/
            .connectionSpecs(
                listOf(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(NYAPI::class.java)
    }
}