package com.wordpress.lonelytripblog.bbblastshots.data;

import android.os.Build;
import android.text.Html;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class for providing shots from the internet.
 */

public class InternetProvider implements ProviderInterface, Callback<List<Shot>> {

    private static InternetProvider INSTANCE = null;

    private final static String BASE_URL = "https://api.dribbble.com/v1/";
    private DribbbleRetrofitInterface dribble;
    private LoadShotsCallback shotsMainCallback;
    private int currentPage;
    private List<Shot> shots;
    private Call<List<Shot>> shotsCall;
    private List<Shot> lastShots;

    public void setLastShots(List<Shot> lastShots) {
        this.lastShots = lastShots;
    }

    public static InternetProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InternetProvider();
        }
        return INSTANCE;
    }

    private InternetProvider() {
        Retrofit retrofit;
        // If device doesn't support HTTPS connection - set OkHttpClient with custom socket,
        // otherwise shots won't be downloaded.
        if (Build.VERSION.SDK_INT < 22) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        dribble = retrofit.create(DribbbleRetrofitInterface.class);
    }

    @Override
    public void getShots(LoadShotsCallback callback) {
        shots = new ArrayList<>(50);
        shotsCall = dribble.getShots(++currentPage);
        shotsCall.enqueue(this);
        shotsMainCallback = callback;
    }

    @Override
    public void getNewShots(LoadShotsCallback callback) {
        getShots(callback);
    }

    @Override
    public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
        if (response.body() == null) return;
        for (Shot shot : response.body()) {
            if (!shot.isAnimated()) {
                if (shot.getDescription() != null) {
                    // Clean string from html tags
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        shot.setDescription(Html.fromHtml(shot.getDescription(), Html.FROM_HTML_MODE_LEGACY).toString());
                    } else {
                        shot.setDescription(Html.fromHtml(shot.getDescription()).toString());
                    }
                }
                if (lastShots != null && lastShots.contains(shot)) {
                    currentPage = 0;
                    shotsMainCallback.onShotsLoaded(shots);
                    lastShots = null;
                    shots = null;
                    return;
                }
                if (!shots.contains(shot)) {
                    shots.add(shot);
                    // When all shots were loaded - return them to invoker and splash buffer.
                    if (shots.size() >= 50) {
                        currentPage = 0;
                        shotsMainCallback.onShotsLoaded(shots);
                        shots = null;
                        return;
                    }
                }
            }
        }
        // When shots to be downloaded left - get new page with shots.
        shotsCall = dribble.getShots(++currentPage);
        shotsCall.enqueue(this);
    }

    @Override
    public void onFailure(Call<List<Shot>> call, Throwable t) {
        shotsMainCallback.onShotsLoaded(null);
    }
}
