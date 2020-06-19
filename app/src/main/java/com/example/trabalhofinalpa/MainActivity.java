package com.example.trabalhofinalpa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOME = "NOME" ;
    public static final String EXTRA_IDADE = "IDADE" ;
    public static final String EXTRA_CONTATO = "CONTATO";
    public static final String EXTRA_MORADA = "MORADA";

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

        EditText editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        String strIdade = editTextIdade.getText().toString();

        int idade;
        try {
            idade = Integer.parseInt(strIdade);
        } catch (NumberFormatException e) {
            editTextIdade.setError("Idade invalida. Preencha a idade.");
            editTextIdade.requestFocus();
            return;
        }

        if (idade < 18) {
            editTextIdade.setError("A idade tem de ser maior ou igual que 18.");
            editTextIdade.requestFocus();
            return;
        }

        EditText editTextMorada = (EditText) findViewById(R.id.editTextMorada);
        String morada = editTextMorada.getText().toString();

        if (morada.length() == 0)

        {
            editTextMorada.setError("Preencha a Morada");
            editTextMorada.requestFocus();
            return;
        }



        Intent intent = new Intent(this, MostraDadosActivity.class);

        intent.putExtra(EXTRA_NOME,nome);
        intent.putExtra(EXTRA_IDADE,idade);
        intent.putExtra(EXTRA_CONTATO,contato);
        intent.putExtra(EXTRA_MORADA,morada);

        startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonPessoaRisco:
                if (checked)
                Log.d("RadioButtonPessoaRisco", "pessoa_de_risco");
                break;

            case R.id.radioButtonVoluntario:
                if (checked)
                Log.d("RadioButtonVoluntario", "voluntario");
                break;

        }
    }
}
