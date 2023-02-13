package com.example.mc_assignment_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_search, btn_translation, btn_git_ink, btn_add;
    DbHandler dbHandler;
    EditText txt_parah, txt_surah;
    TextView txt_res;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_search = findViewById(R.id.btn_search);
        btn_translation = findViewById(R.id.btn_translation);
        btn_git_ink = findViewById(R.id.git_link);
        btn_add = findViewById(R.id.btn_add);
        txt_parah = findViewById(R.id.txt_parah);
        txt_surah = findViewById(R.id.txt_surah);
        txt_res = findViewById(R.id.result);
        dbHandler = new DbHandler(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHandler.fillData();
            }
        });

        btn_translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_res.setText("");
                String parah = txt_parah.getText().toString();
                String surah = txt_surah.getText().toString();
                data = dbHandler.searchTranslation(parah, surah);

                adapter = new MyAdapter(data);
                recyclerView.setAdapter(adapter);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            String listString = "";

            @Override
            public void onClick(View view) {
                listString = "";
                txt_res.setText("");
                String parah = txt_parah.getText().toString();
                String surah = txt_surah.getText().toString();
                ArrayList<String> txt = dbHandler.searchSurah(parah, surah);

                for (String s : txt) {
                    listString += s + " ";
                }
                txt_res.setText(listString);

                txt_res.setMovementMethod(new ScrollingMovementMethod());

            }
        });

        btn_git_ink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://github.com/aafaqjavaid786/assignment4");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<String> data;

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            MyViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.text_view);
            }
        }

        MyAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}