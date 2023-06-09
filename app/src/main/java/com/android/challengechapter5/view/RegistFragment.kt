package com.android.challengechapter5.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.challengechapter5.R
import com.android.challengechapter5.databinding.FragmentRegistBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistFragment : Fragment() {

    lateinit var binding: FragmentRegistBinding
    private lateinit var sharedRegis: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedRegis = requireContext().getSharedPreferences("keyUser", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegist.setOnClickListener {
            val getUsername = binding.etUname.text.toString()
            val getEmail = binding.etEmail.text.toString()
            val getPass = binding.etPwd.text.toString()
            val getRepeatPass = binding.etConPwd.text.toString()

            val addUser = sharedRegis.edit()
            addUser.putString("user", getUsername)

            if (getUsername.isNotEmpty()&& getEmail.isNotEmpty() && getPass.isNotEmpty() && getRepeatPass.isNotEmpty()){
                if (getPass == getRepeatPass){
                    addUser.apply()
                    firebaseAuth.createUserWithEmailAndPassword(getEmail, getPass).addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registFragment_to_loginFragment)
                        } else{
                            Toast.makeText(context,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(context, "Password tidak Sesuai", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Data Belum Lengkap", Toast.LENGTH_SHORT).show()
            }
        }
    }
}