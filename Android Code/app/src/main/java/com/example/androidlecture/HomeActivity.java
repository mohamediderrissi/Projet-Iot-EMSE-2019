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

public class HomeActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<String> rooms = new ArrayList();
    RecyclerView rvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        retrieveRooms();
        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

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
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject room = (JSONObject) response.get(i);

                                rooms.add(room.getString("name"));

                            }
                            System.out.println();
                            RoomsAdapter adapter = new RoomsAdapter(rooms);
                            // Attach the adapter to the recyclerview to populate items
                            rvContacts.setAdapter(adapter);
                            // Set layout manager to position the items
                            rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        Intent intent = new Intent(this, LightActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
