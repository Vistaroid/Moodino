package com.iranmobiledev.moodino.ui.splashScreen

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.ActivitySplashBinding
import com.iranmobiledev.moodino.ui.MainActivity
import com.iranmobiledev.moodino.ui.more.pinLock.PinLockDialog
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.LANGUAGE
import com.iranmobiledev.moodino.utlis.MyContextWrapper
import com.iranmobiledev.moodino.utlis.PERSIAN
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*
import java.util.concurrent.Executor

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    val viewModel : SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch{
            delay(2500)
            checkLogin()
        }
    }

    fun checkLogin(){
        if (viewModel.checkLogin()) {
            binding.lSplashLogin.visibility = View.VISIBLE
            binding.lSplashLoading.visibility = View.GONE
            showLogin()
        }
        else endSplash()
    }

    fun showLogin(){

        setBioMetric()
        val authenticateBiometric = if (Build.VERSION.SDK_INT < 30) {
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.confirm_fingerPrint))
                .setDeviceCredentialAllowed(true)
                .setConfirmationRequired(false)
                .build()
        } else
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.confirm_fingerPrint))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .setConfirmationRequired(false)
                .build()


        if (!viewModel.checkFingerPrint()) binding.btnSplashFingerprint.visibility = View.GONE

        binding.btnSplashPinlock.setOnClickListener {
            showPin()
        }

        binding.btnSplashFingerprint.setOnClickListener {
            biometricPrompt.authenticate(authenticateBiometric)
        }

        if (viewModel.checkFingerPrint()) binding.btnSplashFingerprint.performClick()
        else binding.btnSplashPinlock.performClick()
    }

    fun showPin(){
        PinLockDialog("" , object : PinLockDialog.PinLockDialogListener{
            override fun pin(pin: String) {
                if (pin == viewModel.pin()) endSplash()
                else{
                    Toast.makeText(this@SplashActivity , getString(R.string.error_pin) , Toast.LENGTH_SHORT).show()
                    showPin()
                }
            }
        }).show(supportFragmentManager , null)
    }

    fun setBioMetric(){
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this , executor ,
            object :BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    endSplash()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@SplashActivity , getString(R.string.fingerPrint_error) , Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@SplashActivity , getString(R.string.fingerPrint_error) , Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun endSplash(){
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        val shared = newBase?.getSharedPreferences("sharedPref", MODE_PRIVATE)
        val lan = when (shared?.getInt(LANGUAGE, PERSIAN)) {
            ENGLISH -> "en"
            PERSIAN -> "fa"
            else -> "en"
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, Locale(lan).toString()))
    }
}