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

    public static Document getContent (String placa)
    {

        Document doc = null;

        int i = 0;

        while (i < RETRIES)
        {

            try
            {
                long starTime = System.currentTimeMillis();
                doc = Jsoup.connect(Constants.ULR).data(Constants.PLACA_KEY, placa).userAgent(Constants.USER_AGENT).timeout(TIME_OUT).post();
                System.out.println("Tempo do request = " + (System.currentTimeMillis() - starTime));

                break;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("Tentando mais uma vez...");
            i++;
        }

        return doc;
    }

}
