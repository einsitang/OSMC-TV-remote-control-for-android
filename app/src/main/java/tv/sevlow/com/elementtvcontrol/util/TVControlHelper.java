package tv.sevlow.com.elementtvcontrol.util;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Element on 16/7/19.
 */
public class TVControlHelper {

    private static final String TAG = "TVControlHelper";

    public static final MediaType JSONMedia
            = MediaType.parse("application/json; charset=utf-8");

    public static String domain = "192.168.0.112";

    public static String protocol = "http";

    public static int port = 8080;

    public static String getServer() {
        return protocol + "://" + domain + ":" + port + "/jsonrpc";
    }

    private static final OkHttpClient client = new OkHttpClient();

    private static final Gson gson = new Gson();

    public static void send(String order) throws IOException {
        send(order,null);
    }

    public static void send(String order,Map<String,Object> params){
        String server = getServer();
        String url = server + "?" + order;

        Map<String,Object> jsonParams = new HashMap<String,Object>();

        jsonParams.put("jsonrpc","2.0");
        jsonParams.put("method",order);
        jsonParams.put("id","1");

        if(params!=null){
            jsonParams.put("params",params);
        }

        String jsonParamsStr = gson.toJson(jsonParams);

        Log.d("TVControlHelper", "url: " + url+" , params: "+jsonParamsStr);

        RequestBody requestBody = RequestBody.create(JSONMedia, jsonParamsStr);

        final Request request = new Request.Builder()
                .url(server + "?" + order)
                .post(requestBody)
                .build();

        new Runnable() {
            @Override
            public void run() {

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Log.d(TAG, "str => " + str);
                    }
                });

            }
        }.run();
    }

}
