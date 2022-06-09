package com.bangkit.capstoneproject.kudaur.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bangkit.capstoneproject.kudaur.R
import com.bangkit.capstoneproject.kudaur.data.preferences.SessionModel
import com.bangkit.capstoneproject.kudaur.data.preferences.SessionPreference
import com.bangkit.capstoneproject.kudaur.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mSessionPreference: SessionPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel(view)
        setupAction()
        playAnimation()
    }

    private fun setViewModel(view: View) {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.session.observe(viewLifecycleOwner) {
            login(it)
        }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                showToast(toastText)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mSessionPreference = SessionPreference(view.context)
        if (mSessionPreference.getSession() != null) {
            view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
            activity?.finish()
        }

        LoginFragmentArgs.fromBundle(arguments as Bundle).email?.let {
            binding.emailEditText.setText(it)
        }
        LoginFragmentArgs.fromBundle(arguments as Bundle).password?.let {
            binding.passwordEditText.setText(it)
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
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = getString(R.string.empty_email)
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.empty_password)
                }
                password.length < 6 -> {
                    binding.passwordEditTextLayout.error = getString(R.string.invalid_password)
                }
                else -> {
                    viewModel.login(email, password)
                }
            }
        }

        binding.buttonRegister.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
        )
    }

    private fun login(session: SessionModel) {
        mSessionPreference.setSession(session)
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
        activity?.finish()
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
        val daftar = ObjectAnimator.ofFloat(binding.buttonRegister, View.ALPHA, 1f).setDuration(480)

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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.emailEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
            binding.buttonLogin.isEnabled = false
            binding.buttonRegister.isEnabled = false
            binding.pbLogin.visibility = View.VISIBLE
        } else {
            binding.emailEditText.isEnabled = true
            binding.passwordEditText.isEnabled = true
            binding.buttonLogin.isEnabled = true
            binding.buttonRegister.isEnabled = true
            binding.pbLogin.visibility = View.GONE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }
}