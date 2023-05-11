package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

public class ImageDetection extends AppCompatActivity {

    private static final String TAG = "Main_TAG";
    //카메라나 갤러리에서 가져올 image의 Uri
    private Uri imageUri = null;

    //카메라와 갤러리의 퍼미션을 관리할 코드
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private final String[] cameraPermissions = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};;
    private final String[] storagePermissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};;
    private LoadingDialog loadingDialog;
    private TextRecognizer textRecognizer;

    private Button btn_camera;
    private ImageView img_takenImage;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detection);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //TEST IMAGE DETECTION
        //이미지에서 텍스트 인식할 때 보여줄 dialog 초기화

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        //TextRecognizer 초기화
        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        // RecognizerIntent 생성
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정


        //버튼 클릭 때 보여줄 image dialog
        img_takenImage = findViewById(R.id.img_imageDTakenImage);
        btn_camera = findViewById(R.id.btn_imageDCamera);
        Button btn_gallery = findViewById(R.id.btn_imageDGallery);
        Button btn_recognizeText = findViewById(R.id.btn_imageDRecognizeText);
        et_content = findViewById(R.id.et_imageDContent);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onMenuItemClick: Camera Clicked...");
                if (checkCameraPermissions()) {
                    //카메라 퍼미션이 허가되었으면 카메라 실행
                    pickImageCamera();
                }
                else{
                    //카메라 퍼미션이 허가되지 않았으면 카메라 퍼미션 요구
                    requestCameraPermissions();
                }
            }

        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onMenuItemClick: Gallery Clicked");
                if(checkStoragePermission()){
                    //저장소 퍼미션이 허용되면 갤러리 실행
                    pickImageGallery();
                }
                else{
                    //저장소 퍼미션이 허가되지 않았으면 저장소 퍼미션 요구
                    requestStoragePermission();
                }
            }
        });

        btn_recognizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지가 골라졌는지 체크하고 imageUri가 null이 아니면 고름
                if(imageUri == null){
                    //imageUri가 null이라는 것은 아직 이미지를 고르지 않아 텍스트 인식 불가
                    Toast.makeText(getApplicationContext(), "Pick image first...", Toast.LENGTH_SHORT).show();
                }
                else{
//                    loadingDialog.show();
                    //imageUri가 null이 아니면 이미지가 골라져 텍스트 인식 가능
                    recognizeTextFromImage();
//                    loadingDialog.dismiss();
                }
            }
        });


        //TEST IMAGE DETECTION



    }

    private void recognizeTextFromImage() {
        Log.d(TAG, "recognizeTextFromImage: ");
        //set message and show progress dialog
        loadingDialog.show();
        try {
            //imageUri부터 InputImage 준비
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);

            //이미지로부터 텍스트 인식 시작
            Task<Text> textTaskResult = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>(){
                        @Override
                        public void onSuccess(Text text){
                            //과정이 끝나면 dialog 소멸
                            loadingDialog.dismiss();
                            //인식된 텍스트 추출
                            String recognizedText = text.getText();
                            recognizedText = recognizedText.replaceAll("\\n", " ");  //엔터 제거
                            Log.d(TAG, "onSuccess : recognizedText: " + recognizedText);
                            //인식된 텍스트를 edit 텍스트에 set시킴
                            et_content.setText(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //텍스트 인식을 실패하면 dialog를 소멸하고 Toast로 이유 보여줌
                            loadingDialog.dismiss();
                            Log.e(TAG, "onFailure: ", e);
                            Toast.makeText(getApplicationContext(), "Failed recognizing text due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e){
            //InputImage 준비할 때 예외가 발생하면 dialog를 소멸하고 Toast로 이유 보여줌
            loadingDialog.dismiss();
            Log.e(TAG, "recognizeTextFromImage: ", e);
            Toast.makeText(getApplicationContext(), "Failed preparing image due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImageGallery(){
        Log.d(TAG, "pickImageGallery: ");
        //이미지를 갤러리에서 골라오기를 의도한다면 고를 수 있는 모든 자원을 보여줌
        Intent intent = new Intent(Intent.ACTION_PICK);

        //우리가 고르기 원하는 파일의 타입을 set i.e. image
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        assert result.getData() != null;
                        imageUri = result.getData().getData();
                        Log.d(TAG, "onActivityResult: imageUri" + imageUri);
                        img_takenImage.setImageURI(imageUri);
                    }
                    else{
                        Log.d(TAG, "onActivityResult: Cancelled...");
                        Toast.makeText(getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                    }

                }
            }

    );

    private void pickImageCamera(){
        Log.d(TAG, "pickImageCamera: ");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Log.d(TAG, "onActivityResult: imageUri " +imageUri);
                        img_takenImage.setImageURI(imageUri);
                    }
                    else{
                        Log.d(TAG, "onActivityResult: Cancelled... ");
                        Toast.makeText(getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        boolean cameraResult = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return cameraResult && storageResult;
    }

    private void requestCameraPermissions(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        pickImageCamera();
                    }
                    else{
                        Toast.makeText(this,"Camera & Storage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //둘 다 거부되지 않음을 허용하지 않으면 취소됨
                    Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                //사용 권한 대화 상자에서 동일한 작업이 수행되었거나 허용/거부가 수행되지 않았는지 체크
                if(grantResults.length>0){
                    //저장소 퍼미션이 허용되었는지 boolean 결과를 통해 체크
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    //저장소 퍼미션이 허용되었는지 체크
                    if(storageAccepted){
                        //저장소 퍼미션이 허용되었으면 갤러리 실행
                        pickImageGallery();
                    }
                    else{
                        //저장소 퍼미션이 허용되지 않았으면 갤러리 실행 불가
                        Toast.makeText(this,"Storage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Help:
                startActivity(new Intent(this, Help.class));
                return true;

            case R.id.Bluetooth:
                startActivity(new Intent(this, Bluetooth.class));
                return true;

            case R.id.Setting:
                startActivity(new Intent(this, Setting.class));
                return true;

            case android.R.id.home:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
