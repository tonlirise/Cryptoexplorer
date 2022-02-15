package com.example.cryptoexplorer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoexplorer.databinding.FragmentCoinInfoBinding
import com.squareup.picasso.Picasso

class CoinInfoFragment : Fragment() {
    lateinit var viewModel: CoinViewModel

    private var _banding: FragmentCoinInfoBinding? = null
    val binding: FragmentCoinInfoBinding
        get() = _banding ?: throw RuntimeException("FragmentCoinInfoBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _banding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[CoinViewModel::class.java]

        viewModel.getPriceInfoAboutCoin(getFSym()).observe(viewLifecycleOwner) {
            with(binding) {
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _banding = null
    }

    fun getFSym() = requireArguments().getString(EXTRA_FSYM, EMPTY_FSYM)

    companion object {
        private const val EMPTY_FSYM = ""
        private const val EXTRA_FSYM = "FSYM"

        fun newInstance(fsym: String) = CoinInfoFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_FSYM, fsym)
            }
        }
    }
}