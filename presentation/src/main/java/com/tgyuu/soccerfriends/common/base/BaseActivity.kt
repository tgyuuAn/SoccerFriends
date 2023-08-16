package com.tgyuu.soccerfriends.common.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(val bindingFactory : (LayoutInflater) -> B) : AppCompatActivity() {

    protected lateinit var binding : B
    private set

    override fun onCreate(savedInstanceState: Bundle?) {
        setSplash()
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        afterBinding()
    }

    protected open fun setSplash(){

    }

    protected open fun afterBinding(){

    }

    fun log(str : String) = Log.d("tgyuu",str)

    fun toast(str : String) = Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
}