package com.example.persistence;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Context context = getApplicationContext();
        NameStore nameStore = new DBNameStore(context);
        ArrayList<Name> names = nameStore.getNames(context);
        names.add(new Name("Shane", "Gale"));
        names.add(new Name("Yiming", "Li"));
        names.add(new Name("Haha", "Hehe"));
        nameStore.writeNames(context, names);
        String namesStr = new String();
        for (Name n : nameStore.getNames(context)) {
            namesStr = namesStr + "\n" + n.getFirst() + " " + n.getLast();
        }
        TextView target = findViewById(R.id.greetingView);
        target.setText("Persistence says hello, " + namesStr + "!");
    }
}