package com.zestworks.application.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zestworks.application.R
import com.zestworks.application.model.Users
import com.zestworks.application.viewmodel.ApplicationViewModel
import kotlinx.android.synthetic.main.fragment_users_list.*
import org.koin.androidx.scope.currentScope


class UsersListFragment : Fragment() {

    private val applicationViewModel: ApplicationViewModel by lazy {  requireActivity().currentScope.get<ApplicationViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applicationViewModel._currentState.observe(this, Observer {
            when(it){
                ApplicationViewModel.Status.Loading -> {
                //do nothing
            }
                is ApplicationViewModel.Status.Success -> {
                    if(user_list_recycler.adapter == null){
                        user_list_recycler.apply {
                            when(it.users){
                                is ApplicationViewModel.UserData.UsersList -> {
                                    adapter =
                                        UserListAdapter(it.users.users.data, object : AdapterClickCallback {
                                            override fun onUserItemClicked(userId: Int) {
                                                applicationViewModel.getUserInfo(userId)
                                                val bundle = Bundle()
                                                bundle.putInt("userId", userId)
                                                findNavController().navigate(
                                                    R.id.action_usersListFragment_to_userInfoFragment,
                                                    bundle
                                                )
                                            }
                                        })
                                    layoutManager = LinearLayoutManager(context)
                                }
                                is ApplicationViewModel.UserData.UserInfoData ->{
                                    //do nothing
                                }
                            }


                        }
                    }else{
                        user_list_recycler.adapter!!.notifyDataSetChanged()
                    }
                }
                is ApplicationViewModel.Status.Error -> {
                    //Handle error cases

                    Toast.makeText(this.context,it.reason,Toast.LENGTH_LONG).show()
                }
            }
        })

        applicationViewModel.onUiLoad()
    }

}

interface AdapterClickCallback{
    fun onUserItemClicked(userId:Int)
}