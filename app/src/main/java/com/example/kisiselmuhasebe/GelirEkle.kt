package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*


class GelirEkle : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gelir_ekle)
    }
    fun GelirEkleBtn(view:View)
    {
        val gelirmiktartxt=findViewById<EditText>(R.id.gelirmiktartxt)

        val gelirtarihtxt=findViewById<Spinner>(R.id.spinner)
        val spin1=findViewById<Spinner>(R.id.spinner1)

        val GelirMiktar=java.lang.Double.parseDouble(gelirmiktartxt.text.toString())
        val GelirTarih=gelirtarihtxt.selectedItem.toString()
        val GelirKategori=spin1.selectedItem.toString()


        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe",Context.MODE_PRIVATE,null)

            val sqlstring="INSERT INTO GelirBilgileri (GelirMiktar,GelirKategori,GelirTarih)VALUES(?,?,?)"
            val statement=veritabani.compileStatement(sqlstring)
            statement.bindDouble(1,GelirMiktar)
            statement.bindString(2,GelirKategori)
            statement.bindString(3,GelirTarih)
            statement.execute()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }


        gelirmiktartxt.text=null


        var intent=Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

        val toast:Toast= Toast.makeText(applicationContext,"Yeni Gelir Kaydı Yapıldı",Toast.LENGTH_LONG)
        toast.show()
    }



}