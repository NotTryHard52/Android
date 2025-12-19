package com.example.chirkov_android.data

import com.example.chirkov_android.data.service.CatalogService
import com.example.chirkov_android.data.service.FavouriteService
import com.example.chirkov_android.data.service.ProfileService
import com.example.chirkov_android.data.service.StorageService
import com.example.chirkov_android.data.service.SupabaseApi
import com.example.chirkov_android.data.service.UserManagmentService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy

object RetrofitInstance {
    const val SUPABASE_URL = "https://bqfwplpoyfkxeqdlormm.supabase.co/"

    private val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("10.207.106.77", 3128))
    private val client = OkHttpClient.Builder().proxy(proxy).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(SUPABASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val userManagmentService = retrofit.create(UserManagmentService::class.java)
    val profileService: ProfileService = retrofit.create(ProfileService::class.java)
    val storageService: StorageService = retrofit.create(StorageService::class.java)
    val api: SupabaseApi = retrofit.create(SupabaseApi::class.java)
    val catalogService: CatalogService = retrofit.create(CatalogService::class.java)
    val favouriteService: FavouriteService = retrofit.create(FavouriteService::class.java)
}