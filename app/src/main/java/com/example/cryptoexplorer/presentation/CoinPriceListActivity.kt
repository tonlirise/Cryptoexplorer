package com.example.cryptoexplorer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoexplorer.R
import com.example.cryptoexplorer.databinding.ActivityCoinPriceListBinding
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.example.cryptoexplorer.presentation.adapter.CoinInfoAdapter


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
        binding.rvCoinInfo.itemAnimator = null
        adapter.coinClickListener = object : CoinInfoAdapter.CoinClickListener {
            override fun onCoinClickListener(coinPriceInfo: CoinInfoEntity) {
                if(isPortraitOrientation()){
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                }
                else{
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }

        coinViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        coinViewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    fun isPortraitOrientation() = binding.fragmentContainer == null

    fun launchDetailActivity(fromSymbol : String){
        val intent =
            CoinInfoActivity.newIntent(this@CoinPriceListActivity, fromSymbol)
        startActivity(intent)
    }

    fun launchDetailFragment(fromSymbol : String){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinInfoFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}