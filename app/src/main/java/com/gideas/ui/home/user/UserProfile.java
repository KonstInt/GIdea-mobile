package com.gideas.ui.home.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gideas.AppInfo;
import com.gideas.MainActivity;
import com.gideas.R;
import com.gideas.login.Login;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    private Button UpdateAccoutSettings;

    private TextView back;
    private EditText  userStatus;
    private TextView userName, header;
    private EditText inputUserName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private static final int  GalleryPick = 1;
    private StorageReference UserProfileImagesRef;
    private ProgressDialog loadingBar;
    private boolean firstLogIn;
    private Dialog dialog;
    private Button logout;
    private TextView x_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        UpdateAccoutSettings = findViewById(R.id.update_settings_button);
        userName = findViewById(R.id.user_name);
        userStatus = findViewById(R.id.set_profile_status);
        userProfileImage = findViewById(R.id.set_profile_image);
        loadingBar = new ProgressDialog(this);
        inputUserName = findViewById(R.id.input_user_name);
        header = findViewById(R.id.profile_header);
        back = findViewById(R.id.back_to_menu_from_profile);
        inputUserName.setVisibility(View.GONE);
        userName.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);

        UpdateAccoutSettings.setText("Сохранить");

        UpdateAccoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });




        LoadUserData();

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,GalleryPick);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        header.setText("Профиль");
        if (getIntent().getBooleanExtra("FirstLogin", false))
        {

            inputUserName.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            firstLogIn = true;
            UpdateAccoutSettings.setText("Завершить регистрацию");
            header.setText("Регистрация");
        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            Uri ImageUri = data.getData();


            CropImage.activity(ImageUri)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri=result.getUri();//This contains the cropped image

                final StorageReference filePath=UserProfileImagesRef.child(currentUserID+".jpg");

                UploadTask uploadTask=filePath.putFile(resultUri);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Toast.makeText(UserProfile.this, "Successfully uploaded!!!", Toast.LENGTH_SHORT).show();

                            if (downloadUri != null) {

                                String downloadUrl = downloadUri.toString();
                                RootRef.child("Users").child(currentUserID).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        if(!task.isSuccessful()){
                                            String error=task.getException().toString();
                                            Toast.makeText(UserProfile.this,"Error : "+error,Toast.LENGTH_LONG).show();
                                        }else{

                                        }
                                    }
                                });
                            }

                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(UserProfile.this,"Error",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }

    private void LoadUserData() {

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") && dataSnapshot.hasChild("image"))
                {
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    inputUserName.setText(dataSnapshot.child("name").getValue().toString());
                    userStatus.setText(dataSnapshot.child("status").getValue().toString());
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(retrieveProfileImage).into(userProfileImage);

                    //ui.ava = dataSnapshot.child("image").getValue().toString();
                }
                else if (dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") ){
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    userStatus.setText(dataSnapshot.child("status").getValue().toString());
                }
                else
                    if (firstLogIn == true && dataSnapshot.exists() && dataSnapshot.hasChild("image"))
                    {
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(retrieveProfileImage).into(userProfileImage);
                    }
                else {

                }


            }      @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void UpdateSettings() {
        String setUserName = inputUserName.getText().toString();
        String setUserStatus = userStatus.getText().toString();

        if(TextUtils.isEmpty(setUserStatus))
        {
            setUserStatus = " ";
        }
        if(TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }
        else
            {
                HashMap<String, String>profile = new HashMap<>();
                profile.put("uid", currentUserID);
                profile.put("name", setUserName);
                profile.put("status",setUserStatus);
                RootRef.child("Users").child(currentUserID).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        Toast.makeText(UserProfile.this, "Успешно!", Toast.LENGTH_SHORT).show();
                        else
                        {
                            String ex = task.getException().getMessage();
                            Toast.makeText(UserProfile.this, "Ошибка "+ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                if(firstLogIn)
                {
                    Intent g = new Intent(UserProfile.this, MainActivity.class);
                    g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    g.putExtra("FirstLogin", true);
                    startActivity(g);
                    finish();
                }



            }




    }

    public void BackToMenu(View view) {
        super.onBackPressed();
        finish();
    }

    public void SignOut(View view) {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.logout_card);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logout = dialog.findViewById(R.id.logout);
        x_back = dialog.findViewById(R.id.logout_x);


        x_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent g = new Intent(UserProfile.this, Login.class);
                g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(g);
                finish();
            }
        });

        dialog.show();

    }
}
