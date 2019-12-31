package com.example.androidlecture;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomContextHttpManager {
    ContextManagementActivity cma;
//    ArrayList<RoomContextStateListener> listofShit = new ArrayList<RoomContextStateListener>();
    public RoomContextHttpManager(ContextManagementActivity cma){
        this.cma = cma;
    }

    final String  CONTEXT_SERVER_URL = "";

    public void switchLight(RoomContextState state, String room){
    }
    public RoomContextState retrieveRoomContextState(final String room) {
        final String  CONTEXT_SERVER_URL = "http://thawing-journey-78988.herokuapp.com/api/rooms";
        cma.queue = Volley.newRequestQueue(cma.getApplicationContext());
        String url = CONTEXT_SERVER_URL + "/" + room + "/";

    //get room sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id").toString();
                            int lightLevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
                            String lightStatus = response.getJSONObject("light").get("status").toString();
                            float noiseStatus = Float.parseFloat(response.getJSONObject("noise").get("level").toString());

                            // do something with results...
//                                Toast.makeText(ContextManagementActivity.this, "ca marche", Toast.LENGTH_SHORT).show();
//                            cma.rcs = new RoomContextState(room,lightStatus,lightLevel,noiseStatus);
                               cma.rcs= new RoomContextState(room,lightStatus,lightLevel,noiseStatus);

                            // notify main activity for update...
//                            cma.onUpdate(cma.rcs);

//                      params:  (String room, String status, int light, float noise)
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                        Toast.makeText(cma.getApplicationContext(), "error on the request", Toast.LENGTH_SHORT).show();
                        System.err.println("error on making the request" +error);
                    }
                });
        cma.queue.add(contextRequest);
        return cma.rcs;
    }
    public RoomContextState onUpdate(RoomContextState context){
        return context;
    }
    private void initView() {
//
        cma.contextLayout.setVisibility(View.GONE);
    }

//    private void updateContextView(RoomContextState contextState) {
//
//        if (contextState.getStatus()!= null) {
//            cma.contextLayout.setVisibility(View.VISIBLE);
//            ((TextView) cma.findViewById(R.id.textViewLightValue)).setText(Integer.toString(contextState.getLight()));
//            ((TextView) cma.findViewById(R.id.textViewNoiseValue)).setText(Float.toString(contextState.getNoise()));
//            if (contextState.getStatus().equals(RoomContextState.ON))
//                cma.imageView1.setImageResource(R.drawable.ic_bulb_on);
//            else
//                cma.imageView1.setImageResource(R.drawable.ic_bulb_off);
//        } else {
//            initView();
//        }
//    }
}
