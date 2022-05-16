package com.iti.EShopper.notification;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAw0HaGDk:APA91bEFuEpZMFnZRHSkjpXRsz0F84WsB7OmOWEPrwwr9sE9sW6SAEFaV7efYncY2mWulFxr3L-98C-Cg06TqEH_j4vvFCFcn9bkaaosXupdCDNxl5ex3mHyv7TfjpzVvkAtDD49DJh_"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
