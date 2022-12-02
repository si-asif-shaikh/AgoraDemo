package com.example.agorademo.presentation.home.userChat

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agorademo.R
import com.example.agorademo.business.domain.model.UserData
import com.example.agorademo.common.setUpListAdapter
import com.example.agorademo.databinding.FragmentUserChatBinding
import com.example.agorademo.databinding.ItemTextBinding
import com.example.agorademo.presentation.BaseVBFragment
import com.example.agorademo.presentation.menu.DialogAddUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserList_F : BaseVBFragment<FragmentUserChatBinding>(FragmentUserChatBinding::inflate) {

    private val TAG= "UserChat_F"

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAddUserDialog()
        initUserList()


    }

    private fun initView() {

        mainViewModel.setTitle("Chat List")

        binding.btnAddUser.setOnClickListener {
            dialogAddUser.show()
        }
    }

    private fun initUserList() {
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = userAdapter
        }
        userViewModel.getUserList()
        observer()
    }

    private fun initAddUserDialog() {
        dialogAddUser.setOnDialogActionClickListener {
            val userId = it.getString("user_name")!!
            userViewModel.addUser(userId)
        }
    }

    private val dialogAddUser by lazy {
        DialogAddUser(this.requireActivity())
    }

    private fun observer() {
        userViewModel.loading.observe(viewLifecycleOwner, Observer { isloading ->
            if(isloading)
            {
                showLoading()
            }
            else{
                dismissLoading()
            }
        })
        userViewModel.userListing.observe(viewLifecycleOwner, Observer { result ->
            userAdapter.submitList(result)
        })
    }

    private val userAdapter by lazy {
        binding.rvUserList.setUpListAdapter(
            ItemTextBinding::inflate,
            itemComparator = object : DiffUtil.ItemCallback<UserData>(){
                override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                    return oldItem.user_id == newItem.user_id
                }

                override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                    return oldItem == newItem
                }

            },
            onBindCallback = { _:Int, itemView: ItemTextBinding, data:UserData ->
                itemView.tvRoomid.text = data.user_id
                itemView.root.setOnClickListener {
                    startChatActivity(data.user_id)
                }
            }
        )

    }

    private fun startChatActivity(toChatUser:String) {

        // check username
        if (TextUtils.isEmpty(toChatUser)) {
            showToast(getString(R.string.not_find_send_name))
            return
        }
        else if(!mainViewModel.isClientLogin())
        {
            showToast(getString(R.string.sign_in_first))
            return
        }

        startActivity(Intent(activity, Chat_A::class.java).apply {
            putExtra("toUserId",toChatUser)
        })
    }

}