package com.example.agorademo.presentation.home.groupChat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agorademo.R
import com.example.agorademo.business.domain.model.ChatGroupModel
import com.example.agorademo.common.setUpListAdapter
import com.example.agorademo.databinding.FragmentGroupChatBinding
import com.example.agorademo.databinding.ItemTextBinding
import com.example.agorademo.presentation.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupChat_F : BaseVBFragment<FragmentGroupChatBinding>(FragmentGroupChatBinding::inflate) {

    private val groupViewModel: GroupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initGroupList()
    }

    private fun initView() {
        mainViewModel.setTitle("Group List")
    }

    private fun initGroupList() {
        binding.rvGroupListing.apply {
            layoutManager= LinearLayoutManager(context)
            adapter = groupListAdapter
        }
    }

    private val groupListAdapter by lazy {
        binding.rvGroupListing.setUpListAdapter(
           inflate =  ItemTextBinding::inflate,
           itemComparator = object : DiffUtil.ItemCallback<ChatGroupModel>(){
                override fun areItemsTheSame(
                    oldItem: ChatGroupModel,
                    newItem: ChatGroupModel
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun areContentsTheSame(
                    oldItem: ChatGroupModel,
                    newItem: ChatGroupModel
                ): Boolean {
                    TODO("Not yet implemented")
                }

            },
           onBindCallback = { position: Int, rowBinding: ItemTextBinding, chatGroupModel: ChatGroupModel ->

           }
        )
    }
}