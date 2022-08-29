package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class VerilenBorcEkle : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verilen_borc_ekle)
    }

    fun VerilenBorcEkleBtn(view: View)
    {
        val borcverilenmikyartxt=findViewById<EditText>(R.id.verilemiktartxt)
        val borcvermetarihitxt=findViewById<EditText>(R.id.editTextDate4)
        val borcalmatarihitxt=findViewById<EditText>(R.id.editTextDate5)

        val BorcVerilenMiktar=java.lang.Double.parseDouble(borcverilenmikyartxt.text.toString())
        val BorcVermeTarihi=borcvermetarihitxt.text.toString()
        val BorcAlmaTarihi=borcalmatarihitxt.text.toString()

        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

            val sqlstring="INSERT INTO VerilenBorcBilgileri (VerilenBorcMiktar,VerilenBorcVermeTarihi,VerilenBorcAlmaTarihi)VALUES(?,?,?)"
            val statement=veritabani.compileStatement(sqlstring)
            statement.bindDouble(1,BorcVerilenMiktar)
            statement.bindString(2,BorcVermeTarihi)
            statement.bindString(3,BorcAlmaTarihi)
            statement.execute()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }


        var intent= Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

        val toast: Toast = Toast.makeText(applicationContext,"Yeni Verilen Borç Kaydı Yapıldı", Toast.LENGTH_LONG)
        toast.show()
    }
}