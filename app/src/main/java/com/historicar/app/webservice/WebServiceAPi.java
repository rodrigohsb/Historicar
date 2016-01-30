package com.historicar.app.webservice;

import com.historicar.app.bean.Carro;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rodrigo on 18/01/16.
 */
public interface WebServiceAPi
{

    /********************************************************************************************************************************************************/
    /********************************************************************* BACKEND **************************************************************************/
    /********************************************************************************************************************************************************/


    @Headers(Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE)
    @GET("ticket/{placa}")
    Call<List<Multa>> getTickets (@Header(Constants.X_USER_ACCESS_TOKEN) String userAccessToken,
                                  @Header(Constants.CAPTCHA) String captcha,
                                  @Header(Constants.COOKIE) String cookie,
                                  @Path(Constants.PLACA_KEY) String placa);


    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE ,
            Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @POST("/cars/new")
    Call<Carro> createCar (@Body Carro car);


    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE ,
            Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @POST("/cars/update")
    Call<Carro> updateCar (@Body Carro car);


    @Headers({Constants.APP_ACCESS_TOKEN + ": " + Constants.APP_ACCESS_TOKEN_VALUE ,
            Constants.X_USER_ACCESS_TOKEN + ": " + Constants.X_USER_ACCESS_TOKEN_VALUE})
    @DELETE("/cars/delete")
    Call<Carro> deleteCar (@Body Carro car);


    /********************************************************************************************************************************************************/
    /*********************************************************************** SMTR ***************************************************************************/
    /********************************************************************************************************************************************************/


    @Headers({Constants.ACCEPT_HEADER + ": " + Constants.ACCEPT_VALUE_HEADER ,
            Constants.ENCODING_HEADER + ": " + Constants.ENCODING_VALUE_HEADER ,
            Constants.LANGUAGE_HEADER + ": " + Constants.LANGUAGE_VALUE_HEADER ,
            Constants.CONNECTION_HEADER + ": " + Constants.CONNECTION_VALUE_HEADER ,
            Constants.COOKIE_HEADER + ": " + Constants.COOKIE_DEFAUL_VALUE_HEADER ,
            Constants.HOST_HEADER + ": " + Constants.HOST_VALUE_HEADER ,
            Constants.REFERER_HEADER + ": " + Constants.REFERER_VALUE_HEADER ,
            Constants.UPGRADE_INSECURE_REQUESTS_HEADER + ": " + Constants.UPGRADE_INSECURE_REQUESTS_VALUE_HEADER ,
            Constants.USER_AGENT_HEADER + ": " + Constants.USER_AGENT_VALUE_HEADER})
    @GET("/multas/index.asp")
    Call<ResponseBody> getCookie ();


    @Headers({Constants.ACCEPT_HEADER + ": " + Constants.ACCEPT_VALUE_HEADER ,
            Constants.ENCODING_HEADER + ": " + Constants.ENCODING_VALUE_HEADER ,
            Constants.LANGUAGE_HEADER + ": " + Constants.LANGUAGE_VALUE_HEADER ,
            Constants.CONNECTION_HEADER + ": " + Constants.CONNECTION_VALUE_HEADER ,
            Constants.HOST_HEADER + ": " + Constants.HOST_VALUE_HEADER ,
            Constants.REFERER_HEADER + ": " + Constants.REFERER_VALUE_HEADER ,
            Constants.UPGRADE_INSECURE_REQUESTS_HEADER + ": " + Constants.UPGRADE_INSECURE_REQUESTS_VALUE_HEADER ,
            Constants.USER_AGENT_HEADER + ": " + Constants.USER_AGENT_VALUE_HEADER})
    @GET("/multas/include/captcha.asp")
    Call<ResponseBody> getCaptcha (@Header(Constants.COOKIE_HEADER) String cookie);
}
