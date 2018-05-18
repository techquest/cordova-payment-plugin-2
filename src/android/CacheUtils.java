import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Folarin Omotoriogun, ThingsHeaven.com on 10/07/16.
 */
public abstract class CacheUtils {

    public static void update(String key, Object object, Context context) {
        key = prefixKey(key);
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
		Gson gson = new Gson();
        String data = object == null ? null : object instanceof String ? (String) object : gson.toJson(object);
        editor.remove(key);

        if (!TextUtils.isEmpty(data)) {
            editor.putString(key, data);
        } else {
            editor.remove(key);
        }
        // Use apply for watch and save
        editor.commit();
    }

    public static Object read(String key, Type type, Context context) {
        key = prefixKey(key);
        SharedPreferences preferences = getSharedPreferences(context);
        String data = preferences.getString(key, null);
		Gson gson = new Gson();
        if (data == null) {
            return null;
        } else if (type != null && type.equals(String.class)) {
            return data;
        } else {
            return gson.fromJson(data, type);
        }
    }

    public static Boolean exists (String key, Context context) {
        key = prefixKey(key);
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(key, null) != null;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs;
    }

    private static String prefixKey (String key) {
        return "ISW_SDK_CORDOVA_" + key;
    }

}