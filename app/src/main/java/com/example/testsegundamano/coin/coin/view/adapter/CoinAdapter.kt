package com.example.testsegundamano.coin.coin.view.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsegundamano.R
import com.example.testsegundamano.coin.coin.dto.CoinState
import com.example.testsegundamano.coin.coin.dto.api.DataCoin
import java.time.format.DateTimeFormatter

class CoinAdapter (private val list : MutableList<DataCoin> = mutableListOf())
    : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private val formatByView = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvDate : TextView = view.findViewById(R.id.tvDate)
        val tvCoinFrom : TextView = view.findViewById(R.id.tvCoinFrom)
        val tvCoinTo : TextView = view.findViewById(R.id.tvCoinTo)
        val tvMoneyFrom : TextView = view.findViewById(R.id.tvMoneyFrom)
        val tvMoneyTo : TextView = view.findViewById(R.id.tvMoneyTo)

        val ivFrom : ImageView = view.findViewById(R.id.ivFrom)
        val ivTo : ImageView = view.findViewById(R.id.ivTo)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = this.list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : DataCoin = this.list[position] ?: return

        holder.tvDate.text = item.date.format(formatByView).toString()
        holder.tvCoinFrom.text = item.coinFrom
        holder.tvCoinTo.text = item.coinTo
        holder.tvMoneyFrom.text = item.valueFrom.toString()
        holder.tvMoneyTo.text = item.valueTo.toString()

        if(item.arrowFrom != CoinState.NONE) {
            val arrowFrom = this.assignDrawableToArrow(item.arrowFrom)
            holder.ivFrom.setImageDrawable(holder.itemView.context.getDrawable(arrowFrom))
        }

        if(item.arrowFrom != CoinState.NONE) {
            val arrowTo =  this.assignDrawableToArrow(item.arrowTo)
            holder.ivTo.setImageDrawable(holder.itemView.context.getDrawable(arrowTo))
        }
    }


    private fun assignDrawableToArrow(coinState : CoinState ) : Int{
        return when (coinState!!) {
            CoinState.EQUAL -> {
                R.drawable.ic_equal
            }
            CoinState.UP -> {
                R.drawable.ic_up
            }
            CoinState.DOWN -> {
                R.drawable.ic_down
            }
            else -> 0
        }
    }

    fun updateList(list: List<DataCoin>) {
        this.list.clear()
        this.list.addAll(list)
    }

}

