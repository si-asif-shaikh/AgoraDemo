package com.example.agorademo.business.interactive

import com.example.agorademo.business.domain.model.UserData


class DefaultUsers{
    companion object {
        fun get() : List<UserData>
        {
            return listOf(
                UserData("user2511"),
                UserData("user2611"),
                UserData("user2711"),
                UserData("user2811"),
            )
        }
    }
}