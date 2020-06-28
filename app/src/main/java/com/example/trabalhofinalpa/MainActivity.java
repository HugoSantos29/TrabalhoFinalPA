package com.example.trabalhofinalpa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public class Pessoa {

        int id, idCategoria, idade, contato;
        String nome, morada;

        //construtores
        public Pessoa () {}

        public Pessoa(int id, int idCategoria, int contato, int idade, String nome, String morada) {
            this.id = id;
            this.idCategoria = idCategoria;
            this.contato = contato;
            this.idade = idade;
            this.nome = nome;
            this.morada = morada;
        }

        //setters e getters
        public void setId (int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }

        public void setIdCategoria (int idCategoria) {
            this.idCategoria = idCategoria;
        }
        public int getIdCategoria() {
            return this.idCategoria;
        }

        public void setIdade (int idade) {
            this.idade = idade;
        }
        public int getIdade() {
            return this.idade;
        }

        public void setContato (int contato) {
            this.contato = contato;
        }
        public int getContato() {
            return this.contato;
        }

        public void setNome (String nome) {
            this.nome = nome;
        }
        public String getNome() {
            return this.nome;
        }

        public void setMorada (String morada) {
            this.morada = morada;
        }
        public String getMorada() {
            return this.morada;
        }
    }

    public class Categoria {
        int id;
        String grupo;

        //construtores
        public Categoria() {}
        public Categoria (String grupo) {
            this.grupo = grupo;
        }

        //setters e getters

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }

        public void setGrupo(String grupo){
            this.grupo = grupo;
        }
        public String getGrupo() {
            return this.grupo;
        }
    }

    public class PessoaHelper extends SQLiteOpenHelper {

        //CONSTRUTOR
        public PessoaHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //CRIAÇÃO DA TABELA PESSOA
            String CREATE_PESSOA_TABLE = "CREATE TABLE " + TABLE_PESSOA + "(" +
                    COLUMN_PESSOA_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NOME + " TEXT," +
                    COLUMN_IDADE + " INTEGER," +
                    COLUMN_CONTATO + " INTEGER," +
                    COLUMN_MORADA + " TEXT," +
                    COLUMN_CATEGORIA_ID + " INTEGER" + ")";
            db.execSQL(CREATE_PESSOA_TABLE);

            //CRIAÇÃO DA TABELA CATEGORIA
            String CREATE_CATEGORIA_TABLE = "CREATE TABLE " + TABLE_CATEGORIA + "(" +
                    COLUMN_CATEGORIA_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_CATEGORIA_GRUPO + " TEXT)";
            db.execSQL(CREATE_CATEGORIA_TABLE);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESSOA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
            onCreate(db);

        }

        public void addPessoa(Pessoa pessoa) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOME, pessoa.getNome());
            values.put(COLUMN_IDADE, pessoa.getIdade());
            values.put(COLUMN_CONTATO, pessoa.getContato());
            values.put(COLUMN_MORADA, pessoa.getMorada());
            values.put(COLUMN_CATEGORIA_ID, pessoa.getIdCategoria());
            SQLiteDatabase db = this.getWritableDatabase();
            //INSERIR PESSOA
            db.insert(TABLE_PESSOA, null, values);
            db.close();
        }

        public ArrayList<Pessoa> getPessoas (String categoria) {
            ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

            String query = "SELECT " + TABLE_PESSOA + ".* FROM " +
                    COLUMN_CATEGORIA_GRUPO + "=' " + categoria + "' ";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(cursor.getInt(0));
                pessoa.setNome(cursor.getString(1));
                pessoa.setIdade(cursor.getInt(2));
                pessoa.setContato(cursor.getInt(3));
                pessoa.setMorada(cursor.getString(4));
                pessoa.setIdCategoria(cursor.getInt(5));
                pessoas.add(pessoa);
            }
            return pessoas;

        }

        public boolean deletePessoa (String pessoa_nome) {
            boolean result = false;
            String query = "Select * FROM " + TABLE_PESSOA + " WHERE " +
                    COLUMN_NOME + " = \" " + pessoa_nome + "\" ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Pessoa pessoa = new Pessoa();
            if (cursor.moveToFirst()) {
                pessoa.setId(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_PESSOA, COLUMN_PESSOA_ID + " = ? ",
                        new String[] { String.valueOf(pessoa.getId()) });
                cursor.close();
                result = true;
            }
            db.close();
            return result;

        }


        //NOME E VERSÃO DA BASE DE DADOS
        private static final String DATABASE_NAME = "basedados";
        private static final int DATABASE_VERSION = 1;

        //NOMES DAS TABELAS E COLUNAS
        private static final String TABLE_PESSOA = "pessoa";
        private static final String TABLE_CATEGORIA = "categoria";
        private static final String COLUMN_PESSOA_ID = "idPessoa";
        private static final String COLUMN_NOME = "nome";
        private static final String COLUMN_IDADE = "idade";
        private static final String COLUMN_CONTATO = "contato";
        private static final String COLUMN_MORADA = "morada";
        private static final String COLUMN_CATEGORIA_ID = "idCategoria";
        private static final String COLUMN_CATEGORIA_GRUPO = "grupo";

        //TAG PARA O LOGCAT
        private static final String LOG = "PessoaHelper";


    }

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
