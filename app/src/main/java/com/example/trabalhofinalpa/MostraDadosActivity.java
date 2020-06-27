package com.example.trabalhofinalpa;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MostraDadosActivity extends AppCompatActivity {

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

        public PessoaHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
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
