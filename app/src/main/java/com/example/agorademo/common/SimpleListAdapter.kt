package com.example.agorademo.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.agorademo.utils.safeLet
import com.example.agorademo.utils.setWidth

class SimpleListAdapter<VB: ViewBinding, Data: Any>(
    private val inflate: Inflate<VB>,
    itemComparator: DiffUtil.ItemCallback<Data>,
    private val onBind: (position: Int, rowBinding: VB, Data) -> Unit,
    private val itemInit: ((SimpleListAdapter<VB, Data>.SimpleViewHolder) -> Unit)? = null,
    private val itemCountToFlex: Int? = null
): ListAdapter<Data, SimpleListAdapter<VB, Data>.SimpleViewHolder>(
    AsyncDifferConfig.Builder(
        itemComparator
    ).build()
) {
    private var recyclerView: RecyclerView? = null

    inner class SimpleViewHolder(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemInit?.invoke(this)
        }

        fun getBinding(): VB {
            return binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
        safeLet(recyclerView?.measuredWidth, itemCountToFlex) { totalWidth, count ->
            binding.root.setWidth(totalWidth / count)
        }
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        onBind.invoke(position, holder.getBinding(), getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }
}

fun <VB : ViewDataBinding, Data : Any> RecyclerView.setUpListAdapter(
    inflate: Inflate<VB>,
    itemComparator: DiffUtil.ItemCallback<Data>,
    onBindCallback: (position: Int, rowBinding: VB, Data) -> Unit,
    itemInitCallback: ((SimpleListAdapter<VB, Data>.SimpleViewHolder) -> Unit)? = null,
    itemCountToFlex: Int? = null
): SimpleListAdapter<VB, Data> {
    val simpleAdapter = SimpleListAdapter(
        inflate,
        itemComparator,
        onBindCallback,
        itemInitCallback,
        itemCountToFlex = itemCountToFlex
    )
    adapter = simpleAdapter
    return simpleAdapter
}