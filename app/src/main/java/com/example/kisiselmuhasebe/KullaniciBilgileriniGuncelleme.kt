package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class KullaniciBilgileriniGuncelleme : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_bilgilerini_guncelleme)

    }


    fun KullaniciGuncellebtn(view: View)
    {
        val adtxt=findViewById<EditText>(R.id.adttxt4)
        val soyadtxt=findViewById<EditText>(R.id.soyadtxt4)
        val telefontxt=findViewById<EditText>(R.id.telefontxt4)
        val emailtxt=findViewById<EditText>(R.id.emailtxt4)

        val KullaniciAd=adtxt.text.toString()
        val KullaniciSoyad=soyadtxt.text.toString()
        val KullaniciTelefon=telefontxt.text.toString()
        val KullaniciEmail=emailtxt.text.toString()

        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

            val sqlstring="UPDATE  KullaniciBilgileri SET isim=?,soyisim=?,email=?,telefon=? WHERE id=1"

            val statement=veritabani.compileStatement(sqlstring)
            statement.bindString(1,KullaniciAd)
            statement.bindString(2,KullaniciSoyad)
            statement.bindString(3,KullaniciEmail)
            statement.bindString(4,KullaniciTelefon)

            statement.execute()


        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        var intent= Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

        val toast: Toast = Toast.makeText(applicationContext,"Kullanıcı Bilgilerli Güncellendi", Toast.LENGTH_LONG)
        toast.show()
    }


}