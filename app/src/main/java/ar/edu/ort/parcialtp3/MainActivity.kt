package ar.edu.ort.parcialtp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onBackPressed() {
        val fragmentRegister = this.supportFragmentManager.findFragmentById(R.id.registerFragment)
        (fragmentRegister as? IOnBackPressed)?.onBackPressed()?.not()?.let { isCanceled: Boolean ->
            if (!isCanceled) super.onBackPressed()
        }
        val fragmentHome = this.supportFragmentManager.findFragmentById(R.id.homeFragment)
        (fragmentHome as? IOnBackPressed)?.onBackPressed()?.not()?.let { isCanceled: Boolean ->
            if (!isCanceled) super.onBackPressed()
        }
    }
}