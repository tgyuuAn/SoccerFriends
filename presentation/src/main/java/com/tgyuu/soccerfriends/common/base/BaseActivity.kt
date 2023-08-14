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

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setSplash()
        super.onCreate(savedInstanceState, persistentState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    protected open fun setSplash(){

    }

    fun log(str : String) = Log.d("tgyuu",str)

    fun toast(str : String) = Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
}