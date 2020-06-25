package com.example.faculdadesapienssql.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.faculdadesapienssql.R;
import com.example.faculdadesapienssql.database.User;
import com.example.faculdadesapienssql.database.UserDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class AddUserActivity extends AppCompatActivity {
    //Intent Constant
    public static final String ACTION_ADD_USERS = "com.example.faculdadesapienssql.ACTION_ADD_USERS";
    public static final String EXTRA_EDIT_USER = "com.example.faculdadesapienssql.EXTRA_EDIT_USER";
    //constante para foto
    public  static final int REQUEST_PIC_CODE = 1;
    //Atributo do Layout
    private ImageView userImageView;
    private EditText edtName, edtRa, edtCurso;
    private UserDAO userDAO;
    private User userToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtName = findViewById(R.id.edtName);
        edtRa = findViewById(R.id.edtRa);
        edtCurso = findViewById(R.id.edtCurso);
        userImageView = findViewById(R.id.img_User_Photo);
        userDAO = new UserDAO(this);

        //Cheking if App has Camera Access
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=
                    PackageManager.PERMISSION_GRANTED){
            //Asking for User Permissin
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_EDIT_USER)){
            userToEdit = (User)intent.getSerializableExtra(EXTRA_EDIT_USER);
            //Filing Text Infomation
            edtName.setText(userToEdit.getName());
            edtRa.setText(userToEdit.getRa());
            edtCurso.setText(userToEdit.getCurso());
            //Filling Image from Object to view
            byte[] bitmapdata = userToEdit.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(bitmapdata);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            userImageView.setImageBitmap(bitmap);
        }
    }
    public void SaveClick(View view){

        User usr = new User();
        usr.setName(edtName.getText().toString());
        usr.setRa(edtRa.getText().toString());
        usr.setCurso(edtCurso.getText().toString());

        Drawable d = userImageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        usr.setImage(bitmapdata);

        if (userToEdit == null){
            //To add new User
            long id = userDAO.Insert(usr);
            Toast.makeText(this, "Usuário Inserido Id:" + id, Toast.LENGTH_LONG).show();

        }else {
            //Geting id To Edit User
            usr.setId(userToEdit.getId());
            userDAO.update(usr);
            Toast.makeText(this, "Usuário Atualizado Id:" + usr.getId(), Toast.LENGTH_LONG).show();
        }
    }
    public void ShowClick(View view){
        //Finalizando uma Intent
        finish();
    }
    public void ImageViewClick(View view){
        //Intent to Open Device Camera
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Checking if there is a component to Resolve intent
        if (intentCamera.resolveActivity(getPackageManager())!=null){
            //starting intent and wating for result
            startActivityForResult(intentCamera, REQUEST_PIC_CODE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //if return is from IntentCamera

        if (requestCode == REQUEST_PIC_CODE && resultCode == RESULT_OK && data!= null){
           //Getting thumbmail image from intent
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap)extras.get("data");
            userImageView.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
