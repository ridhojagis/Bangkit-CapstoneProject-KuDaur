package com.bangkit.capstoneproject.kudaur

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        playAnimation()
    }

    private fun playAnimation() {

        val emailTextView = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(480)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(480)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(480)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(480)
        val login = ObjectAnimator.ofFloat(binding.buttonMasuk, View.ALPHA, 1f).setDuration(480)
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