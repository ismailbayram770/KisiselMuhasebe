package com.example.kisiselmuhasebe.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kisiselmuhasebe.GiderRecyclerAdapter
import com.example.kisiselmuhasebe.R
import com.example.kisiselmuhasebe.databinding.FragmentSlideshowBinding
import java.time.LocalDate

class SlideshowFragment : Fragment()
{

    val GiderId=ArrayList<Int>()
    val GiderMiktarList=ArrayList<Int>()
    val GiderKategoriList=ArrayList<String>()
    val GiderTarihList=ArrayList<String>()
    private  lateinit var giderAdapter:GiderRecyclerAdapter

    val Gun=LocalDate.now().toString()
    val GiderGun=LocalDate.now().dayOfMonth.toString()
    val GiderAy=LocalDate.now().month.toString()
    val GiderYıl=LocalDate.now().year.toString()



    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        val rec=view.findViewById<RecyclerView>(R.id.recyclerView2)
        giderAdapter= GiderRecyclerAdapter(GiderId,GiderMiktarList,GiderKategoriList,GiderTarihList)
        rec.layoutManager=LinearLayoutManager(context)
        rec.adapter=giderAdapter

        BugunGiderBilgileriniAlma()

        val GiderBugunBtn=view?.findViewById<Button>(R.id.gidergunlukbtn)
        val GiderAylıkBtn=view?.findViewById<Button>(R.id.gideraylıkbtn)
        val GiderYıllıkBtn=view?.findViewById<Button>(R.id.gideryıllıkbtn)

        GiderBugunBtn?.setOnClickListener {
            BugunGiderBilgileriniAlma()
        }
        GiderAylıkBtn?.setOnClickListener {
            AylıkGiderBilgileriniAlma()
        }
        GiderYıllıkBtn?.setOnClickListener {
            YıllıkGiderBilgileriniAlma()
        }


    }

    fun BugunGiderBilgileriniAlma()
    {
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val giderkategoriColumnIndex=cursor.getColumnIndex("GiderKategori")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val gidergunColumnIndex=cursor.getColumnIndex("GiderGun")

                GiderMiktarList.clear()
                GiderKategoriList.clear()
                GiderTarihList.clear()
                GiderId.clear()

                while (cursor.moveToNext())
                {
                    if (Gun.toString()==cursor.getString(gidertarihColumnIndex))
                    {
                        GiderId.add(cursor.getInt(gideridColumnIndex))
                        GiderMiktarList.add(cursor.getInt(gidermiktarColumnIndex))
                        GiderKategoriList.add(cursor.getString(giderkategoriColumnIndex))
                        GiderTarihList.add(cursor.getString(gidertarihColumnIndex))
                    }
                }
                giderAdapter.notifyDataSetChanged()
                cursor.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }
    fun AylıkGiderBilgileriniAlma()
    {
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val giderkategoriColumnIndex=cursor.getColumnIndex("GiderKategori")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val giderayColumnIndex=cursor.getColumnIndex("GiderAy")

                GiderMiktarList.clear()
                GiderKategoriList.clear()
                GiderTarihList.clear()
                GiderId.clear()

                while (cursor.moveToNext())
                {
                    if (GiderAy.toString()==cursor.getString(giderayColumnIndex))
                    {
                        GiderId.add(cursor.getInt(gideridColumnIndex))
                        GiderMiktarList.add(cursor.getInt(gidermiktarColumnIndex))
                        GiderKategoriList.add(cursor.getString(giderkategoriColumnIndex))
                        GiderTarihList.add(cursor.getString(gidertarihColumnIndex))
                    }

                }
                giderAdapter.notifyDataSetChanged()
                cursor.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }

    fun YıllıkGiderBilgileriniAlma()
    {

        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val giderkategoriColumnIndex=cursor.getColumnIndex("GiderKategori")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val gideryılColumnIndex=cursor.getColumnIndex("GiderYıl")

                GiderMiktarList.clear()
                GiderKategoriList.clear()
                GiderTarihList.clear()
                GiderId.clear()

                while (cursor.moveToNext())
                {
                    if (GiderYıl.toString()==cursor.getString(gideryılColumnIndex))
                    {
                        GiderId.add(cursor.getInt(gideridColumnIndex))
                        GiderMiktarList.add(cursor.getInt(gidermiktarColumnIndex))
                        GiderKategoriList.add(cursor.getString(giderkategoriColumnIndex))
                        GiderTarihList.add(cursor.getString(gidertarihColumnIndex))
                    }

                }
                giderAdapter.notifyDataSetChanged()
                cursor.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }
}