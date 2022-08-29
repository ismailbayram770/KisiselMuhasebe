package com.example.kisiselmuhasebe.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kisiselmuhasebe.GiderRecyclerAdapter
import com.example.kisiselmuhasebe.R
import com.example.kisiselmuhasebe.databinding.FragmentHomeBinding
import com.example.kisiselmuhasebe.ui.slideshow.SlideshowFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment()
{
    val GiderId=ArrayList<Int>()
    val GiderMiktarList=ArrayList<Int>()
    val GiderKategoriList=ArrayList<String>()
    val GiderTarihList=ArrayList<String>()
    private  lateinit var giderAdapter: GiderRecyclerAdapter

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    public var ToplamGelir=0
    public var ToplamGider=0
    public var Kalan=0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        ToplamGelir=0
        ToplamGider=0
        Kalan=0

        var tarihtxt=view.findViewById<TextView>(R.id.textView)
        var toplamgelirtxt=view.findViewById<TextView>(R.id.textView2)
        var toplamgidertxt=view.findViewById<TextView>(R.id.textView3)
        var kalantxt=view.findViewById<TextView>(R.id.textView4)



        val currentDate= LocalDate.now().month
        tarihtxt.text=currentDate.toString()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate2 = sdf.format(Date())

        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe",Context.MODE_PRIVATE,null)



                val cursor=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)
                val gelirmiktarColumnIndex=cursor.getColumnIndex("GelirMiktar")
                val gelirkategoriColumnIndex=cursor.getColumnIndex("GelirKategori")
                val gelirtarihColumnIndex=cursor.getColumnIndex("GelirTarih")

                while (cursor.moveToNext())
                {
                    ToplamGelir=ToplamGelir+cursor.getInt(gelirmiktarColumnIndex)
                }
                cursor.close()

                val cursor4=veritabani.rawQuery("SELECT * FROM AlinanBorcBilgileri",null)
                val alinanborcmiktarColumnIndex=cursor4.getColumnIndex("AlinanBorcMiktar")
                val alinanborctarihColumnIndex=cursor4.getColumnIndex("AlinanBorcVermeTarihi")
                var alinanborcsayacı=0
                while (cursor4.moveToNext())
                {
                    ToplamGelir = ToplamGelir + cursor4.getInt(alinanborcmiktarColumnIndex)
                    if(currentDate2.toString()==cursor4.getString(alinanborctarihColumnIndex))
                    {
                        alinanborcsayacı++
                    }
                }
                cursor4.close()


                toplamgelirtxt.text="Toplam Gelir: ${ToplamGelir} "


                val cursor2=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gidermiktarColumnIndex=cursor2.getColumnIndex("GiderMiktar")

                while (cursor2.moveToNext())
                {
                    ToplamGider = ToplamGider + cursor2.getInt(gidermiktarColumnIndex)
                }
                cursor2.close()

                val cursor5=veritabani.rawQuery("SELECT * FROM VerilenBorcBilgileri",null)
                val verilenborcmiktarColumnIndex=cursor5.getColumnIndex("VerilenBorcMiktar")
                val verilenborctarihColumnIndex=cursor5.getColumnIndex("VerilenBorcAlmaTarihi")
                var verilenborcsayacı=0
                while (cursor5.moveToNext())
                {
                    ToplamGider = ToplamGider + cursor5.getInt(verilenborcmiktarColumnIndex)

                    if(currentDate2.toString()==cursor5.getString(verilenborctarihColumnIndex))
                    {
                        verilenborcsayacı++
                    }
                }
                cursor5.close()


                toplamgidertxt.text="Toplam Gider: ${ToplamGider} "

                Kalan=ToplamGelir-ToplamGider
                kalantxt.text="Kalan: ${Kalan} "

            }


        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }




        val rec=view.findViewById<RecyclerView>(R.id.recyclerView3)
        giderAdapter= GiderRecyclerAdapter(GiderId,GiderMiktarList,GiderKategoriList,GiderTarihList)
        rec.layoutManager=LinearLayoutManager(context)
        rec.adapter=giderAdapter
        GiderBilgileriniAlma()


    }

    fun GiderBilgileriniAlma()
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

                GiderId.clear()
                GiderMiktarList.clear()
                GiderKategoriList.clear()
                GiderTarihList.clear()
                println(LocalDate.now().dayOfMonth.toString() )
                while (cursor.moveToNext())
                {
                    if (LocalDate.now().toString()==cursor.getString(gidertarihColumnIndex))
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