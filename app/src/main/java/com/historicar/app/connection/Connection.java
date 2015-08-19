package com.historicar.app.connection;

import com.historicar.app.contants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class Connection
{

    private static final int TIME_OUT = 10000;

    private static final int RETRIES = 3;

    private static final String ULR = "http://www2.rio.rj.gov.br/multas/A516C.asp";

    private static final String USER_AGENT = "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52";

    public static Document getContent (String placa)
    {

        int i = 0;

        while (i < RETRIES)
        {

            try
            {
                return Jsoup.connect(ULR).data(Constants.PLACA_KEY, placa).userAgent(USER_AGENT).timeout(TIME_OUT).post();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            i++;
        }

        return null;
    }

}
