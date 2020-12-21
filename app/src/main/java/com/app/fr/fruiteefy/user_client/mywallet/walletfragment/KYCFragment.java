package com.app.fr.fruiteefy.user_client.mywallet.walletfragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.SingleUploadBroadcastReceiver;
import com.app.fr.fruiteefy.Util.VolleyMultipartRequest;
import com.app.fr.fruiteefy.user_client.mywallet.MyWalletInforFragment;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.iceteck.silicompressorr.FileUtils.getDataColumn;


public class KYCFragment extends Fragment implements SingleUploadBroadcastReceiver.Delegate{
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static final int REQUEST_PERMISSIONS = 100;
    private View mMainView;
    String selectedtype="IDENTITY_PROOF",filePath,pdfpath,filename="";
    TextView valider,choosefile,txtseletedfile;
    Spinner typespin;
    private Bitmap bitmap;
    ImageView backimg;
    private Activity activity;
    private ArrayList<String> offertype = new ArrayList<>();
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();
    public KYCFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        uploadReceiver.register(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        uploadReceiver.unregister(getActivity());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_k_y_c, container, false);
        activity=(WalletActivity)getActivity();
        backimg=activity.findViewById(R.id.backimg);
        setUI();

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyWalletInforFragment()).commit();

            }
        });

        offertype.add(0, "Preuve d'identité (Passeport, Permis,CNI)");
        offertype.add(1, "Preuve d’enregistrement (Kbis/Journal officiel/déclaration URSSAF ou SIRENE)");
        offertype.add(2, "Statuts complets, à jour et signés");
        offertype.add(3, "Déclaration d’actionnaire");
        offertype.add(4, "Preuve d’adresse (Facture EDF,...)");

        typespin.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, offertype));


        typespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getId() == R.id.typespin) {

                    selectedtype = adapterView.getItemAtPosition(i).toString();

                    if (selectedtype.equals("Preuve d'identité (Passeport, Permis,CNI)"))
                    {
                        selectedtype = "IDENTITY_PROOF";


                    }
                    else if (selectedtype.equals("Preuve d’enregistrement (Kbis/Journal officiel/déclaration URSSAF ou SIRENE)"))
                    {
                        selectedtype = "REGISTRATION_PROOF";

                    }
                    else if (selectedtype.equals("Statuts complets, à jour et signés"))
                    {
                        selectedtype = "ARTICLES_OF_ASSOCIATION";

                    }
                    else if (selectedtype.equals("Déclaration d’actionnaire"))
                    {
                        selectedtype = "SHAREHOLDER_DECLARATION";

                    }
                    else if (selectedtype.equals("Preuve d’adresse (Facture EDF,...)"))
                    {
                        selectedtype = "ADDRESS_PROOF";

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("selectedtype",selectedtype);
                if(selectedtype != null || !selectedtype.isEmpty()){

                    if(filename.contains(".png") || filename.contains(".jpg") ||filename.contains("jpeg")){
                        callapiforimg();
                    }
                    else if(filename.contains(".pdf"))
                    {
                        callapiforpdf();
                    }  else if(filename.isEmpty() || filename.equals("")){
                        Toast.makeText(getActivity(),"Please Select Image or PDF File",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Please Select Type",Toast.LENGTH_SHORT).show();
                }
            }
        });


choosefile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            showFileChooser();

        }
    }
});
        return mMainView;
    }


    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("*/*"); //image/*
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        //   startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);   noneed


       final String IMAGE = "image/*";
       final String PDF = "application/pdf";
        Intent intent = getCustomFileChooserIntent(PDF, IMAGE);

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public static Intent getCustomFileChooserIntent(String ...types){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        // Filter to only show results that can be "opened"
       intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        return intent;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            String pattern = "/^[ A-Za-z0-9_@./#&+-]*$/";
            Uri mainuri = data.getData();
            mainuri = Uri.parse(mainuri.toString().replaceAll(pattern, mainuri.toString()));
            Log.d("filePath", String.valueOf(mainuri));
            String getmaindata =  String.valueOf(mainuri);
            if(getmaindata.contains("pdf") || getmaindata.contains("document") && !getmaindata.contains(".png") && !getmaindata.contains("image")  && !getmaindata.contains(".jpg") && !getmaindata.contains("jpeg")){
                Log.d("inpdf","inpdf");
                Uri fileuri = data.getData();
                pdfpath = getPDFPath(fileuri);
                if(pdfpath != null){

                    filename = pdfpath.substring(pdfpath.lastIndexOf("/") + 1);
                    txtseletedfile.setText(filename);//"File Selected"
                    Log.d("filename", filename);
                }else{
                    Toast.makeText(
                            getActivity(), "no pdf selected",
                            Toast.LENGTH_LONG).show();
                }
            }else {
                Log.d("inimg","inimg");


                    filePath = getPDFPath(data.getData());

                Log.d("filepathofimg",filePath);
                if (filePath != null) {
                    try {
                        filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                        txtseletedfile.setText(filename);//"File Selected"
                        Log.d("filename", filename);

                        if (filename.contains(".png") || filename.contains(".jpg") || filename.contains("jpeg")) {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                            Log.d("takebitmap", String.valueOf(bitmap));
                        }
                        else {
                            Toast.makeText(
                                    getActivity(), "no image selected",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(
                            getActivity(), "no image selected",
                            Toast.LENGTH_LONG).show();
                }

            }

        }else{
            //Toast.makeText(getActivity(),"wrong",Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

//    public String getPath(Uri uri) {
//        Cursor cursor =getActivity().getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//
//        cursor = getActivity().getContentResolver().query(
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }



    private String getPDFPath(Uri filePath) {
        Uri returnUri = filePath;
        Cursor returnCursor = getActivity().getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(getActivity().getFilesDir(), name);
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(filePath);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();

    }



    private void callapiforpdf() {
       CustomUtil.ShowDialog(getActivity(),false);
        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate((SingleUploadBroadcastReceiver.Delegate) this);
            uploadReceiver.setUploadID(uploadId);

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, BaseUrl.BASEURL.concat("kyc_update"))
                    .addFileToUpload(pdfpath, "File") //Adding file
                    .addParameter("type", selectedtype)
                    .addParameter("user_id", PrefManager.getUserId(getActivity()))//Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
Log.d("uploading","1");
        } catch (Exception exc) {
         //   CustomUtil.DismissDialog(getActivity());
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void callapiforimg() {
        CustomUtil.ShowDialog(getActivity(),false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //
        //"https://fruiteefy.fr/App/kyc_update"
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,BaseUrl.BASEURL.concat("kyc_update") ,
                new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                CustomUtil.DismissDialog(getActivity());
                Log.d("kyc_update_img", String.valueOf(response));
                try {
                    String result = new String(response.data);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        txtseletedfile.setText("");
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();




                    }
                    else {
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kyc_update_img", error.toString());

                CustomUtil.DismissDialog(getActivity());


//                NetworkResponse networkResponse=error.networkResponse;
//
//                Log.d("kyc_update_img", String.valueOf(networkResponse.data));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                param.put("type", selectedtype);


                Log.d("selectedtype",selectedtype);

                Log.d("userid", PrefManager.getUserId(getActivity()));


                return param;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws AuthFailureError {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
//Log.d("filenmae",filename);
                if(filename.contains(".png") || filename.contains(".jpg") ||filename.contains("jpeg") || !filename.isEmpty() ){

                    try {

                        if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {

                            params.put("File", new VolleyMultipartRequest.DataPart(filename,getFileDataFromDrawable(scaledBitmap(bitmap))));
                            Log.d("filenmae",filename);
                        } else {

                            params.put("File", new VolleyMultipartRequest.DataPart(filename, getFileDataFromDrawable(bitmap)));
                            Log.d("filenmae",filename);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }





                return params;
            }
        };

        requestQueue.add(volleyMultipartRequest);
    }

    private Bitmap scaledBitmap(Bitmap bitmap) {
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1500, 1000, true);
        return scaled;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        return byteArrayOutputStream.toByteArray();
    }

    private void setUI() {
        valider = mMainView.findViewById(R.id.valider);
        choosefile = mMainView.findViewById(R.id.choosefile);
        typespin = mMainView.findViewById(R.id.typespin);
        txtseletedfile = mMainView.findViewById(R.id.txtseletedfile);

    }

    @Override
    public void onProgress(int progress) {
        CustomUtil.DismissDialog(getActivity());
        Log.d("onprogress", String.valueOf(progress));
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        CustomUtil.DismissDialog(getActivity());
        Log.d("onprogresslong", String.valueOf(uploadedBytes));
    }

    @Override
    public void onError(Exception exception) {
        CustomUtil.DismissDialog(getActivity());
        Log.d("onError", String.valueOf(exception));
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {

        if(serverResponseCode == 200){
            txtseletedfile.setText("");
            CustomUtil.DismissDialog(getActivity());
            Toast.makeText(getActivity(),"Merci. Nous allons vérifier les documents transmis. Cette vérification peut prendre jusqu à 24h",Toast.LENGTH_LONG).show();
        }else{
            txtseletedfile.setText("");
            CustomUtil.DismissDialog(getActivity());
            Toast.makeText(getActivity(),"Accès refusé",Toast.LENGTH_SHORT).show();
        }

        Log.d("serverResponse", String.valueOf(serverResponseCode));
    }

    @Override
    public void onCancelled() {
        CustomUtil.DismissDialog(getActivity());
        Log.d("cancel", "cancel");
    }
}