package com.example.trabalhofinalpa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviar(View view) {

        EditText editTextNome = (EditText) findViewById(R.id.editTextNome);
        String nome = editTextNome.getText().toString();

        if (nome.length() == 0)

    {
        editTextNome.setError("Preencha o nome");
        editTextNome.requestFocus();
        return;
    }

        EditText editTextContato = (EditText) findViewById(R.id.editTextContato);
        String contato = editTextContato.getText().toString();

        if (contato.length() < 9) {
            editTextContato.setError("Contato inválido. Preencha corretamente.");
            editTextContato.requestFocus();
            return;
        }

        Intent intent = new Intent(this, MostraDadosActivity.class);

        startActivity(intent);
    }
}
