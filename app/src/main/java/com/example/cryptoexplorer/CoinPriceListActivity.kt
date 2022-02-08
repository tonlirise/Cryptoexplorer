package com.example.cryptoexplorer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoexplorer.adapter.CoinInfoAdapter
import com.example.cryptoexplorer.databinding.ActivityCoinPriceListBinding
import com.example.cryptoexplorer.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {
    private var _binding: ActivityCoinPriceListBinding? = null
    private val binding: ActivityCoinPriceListBinding
        get() = _binding ?: throw RuntimeException("ActivityCoinPriceListBinding is null")

    lateinit var coinViewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCoinPriceListBinding.inflate(LayoutInflater.from(this))

        _binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter()
        binding.rvCoinInfo.adapter = adapter
        adapter.coinClickListener = object : CoinInfoAdapter.CoinClickListener {
            override fun onCoinClickListener(coinPriceInfo: CoinPriceInfo) {
                val intent =
                    CoinInfoActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }

        coinViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        coinViewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}