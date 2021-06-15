package com.example.runoassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.runoassignment.R.menu.menu;

public class ViewNotes extends AppCompatActivity {

//    static ArrayList<String> notes = new ArrayList<String>();
//    static ArrayAdapter<String> arrayAdapter;
    static String notes;
    TextView noteview;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_note:
                Intent intent = new Intent(getApplicationContext(), NoteEditor.class);
                intent.putExtra("notes","");
                startActivity(intent);
                return true;
            case R.id.logoutmenuId:
                logout();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        noteview=(TextView)findViewById(R.id.notesview);

//        ListView listView = (ListView) findViewById(R.id.listview);
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.runoassignment", Context.MODE_PRIVATE);
//        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);

//        if(set == null)
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    notes=String.valueOf(task.getResult().getValue());
                    noteview.setText(notes);
                }
            }
        });




//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
//        listView.setAdapter(arrayAdapter);
        noteview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteEditor.class);
                intent.putExtra("notes",notes);
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Intent intent = new Intent(getApplicationContext(), NoteEditor.class);
//                intent.putExtra("noteID", position);
//                startActivity(intent);
//            }
//        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
//            {
//                new AlertDialog.Builder(ViewNotes.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Delete?")
//                        .setMessage("Are you sure you want to delete this note?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
//                            {
//                                notes.remove(position);
//                                arrayAdapter.notifyDataSetChanged();
//
//                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
//                                HashSet<String> set = new HashSet<>(ViewNotes.notes);
//                                sharedPreferences.edit().putStringSet("notes", set).apply();
//                            }
//                        })
//
//                        .setNegativeButton("No", null)
//                        .show();
//
//                return true;               // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
//            }
//        });

    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ViewNotes.this,MainActivity.class));
    }
}