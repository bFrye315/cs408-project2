package edu.jsu.mcis.cs408.project2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.jsu.mcis.cs408.project2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        DatabaseHandler db = new DatabaseHandler(this, null, null, 1);
        // Create and initialize shared ViewModel

        CrosswordViewModel model = new ViewModelProvider(this).get(CrosswordViewModel.class);
        model.init(this);
        ArrayList<String> keys = db.getAllKeysAsList();
        model.resume(keys);

    }

}