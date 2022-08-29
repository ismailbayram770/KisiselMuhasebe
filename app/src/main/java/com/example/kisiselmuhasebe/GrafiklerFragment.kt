package com.example.kisiselmuhasebe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.eazegraph.lib.models.PieModel
import java.time.LocalDate


class GrafiklerFragment : Fragment()
{

    val GiderGun=LocalDate.now().dayOfMonth.toString()
    val GiderAy=LocalDate.now().month.toString()
    val GiderYıl=LocalDate.now().year.toString()

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
        return inflater.inflate(R.layout.fragment_grafikler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val currentDate= LocalDate.now()

        val GiderAylıkBtn=view.findViewById<Button>(R.id.gideraylıkbtn)
        val GiderYıllıklıkBtn=view.findViewById<Button>(R.id.gideryıllıkbtn)
        val GiderGunluklukBtn=view.findViewById<Button>(R.id.gidergunlukbtn)


        GrafikGunluk()

        GiderAylıkBtn.setOnClickListener {
            GrafikAylık()
        }
        GiderYıllıklıkBtn.setOnClickListener {
            GrafikYıllık()
        }
        GiderGunluklukBtn.setOnClickListener {
            GrafikGunluk()
        }
    }

    fun GrafikAylık()
    {
        var pieChart =view?.findViewById<org.eazegraph.lib.charts.PieChart>(R.id.piechart)
        var baslıktxt=view?.findViewById<TextView>(R.id.textView8)
        baslıktxt?.text="Aylık"

        val currentDate= LocalDate.now()
        var AylıkToplamGider=0
        var ToplamGelir=0
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val gidergunColumnIndex=cursor.getColumnIndex("GiderGun")
                val giderayColumnIndex=cursor.getColumnIndex("GiderAy")
                val gideryılColumnIndex=cursor.getColumnIndex("GiderYıl")

                while (cursor.moveToNext())
                {
                    if (cursor.getString(giderayColumnIndex)== GiderAy)
                    {
                        AylıkToplamGider=AylıkToplamGider+cursor.getInt(gidermiktarColumnIndex)
                    }

                }
                cursor.close()

                val cursor2=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)
                val geliridColumnIndex=cursor2.getColumnIndex("id")
                val gelirmiktarColumnIndex=cursor2.getColumnIndex("GelirMiktar")
                val gelirtarihColumnIndex=cursor2.getColumnIndex("GelirTarih")

                while (cursor2.moveToNext())
                {
                    ToplamGelir=ToplamGelir+cursor2.getInt(gelirmiktarColumnIndex)

                }

                cursor2.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        pieChart?.clearDisappearingChildren()
        pieChart?.clearChart()
        pieChart?.addPieSlice(PieModel("GELİR", ToplamGelir.toFloat(), Color.GREEN))
        pieChart?.addPieSlice(PieModel("GİDER", AylıkToplamGider.toFloat(), Color.RED))
        pieChart?.startAnimation()
    }

    fun GrafikYıllık()
    {
        var pieChart =view?.findViewById<org.eazegraph.lib.charts.PieChart>(R.id.piechart)
        var baslıktxt=view?.findViewById<TextView>(R.id.textView8)
        baslıktxt?.text="Yıllık"

        val currentDate= LocalDate.now()
        var YılıkToplamGider=0
        var ToplamGelir=0
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val gidergunColumnIndex=cursor.getColumnIndex("GiderGun")
                val giderayColumnIndex=cursor.getColumnIndex("GiderAy")
                val gideryılColumnIndex=cursor.getColumnIndex("GiderYıl")

                while (cursor.moveToNext())
                {
                    if (cursor.getString(gideryılColumnIndex)== GiderYıl)
                    {
                        YılıkToplamGider=YılıkToplamGider+cursor.getInt(gidermiktarColumnIndex)
                    }

                }
                cursor.close()

                val cursor2=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)
                val geliridColumnIndex=cursor2.getColumnIndex("id")
                val gelirmiktarColumnIndex=cursor2.getColumnIndex("GelirMiktar")
                val gelirtarihColumnIndex=cursor2.getColumnIndex("GelirTarih")

                while (cursor2.moveToNext())
                {
                    ToplamGelir=ToplamGelir+cursor2.getInt(gelirmiktarColumnIndex)

                }

                cursor2.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        ToplamGelir=ToplamGelir*12
        pieChart?.clearDisappearingChildren()
        pieChart?.clearChart()
        pieChart?.addPieSlice(PieModel("GELİR", ToplamGelir.toFloat(), Color.GREEN))
        pieChart?.addPieSlice(PieModel("GİDER", YılıkToplamGider.toFloat(), Color.RED))
        pieChart?.startAnimation()
    }

    fun GrafikGunluk()
    {
        var GunlukToplamGider=0
        var ToplamGelir=0
        var baslıktxt=view?.findViewById<TextView>(R.id.textView8)
        baslıktxt?.text="Bugün"

        var pieChart =view?.findViewById<org.eazegraph.lib.charts.PieChart>(R.id.piechart)

        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
                val gideridColumnIndex=cursor.getColumnIndex("id")
                val gidermiktarColumnIndex=cursor.getColumnIndex("GiderMiktar")
                val gidertarihColumnIndex=cursor.getColumnIndex("GiderTarih")
                val gidergunColumnIndex=cursor.getColumnIndex("GiderGun")
                val giderayColumnIndex=cursor.getColumnIndex("GiderAy")
                val gideryılColumnIndex=cursor.getColumnIndex("GiderYıl")

                while (cursor.moveToNext())
                {

                    if (cursor.getString(gidergunColumnIndex)==GiderGun)
                    {
                        GunlukToplamGider=GunlukToplamGider+cursor.getInt(gidermiktarColumnIndex)
                    }

                }
                cursor.close()

                val cursor2=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)
                val geliridColumnIndex=cursor2.getColumnIndex("id")
                val gelirmiktarColumnIndex=cursor2.getColumnIndex("GelirMiktar")
                val gelirtarihColumnIndex=cursor2.getColumnIndex("GelirTarih")


                while (cursor2.moveToNext())
                {
                    ToplamGelir=ToplamGelir+cursor2.getInt(gelirmiktarColumnIndex)

                }

                cursor2.close()
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        pieChart?.clearDisappearingChildren()
        pieChart?.clearChart()
        pieChart?.addPieSlice(PieModel("GELİR", ToplamGelir.toFloat(), Color.GREEN))
        pieChart?.addPieSlice(PieModel("GİDER", GunlukToplamGider.toFloat(), Color.RED))
        pieChart?.startAnimation()
    }



}