package com.example.trabalhofinalpa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MostraDadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_dados);

        Intent intent = getIntent();

        String nome = intent.getStringExtra(MainActivity.EXTRA_NOME);
        int idade = intent.getIntExtra(MainActivity.EXTRA_IDADE, 18);
        String contato = intent.getStringExtra(MainActivity.EXTRA_CONTATO);
        String morada = intent.getStringExtra(MainActivity.EXTRA_MORADA);

        Toast.makeText(this, nome + " (idade = " + idade + ")" + " (contato: " + contato + ")" + " (morada: " + morada + ")", Toast.LENGTH_LONG).show();

    }
}
