package com.example.trabalhofinalpa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MostraDadosActivity myDb;
    EditText editTextNome, editTextIdade, editTextContato, editTextMorada, editTextID;
    Button buttonEnviar;
    Button buttonVerDados;
    Button buttonEditar;



    public static final String EXTRA_NOME = "NOME" ;
    public static final String EXTRA_IDADE = "IDADE" ;
    public static final String EXTRA_CONTATO = "CONTATO";
    public static final String EXTRA_MORADA = "MORADA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new MostraDadosActivity(this);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        editTextContato = (EditText) findViewById(R.id.editTextContato);
        editTextMorada = (EditText) findViewById(R.id.editTextMorada);
        editTextID = (EditText)findViewById(R.id.editTextID);
        buttonEnviar = (Button) findViewById(R.id.buttonEnviar);
        buttonVerDados = (Button) findViewById(R.id.buttonVerDados);
        buttonEditar = (Button) findViewById(R.id.buttonEditar);
        AddData();
        viewAll();
        UpdateData();
    }
    public void UpdateData() {
        buttonEditar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextID.getText().toString(),
                                editTextNome.getText().toString(),
                                editTextIdade.getText().toString(),
                                editTextContato.getText().toString(),
                                editTextMorada.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Dados editados", Toast.LENGTH_LONG).show();
                       else
                            Toast.makeText(MainActivity.this, "Dados não editados", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData() {
        buttonEnviar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextNome.length() == 0)

                        {
                            editTextNome.setError("Preencha o nome");
                            editTextNome.requestFocus();
                            return;
                        }

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

                        if (editTextContato.length() < 9) {
                            editTextContato.setError("Contato inválido. Preencha corretamente.");
                            editTextContato.requestFocus();
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
                       boolean isInserted = myDb.insertData(editTextNome.getText().toString(),
                              editTextIdade.getText().toString(),
                                editTextContato.getText().toString(),
                                editTextMorada.getText().toString());



                       if (isInserted == true)
                           Toast.makeText(MainActivity.this, "Dados enviados", Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(MainActivity.this, "Dados não enviados", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }

    public void viewAll() {
        buttonVerDados.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = myDb.getAllData();
                       if (res.getCount() == 0){
                           //show message
                           showMessage("Erro", "Sem dados");
                           return;
                       }

                       StringBuffer buffer = new StringBuffer();
                       while (res.moveToNext()) {
                           buffer.append("ID :" + res.getString(0)+"\n");
                           buffer.append("NOME :" + res.getString(1)+"\n");
                           buffer.append("IDADE :" + res.getString(2)+"\n");
                           buffer.append("CONTATO :" + res.getString(3)+"\n");
                           buffer.append("MORADA :" + res.getString(4)+"\n\n");

                       }
                       // show all data
                        showMessage("Dados", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
