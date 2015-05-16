package com.historicar.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rodrigo on 27/04/15.
 */
public class ValidateUtils
{

    public static boolean validatePlate (String placa)
    {

        Pattern pattern = Pattern.compile("[a-zA-Z]{3}\\d\\d\\d\\d");
        Matcher matcher = pattern.matcher(placa);

        return (placa.length() == 7 && matcher.matches());
    }

    public static boolean validateLetters (String letras)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z]{3}");
        Matcher matcher = pattern.matcher(letras);

        return (letras.length() == 3 && matcher.matches());
    }

    public static boolean validateNumbers (String numeros)
    {
        Pattern pattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher matcher = pattern.matcher(numeros);

        return (matcher.matches());
    }
}
