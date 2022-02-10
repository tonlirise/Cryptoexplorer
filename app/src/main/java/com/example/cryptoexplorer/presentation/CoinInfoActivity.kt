package com.example.cryptoexplorer.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoexplorer.databinding.ActivityCoinInfoBinding
import com.squareup.picasso.Picasso
import java.lang.RuntimeException

class CoinInfoActivity : AppCompatActivity() {
    lateinit var viewModel: CoinViewModel

    private var _banding : ActivityCoinInfoBinding? = null
    val binding : ActivityCoinInfoBinding
        get() = _banding?: throw RuntimeException("ActivityCoinInfoBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _banding = ActivityCoinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        val fsym = intent.extras?.getString(EXTRA_FSYM)
        if (fsym == null){
            finish()
            return
        }
        viewModel.getPriceInfoAboutCoin(fsym).observe(this){
            with(binding) {
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.getFormattedTime()
                Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
            }
        }
    }

    companion object{
        private const val EXTRA_FSYM = "FSYM"

        fun newIntent(context: Context, fsym : String) : Intent{
            val intent = Intent(context, CoinInfoActivity::class.java)
            intent.putExtra(EXTRA_FSYM,fsym)
            return intent
        }
    }
}