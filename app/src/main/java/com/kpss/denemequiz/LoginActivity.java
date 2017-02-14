package com.kpss.denemequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kpss.denemequiz.Async.AsyncBaseClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends Activity {

    Button login;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProgressDialog progressDialog;
    AlertDialog builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.buttonFacebookLogin);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        if (AccessToken.getCurrentAccessToken() != null) {

            updateWithToken(AccessToken.getCurrentAccessToken());
        }

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("FaceLog", "Success");
            }

            @Override
            public void onCancel() {
                Log.i("FaceLog", "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i("FaceLog", "Error");
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                updateWithToken(currentAccessToken);
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppController.getInstance().isConnected()) {

                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                } else {
                    AlertDialogShow();
                }
            }
        });

    }

    private void AlertDialogShow() {


        if (builder == null) builder = new AlertDialog.Builder(LoginActivity.this)

                .setMessage("İnternet Bağlantısı Yok")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        if (!builder.isShowing()) builder.show();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppController.getInstance().isConnected()) {

            Profile profile = Profile.getCurrentProfile();
        } else {
            AlertDialogShow();
        }
    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (AppController.getInstance().isConnected()) {

            if (currentAccessToken != null) {


                Log.i("FaceLog", "Already Logged.");



                    GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            Log.i("FaceLog", jsonObject.toString());
                            Profile profile = Profile.getCurrentProfile();
                            if (profile != null) {
                                AppController.getInstance().profilImageUrl = profile.getProfilePictureUri(200, 200);
                                AppController.getInstance().profilName = profile.getName();
                                AppController.getInstance().profilLink = profile.getLinkUri();
                                AppController.getInstance().profileID = profile.getId();
                                try {
                                    AppController.getInstance().profileID = jsonObject.getString("id");
                                    AppController.getInstance().profilGender = jsonObject.getString("gender");
                                    AppController.getInstance().profilEmail = jsonObject.getString("email");
                                    AppController.getInstance().profilLocale = jsonObject.getString("locale");
                                    AppController.getInstance().profilTimezone = jsonObject.getString("timezone");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + "me" + "/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse graphResponse) {


                                    Log.i("FaceLog", graphResponse.getJSONObject().toString());
                                    try {
                                        AppController.getInstance().profilFriendsCount = graphResponse.getJSONObject().getJSONObject("summary").getString("total_count");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    AsyncBaseClass mr = new AsyncBaseClass(LoginActivity.this) {
                                        @Override
                                        protected String doInBackground(String... strings) {
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(String response) {
                                            finish();
                                        }
                                    };

                                    mr.execute();










                                }
                            }).executeAsync();
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,locale,timezone");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();




            } else {
                Log.i("FaceLog", "Not Logged.");

            }

        } else {
            AlertDialogShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }


}
