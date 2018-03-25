package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.customtabs.CustomTabsIntent;

/**
 * Created by hessel on 25.03.2018.
 */

public class CustomTabs {


    public static void openInCustomTab(String url,Context mContext){

        Uri websiteUri;
        if(!url.contains("https://") && !url.contains("http://")){
            websiteUri = Uri.parse("http://" + url);
        } else {
            websiteUri = Uri.parse(url);
        }

        CustomTabsIntent.Builder customtabintent = new CustomTabsIntent.Builder();
        customtabintent.setShowTitle(false);
        customtabintent.setToolbarColor(Color.parseColor("#151515"));


        if(chromeInstalled(mContext)){
            customtabintent.build().intent.setPackage("com.android.chrome");
        }

        customtabintent.build().launchUrl(mContext, websiteUri);
    }

    private static boolean chromeInstalled(Context mContext){
        try {
            mContext.getPackageManager().getPackageInfo("com.android.chrome", 0);
            return true;
        } catch (Exception e){
            return false;
        }
    }



}
