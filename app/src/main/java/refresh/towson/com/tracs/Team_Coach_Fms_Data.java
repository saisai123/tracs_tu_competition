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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team_Coach_Fms_Data extends AppCompatActivity {

    Button button;
    String athlete_collect;
    String athlete_result_type;
    TextView shoulder_mobility_val,deep_squat_val,hurdle_step_val,in_lunge_val,active_straight_leg_raise_val,push_up_val,rotarty_stability_val,fms_total_score_val;
    ArrayList<String> attributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team__coach__fms__data);
        Bundle extras = getIntent().getExtras();
        AppConfig app=new AppConfig();
        if(extras!=null) {
            athlete_collect= extras.getString("athlete_id_pass");
            athlete_result_type=extras.getString("type_result");
        }

        button = (Button) findViewById(R.id.action_back1);
        shoulder_mobility_val=(TextView)(findViewById(R.id.shoulder_mobility_val));
        deep_squat_val=(TextView)(findViewById(R.id.deep_squat_val));
        hurdle_step_val=(TextView)(findViewById(R.id.hurdle_step_val));
        in_lunge_val=(TextView)(findViewById(R.id.in_lunge_val));
        active_straight_leg_raise_val=(TextView)(findViewById(R.id.active_straight_leg_raise_val));
        push_up_val=(TextView)(findViewById(R.id.push_up_val));
        rotarty_stability_val=(TextView)(findViewById(R.id.rotarty_stability_val));
        fms_total_score_val=(TextView)(findViewById(R.id.fms_total_score_val));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Team_Coach_Fms_Data.this,View_Results_Team_Coach.class);
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
                    attributes.add(height.getString("shoulder_mobility"));
                    attributes.add(height.getString("deep_squat"));
                    attributes.add(height.getString("hurdle_step"));
                    attributes.add(height.getString("in_lunge"));
                    attributes.add(height.getString("active_straight_leg_raise"));
                    attributes.add(height.getString("push_up"));
                    attributes.add(height.getString("rotarty_stability"));
                    attributes.add(height.getString("fms_total_score"));


                    if(attributes!=null && attributes.size()>0 && attributes.size()<=8) {

                        shoulder_mobility_val.setText(attributes.get(0));

                        deep_squat_val.setText(attributes.get(1));

                        hurdle_step_val.setText(attributes.get(2));

                        in_lunge_val.setText(attributes.get(3));

                        active_straight_leg_raise_val.setText(attributes.get(4));


                        push_up_val.setText(attributes.get(5));

                        rotarty_stability_val.setText(attributes.get(6));

                        fms_total_score_val.setText(attributes.get(7));
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
