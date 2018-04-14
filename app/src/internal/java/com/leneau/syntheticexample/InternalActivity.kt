package com.leneau.syntheticexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.internal.activity_internal.*

class InternalActivity : AppCompatActivity() {

    internal lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal)

        text = internal_text
    }
}
