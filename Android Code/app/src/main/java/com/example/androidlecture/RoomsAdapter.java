package com.example.androidlecture;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomsAdapter extends
        RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    // Handles the row being being clicked


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    // Store a member variable for the contacts
    private List<String> mContacts;

    // Pass in the contact array into the constructor
    public RoomsAdapter(List<String> contacts) {
        mContacts = contacts;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_ticket_home, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RoomsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String room = mContacts.get(position);
        final int pos = position;
        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText("Room: " + room);
        final Button button = viewHolder.messageButton;
        button.setText("Open the door");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                System.out.println(pos);
                redirect(v, pos);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void redirect(View view, int position) {
        Intent intent = new Intent(view.getContext(), LightActivity.class);
        intent.putExtra("position", position);
        view.getContext().startActivity(intent);
    }
}