package refresh.towson.com.tracs;

import android.content.Intent;

public class Logout {

    boolean logout(){
        //finish();
        Intent i=new Intent();
        i.putExtra("finish", true);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        //startActivity(i);
        //finish();
        return true;


    }

}

