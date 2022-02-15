package com.example.cryptoexplorer.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoexplorer.R
import com.example.cryptoexplorer.databinding.ActivityCoinInfoBinding

class CoinInfoActivity : AppCompatActivity() {
    private var _banding: ActivityCoinInfoBinding? = null
    val binding: ActivityCoinInfoBinding
        get() = _banding ?: throw RuntimeException("ActivityCoinInfoBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _banding = ActivityCoinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fsym = intent.extras?.getString(EXTRA_FSYM)
        if (fsym == null) {
            finish()
            return
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CoinInfoFragment.newInstance(fsym))
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _banding = null
    }

    companion object {
        private const val EXTRA_FSYM = "FSYM"

        fun newIntent(context: Context, fsym: String): Intent {
            val intent = Intent(context, CoinInfoActivity::class.java)
            intent.putExtra(EXTRA_FSYM, fsym)
            return intent
        }
    }
}