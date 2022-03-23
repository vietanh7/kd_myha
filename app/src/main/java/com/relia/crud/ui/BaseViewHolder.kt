package com.relia.crud.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BaseViewHolder<BINDING : ViewDataBinding>(val binder: BINDING) :
    RecyclerView.ViewHolder(binder.root)