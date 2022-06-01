package com.bangkit.capstoneproject.kudaur

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bangkit.capstoneproject.kudaur.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        playAnimation()
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
                    if (s.count() < 8) getString(R.string.invalid_password) else null
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

        // action when login button clicked
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password.length < 8 -> {
                    binding.passwordEditTextLayout.error = getString(R.string.invalid_password)
                }
                else -> Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            }
        }

        binding.buttonDaftar.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun playAnimation() {

        val emailTextView = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(480)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(480)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(480)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(480)
        val login = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(480)
        val daftar = ObjectAnimator.ofFloat(binding.buttonDaftar, View.ALPHA, 1f).setDuration(480)

        val together = AnimatorSet().apply {
            playTogether(login, daftar)
        }

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                together
            )
            startDelay = 480
            start()
        }
    }
}