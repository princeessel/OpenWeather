package com.openweather.data.di

import com.openweather.Constants
import com.openweather.data.service.WeatherService
import com.openweather.data.source.WeatherSource
import com.openweather.data.source.WeatherSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Named("API_ID")
    fun provideApiId() = Constants.API_ID

    @Provides
    @Named("API_UNITS")
    fun provideApiUnits() = Constants.API_UNITS

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (true){//if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(weatherSource: WeatherSourceImpl): WeatherSource = weatherSource

}
