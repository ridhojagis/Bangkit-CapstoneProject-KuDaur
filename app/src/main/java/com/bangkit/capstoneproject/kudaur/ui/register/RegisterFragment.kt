package com.bangkit.capstoneproject.kudaur.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bangkit.capstoneproject.kudaur.R
import com.bangkit.capstoneproject.kudaur.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel(view)
        setupAction()
    }

    private fun setViewModel(view: View) {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.isRegisterSuccess.observe(viewLifecycleOwner) {
            if (it) toLogin(true)
        }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                showToast(toastText)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setupAction() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.emailEditTextLayout.error =
                    if (!isValidEmail(s)) getString(R.string.invalid_email) else null
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.passwordEditTextLayout.error =
                    if (s.count() < 6) getString(R.string.invalid_password) else null
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

        // action when login button clicked
        binding.buttonRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password.length < 6 -> {
                    binding.passwordEditTextLayout.error = getString(R.string.invalid_password)
                }
                else ->
//                    view?.findNavController()
//                    ?.navigate(R.id.action_registerFragment_to_loginFragment)
                    viewModel.register(name, email, password)
            }
        }
        binding.tvLogin.setOnClickListener{
            toLogin(false)
        }
    }

    private fun toLogin(isRegistered: Boolean) {
        val toLoginFragment = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        if (isRegistered) {
            toLoginFragment.email = binding.emailEditText.text.toString()
            toLoginFragment.password = binding.passwordEditText.text.toString()
        }
        view?.findNavController()?.navigate(toLoginFragment)
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.nameEditText.isEnabled = false
            binding.emailEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
            binding.buttonRegister.isEnabled = false
            binding.tvLogin.isEnabled = false
            binding.pbRegister.visibility = View.VISIBLE
        } else {
            binding.nameEditText.isEnabled = true
            binding.emailEditText.isEnabled = true
            binding.passwordEditText.isEnabled = true
            binding.buttonRegister.isEnabled = true
            binding.tvLogin.isEnabled = true
            binding.pbRegister.visibility = View.GONE
        }
    }
}