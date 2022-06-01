package com.bangkit.capstoneproject.kudaur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
        val mLoginFragment = LoginFragment()
        val mFragmentManager = supportFragmentManager
        val fragment = mFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)
        if (fragment !is LoginFragment) {
            Log.d("MyLoginFragment", "Fragment Name :" + LoginFragment::class.java.simpleName)
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mLoginFragment, LoginFragment::class.java.simpleName)
                .commit()
        }
    }
}