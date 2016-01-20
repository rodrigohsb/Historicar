package com.historicar.app.webservice;

import android.graphics.drawable.Drawable;

import com.historicar.app.bean.Carro;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rodrigo on 18/01/16.
 */
public interface WebServiceAPi
{

    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE , Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @POST("/cars/new")
    Call<Carro> createCar (@Body Carro car);

    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE , Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @POST("/cars/update")
    Call<Carro> updateCar (@Body Carro car);

    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE , Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @DELETE("/cars/delete")
    Call<Carro> deleteCar (@Body Carro car);

    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE , Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @GET("/getCaptcha")
    Call<Drawable> getCaptcha ();

    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE , Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @GET("ticket/{placa}/{captcha}/{cookie}")
    Call<List<Multa>> getTickets (@Path("placa") String placa, @Path("captcha") String captcha,@Path("cookie") String cookie);

}
