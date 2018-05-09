package edu.kiet.www.blackbox.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.fitness.HistoryApi;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kiet.www.blackbox.R;
import edu.kiet.www.blackbox.interfaces.HistoryInterface;
import edu.kiet.www.blackbox.models.HistoryPOJO;
import edu.kiet.www.blackbox.networking.ServiceGenerator;
import edu.kiet.www.blackbox.ui.ColoredSnackbar;
import edu.kiet.www.blackbox.ui.SelectDateFragment;
import edu.kiet.www.blackbox.util.NetworkCheck;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class HistoryDateActivity extends AppCompatActivity implements View.OnClickListener {


    public static EditText FromDate, ToDate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Button submit_attendance;
    ProgressDialog progressDialog;
    String minDate;
    SelectDateFragment dateFragment;
    Gson gson = new Gson();
    private ColoredSnackbar coloredSnackBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Dates");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FromDate = (EditText) findViewById(R.id.fromdate);
        ToDate = (EditText) findViewById(R.id.todate);
        FromDate.setOnClickListener(this);
        ToDate.setOnClickListener(this);
        FromDate.setLongClickable(false);
        ToDate.setLongClickable(false);
        minDate = "2016-01-01";
        FromDate.setText(sdf.format(new Date()));
        ToDate.setText(sdf.format(new Date()));
        dateFragment = new SelectDateFragment();
        submit_attendance = (Button) findViewById(R.id.submit_attendance);
        submit_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(HistoryDateActivity.this)) {
                    fetch();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.network_error, Snackbar.LENGTH_LONG);
                    coloredSnackBar.warning(snackbar).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromdate:
                FromDate.setClickable(false);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        FromDate.setClickable(true);
                    }
                }, 500);
                dateFragment.show(getSupportFragmentManager(), FromDate.getText().toString(),FromDate,minDate,ToDate.getText().toString());
                break;
            case R.id.todate:
                ToDate.setClickable(false);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ToDate.setClickable(true);
                    }
                }, 500);
                dateFragment.show(getSupportFragmentManager(),ToDate.getText().toString(),ToDate,FromDate.getText().toString(),sdf.format(new Date()));
                break;
        }
    }
    private void fetch() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        HistoryInterface attendanceRequest = ServiceGenerator.createService(HistoryInterface.class);
        Call<List<HistoryPOJO>> call = attendanceRequest.getResponse("1",FromDate.getText().toString(), ToDate.getText().toString());

        call.enqueue(new Callback<List<HistoryPOJO>>() {
            @Override
            public void onResponse(Call<List<HistoryPOJO>> call, Response<List<HistoryPOJO>> response) {
                List<HistoryPOJO> responseBody = response.body();
                progressDialog.dismiss();
                if (response.code() == 200) {
                        Intent i = new Intent(HistoryDateActivity.this, HistoryActivity.class);
                        Bundle b = new Bundle();
                        b.putString("data",gson.toJson(responseBody));
                        i.putExtras(b);
                        startActivity(i);

                      }
           /*     else
                {    progressDialog.dismiss();
                    new AlertDialog.Builder(HistoryDateActivity.this)
                        .setMessage(responseBody.getMsg())
                        .setTitle("Some Error Occured")
                        .show();

                }*/
            }

            @Override
            public void onFailure(Call<List<HistoryPOJO>> call, Throwable t) {
                progressDialog.dismiss();
               Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Error in Connection", Snackbar.LENGTH_LONG);
                coloredSnackBar.warning(snackbar).show();
                /*Intent i = new Intent(HistoryDateActivity.this, HistoryActivity.class);
                Bundle b = new Bundle();
                List<HistoryPOJO> historyPOJOs = new ArrayList<HistoryPOJO>();
                HistoryPOJO historyPOJO = new HistoryPOJO();
                historyPOJO.setDate("");
                historyPOJO.setEnd_location("end location oifhwiovnddhjdsv");
                historyPOJO.setStart_location("start location oifhwiovnddhjdsv");
                historyPOJO.setStart_time("17:00");
                historyPOJO.setEnd_time("18:00");
                historyPOJOs.add(historyPOJO);
                historyPOJO.setDate("2017-08-12");
                for(int c=0;c<6;c++){
                    historyPOJOs.add(historyPOJO);
                }
                b.putString("data",gson.toJson(historyPOJOs));
                Log.e(";jd","lhccds");
                Log.e("details",gson.toJson(historyPOJOs));
                i.putExtras(b);
                startActivity(i);*/
                t.printStackTrace();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
