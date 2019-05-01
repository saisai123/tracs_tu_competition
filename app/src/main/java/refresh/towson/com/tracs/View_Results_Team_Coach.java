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

public class View_Results_Team_Coach extends AppCompatActivity {


    //String URL="http://techiesatish.com/demo_api/spinner.php";

    ArrayList<String> TeamName;
    ArrayList<String> AthleteName;
    String teamname_select;
    String athlete_result_select;
    String athlete_select;
    Spinner spinner,spinner2,spinner3;
    Button button,view_results;
    TextView athelete_view,text_team_select,text_result__type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConfig app=new AppConfig();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__results_team_coach);
        button = (Button) findViewById(R.id.action_back1);
        view_results = (Button) findViewById(R.id.view_results);

        TeamName=new ArrayList<>();
        AthleteName=new ArrayList<>();

        spinner=(Spinner)findViewById(R.id.spinner_team_selection);
        spinner2=(Spinner)findViewById(R.id.spinner_athl_team_selecte);
        spinner3=(Spinner)findViewById(R.id.spinner_result_type);

        athelete_view=(TextView)findViewById(R.id.text_athl_team_selected);
        text_result__type=(TextView)findViewById(R.id.text_result__type);
        text_team_select=(TextView)findViewById(R.id.text_team_select);


        loadSpinnerData(app.ServerTeamNames);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(View_Results_Team_Coach.this,Team_Coach.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                // your handler code here
            }
        });


        view_results.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(athlete_result_select.equals("Body Composition")) {
                    Intent intent = new Intent(View_Results_Team_Coach.this, View_Results_Team_Coach_display.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("athlete_id_pass", athlete_select);
                    intent.putExtra("type_result", athlete_result_select);
                    startActivity(intent);

                    finish();
                    // your handler code here
                }
                if(athlete_result_select.equals("FMS")) {
                    Intent intent = new Intent(View_Results_Team_Coach.this, Team_Coach_Fms_Data.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("athlete_id_pass", athlete_select);
                    intent.putExtra("type_result", athlete_result_select);
                    startActivity(intent);

                    finish();
                    // your handler code here
                }

                if(athlete_result_select.equals("PhysiMax")) {
                    Intent intent = new Intent(View_Results_Team_Coach.this, Team_Coach_Physimax.class);
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

                String teamname=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                teamname_select=String.valueOf(spinner.getSelectedItem());
                Toast.makeText(getApplicationContext(),teamname,Toast.LENGTH_LONG).show();
                loadAthleteData(app.ServerAthleteNames,teamname_select);
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                athlete_select=spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),athlete_select,Toast.LENGTH_LONG).show();
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                athlete_result_select=   spinner3.getItemAtPosition(spinner3.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),athlete_result_select,Toast.LENGTH_LONG).show();
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }

        });


    }






    private void loadSpinnerData(String url) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

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

                    spinner.setAdapter(new ArrayAdapter<String>(View_Results_Team_Coach.this, android.R.layout.simple_spinner_dropdown_item, TeamName));

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

                    spinner2.setAdapter(new ArrayAdapter<String>(View_Results_Team_Coach.this, android.R.layout.simple_spinner_dropdown_item, AthleteName));

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
