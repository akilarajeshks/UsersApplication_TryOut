package com.zestworks.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.zestworks.application.R
import com.zestworks.application.model.UserInfo
import com.zestworks.application.viewmodel.ApplicationViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.koin.androidx.scope.currentScope


class UserInfoFragment : Fragment() {

    private val applicationViewModel: ApplicationViewModel by lazy { requireActivity().currentScope.get<ApplicationViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }


    override fun onStart() {
        super.onStart()

        applicationViewModel._currentState.observe(this, Observer {
            when (it) {
                ApplicationViewModel.Status.Loading -> {
                    //do nothing
                }
                is ApplicationViewModel.Status.Success -> {
                    when(it.users){
                        is ApplicationViewModel.UserData.UsersList -> {
                            //do nothing
                        }
                        is ApplicationViewModel.UserData.UserInfoData -> {
                            renderUserInfo(it.users.user)
                        }
                    }
                }
                is ApplicationViewModel.Status.Error -> {
                    //Handle error cases
                    Toast.makeText(this.context,it.reason, Toast.LENGTH_LONG).show()

                }
            }
        })
        if (arguments != null) {
            val userId = arguments!!.getInt("userId")
            applicationViewModel.getUserInfo(userId)
        }

    }

    private fun renderUserInfo(userInfo: UserInfo) {
        Picasso.get().load(userInfo.image).into(user_image)
        user_name.text = String.format(
            getString(R.string.username),
            userInfo.nameTitle.toUpperCase(),
            userInfo.firstName,
            userInfo.lastName
        )
        user_gender.text = String.format(getString(R.string.gender), userInfo.gender)
        user_email.text = String.format(getString(R.string.email), userInfo.email)
        user_dob.text = String.format(getString(R.string.dob), userInfo.dob)
        user_phone.text = String.format(getString(R.string.phone), userInfo.phone)
        user_cell.text = String.format(getString(R.string.cell), userInfo.cell)
    }

}
