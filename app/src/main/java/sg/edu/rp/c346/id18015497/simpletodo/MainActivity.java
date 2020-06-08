package sg.edu.rp.c346.id18015497.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Spinner addRemove;
    EditText todo;
    Button btnAdd, btnRemove, btnClear;
    ListView lvtd;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo = findViewById(R.id.toDo);
        btnAdd = findViewById(R.id.btnAdd);
        btnClear = findViewById(R.id.btnClear);
        btnRemove = findViewById(R.id.btnRemove);
        lvtd = findViewById(R.id.listView);
        addRemove = findViewById(R.id.spinner);

        final ArrayList<String> toDoList = new ArrayList<String>();
        todo = findViewById(R.id.toDo);

        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,toDoList);
        lvtd.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoitem = todo.getText().toString();

                if (todoitem.isEmpty()) {
                    Toast.makeText(MainActivity.this,String.format("Enter a Task."), Toast.LENGTH_SHORT).show();
                }
                else {
                    toDoList.add(todoitem);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoitem = todo.getText().toString();
                if (toDoList.size() != 0) {
                    boolean valid = isValidIndex(todoitem);
                    if (valid == true) {
                        int pos = Integer.parseInt(todo.getText().toString());
                        if (pos < toDoList.size()) {
                            toDoList.remove(pos);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(MainActivity.this,String.format("%s does not exist.", pos), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if (valid == false) {
                        Toast.makeText(MainActivity.this,String.format("Please enter a valid number."), Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(MainActivity.this,String.format("You don't have any task to remove."), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoList.clear();
                todo.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        addRemove.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        todo.setHint("Type in a new task here.");
                        btnAdd.setEnabled(true);
                        btnRemove.setEnabled(false);
                        break;
                    case 1:
                        todo.setHint("Type in the index to be removed.");
                        btnAdd.setEnabled(false);
                        btnRemove.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean isValidIndex(String target) {
        return Pattern.compile("(?<=\\s|^)\\d+(?=\\s|$)").matcher(target).matches();
    }
}
