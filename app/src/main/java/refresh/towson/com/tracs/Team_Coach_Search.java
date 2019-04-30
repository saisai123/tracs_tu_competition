package refresh.towson.com.tracs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;
import java.util.*;
import android.widget.TextView;
import org.json.*;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RetryPolicy;
import com.android.volley.DefaultRetryPolicy;

//Team_Coach_Search



public  class Team_Coach_Search extends AppCompatActivity {



    //String URL="http://techiesatish.com/demo_api/spinner.php";

    ArrayList<String> TeamName;
    ArrayList<String> AthleteName;
    String teamname_select;
    Spinner spinner,spinner2;
    Button button;
    TextView athelete_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConfig app=new AppConfig();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team__coach__search);
        button = (Button) findViewById(R.id.action_back1);
        TeamName=new ArrayList<>();
        AthleteName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner_team_selection);
        spinner2=(Spinner)findViewById(R.id.spinner_athl_team_selecte);
        athelete_view=(TextView)findViewById(R.id.text_athl_team_selected);



        loadSpinnerData(app.ServerTeamNames);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Team_Coach_Search.this,Team_Coach.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                // your handler code here
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String teamname=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),teamname,Toast.LENGTH_LONG).show();
                loadAthleteData(app.ServerAthleteNames,teamname);
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String athlete_select=   spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
               // Toast.makeText(getApplicationContext(),athlete_select,Toast.LENGTH_LONG).show();
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });


    }

    private void loadSpinnerData(String url) {

        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try{
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject team_name_ob = array.getJSONObject(i);

                        //adding the team_name to team list
                        TeamName.add(team_name_ob.getString("teamname"));
                    }

                spinner.setAdapter(new ArrayAdapter<String>(Team_Coach_Search.this, android.R.layout.simple_spinner_dropdown_item, TeamName));

                }catch (JSONException e){e.printStackTrace();}

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }

        });

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }



    private void loadAthleteData(String url,String teamname_select) {

        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try{
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject athlete_name_ob = array.getJSONObject(i);

                        //adding the team_name to team list
                        AthleteName.add(athlete_name_ob.getString("athlete_name"));
                    }

                    spinner2.setAdapter(new ArrayAdapter<String>(Team_Coach_Search.this, android.R.layout.simple_spinner_dropdown_item, AthleteName));

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
                params.put("teamname", teamname_select);
                return params;
            }
        };

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }








}



