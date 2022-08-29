package com.example.kisiselmuhasebe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GiderRecyclerAdapter(val Id:ArrayList<Int>, val GiderMiktari:ArrayList<Int>,val GiderKategori:ArrayList<String>,val GiderTarih:ArrayList<String>):RecyclerView.Adapter<GiderRecyclerAdapter.GiderHodlder>()
{
    class GiderHodlder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val miktartxt: TextView
        val kategoritxt: TextView
        val tarihtxt: TextView
        val idtxt:TextView
        val idbtn:Button
        init
        {
            miktartxt=itemView.findViewById(R.id.gidermiktartxt)
            kategoritxt=itemView.findViewById(R.id.giderkategoritxt)
            tarihtxt=itemView.findViewById(R.id.gidertarihtxt)
            idtxt=itemView.findViewById(R.id.alinanborcidtxt)
            idbtn=itemView.findViewById(R.id.gidersilbtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiderHodlder
    {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row_gider,parent,false)
        return GiderHodlder(view)
    }

    override fun onBindViewHolder(holder: GiderHodlder, position: Int) {
        holder.miktartxt.text=GiderMiktari[position].toString()
        holder.kategoritxt.text=GiderKategori[position].toString()
        holder.tarihtxt.text=GiderTarih[position].toString()
        holder.idtxt.text=Id[position].toString()
    }

    override fun getItemCount(): Int {
        return GiderMiktari.size
    }

}