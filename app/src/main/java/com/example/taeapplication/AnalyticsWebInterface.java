/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.taeapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

// [START analytics_web_interface]
public class AnalyticsWebInterface {

    public static final String TAG = "AnalyticsWebInterface";
    private final FirebaseAnalytics mAnalytics;

    public AnalyticsWebInterface(Context context) {
        mAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @JavascriptInterface
    public void logEvent(String name, String jsonParams) {
        Log.d(TAG, "logEvent:" + name);
        mAnalytics.logEvent(name, bundleFromJson(jsonParams));
    }

    @JavascriptInterface
    public void setUserProperty(String name, String value) {
        Log.d(TAG, "setUserProperty:" + name);
        mAnalytics.setUserProperty(name, value);
    }

    @JavascriptInterface
    public void logScreen(String name) {
        Log.d(TAG, "ScreenName:" + name);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, name);
        mAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void LOGD(String message) {
        Log.d(TAG, message);
    }

    private Bundle bundleFromJson(String json) {
        // [START_EXCLUDE]
        if (TextUtils.isEmpty(json)) {
            return new Bundle();
        }
        Bundle result = new Bundle();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);

                if (value instanceof String) {
                    result.putString(key, (String) value);
                } else if (value instanceof Integer) {
                    result.putInt(key, (Integer) value);
                } else if (value instanceof Double) {
                    result.putDouble(key, (Double) value);
                } else {
                    Log.w(TAG, "Value for key " + key + " not one of [String, Integer, Double]");
                }
            }
        } catch (JSONException e) {
            Log.w(TAG, "Failed to parse JSON, returning empty Bundle.", e);
            return new Bundle();
        }
        return result;
        // [END_EXCLUDE]
    }
}
// [END analytics_web_interface]
