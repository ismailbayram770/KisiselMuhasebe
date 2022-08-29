package com.example.kisiselmuhasebe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class GelirRecyclerAdapter(val GelirID:ArrayList<Int>,val GelirMiktari:ArrayList<Int>,val GelirKategori:ArrayList<String>,val GelirTarih:ArrayList<String>): RecyclerView.Adapter<GelirRecyclerAdapter.GelirHolder>()
{
    class GelirHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val miktartxt: TextView
        val kategoritxt: TextView
        val tarihtxt: TextView
        val idtxt:TextView
        init
        {
            miktartxt=itemView.findViewById(R.id.recycler_row_miktartxt)
            kategoritxt=itemView.findViewById(R.id.recycler_row_kategoritxt)
            tarihtxt=itemView.findViewById(R.id.recycler_row_tarihtxt)
            idtxt=itemView.findViewById(R.id.geliridtxt)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GelirHolder
    {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)
        return  GelirHolder(view)
    }
    override fun getItemCount(): Int
    {
        return  GelirMiktari.size
    }

    override fun onBindViewHolder(holder: GelirHolder, position: Int)
    {
        holder.miktartxt.text="Gelir Miktarı: "+GelirMiktari[position].toString()
        holder.kategoritxt.text="Gelir Kategorisi: "+GelirKategori[position]
        holder.tarihtxt.text="Gelir Tarih: "+GelirTarih[position]
        holder.idtxt.text=GelirID[position].toString()
    }


}