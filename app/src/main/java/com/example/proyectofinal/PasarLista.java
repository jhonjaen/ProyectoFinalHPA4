package com.example.proyectofinal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.Asistencia;
import com.example.proyectofinal.entity.AsistenciaR;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasarLista extends AppCompatActivity {
    private String fecha, hora;
    private int grupoAsistenciaId, estudianteId, estadoAsistenciaId;
    private Button btnGuardar;
    private Spinner spestado;
    String text2;

    public static final String Error_Detected= "NFC no encontrado";
    public static final String Write_Success= "Texto editado con exito";
    public static final String Write_Error= "Error durante la escritura";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView edit_message, nfc_contents;
    Button ActivateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasar_lista);
        spestado=(Spinner)findViewById(R.id.spestado);
        nfc_contents=(TextView) findViewById(R.id.nfc2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PasarLista.this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spestado.setAdapter(adapter);
        spestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                estadoAsistenciaId = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(PasarLista.this, "Seleccione un estado", Toast.LENGTH_LONG).show();
            }
        });
        context= this;
       /* ActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(myTag==null){
                        Toast.makeText(context, Error_Detected,Toast.LENGTH_LONG).show();
                    }
                    else{
                        write(" "+edit_message.getText().toString(),myTag);
                        Toast.makeText(context, Write_Success,Toast.LENGTH_LONG).show();
                    }

                }catch (IOException e){
                    Toast.makeText(context, Write_Error,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }catch (FormatException e){
                    Toast.makeText(context, Write_Error,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });*/
        nfcAdapter=NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter==null){
            Toast.makeText(this,"el dispositivo no soporta NFC",Toast.LENGTH_LONG).show();
            finish();
        }
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),PendingIntent.FLAG_MUTABLE);
        IntentFilter tagDetected= new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters=new IntentFilter[]{tagDetected};

    }
    private void readFromIntent(Intent intent){
        String action=intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawMsgs= intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs =null;
            if(rawMsgs!=null){
                msgs= new NdefMessage[rawMsgs.length];
                for(int i=0;i<rawMsgs.length;i++){
                    msgs[i]=(NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);

        }
    }
    private void buildTagViews(NdefMessage[] msgs){
        if(msgs==null||msgs.length==0)return;


        byte[] payload=msgs[0].getRecords()[0].getPayload();
        String textEncoding=((payload[0]&128)==0)? "UTF-8" : "UTF-16";
        int languageCodeLength= payload[0]& 0063;


        try{
            text2= new String(payload,languageCodeLength+1,payload.length-languageCodeLength-1, textEncoding);
            text2=text2.substring(1,text2.length());
            estudianteId=Integer.parseInt(text2);
        }catch (UnsupportedEncodingException e){
            Log.e("UnsupportedEncoding",e.toString());
        }


        nfc_contents.setText(text2);
        this.probar();
    }

    private void write(String text, Tag tag)throws IOException, FormatException{
        NdefRecord[] records={createRecord(text)};
        NdefMessage message= new NdefMessage(records);

        Ndef ndef=Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }
    private NdefRecord createRecord(String text)throws UnsupportedEncodingException {
        String lang= "es";
        byte[] textBytes= text.getBytes();
        byte[] langBytes= lang.getBytes("US-ASCII");
        int langLength= langBytes.length;
        int textLength= textBytes.length;
        byte[] payload = new byte[1+langLength+textLength];
        payload[0]=(byte)langLength;

        System.arraycopy(langBytes,0,payload,1,langLength);
        System.arraycopy(textBytes,0,payload,1+langLength,textLength);

        NdefRecord recordNFC= new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT, new byte[0],payload);
        return recordNFC;
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag= intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }
    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }
    /********************************* enable write****/
    private void WriteModeOn(){
        writeMode=true;
        nfcAdapter.enableForegroundDispatch(this,pendingIntent, writingTagFilters,null);
    }
    /********************************* disable write****/
    private void WriteModeOff(){
        writeMode=false;
        nfcAdapter.disableForegroundDispatch(this);
    }



   /* private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String estid;
        if (estid.equals(Ndef.class.getName())) {
            Ndef mNdef = Ndef.get(tag);
            if (mNdef != null) {
                NdefMessage mNdefMessage = mNdef.getCachedNdefMessage();
                NdefRecord[] records = mNdefMessage.getRecords();
                if (records != null) {
                    Estudiantes estudiante = new Estudiantes(
                            new String(mNdefMessage.getRecords()[0].getPayload()),
                            "",
                            new String(mNdefMessage.getRecords()[1].getPayload()),
                            "12:00 md"
                    );
                }
            }
        }
    }*/

    public void probar() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
         fecha = date;
        pattern = "h:mm a";
        simpleDateFormat = new SimpleDateFormat(pattern);
        hora = simpleDateFormat.format(new Date());
        grupoAsistenciaId = 2;

                enviarWs(fecha, estudianteId, hora, estadoAsistenciaId, grupoAsistenciaId);

    }

    private void enviarWs(String fecha, Integer estudianteId, String hora, Integer estadoAsistenciaId, Integer grupoAsistenciaId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Asistencia asistencia = new Asistencia(fecha, estudianteId, hora, estadoAsistenciaId, grupoAsistenciaId);
        Call<AsistenciaR> call = serviceApi.Enviarasistencia(asistencia);

        call.enqueue(new Callback<AsistenciaR>() {
            @Override
            public void onResponse(Call<AsistenciaR> call, retrofit2.Response<AsistenciaR> response) {
                if (!response.isSuccessful()) {
                    if(response.code()==400) {
                        Toast.makeText(PasarLista.this, "Ya existe" , Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(PasarLista.this, "" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                AsistenciaR asistenciaR = response.body();

                String content = "";
                content += "Title: " + asistenciaR.getStatus() + "\n\n";
                Toast.makeText(PasarLista.this, "registrado " , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AsistenciaR> call, Throwable t) {
                Toast.makeText(PasarLista.this, "Ya existe" , Toast.LENGTH_LONG).show();

            }
        });


    }

}