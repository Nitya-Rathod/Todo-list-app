package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
            intent.putExtra("type", "addMode");
            startActivityForResult(intent, 1);
        });
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
        RvAdaptor adaptor = new RvAdaptor(MainActivity.this);
        binding.rv.setAdapter(adaptor);

        noteViewModel.getAllNotes().observe(this, notes -> adaptor.submitList(notes));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){
                    Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title", adaptor.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disp", adaptor.getNote(viewHolder.getAdapterPosition()).getDisp());
                    intent.putExtra("id", adaptor.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);
                }
                else {
                    noteViewModel.delete(adaptor.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(binding.rv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            Note note = new Note(title, disp);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == 2){
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            Note note = new Note(title, disp);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        }
    }
}
