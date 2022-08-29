package com.example.kisiselmuhasebe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kisiselmuhasebe.databinding.FragmentGalleryBinding
import com.example.kisiselmuhasebe.ui.gallery.GalleryViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlinanBorclarFragment : Fragment()
{
    val AlinanBorcID=ArrayList<Int>()
    val AlinanBorcMiktar=ArrayList<Int>()
    val AlinanBorcAlmaTarihi=ArrayList<String>()
    val AlinanBorcVermeTarihi=ArrayList<String>()

    private  lateinit var alinanborcadapter:AlinanBorcRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alinan_borclar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rec=view.findViewById<RecyclerView>(R.id.recyclerView3)
        alinanborcadapter= AlinanBorcRecyclerAdapter(AlinanBorcID,AlinanBorcMiktar,AlinanBorcAlmaTarihi,AlinanBorcVermeTarihi)
        rec.layoutManager=LinearLayoutManager(context)
        rec.adapter=alinanborcadapter
        AlinanBorclarBilgileriniALma()
    }

    fun AlinanBorclarBilgileriniALma()
    {

        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM AlinanBorcBilgileri",null)
                val AlinanBorcIDColumnIndex=cursor.getColumnIndex("id")
                val AlinanBorcmiktarColumnIndex=cursor.getColumnIndex("AlinanBorcMiktar")
                val BorcAlmaTarihiColumnIndex=cursor.getColumnIndex("AlinanBorcAlmaTarihi")
                val BorcVermeTarihiColumnIndex=cursor.getColumnIndex("AlinanBorcVermeTarihi")

                AlinanBorcID.clear()
                AlinanBorcMiktar.clear()
                AlinanBorcAlmaTarihi.clear()
                AlinanBorcVermeTarihi.clear()

                while (cursor.moveToNext())
                {
                    AlinanBorcID.add(cursor.getInt(AlinanBorcIDColumnIndex))
                    AlinanBorcMiktar.add(cursor.getInt(AlinanBorcmiktarColumnIndex))
                    AlinanBorcAlmaTarihi.add(cursor.getString(BorcAlmaTarihiColumnIndex))
                    AlinanBorcVermeTarihi.add(cursor.getString(BorcVermeTarihiColumnIndex))

                }

                alinanborcadapter.notifyDataSetChanged()

                cursor.close()

            }

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }


}