package com.example.kisiselmuhasebe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlinanBorcRecyclerAdapter(val AlinanBorcID:ArrayList<Int>,val AlinanBorcMiktar:ArrayList<Int>,val AlinanBorcAlmaTarihi:ArrayList<String>,val AlinanBorcVermeTarihi:ArrayList<String>):RecyclerView.Adapter<AlinanBorcRecyclerAdapter.AlinanBorcHolder>()
{
    class AlinanBorcHolder(itemview:View):RecyclerView.ViewHolder(itemview)
    {
        val miktartxt: TextView
        val AlinanBorcAlmaTarihitxt: TextView
        val AlinanBorcVermeTarihitxt: TextView
        val AlinanBorcIDtxt:TextView
        init
        {
            miktartxt=itemView.findViewById(R.id.recycler_row_alinanborcmiktartxt)
            AlinanBorcAlmaTarihitxt=itemView.findViewById(R.id.recycler_row_borcalinantarihtxt)
            AlinanBorcVermeTarihitxt=itemView.findViewById(R.id.recycler_row_borcverilecektarihtxt)
            AlinanBorcIDtxt=itemview.findViewById(R.id.alinanborcidtxt)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlinanBorcHolder {
       val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row_alinanborclar,parent,false)
        return AlinanBorcHolder(view)
    }

    override fun onBindViewHolder(holder: AlinanBorcHolder, position: Int) {
        holder.miktartxt.text="Alınan Borç Miktar: "+AlinanBorcMiktar[position].toString()
        holder.AlinanBorcAlmaTarihitxt.text="Alınan Borcun Alma Tarihi: "+AlinanBorcAlmaTarihi[position].toString()
        holder.AlinanBorcVermeTarihitxt.text="Alınan Borcun Verme Tarihi: "+AlinanBorcVermeTarihi[position].toString()
        holder.AlinanBorcIDtxt.text=AlinanBorcID[position].toString()
    }

    override fun getItemCount(): Int {
        return AlinanBorcMiktar.size
    }
}