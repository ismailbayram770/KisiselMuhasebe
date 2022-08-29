package com.example.kisiselmuhasebe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.measureTimedValue

class VerilenBorcRecyclerAdapter(val VerilenBorcID:ArrayList<Int>,val VerilenBorcMiktar:ArrayList<Int>,val VerilenBorcVermeTarihi:ArrayList<String>,val VerilenBorcAlmaTarihi:ArrayList<String>):RecyclerView.Adapter<VerilenBorcRecyclerAdapter.VerilenBorclarHolder>()
{
    class VerilenBorclarHolder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        val miktartxt: TextView
        val VerilenBorcVermeTarihitxt: TextView
        val VerilenBorcAlmaTarihitxt: TextView
        val VerilenBorcIDtxt:TextView
        init
        {
            miktartxt=itemView.findViewById(R.id.recycler_row_verilenborcmiktartxt)
            VerilenBorcVermeTarihitxt=itemView.findViewById(R.id.recycler_row_borcverilentarihtxt)
            VerilenBorcAlmaTarihitxt=itemView.findViewById(R.id.recycler_row_borcalınacaktarihtxt)
            VerilenBorcIDtxt=itemview.findViewById(R.id.verilenborcidtxt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerilenBorclarHolder
    {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row_verilenborclar,parent,false)
        return VerilenBorclarHolder(view)
    }

    override fun onBindViewHolder(holder: VerilenBorclarHolder, position: Int)
    {
        holder.miktartxt.text="Verilen Borç Miktar: "+VerilenBorcMiktar[position].toString()
        holder.VerilenBorcVermeTarihitxt.text="Verilen Borç Verme Tarihi: "+VerilenBorcVermeTarihi[position].toString()
        holder.VerilenBorcAlmaTarihitxt.text="Verilen Borç Alma Tarihi: "+VerilenBorcAlmaTarihi[position].toString()
        holder.VerilenBorcIDtxt.text=VerilenBorcID[position].toString()
    }

    override fun getItemCount(): Int
    {
        return VerilenBorcMiktar.size
    }
}