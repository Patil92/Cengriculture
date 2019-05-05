package com.companies.patil.agri_di;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Give_Rent extends AppCompatActivity {

    EditText item_name;
    EditText name;
    EditText phone_no;
    EditText address;
    Button btnChooseImg;
    Button btnUploadData;
    ImageView setImg;
    EditText place;

    private Bitmap bitmap;
    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;

    String url;
    private final int PICK_IMAGE_REQUEST = 71;

    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Rent");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give__rent);

        item_name=findViewById(R.id.item_name);
        name=findViewById(R.id.user_name);
        phone_no=findViewById(R.id.mobile_no);
        address=findViewById(R.id.contact);
        btnChooseImg=findViewById(R.id.btnChooseImg);
        btnUploadData=findViewById(R.id.UploadItemData);
        setImg=findViewById(R.id.setImg);
        place=findViewById(R.id.Place1);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Rent_Images/");

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();

            }
        });
    }

    public void toastTop(String data)
    {
        Toast toast = Toast.makeText(Give_Rent.this,data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    public void toast(String str)
    {
        Toast.makeText(Give_Rent.this,str, Toast.LENGTH_SHORT).show();
    }
    public void sendData()
    {

        toastTop("Uploading...");
        String user_name,Item_name,Mobile,Add,Place;

        Item_name=item_name.getText().toString();
        user_name=name.getText().toString();
        Mobile=phone_no.getText().toString();
        Add=address.getText().toString();
        Place=place.getText().toString();

        String storageurl=UploadImage(Item_name+"_"+user_name);

        final GiveRent rent=new GiveRent(Item_name,user_name,Mobile,Add,storageurl,Place);

        final String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(rent);

        toastTop("Uploaded..");
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                setImg.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String UploadImage(String imgname)
    {

        setImg.setDrawingCacheEnabled(true);
        setImg.buildDrawingCache();
        Bitmap bitmap = setImg.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.child(imgname).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                url= downloadUrl.toString();
            }
        });

        toastTop("Url : "+url);
        return url;
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

       final  StorageReference ref=storageReference.child(imgname);

        UploadTask uploadTask = ref.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    url = task.getResult().toString();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        /*uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                toastTop("Image Upload Failed...");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                if (downloadUri.isSuccessful()) {
                    String generatedFilePath = downloadUri.getResult().toString();
                    System.out.println("## Stored path is " + generatedFilePath);

                    toast("Image Url "+generatedFilePath);
                    url = generatedFilePath;

                }else {
                    toast("Image Url Unsuccssful...");
                }
            }});


        return urlTask.toString();*/
    }

   /* private void uploadImageWithotCompress() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Rent_Images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Give_Rent.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Give_Rent.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    public Uri resize(Uri uri)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 50;
        Bitmap bmpSample = BitmapFactory.decodeFile(fileUri.getPath(), options);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmpSample.compress(Bitmap.CompressFormat.JPEG, 1, out);
        byte[] byteArray = out.toByteArray();

        return convertToUri(byteArray);
    }*/
}
