package com.payot.pos.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.payot.pos.Activity.LoginActivity
import com.payot.pos.Activity.MainActivity

class BootApplicationRunningReciver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                val intent = Intent(context, LoginActivity::class.java)
                context?.startActivity(intent)

                Toast.makeText(context, "부팅이 완료되었습니다", Toast.LENGTH_SHORT).show()
//                infinixAppLaunch(context)
            }
            Intent.ACTION_INSTALL_PACKAGE -> {

            }
        }
    }

    fun infinixAppLaunch(context: Context?) {
        val manager = context?.packageManager

        val intent = manager?.getLeanbackLaunchIntentForPackage("kr.infinix.hpay.appposw")
        context?.startActivity(intent)
    }
}