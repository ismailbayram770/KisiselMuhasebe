package com.example.kisiselmuhasebe.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kisiselmuhasebe.GelirRecyclerAdapter
import com.example.kisiselmuhasebe.R
import com.example.kisiselmuhasebe.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment()
{
    var IdList=ArrayList<Int>()
    var GelirMiktarList=ArrayList<Int>()
    var GelirKategoriList=ArrayList<String>()
    var GelirTarihList=ArrayList<String>()
    private lateinit var gelirAdapter : GelirRecyclerAdapter



    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val rec=view.findViewById<RecyclerView>(R.id.recyclerView)
        gelirAdapter= GelirRecyclerAdapter(IdList,GelirMiktarList,GelirKategoriList,GelirTarihList)
        rec.layoutManager=LinearLayoutManager(context)
        rec.adapter=gelirAdapter
        GelirBilgileriniAlma()
    }

    fun GelirBilgileriniAlma()
    {
        try
        {
            activity?.let {
                val veritabani=it.openOrCreateDatabase("KisiselMuhasebe",Context.MODE_PRIVATE,null)

                val cursor=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)

                val idColumnIndex=cursor.getColumnIndex("id")
                val gelirmiktarColumnIndex=cursor.getColumnIndex("GelirMiktar")
                val gelirkategoriColumnIndex=cursor.getColumnIndex("GelirKategori")
                val gelirtarihColumnIndex=cursor.getColumnIndex("GelirTarih")

                IdList.clear()
                GelirMiktarList.clear()
                GelirKategoriList.clear()
                GelirTarihList.clear()

                while (cursor.moveToNext())
                {
                    IdList.add(cursor.getInt(idColumnIndex))
                    GelirMiktarList.add(cursor.getInt(gelirmiktarColumnIndex))
                    GelirKategoriList.add(cursor.getString(gelirkategoriColumnIndex))
                    GelirTarihList.add(cursor.getString(gelirtarihColumnIndex))
                }
                gelirAdapter.notifyDataSetChanged()
                cursor.close()
            }


        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }



}