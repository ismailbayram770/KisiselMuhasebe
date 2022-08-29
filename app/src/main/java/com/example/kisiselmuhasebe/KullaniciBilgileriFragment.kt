package com.example.kisiselmuhasebe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView


class KullaniciBilgileriFragment : Fragment()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kullanici_bilgileri, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        KullaniciBilgileriniAlma()

    }

    fun KullaniciBilgileriniAlma()
    {
        val adtxt= view?.findViewById<TextView>(R.id.adttxt)
        val soyadtxt=view?.findViewById<TextView>(R.id.soyadtxt)
        val telefontxt=view?.findViewById<TextView>(R.id.telefontxt)
        val emailtxt=view?.findViewById<TextView>(R.id.emailtxt)
        var id=0
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM KullaniciBilgileri",null)

                val idColumnIndex=cursor.getColumnIndex("id")
                val isimColumnIndex=cursor.getColumnIndex("isim")
                val soyisimColumnIndex=cursor.getColumnIndex("soyisim")
                val emailColumnIndex=cursor.getColumnIndex("email")
                val telefonColumnIndex=cursor.getColumnIndex("telefon")



                while (cursor.moveToNext())
                {
                    id=cursor.getInt(idColumnIndex)
                    if (adtxt != null) {
                        adtxt.text=cursor.getString(isimColumnIndex).toString()
                    }
                    if (soyadtxt != null) {
                        soyadtxt.text=cursor.getString(soyisimColumnIndex).toString()
                    }
                    if (telefontxt != null) {
                        telefontxt.text=cursor.getString(telefonColumnIndex).toString()
                    }
                    if (emailtxt != null) {
                        emailtxt.text=cursor.getString(emailColumnIndex).toString()
                    }
                }

                cursor.close()
            }


        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

}