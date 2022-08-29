package com.example.kisiselmuhasebe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VerilenBorcFragment : Fragment()
{
    val VerilenBorcID=ArrayList<Int>()
    val VerilenBorcMiktar=ArrayList<Int>()
    val VerilenBorcAlmaTarihi=ArrayList<String>()
    val VerilenBorcVermeTarihi=ArrayList<String>()

    private  lateinit var verilenborcadapter:VerilenBorcRecyclerAdapter

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
        return inflater.inflate(R.layout.fragment_verilen_borc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val rec=view.findViewById<RecyclerView>(R.id.recyclerView4)
        verilenborcadapter= VerilenBorcRecyclerAdapter(VerilenBorcID,VerilenBorcMiktar,VerilenBorcVermeTarihi,VerilenBorcAlmaTarihi)
        rec.layoutManager=LinearLayoutManager(context)
        rec.adapter=verilenborcadapter

        VerilenBorclarBilgileriniALma()

    }

    fun VerilenBorclarBilgileriniALma()
    {
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM VerilenBorcBilgileri",null)
                val verilenborcIDColumnIndex=cursor.getColumnIndex("id")
                val verilenborcmiktarColumnIndex=cursor.getColumnIndex("VerilenBorcMiktar")
                val verilenborcvermetarihiColumnIndex=cursor.getColumnIndex("VerilenBorcVermeTarihi")
                val verilenborcalmatarihiColumnIndex=cursor.getColumnIndex("VerilenBorcAlmaTarihi")

                VerilenBorcID.clear()
                VerilenBorcMiktar.clear()
                VerilenBorcVermeTarihi.clear()
                VerilenBorcAlmaTarihi.clear()

                while (cursor.moveToNext())
                {
                    VerilenBorcID.add(cursor.getInt(verilenborcIDColumnIndex))
                    VerilenBorcMiktar.add(cursor.getInt(verilenborcmiktarColumnIndex))
                    VerilenBorcVermeTarihi.add(cursor.getString(verilenborcvermetarihiColumnIndex))
                    VerilenBorcAlmaTarihi.add(cursor.getString(verilenborcalmatarihiColumnIndex))
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