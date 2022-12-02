package com.example.agorademo.presentation.home.roomChat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agorademo.R
import com.example.agorademo.business.domain.model.ChatRoomModel
import com.example.agorademo.common.setUpListAdapter
import com.example.agorademo.databinding.FragmentRoomChatBinding
import com.example.agorademo.databinding.ItemTextBinding
import com.example.agorademo.presentation.BaseVBFragment
import com.example.agorademo.presentation.menu.DialogAddRoom
import dagger.hilt.android.AndroidEntryPoint
import io.agora.chat.ChatClient

@AndroidEntryPoint
class RoomListing_F : BaseVBFragment<FragmentRoomChatBinding>(FragmentRoomChatBinding::inflate) {

    private val roomViewModel: RoomViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        mainViewModel.setTitle("Rooms List")

        binding.recRoomList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =chatRoomAdapter
        }

        chatRoomAdapter.submitList(roomViewModel.getChatRoomList())

        binding.btnCreateRoom.setOnClickListener {

            if(!mainViewModel.isClientLogin())
            {
                showToast(getString(R.string.sign_in_first))
                return@setOnClickListener
            }

            openCreateRoomInfoDialog()
        }
    }

    private val chatRoomAdapter by lazy {
        binding.recRoomList.setUpListAdapter(
            ItemTextBinding::inflate,
            onBindCallback = { _: Int, itemView: ItemTextBinding, data: ChatRoomModel ->
                itemView.tvRoomid.text = data.roomId
                itemView.root.setOnClickListener {
                    startRoomChatActivity(data.roomId)
                }
            },
            itemComparator = object : DiffUtil.ItemCallback<ChatRoomModel>() {
                override fun areItemsTheSame(
                    oldItem: ChatRoomModel,
                    newItem: ChatRoomModel
                ): Boolean {
                    return oldItem.roomId == newItem.roomId
                }

                override fun areContentsTheSame(
                    oldItem: ChatRoomModel,
                    newItem: ChatRoomModel
                ): Boolean {
                    return oldItem == newItem
                }

            }
        )
    }

    private fun startRoomChatActivity(roomId: String) {

        if(mainViewModel.isClientLogin())
            startActivity(Intent(activity,RoomChat_A::class.java).apply {
                putExtra("room_id",roomId)
            })
        else
        {
            showToast(getString(R.string.sign_in_first))
        }
    }

    fun openCreateRoomInfoDialog() {
        if(mainViewModel.isClientLogin())
        {
            val dialogAddRoom  = DialogAddRoom(this.requireContext())
            dialogAddRoom.setOnDialogActionClickListener {
                createRoom(bundle = it)
            }
            dialogAddRoom.show()
        }

    }

    fun createRoom(bundle: Bundle){
        try {
            val chatRoom = ChatClient.getInstance().chatroomManager()
                .createChatRoom(
                    bundle.getString("subject"),
                    bundle.getString("description"),
                    bundle.getString("welcomMessage"),
                    bundle.getInt("maxUserCount"),
                    bundle.getStringArrayList("members"))

            showToast(""+chatRoom.id)
        }
        catch (ex: Exception)
        {
            showToast(""+ex.localizedMessage)
            ex.printStackTrace()
        }

    }
}