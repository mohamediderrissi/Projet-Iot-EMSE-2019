package com.example.androidlecture;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LightsAdapter extends
        RecyclerView.Adapter<LightsAdapter.ViewHolder> {

    // Handles the row being being clicked

    RequestQueue queue;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;
        public Button messageButton2;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            messageButton2 = (Button) itemView.findViewById(R.id.message_button2);

        }
    }

    // Store a member variable for the contacts
    private List<String> mLights;

    // Pass in the contact array into the constructor
    public LightsAdapter(List<String> lights) {
        mLights = lights;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public LightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_ticket, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(LightsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String hueID = mLights.get(position);
        final String hueid = hueID;


        final int pos = position;
        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText("hue's id: " + hueID);
        final Button turnOn = viewHolder.messageButton;
        final Button turnOff = viewHolder.messageButton2;

        turnOn.setText("Switch ON");
        turnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchLight(v, hueid, "ON");
            }
        });
        turnOff.setText("Switch OFF");
        turnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchLight(v, hueid, "OFF");
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mLights.size();
    }

    public void switchLight(final View v, String hueId, String ON_OFF) {
        queue = Volley.newRequestQueue(v.getContext());

        // switch light
        try {
            String URL = "http://idrissi.cleverapps.io/api/lights/"
                    + hueId;
            if (ON_OFF.equals("ON")) {
                URL += "/switchOn";
            } else if (ON_OFF.equals("OFF")) {
                URL += "/switchOff";
            }
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("id", Integer.parseInt(hueId));

            System.out.println(jsonBody);
            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PUT,
                    URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(v.getContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    System.err.println(error);
                    // we have to expect JSONArray not jsonObject
                    //  onBackPressed();
                    Toast.makeText(v.getContext(), "error", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(v.getContext(), "405 error", Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }

    public void redirect(View view, int position) {
        Intent intent = new Intent(view.getContext(), ContextManagementActivity.class);
        intent.putExtra("position", position);
        view.getContext().startActivity(intent);
    }
}