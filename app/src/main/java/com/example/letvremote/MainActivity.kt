package com.example.letvremote

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val UEI_PACKAGE = "com.uei.quicksetsdk.letv"
    private val UEI_SERVICE = "com.uei.control.Service"
    private val IR_FREQ = 38000

    private val CODE_POWER    = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_UP       = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_DOWN     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_LEFT     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_RIGHT    = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_OK       = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_MENU     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,47628)
    private val CODE_EXIT     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,47628)
    private val CODE_CH_UP    = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,47628)
    private val CODE_CH_DOWN  = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,1690,560,560,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,47628)
    private val CODE_VOL_UP   = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_VOL_DOWN = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,1690,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_MUTE     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,1690,560,560,560,560,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,47628)
    private val CODE_RED      = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,1690,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_GREEN    = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,1690,560,47628)
    private val CODE_YELLOW   = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,1690,560,1690,560,1690,560,47628)
    private val CODE_BLUE     = intArrayOf(9000,4500,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,560,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,1690,560,560,560,560,560,560,560,560,560,1690,560,560,560,560,560,560,560,560,560,560,560,1690,560,1690,560,47628)

    private var ueiService: IBinder? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            ueiService = service
            isServiceBound = true
            Toast.makeText(this@MainActivity, "IR Service mifandray!", Toast.LENGTH_SHORT).show()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            ueiService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUeiService()
        setupButtons()
    }

    private fun bindUeiService() {
        try {
            val intent = Intent(UEI_SERVICE).apply {
                component = ComponentName(UEI_PACKAGE, UEI_SERVICE)
            }
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        } catch (e: Exception) {
            Toast.makeText(this, "Hadisoana: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupButtons() {
        mapOf(
            R.id.btnPower   to CODE_POWER,
            R.id.btnUp      to CODE_UP,
            R.id.btnDown    to CODE_DOWN,
            R.id.btnLeft    to CODE_LEFT,
            R.id.btnRight   to CODE_RIGHT,
            R.id.btnOk      to CODE_OK,
            R.id.btnMenu    to CODE_MENU,
            R.id.btnExit    to CODE_EXIT,
            R.id.btnChUp    to CODE_CH_UP,
            R.id.btnChDown  to CODE_CH_DOWN,
            R.id.btnVolUp   to CODE_VOL_UP,
            R.id.btnVolDown to CODE_VOL_DOWN,
            R.id.btnMute    to CODE_MUTE,
            R.id.btnRed     to CODE_RED,
            R.id.btnGreen   to CODE_GREEN,
            R.id.btnYellow  to CODE_YELLOW,
            R.id.btnBlue    to CODE_BLUE
        ).forEach { (id, code) ->
            findViewById<Button>(id).setOnClickListener { sendIR(code) }
        }
    }

    private fun sendIR(pattern: IntArray) {
        if (!isServiceBound || ueiService == null) {
            Toast.makeText(this, "IR Service tsy mifandray", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            try {
                data.writeInterfaceToken("com.uei.control.IUEIControl")
                data.writeInt(IR_FREQ)
                data.writeIntArray(pattern)
                ueiService!!.transact(1, data, reply, 0)
                reply.readException()
            } finally {
                data.recycle()
                reply.recycle()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Hadisoana: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) unbindService(serviceConnection)
    }
}
