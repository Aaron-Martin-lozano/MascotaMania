package com.aaron.mascotamania.Config;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {

    public static void initCloudinary(Context context) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dglwxcyks");
        config.put("api_key", "552773299927129");
        config.put("api_secret", "Mb2FcD9zJQgduidP-2qve7GbEeI");

        MediaManager.init(context, config);

    }
}
