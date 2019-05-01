package refresh.towson.com.tracs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Team_Coach_Physimax extends AppCompatActivity {

    Button button;
    String athlete_collect;
    String athlete_result_type;
    TextView sls_left_score_val,sls_left_valgus_val,sls_left_valgus_err_val,sls_right_score_val,sls_right_score_valgus_val,sls_right_score_valgus_err_val;
    ArrayList<String> attributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_coach_physimax);
        Bundle extras = getIntent().getExtras();
        AppConfig app=new AppConfig();
        if(extras!=null) {
            athlete_collect= extras.getString("athlete_id_pass");
            athlete_result_type=extras.getString("type_result");
        }
        button = (Button) findViewById(R.id.action_back1);
        sls_left_score_val=(TextView)findViewById(R.id.sls_left_score_val);
        sls_left_valgus_val=(TextView)findViewById(R.id.sls_left_valgus_val);
        sls_left_valgus_err_val=(TextView)findViewById(R.id.sls_left_valgus_err_val);
        sls_right_score_val=(TextView)findViewById(R.id.sls_right_score_val);
        sls_right_score_valgus_val=(TextView)findViewById(R.id.sls_right_score_valgus_val);
        sls_right_score_valgus_err_val=(TextView)findViewById(R.id.sls_right_score_valgus_err_val);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Team_Coach_Physimax.this,View_Results_Team_Coach.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                // your handler code here
            }
        });
        loadSpinnerData(app.ServerAthleteResults,athlete_collect);
    }


    private void loadSpinnerData(String url,String athlete_collect) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                attributes=new ArrayList<String>();
                try{

                    JSONArray array = new JSONArray(response);
                    JSONObject height=array.getJSONObject(0);
                    attributes.add(height.getString("sls_left_score"));
                    attributes.add(height.getString("sls_left_valgus"));
                    attributes.add(height.getString("sls_left_valgus_err"));
                    attributes.add(height.getString("sls_right_score"));
                    attributes.add(height.getString("sls_right_score_valgus"));
                    attributes.add(height.getString("sls_right_score_valgus_err"));


                    if(attributes!=null && attributes.size()>0 && attributes.size()<=6) {

                        sls_left_score_val.setText(attributes.get(0));

                        sls_left_valgus_val.setText(attributes.get(1));

                        sls_left_valgus_err_val.setText(attributes.get(2));

                        sls_right_score_val.setText(attributes.get(3));

                        sls_right_score_valgus_val.setText(attributes.get(4));


                        sls_right_score_valgus_err_val.setText(attributes.get(5));

                    }

                    //traversing through all the object
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
                params.put("athlete_id", athlete_collect);
                params.put("result_type",athlete_result_type); //params.put("result_type",athlete_result_type);

                return params;
            }
        };

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }
}
