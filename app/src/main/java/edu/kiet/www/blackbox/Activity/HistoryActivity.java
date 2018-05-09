package edu.kiet.www.blackbox.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.kiet.www.blackbox.R;
import edu.kiet.www.blackbox.adapters.historyAdapter;
import edu.kiet.www.blackbox.models.HistoryPOJO;

public class HistoryActivity extends AppCompatActivity {
 RecyclerView recyclerView;
    List<HistoryPOJO> historyPOJOList=  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Previous Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.e("data",getIntent().getExtras().getString("data"));
        historyPOJOList = new Gson().fromJson(getIntent().getExtras().getString("data"),new TypeToken<ArrayList<HistoryPOJO>>() {}.getType());
        Log.e("data111",new Gson().toJson(historyPOJOList));

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter adapter = new historyAdapter(this,historyPOJOList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
