package com.historicar.app.connection;

import android.graphics.drawable.Drawable;

import com.historicar.app.contants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class Connection
{

    public static String getCookies()
    {

        int i = 0;

        while (i < Constants.RETRIES)
        {

            try
            {

                URL obj = new URL(Constants.COOKIE_ULR);
                URLConnection conn = obj.openConnection();

                conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                conn.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
                conn.setRequestProperty("Accept-Language","pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4,es;q=0.2,de;q=0.2");
                conn.setRequestProperty("Connection","keep-alive");
                conn.setRequestProperty("Cookie","dtCookie=|_default|0");
                conn.setRequestProperty("Host","www2.rio.rj.gov.br");
                conn.setRequestProperty("Referer","http://www2.rio.rj.gov.br/multas/index.html");
                conn.setRequestProperty("Upgrade-Insecure-Requests","1");
                conn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");

                List<String> headerCookies = conn.getHeaderFields().get("Set-Cookie");

                if(headerCookies != null && !headerCookies.isEmpty())
                {
                    String first = headerCookies.get(0);

                    String [] cookies = first.split(";");

                    String cookie = "";

                    for(String c : cookies)
                    {
                        if(c.contains("ASPSESSIONID"))
                        {
                            cookie = cookie.concat(c);
                        }
                    }

                    return cookie;
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            i++;
        }

        return null;
    }

    public static Drawable getCaptcha(String cookies)
    {

        int i = 0;

        while (i < Constants.RETRIES)
        {

            try
            {
                URLConnection connection = new URL(Constants.CAPTCHA_ULR).openConnection();
                connection.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
                connection.setRequestProperty("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4,es;q=0.2,de;q=0.2");
                connection.setRequestProperty("Cache-Control", "max-age=0");
                connection.setRequestProperty("Connection", "keep-alive");
                connection.setRequestProperty("Cookie", cookies);
                connection.setRequestProperty("Host", "www2.rio.rj.gov.br");
                connection.setRequestProperty("Referer", "http://www2.rio.rj.gov.br/multas/index.asp");
                connection.setRequestProperty("User-Agent", Constants.USER_AGENT);

                InputStream is = (InputStream) connection.getContent();
                return Drawable.createFromStream(is, "src name");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            i++;
        }
        return null;
    }
}
