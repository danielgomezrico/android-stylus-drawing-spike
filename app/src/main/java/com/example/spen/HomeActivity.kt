package com.example.spen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val TAG = "SpenRemoteSample"

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.button_clean).setOnClickListener {
            findViewById<DrawingView>(R.id.drawing_view).clear()
        }

        findViewById<SwitchCompat>(R.id.switch_hand_draw).setOnCheckedChangeListener { _, isChecked ->
            findViewById<DrawingView>(R.id.drawing_view).allowHandDrawing = isChecked
        }
    }
}
