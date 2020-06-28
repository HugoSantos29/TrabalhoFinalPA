package com.example.trabalhofinalpa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MostraDadosActivity extends AppCompatActivity {

    public class Pessoa {

        int id, idCategoria, idade, contato;
        String nome, morada;

        //construtores
        public Pessoa() {
        }

        public Pessoa(int id, int idCategoria, int contato, int idade, String nome, String morada) {
            this.id = id;
            this.idCategoria = idCategoria;
            this.contato = contato;
            this.idade = idade;
            this.nome = nome;
            this.morada = morada;
        }

        //setters e getters
        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setIdCategoria(int idCategoria) {
            this.idCategoria = idCategoria;
        }

        public int getIdCategoria() {
            return this.idCategoria;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public int getIdade() {
            return this.idade;
        }

        public void setContato(int contato) {
            this.contato = contato;
        }

        public int getContato() {
            return this.contato;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return this.nome;
        }

        public void setMorada(String morada) {
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
        public Categoria() {
        }

        public Categoria(String grupo) {
            this.grupo = grupo;
        }

        //setters e getters

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setGrupo(String grupo) {
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

        public ArrayList<Pessoa> getPessoas(String categoria) {
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

        public boolean deletePessoa(String pessoa_nome) {
            boolean result = false;
            String query = "Select * FROM " + TABLE_PESSOA + " WHERE " +
                    COLUMN_NOME + " = \" " + pessoa_nome + "\" ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Pessoa pessoa = new Pessoa();
            if (cursor.moveToFirst()) {
                pessoa.setId(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_PESSOA, COLUMN_PESSOA_ID + " = ? ",
                        new String[]{String.valueOf(pessoa.getId())});
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
