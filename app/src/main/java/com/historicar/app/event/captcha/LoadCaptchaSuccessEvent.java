package com.historicar.app.event.captcha;

import android.graphics.drawable.Drawable;

/**
 * Created by Rodrigo on 18/01/16.
 */
public class LoadCaptchaSuccessEvent
{

    private Drawable drawable;

    public LoadCaptchaSuccessEvent (Drawable drawable)
    {
        this.drawable = drawable;
    }

    public Drawable getDrawable ()
    {
        return drawable;
    }
}
