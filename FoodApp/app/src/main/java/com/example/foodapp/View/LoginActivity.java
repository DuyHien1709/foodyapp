package com.example.foodapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.foodapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener/**, FirebaseAuth.AuthStateListener**/ {

    SignInButton btnDangNhapGoogle;
    Button btnDangNhap;
    Button btnDangKy;
    EditText edEmailDN;
    EditText edPassworDN;

    LoginButton btnDangNhapFaceBook;

    GoogleApiClient apiClient;
    public static int REQUESTCODE_DANGNHAP_GOOGLE = 99;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 0;
    FirebaseAuth firebaseAuth;

    CallbackManager mCallbackFaceBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        btnDangNhapGoogle = (SignInButton) findViewById(R.id.btnDangNhapGoogle);
        btnDangNhapGoogle.setOnClickListener(this);

        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(this);

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(this);

        edEmailDN = (EditText) findViewById(R.id.edEmailDN);
        edPassworDN = (EditText) findViewById(R.id.edPasswordDN);


        /**TaoClientDangNhapGoogle();**/
    }


    /**private void TaoClientDangNhapGoogle(){
     GoogleSignInOptions signInOptions = new GoogleSignInOptions
     .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
     .requestIdToken("463566256684-fdunoog9qnsoj8mjrksuocq58tt1astq.apps.googleusercontent.com")
     .requestEmail()
     .build();

     apiClient = new GoogleApiClient.Builder(this)
     .enableAutoManage(this,this)
     .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
     .build();
     }

     @Override
     protected void onStart() {
     super.onStart();
     firebaseAuth.addAuthStateListener(this);
     }

     @Override
     protected void onStop() {
     super.onStop();
     firebaseAuth.removeAuthStateListener(this);
     }

     private void DangNhapGoogle(GoogleApiClient apiClient){
     KIEMTRA_PROVIDER_DANGNHAP = 1;
     Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
     startActivityForResult(iDNGoogle,REQUESTCODE_DANGNHAP_GOOGLE);
     }

     private void ChungThucDangNhapFireBase(String tokenID) {
     if (KIEMTRA_PROVIDER_DANGNHAP == 1) {
     AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
     firebaseAuth.signInWithCredential(authCredential);
     } else if (KIEMTRA_PROVIDER_DANGNHAP == 2) {
     AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
     firebaseAuth.signInWithCredential(authCredential);
     }

     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == REQUESTCODE_DANGNHAP_GOOGLE) {
     if (resultCode == RESULT_OK) {
     GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
     GoogleSignInAccount account = signInResult.getSignInAccount();
     String tokenID = account.getIdToken();
     ChungThucDangNhapFireBase(tokenID);
     }
     else{
     mCallbackFaceBook.onActivityResult(requestCode,resultCode,data);
     }
     }
     }


     @Override
     public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
     FirebaseUser user = firebaseAuth.getCurrentUser();
     if(user != null){
     Toast.makeText(DangNhapActivity.this,"Đăng nhập thành công bằng Google !",Toast.LENGTH_SHORT).show();
     }else{
     Toast.makeText(DangNhapActivity.this,"Đăng nhập không thành công bằng Google !",Toast.LENGTH_SHORT).show();
     }
     }
     **/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
//            case R.id.btnDangNhapGoogle:
//                /** DangNhapGoogle(apiClient); **/
//                /**startActivity(new Intent(LoginActivity.this, MainActivity.class));**/
//                break;

            case R.id.btnDangNhap:
                String Email, Password;
                Email = edEmailDN.getText().toString();
                Password = edPassworDN.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(this,"Vui lòng nhập Email !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    Toast.makeText(this,"Vui lòng nhập mật khẩu !",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Đăng nhập không thành công !",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.btnDangKy:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

}