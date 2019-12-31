package com.example.androidlecture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ContextManagementActivity extends Activity {
    RequestQueue queue;
    RoomContextState rcs;
    private String room;
    public ImageView imageView1;
    public TextView textViewLightValue;
    @Bind(R.id.textViewNoiseValue)
    public TextView textViewNoiseValue;
    public Button button1; // value of light on or off
    public Button button2; // value of ringer on or off
    public RelativeLayout contextLayout;
    //    public ArrayList<RoomContextRule> rules = new ArrayList<RoomContextRule>();
    RoomContextHttpManager rchm = new RoomContextHttpManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_management_activity);
        ButterKnife.bind(this);


        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                rchm.retrieveRoomContextState(room);

            }
        });

        Intent intent = getIntent();
//        System.out.println(intent.getIntExtra("text",0));
        int message = intent.getIntExtra("position", 0);
        Toast.makeText(ContextManagementActivity.this, "Room "+message, Toast.LENGTH_SHORT).show();
        contextLayout = (RelativeLayout) findViewById(R.id.contextLayout);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textViewLightValue = (TextView) findViewById(R.id.textViewLightValue);
//        textViewNoiseValue = (TextView)findViewById(R.id.textViewNoiseValue);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

    }


//    public RoomContextState retrieveRoomContextState(final String room) {
//        final String  CONTEXT_SERVER_URL = "http://thawing-journey-78988.herokuapp.com/api/rooms";
//             queue = Volley.newRequestQueue(this);
//            String url = CONTEXT_SERVER_URL + "/" + room + "/";
//
////get room sensed context
//            JsonObjectRequest contextRequest = new JsonObjectRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                String id = response.getString("id").toString();
//                                int lightLevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
//                                String lightStatus = response.getJSONObject("light").get("status").toString();
//                                float noiseStatus = Float.parseFloat(response.getJSONObject("noise").get("level").toString());
//
//                                // do something with results...
////                                Toast.makeText(ContextManagementActivity.this, "ca marche", Toast.LENGTH_SHORT).show();
//                               rcs = new RoomContextState(room,lightStatus,lightLevel,noiseStatus);
//                                // notify main activity for update...
//                                updateContextView(onUpdate(rcs),imageView1);
//
////                      params:  (String room, String status, int light, float noise)
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // Some error to access URL : Room may not exists...
//                            Toast.makeText(getApplicationContext(), "error on the request", Toast.LENGTH_SHORT).show();
//                            System.err.println("error on making the request" +error);
//                        }
//                    });
//            queue.add(contextRequest);
//     return rcs;
//    }

//    public RoomContextState onUpdate(RoomContextState contextState){
//
//    return contextState;
//    }

    //    private void updateContextView(RoomContextState contextState, ImageView image) {
//
//        if (contextState.getStatus()!= null) {
//            contextLayout.setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer.toString(contextState.getLight()));
//            ((TextView) findViewById(R.id.textViewNoiseValue)).setText(Float.toString(contextState.getNoise()));
//            if (contextState.getStatus().equals(RoomContextState.ON))
//                image.setImageResource(R.drawable.ic_bulb_on);
//            else
//                image.setImageResource(R.drawable.ic_bulb_off);
//        } else {
//            initView();
//        }
//    }
    private void initView() {
//        Toast.makeText(getApplicationContext(), "contextStatus is null !", Toast.LENGTH_SHORT).show();
//        imageView1.setImageResource(R.drawable.ic_bulb_on);
//        textViewLightValue.setText("11");
//        textViewNoiseValue.setText("20");
        contextLayout.setVisibility(View.GONE);

//        button1.setText();
//        button2.setText();

    }

    public void switchLight(RoomContextState contextState, String room) {
        // switch light
        try {
            String URL = "http://thawing-journey-78988.herokuapp.com/api/rooms/"
                    + room + "/switch-light-and-list";

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("id", Integer.parseInt(room));

            JSONObject lightObject = new JSONObject();
            lightObject.put("id", 1);
            lightObject.put("level", 100);
            String stateLight = rcs.getStatus().equals(RoomContextState.ON) ? RoomContextState.OFF : RoomContextState.ON;
            lightObject.put("status", stateLight);

            JSONObject noiseObject = new JSONObject();
            noiseObject.put("id", 1);
            noiseObject.put("level", 20.0);
            noiseObject.put("status", "ON");
            jsonBody.put("light", lightObject);
            jsonBody.put("moise", noiseObject);
            System.out.println("jsonBody: " + jsonBody);

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PUT,
                    URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.err.println(error);
                    // we have to expect JSONArray not jsonObject
                    //  onBackPressed();
                    // Toast.makeText(ContextManagementActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");
                    //put your token here
                    return headers;
                }
            };
            queue.add(jsonOblect);

        } catch (JSONException e) {
            System.err.println(e);
//            e.printStackTrace();
            Toast.makeText(this, "405 error", Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }

    public void switchLight(View view) {
        this.switchLight(rchm.retrieveRoomContextState(room), room);
        //  this.retrieveRoomContextState(room);
    }

    //    public void checkRoommmm(View view) {
//        final TextView textView = (TextView) findViewById(R.id.text);
//        // Instantiate the RequestQueue.
//        String url = "http://thawing-journey-78988.herokuapp.com/api/rooms/2";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        String id ="";
//                        try {
//                            id = (String)response.get("id");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//                        Toast.makeText(getApplicationContext(), "ERROR !", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//
//    } // to test req
    public void switchRinger(View view) {

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();
        if (mode == AudioManager.RINGER_MODE_SILENT)
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }


    public void updateContextView(RoomContextState contextState) {

        if (contextState.getStatus() != null) {
            contextLayout.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer.toString(contextState.getLight()));
            ((TextView) findViewById(R.id.textViewNoiseValue)).setText(Float.toString(contextState.getNoise()));
            if (contextState.getStatus().equals(RoomContextState.ON))
                imageView1.setImageResource(R.drawable.ic_bulb_on);
            else
                imageView1.setImageResource(R.drawable.ic_bulb_off);
        } else {
            initView();
        }
    }
}
