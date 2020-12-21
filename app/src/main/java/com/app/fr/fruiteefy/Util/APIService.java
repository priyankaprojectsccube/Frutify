package com.app.fr.fruiteefy.Util;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAARe2K-nQ:APA91bH0ignl_Yh-IljEEdiv9eo8OQKLpu9JPSG7e7Bnk6W8uA1XntPNbx59n1xYhA_yoRQz1as4z5Kp5tmZ5BV_gmIDuyFsn8UNKNvS8WsNt3HxWbEZ9mgcwGP-NcLqK91gpoPajiFw"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
