package com.example.androidlecture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class LightActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<String> lights = new ArrayList();
    RecyclerView rvLights;
    int roomPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_activity);
        ButterKnife.bind(this);
        // Lookup the recyclerview in activity layout
        rvLights = (RecyclerView) findViewById(R.id.rvLights);

        Intent intent = getIntent();
//        System.out.println(intent.getIntExtra("text",0));
        int message = intent.getIntExtra("position", 0);
        roomPos = message;
        Toast.makeText(LightActivity.this, "Room  "+message, Toast.LENGTH_SHORT).show();
        retrieveRooms();

// Get the Intent that started this activity and extract the string

    }


    public void retrieveRooms() {
//        final String  CONTEXT_SERVER_URL = "http://thawing-journey-78988.herokuapp.com/api/rooms";
        final String CONTEXT_SERVER_URL = " http://idrissi.cleverapps.io/api/rooms";

        queue = Volley.newRequestQueue(getApplicationContext());

//        String url = CONTEXT_SERVER_URL + "/" + room + "/";
        //get room sensed context
        JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, CONTEXT_SERVER_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject Room = (JSONObject)response.get(roomPos);
                            JSONArray lightsArray = (JSONArray)Room.get("lights");
//                            System.out.println(lightsArray);
                            for (int i = 0; i<lightsArray.length();i++){
                                System.out.println(lightsArray.get(i));
                                lights.add(((JSONObject)(lightsArray.get(i))).getString("id"));
                            }
                            LightsAdapter adapter = new LightsAdapter(lights);
                            // Attach the adapter to the recyclerview to populate items
                            rvLights.setAdapter(adapter);
                            // Set layout manager to position the items
                            rvLights.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            // That's all!

//                            System.out.println(rooms);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                        Toast.makeText(getApplicationContext(), "error on the request", Toast.LENGTH_SHORT).show();
                        System.err.println("error on making the request" + error);
                    }
                });

        queue.add(contextRequest);
//        System.out.println(rooms);
//        return rooms;
    }


    public void redirect(View view) {
        Intent intent = new Intent(this, ContextManagementActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
