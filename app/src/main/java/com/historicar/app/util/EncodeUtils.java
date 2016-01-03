package com.historicar.app.util;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class EncodeUtils
{

    public static String formatter (String s)
    {
        s = s.replaceAll("&aacute;", "á");
        s = s.replaceAll("¾", "á");
        s = s.replaceAll("&Aacute;", "Á");
        s = s.replaceAll("&eacute;", "é");
        s = s.replaceAll("&Eacute;", "É");
        s = s.replaceAll("&iacute;", "í");
        s = s.replaceAll("&Iacute;", "Í");
        s = s.replaceAll("&oacute;", "ó");
        s = s.replaceAll("ÿ", "ó");
        s = s.replaceAll("&Oacute;", "Ó");
        s = s.replaceAll("&uacute;", "ú");
        s = s.replaceAll("&Uacute;", "Ú");

        s = s.replaceAll("&ccedil;", "ç");
        s = s.replaceAll("&Ccedil;", "Ç");
        s = s.replaceAll("&atilde;", "ã");
        s = s.replaceAll("&Atilde;", "Â");
        s = s.replaceAll("&otilde;", "õ");
        s = s.replaceAll("&Otilde;", "Õ");

        s = s.replaceAll(" ENTR", " ");
        s = s.replaceAll("<br />", " ");
        s = s.replaceAll("ø", "úmero");
        s = s.trim();

        return s;

    }

    public static String formatText (String s)
    {
        return formatter(s).replaceAll("\u00A0", "");
    }

    public static String replaceAll(String s)
    {
        return s.replaceAll("MAXIMA", "MÁXIMA").replaceAll("ELETRONICA", "ELETRÔNICA").replaceAll("OBSERVANCIA", "OBSERVÂNCIA").replaceAll("SEGURANCA", "SEGURANÇA").replaceAll("HORARIO", "HORÁRIO").replaceAll("CRIANCA", "CRIANÇA").replaceAll("VEICULO", "VEÍCULO").replaceAll(" ATE ", " ATÉ ").replaceAll("CAO", "ÇÃO").replaceAll("PUBLICO", "PÚBLICO");
    }

}
