package ae.chamber.mvcapplication.network

import ae.chamber.mvcapplication.model.ResponseAPI
import com.google.gson.GsonBuilder
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
    @GET("mostviewed/{section}/{time-period}.json")
    fun getArticles(
        @Path("section") section: String?,
        @Path("time-period") timePeriod: Int,
        @Query("api-key") apiKey: String?
    ): Call<ResponseAPI>

}

object Api {
    val urlApiService: NYAPI by lazy {
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
}