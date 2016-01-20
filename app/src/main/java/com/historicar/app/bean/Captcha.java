package com.historicar.app.bean;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class Captcha
{

    private String cookie;

    private BufferedImage image;

    public Captcha(String cookie, BufferedImage image)
    {
        this.cookie = cookie;
        this.image = image;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "cookie='" + cookie + '\'' +
                ", image=" + image +
                '}';
    }

}
