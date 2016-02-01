package com.historicar.app.contants;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class Constants
{

    public static final String CAPTCHA = "captcha";

    public static final String PLACA_KEY = "placa";

    public static final String MULTA = "multa";

    public static final String OK = "OK";

    public static final String YES = "Sim";

    public static final String NO = "Nao";

    public static final String WARNING = "Aviso";

    public static final int REQUEST_FOR_UPDATE_PLATE = 1000;

    public static final int REQUEST_FOR_CREATE_PLATE = 2000;

    public static final String BACKEND_BASE_URL = "http://192.168.0.13:8090/historicar/";

    public static final String APP_ACCESS_TOKEN = "X-APP-ACCESS-TOKEN";

    public static final String APP_ACCESS_TOKEN_VALUE = "DCFA8506-4F3D-4BEE-A007-322E0BCB2C12";

    public static final String X_USER_ACCESS_TOKEN = "X-USER-ACCESS-TOKEN";

    public static final String X_USER_ACCESS_TOKEN_VALUE = "DCFA8506-4F3D-4BEE-A007-322E0BCB2C12";

    public static final String COOKIE = "COOKIE";

    public static final int RETRIES = 3;

    public static final String COOKIE_ULR = "http://www2.rio.rj.gov.br/multas/index.asp";

    public static final String CAPTCHA_ULR = "http://www2.rio.rj.gov.br/multas/include/captcha.asp";

    public static final String ACCEPT_HEADER = "Accept";
    public static final String ACCEPT_VALUE_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

    public static final String ENCODING_HEADER = "Accept-Encoding";
    public static final String ENCODING_VALUE_HEADER = "gzip, deflate, sdch";

    public static final String LANGUAGE_HEADER = "Accept-Language";
    public static final String LANGUAGE_VALUE_HEADER = "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4,es;q=0.2,de;q=0.2";

    public static final String CONNECTION_HEADER = "Connection";
    public static final String CONNECTION_VALUE_HEADER = "keep-alive";

    public static final String COOKIE_HEADER = "Cookie";
    public static final String COOKIE_DEFAUL_VALUE_HEADER = "dtCookie=|_default|0";

    public static final String HOST_HEADER = "Host";
    public static final String HOST_VALUE_HEADER = "www2.rio.rj.gov.br";

    public static final String REFERER_HEADER = "Referer";
    public static final String REFERER_VALUE_HEADER = "http://www2.rio.rj.gov.br/multas/index.html";

    public static final String UPGRADE_INSECURE_REQUESTS_HEADER = "Upgrade-Insecure-Requests";
    public static final String UPGRADE_INSECURE_REQUESTS_VALUE_HEADER = "1";

    public static final String USER_AGENT_HEADER = "User-Agent";
    public static final String USER_AGENT_VALUE_HEADER = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
}
