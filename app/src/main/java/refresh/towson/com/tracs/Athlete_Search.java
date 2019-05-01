package refresh.towson.com.tracs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Athlete_Search extends AppCompatActivity {

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    ArrayList<String> AthleteName;
    Spinner athletes_drpdown_btn;
    String email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConfig app=new AppConfig();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_search);

        AthleteName = new ArrayList<String>();
        athletes_drpdown_btn = (Spinner) findViewById(R.id.athlete_spinner);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MyPreferences,Context.MODE_PRIVATE);
        email_id = sharedPreferences.getString("Email_ID",null);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        Button back_button = (Button) findViewById(R.id.back_button);


        // Back Button Code, After click redirecting to Athlete Main Screen

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Athlete_Search.this,MainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        loadAthletesByTeam(app.ServerAthletesByTeam,email_id);

        athletes_drpdown_btn.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*athletes_drpdown_btn.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //String athleteID =   athletes_drpdown_btn.getItemAtPosition(athletes_drpdown_btn.getSelectedItemPosition()).toString();
                loadAthletesByTeam(app.ServerAthletesByTeam,email_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }
        });*/
    }

    private  void loadAthletesByTeam(String url, String athleteID) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try{
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject athlete_name_ob = array.getJSONObject(i);
                        Log.d("Athlete_name", athlete_name_ob.toString());

                        //adding the team_name to team list
                        AthleteName.add(athlete_name_ob.getString("athlete_name"));
                        Log.d("AthName", AthleteName.toString());
                    }

                    athletes_drpdown_btn.setAdapter(new ArrayAdapter<String>(Athlete_Search.this, android.R.layout.simple_spinner_dropdown_item, AthleteName));

                }catch (JSONException e){e.printStackTrace();}

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }

        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String>  params = new HashMap<String,String>();
                params.put("athleteID", athleteID);
                return params;
            }
        };

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);
    }


}
