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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class cambium extends AppCompatActivity {
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
        setContentView(R.layout.activity_cambionfc);
        edit_message=(TextView) findViewById(R.id.editext);
        nfc_contents=(TextView) findViewById(R.id.nfc_contents);
        ActivateButton=(Button) findViewById(R.id.but);
        context= this;
        ActivateButton.setOnClickListener(new View.OnClickListener() {
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
        });
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
        String text="";

        byte[] payload=msgs[0].getRecords()[0].getPayload();
        String textEncoding=((payload[0]&128)==0)? "UTF-8" : "UTF-16";
        int languageCodeLength= payload[0]& 0063;


        try{
            text= new String(payload,languageCodeLength+1,payload.length-languageCodeLength-1, textEncoding);

        }catch (UnsupportedEncodingException e){
            Log.e("UnsupportedEncoding",e.toString());
        }
        nfc_contents.setText("Informacion del NFC:"+text);
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
}