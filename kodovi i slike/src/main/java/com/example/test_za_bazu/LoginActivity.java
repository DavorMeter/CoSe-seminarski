package com.example.test_za_bazu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import java.lang.reflect.Type;
import java.util.Collection;

public class LoginActivity extends AppCompatActivity {
    public Users loggedInUser = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName = (EditText) findViewById(R.id.UsernameEntry);
        final EditText userPassword = (EditText) findViewById(R.id.PasswordEntry);
        Button singInButton = (Button) findViewById(R.id.SignInBottun);

        AsyncHttpClient myClient = new AsyncHttpClient();
        final Gson myGson = new Gson();

        myClient.get("https://raw.githubusercontent.com/warbossy/CoSe-seminarski/main/users.txt", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Nesto nevalja s API-em",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type collectionType = new TypeToken<Collection<Users>>(){}.getType();
                Collection<Users> users = myGson.fromJson(responseString, collectionType);
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                for(Users u : users) {
                    userDataSource.addUserToDb(
                            u.getUser_ID(),
                            u.getUser_name(),
                            u.getUser_password()
                    );
                }
                DataStorage.usersList = userDataSource.getAllUsers();
            }
        });

    singInButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String userNameString = null;
            String userPasswordString = null;
            userNameString = userName.getText().toString();
            userPasswordString = userPassword.getText().toString();
            UserDataSource userDataSource = new UserDataSource(getApplicationContext());
            Users userZaUsporedivanje = userDataSource.getUserByName(userNameString);
            if(!userNameString.isEmpty() && !userPasswordString.isEmpty())
            {

                Log.d("userNameString!!!!!",userNameString);
                Log.d("userPasswordString!!!!!",userPasswordString);
                Log.d("userZaUsName!!!!!",String.valueOf(userZaUsporedivanje.getUser_name()));
                Log.d("userZaUsPass!!!!!",String.valueOf(userZaUsporedivanje.getUser_password()));
                if(userPasswordString.equals(userZaUsporedivanje.getUser_password()))
                {
                    editor.putString("value",userZaUsporedivanje.getUser_name());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                }
                else Toast.makeText(getApplicationContext(),"Neispravni username ili password.",Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(getApplicationContext(),"Nije upisano ime ili lozinka.",Toast.LENGTH_LONG).show();


        }
    });
    }
}