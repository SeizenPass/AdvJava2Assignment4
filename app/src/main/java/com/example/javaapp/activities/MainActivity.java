package com.example.javaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.javaapp.R;
import com.example.javaapp.models.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private List<ToDo> tasks = new ArrayList<>();
    private String sFileName = "data";
    private ArrayAdapter<ToDo> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDoCreateDialog(view);
            }
        });
        File dir = new File(getApplicationContext().getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, sFileName);
            Scanner s = new Scanner(gpxfile);
            while (s.hasNextLine()) {
                try {
                    List<String> todo = Arrays.asList(s.nextLine().split(","));
                    ToDo doDTO = new ToDo();
                    doDTO.setTaskName(todo.get(0).trim());
                    doDTO.setTaskDescription(todo.get(1).trim());
                    doDTO.setTaskCrossed(Boolean.parseBoolean(todo.get(2).trim()));
                    tasks.add(doDTO);
                    System.out.println(doDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Continuing data read...");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        lvItems = (ListView) findViewById(R.id.list);
        itemsAdapter = new ArrayAdapter<ToDo>(this,
                android.R.layout.simple_list_item_1, tasks);
        lvItems.setAdapter(itemsAdapter);
        /*for (int i = 0; i < lvItems.getLastVisiblePosition() - lvItems.getFirstVisiblePosition(); i++) {
            System.out.println("Data Index: " + i);
            View v = lvItems.getChildAt(i);
            //View v = lvItems.getAdapter().getView(i, null, null);
            if (!tasks.get(i).isTaskCrossed()) {
                v.setBackgroundColor(Color.parseColor("#FAFAFA"));

            } else {
                v.setBackgroundColor(Color.GRAY);
            }
        }*/
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 ToDo todo = tasks.get(i);
                 if (todo.isTaskCrossed()) {
                     todo.setTaskCrossed(false);
                 } else {
                     todo.setTaskCrossed(true);
                 }
                 save();
                 lvItems.setAdapter(itemsAdapter);
            }
        });
    }

    private void write() {
        //FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
        //fos.write(internalStorageBinding.saveFileEditText.getText().toString().getBytes());
        //fos.close();
    }

    private void save() {
        try {
            File dir = new File(getApplicationContext().getFilesDir(), "mydir");
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            StringBuilder sBody = new StringBuilder();
            for (ToDo d:
            tasks) {
                sBody.append(d.toParsable()).append("\n");
                System.out.println(d);
            }
            writer.append(sBody.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToDoCreateDialog(View view){
        final Dialog brushDialog = new Dialog(MainActivity.this);
        brushDialog.setContentView(R.layout.dialog_create_todo);
        brushDialog.setTitle("New To-Do");
        Button btn = brushDialog.findViewById(R.id.saveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo toDoDTO = new ToDo();
                EditText btn = (EditText)brushDialog.findViewById(R.id.taskName);
                String taskName = btn.getText().toString();
                btn = (EditText)brushDialog.findViewById(R.id.taskDescription);
                String taskDescription = btn.getText().toString();
                if (!taskName.trim().equals("") && !taskDescription.trim().equals("")) {
                    toDoDTO.setTaskName(taskName);
                    toDoDTO.setTaskDescription(taskDescription);
                    toDoDTO.setTaskCrossed(false);
                    itemsAdapter.add(toDoDTO);
                    save();
                    brushDialog.dismiss();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Fill the gaps!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        brushDialog.show();


    }

}
