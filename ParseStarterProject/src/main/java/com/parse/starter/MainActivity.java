/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener , View.OnKeyListener {

 Boolean signUpMode = true;
  TextView loginText;
  EditText userET;
  EditText passwordET;

     public  void showUserList(){

      Intent intent = new Intent(getApplicationContext(),userList.class);
      startActivity(intent);
  }


  @Override
  public boolean onKey(View v, int i, KeyEvent event) {
    if (i == KeyEvent.KEYCODE_ENTER &&  event.getAction()==KeyEvent.ACTION_DOWN){

      signupClicked(v);


    }
    return false;
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.loginTV){
      Button signUpbutton = findViewById(R.id.signInButton);
      if(signUpMode){
        signUpMode = false;
        signUpbutton.setText("LOGIN");
        loginText.setText("OR SIGN UP");
      }
      else{
        signUpMode = true;
        signUpbutton.setText("SIGN UP");
        loginText.setText("OR LOGIN");

      }
      Log.i("done","it was tapped");
    }

    else if(v.getId() == R.id.logoIV || v.getId() == R.id.layoutRV){
      InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  public void signupClicked (View view){

    if(userET.getText().toString() .matches("") || passwordET.getText().toString().matches("")){
      Toast.makeText(this,"Username or Password cannot be empty",Toast.LENGTH_LONG).show();
    }else {
      if (signUpMode){
        ParseUser user = new ParseUser();
      user.setUsername(userET.getText().toString());
      user.setPassword(passwordET.getText().toString());

      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null)
              showUserList();
            //Log.i("Success", "DONE!");
          else
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
      });
    } else{
        // Login case

        ParseUser.logInInBackground(userET.getText().toString(), passwordET.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(user != null){
                showUserList();
              Log.i("DONE!","LOG IN COMPLETE");
            }
            else{
              Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram");
    loginText = findViewById(R.id.loginTV);
    loginText.setOnClickListener(this);

    userET = findViewById(R.id.usernameEditText);
    passwordET = findViewById(R.id.passwordEditText);
    passwordET.setOnKeyListener(this);  // setting on key listener to password

      //If the app is opened second time ,, and someone has already logged in  then-----

      if(ParseUser.getCurrentUser() != null){
          showUserList();
      }

    ImageView instalogo = findViewById(R.id.logoIV);
    RelativeLayout instalayout = findViewById(R.id.layoutRV);
    instalogo.setOnClickListener(this);
    instalayout.setOnClickListener(this);


    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}







 /* ParseUser  user = new ParseUser();
    user.setUsername("Koustav");
    user.setPassword("kroy");
    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if(e==null)
                Log.i("SUCCESS","DONE!");
            else
                e.printStackTrace();
        }
    });


   ParseUser.logInInBackground("Koustav", "kroy", new LogInCallback() {
       @Override
       public void done(ParseUser user, ParseException e) {
           if(user!=null)
               Log.i("Logged in","**");
           else
               e.printStackTrace();
       }
   });

   if(ParseUser.getCurrentUser()!=null){
       Log.i("Logged in as",ParseUser.getCurrentUser().getUsername());

   }
   else
       Log.i("Error","No one is logged in!");*/











/* ParseObject score2 = new ParseObject("Score2");
    score2.put("username","kroy");
    score2.put("marks",25);
    score2.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null)
          Log.i("SUCCESSFUL","");
        else
          e.printStackTrace();
      }
    });
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score2");
    query.getInBackground("SLkXX4PTKu", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null && object != null) {

          object.put("marks",100);
          object.saveInBackground();
          Log.i("username = ", object.getString("username"));
          Log.i("score = " , Integer.toString(object.getInt("marks")) );
        }
        else
          e.printStackTrace();
      }
    });*/




      /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Score2");
    query.whereLessThanOrEqualTo("marks",50);
   query.findInBackground(new FindCallback<ParseObject>() {
     @Override
     public void done(List<ParseObject> objects, ParseException e) {
       if (e == null) {


         if (objects.size() > 0) {
           for (ParseObject object : objects) {
               object.put("marks", object.getInt("marks")+20);
                object.saveInBackground();
           }
         }
       }
     }
   });*/
