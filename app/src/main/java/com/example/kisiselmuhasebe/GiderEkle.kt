package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate

class GiderEkle : AppCompatActivity()
{
    public var AnlıkTarih=""
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gider_ekle)

        val gidertarihtxt=findViewById<TextView>(R.id.tarihtextview)
        val currentDate=LocalDate.now()
        AnlıkTarih=currentDate.toString()
        gidertarihtxt.text="Tarih: ${AnlıkTarih.toString()}"

    }

    fun GiderEkleBtn(view: View)
    {

        val gidermiktartxt=findViewById<EditText>(R.id.gidermiktartxt)

        val gidertarihtxt=findViewById<TextView>(R.id.tarihtextview)
        val spin2=findViewById<Spinner>(R.id.spinner2)

        val GiderMiktar=java.lang.Double.parseDouble(gidermiktartxt.text.toString())
        val GiderKategori=spin2.selectedItem.toString()
        val GiderTarih=AnlıkTarih.toString()
        val GiderGun=LocalDate.now().dayOfMonth.toString()
        val GiderAy=LocalDate.now().month.toString()
        val GiderYıl=LocalDate.now().year.toString()

        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

            val sqlstring="INSERT INTO GiderBilgileri (GiderMiktar,GiderKategori,GiderTarih,GiderGun,GiderAy,GiderYıl)VALUES(?,?,?,?,?,?)"

            val statement=veritabani.compileStatement(sqlstring)
            statement.bindDouble(1,GiderMiktar)
            statement.bindString(2,GiderKategori)
            statement.bindString(3,GiderTarih)
            statement.bindString(4,GiderGun)
            statement.bindString(5,GiderAy)
            statement.bindString(6,GiderYıl)
            statement.execute()




        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        gidermiktartxt.text=null
        gidertarihtxt.text=null


        var intent= Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

        val toast: Toast = Toast.makeText(applicationContext,"Yeni Gider Kaydı Yapıldı", Toast.LENGTH_LONG)
        toast.show()
    }
}