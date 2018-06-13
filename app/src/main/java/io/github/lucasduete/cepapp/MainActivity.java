package io.github.lucasduete.cepapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static Handler mainHandle;
    private static final String TAG ="CEPAPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editTextCep);
        final Context context = this;

        mainHandle = new MyHandle();

        Button button = findViewById(R.id.button);
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(context, MyService.class);
            intent.putExtra("cep", editText.getText().toString());
            context.startService(intent);
        });
    }

    private class MyHandle extends Handler {
        public MyHandle() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {

            TextView textView;
            JSONObject object = (JSONObject) msg.obj;

            try {

                textView = (TextView) findViewById(R.id.editTextLogradouro);
                textView.setText(object.getString("logradouro"));

                textView = (TextView) findViewById(R.id.editTextComplemento);
                textView.setText(object.getString("complemento"));

                textView = (TextView) findViewById(R.id.editTextBairro);
                textView.setText(object.getString("bairro"));

                textView = (TextView) findViewById(R.id.editTextCidade);
                textView.setText(object.getString("localidade"));

                textView = (TextView) findViewById(R.id.editTextEstado);
                textView.setText(object.getString("estado"));

            } catch (Exception ex) {
                Log.d(TAG, "Deu ruim ao preencher");
            }
        }
    }
}
