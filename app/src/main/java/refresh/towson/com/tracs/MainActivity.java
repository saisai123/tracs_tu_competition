package refresh.towson.com.tracs;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email= findViewById(R.id.input_email);
        login= findViewById(R.id.login_bt);
        password= findViewById(R.id.input_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if(e.equals("111")&&p.equals("pass")){
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),MainScreen.class);
                    startActivity(i);
                    setContentView(R.layout.activity_main_screen);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    login.setEnabled(false);
                }
            }
        });
    }


    public void login(){
        if(!validate()){
            loginAgain();
            return;
        }
    }

    public boolean validate(){
        boolean valid=true;
        String e=email.getText().toString();
        String p=password.getText().toString();
        if( e==null){
            email.setError("enter valid email");
            valid=false;
        }

        if(p==null){
            password.setError("Invalid");
        valid=false;
        }

        else
        {
            if(e.equals("111")&&p.equals("pass")){
                Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_LONG).show();
            }

        }
    return valid;
    }


    public void loginAgain(){
        Toast.makeText(getBaseContext(),"Try Again",Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }

    public void onLoginSuccess(){
        login.setEnabled(true);
        finish();
    }
}
