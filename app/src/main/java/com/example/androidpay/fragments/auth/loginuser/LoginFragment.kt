package com.example.androidpay.fragments.auth.loginuser

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidpay.R
import com.example.androidpay.databinding.FragmentLoginBinding
import com.example.androidpay.utils.hideKeyboard
import com.example.androidpay.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    private val  mUserViewModel: UserViewModel by viewModels()
    private val args  by navArgs<LoginFragmentArgs>()
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.args = args

        (activity as AppCompatActivity).setSupportActionBar(binding.root.createUserToolbar)


        binding.loginUserButton.setOnClickListener {
            navigateToMain()
        }


        //hide soft keyboard
        hideKeyboard(requireActivity())

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun navigateToMain() {
        if(userNameText.text.toString().trim().isNotEmpty() && emailAddressTextView.text.toString().trim().isNotEmpty()
            && userPhoneNumberTextView.text.toString().trim().isNotEmpty()){
            val newData = com.example.androidpay.data.models.UserData(
                0,userNameText.text.toString(),
                "https://raw.githubusercontent.com/thebatinarkham/android-pay-Api/main/images/046-mummy%20min.png"
                ,emailAddressTextView.text.toString()
                ,userPhoneNumberTextView.text.toString()            )
            mUserViewModel.saveCurrentUser(newData)
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }else{
            Toast.makeText(context,"Please fill out field.",Toast.LENGTH_LONG).show()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}