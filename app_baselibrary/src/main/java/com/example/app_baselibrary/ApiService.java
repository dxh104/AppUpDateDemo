package com.example.app_baselibrary;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XHD on 2019/03/27
 */
public interface ApiService {

    /**
     * 日历详述
     */
    @GET(Constant.UrlOrigin.CALENDAR_DETAILS)
    Observable<CalendarInfo> calendarDetails(@Query("client") String client, @Query("timestamp") String timestamp, @Query("token") String token);


}
