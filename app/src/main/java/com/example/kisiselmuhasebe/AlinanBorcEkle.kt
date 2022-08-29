package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AlinanBorcEkle : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alinan_borc_ekle)
    }

    fun AlinanBorcEklebtn(view:View)
    {
        val borcalinanmikyartxt=findViewById<EditText>(R.id.alinanmiktartxt)
        val borcalmatarihitxt=findViewById<EditText>(R.id.editTextDate)
        val borcvermetarihitxt=findViewById<EditText>(R.id.editTextDate2)

        val BorcAlinanMiktar=java.lang.Double.parseDouble(borcalinanmikyartxt.text.toString())
        val BorcALmaTarihi=borcalmatarihitxt.text.toString()
        val BorcVermeTarihi=borcvermetarihitxt.text.toString()

        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

            val sqlstring="INSERT INTO AlinanBorcBilgileri (AlinanBorcMiktar,AlinanBorcAlmaTarihi,AlinanBorcVermeTarihi)VALUES(?,?,?)"
            val statement=veritabani.compileStatement(sqlstring)
            statement.bindDouble(1,BorcAlinanMiktar)
            statement.bindString(2,BorcALmaTarihi)
            statement.bindString(3,BorcVermeTarihi)
            statement.execute()


        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        var intent= Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

        val toast: Toast = Toast.makeText(applicationContext,"Yeni Alınan Borç Kaydı Yapıldı", Toast.LENGTH_LONG)
        toast.show()
    }
}