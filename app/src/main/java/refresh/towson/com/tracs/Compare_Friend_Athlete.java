package refresh.towson.com.tracs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Compare_Friend_Athlete extends AppCompatActivity {

    ArrayList<String> AthleteName,TeamName;
    String athlete_result_select;
    String athlete_select;
    Spinner spinner,spinner2;
    Button button,compare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare__friend__athlete);
        TeamName=new ArrayList<>();
        AthleteName=new ArrayList<>();
        AppConfig app=new AppConfig();
        spinner=(Spinner)findViewById(R.id.spinner_select_friend);
        spinner2=(Spinner)findViewById(R.id.spinner_result_type);
        button=(Button)(findViewById(R.id.action_back1));
        compare=(Button)(findViewById(R.id.compare_results));
        loadAthleteData(app.ServerAthleteNames);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Compare_Friend_Athlete.this,Team_Coach.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                // your handler code here
            }
         });

        compare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(athlete_result_select.equals("Body Composition")) {
                    Intent intent = new Intent(Compare_Friend_Athlete.this, View_Results_Team_Coach_display.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("athlete_id_pass", athlete_select);
                    intent.putExtra("type_result", athlete_result_select);
                    startActivity(intent);

                    finish();
                    // your handler code here
                }
                if(athlete_result_select.equals("FMS")) {
                    Intent intent = new Intent(Compare_Friend_Athlete.this, Team_Coach_Fms_Data.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("athlete_id_pass", athlete_select);
                    intent.putExtra("type_result", athlete_result_select);
                    startActivity(intent);

                    finish();
                    // your handler code here
                }

                if(athlete_result_select.equals("PhysiMax")) {
                    Intent intent = new Intent(Compare_Friend_Athlete.this, Team_Coach_Physimax.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("athlete_id_pass", athlete_select);
                    intent.putExtra("type_result", athlete_result_select);
                    startActivity(intent);

                    finish();
                    // your handler code here
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppConfig app1=new AppConfig();

                athlete_select=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),athlete_select,Toast.LENGTH_LONG).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppConfig app1=new AppConfig();

                athlete_result_select=spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),athlete_select,Toast.LENGTH_LONG).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });

    }

    private void loadAthleteData(String url) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try{
                    JSONArray array = new JSONArray(response);
                    AthleteName.clear();
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject athlete_name_ob = array.getJSONObject(i);

                        //adding the team_name to team list
                        AthleteName.add(athlete_name_ob.getString("athlete_name"));
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(Compare_Friend_Athlete.this, android.R.layout.simple_spinner_dropdown_item, AthleteName));

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
                params.put("teamname", "Men's Lacrosse");
                return params;
            }
        };

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }

}
