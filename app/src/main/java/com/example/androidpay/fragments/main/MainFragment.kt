package com.example.androidpay.fragments.main

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.androidpay.R
import com.example.androidpay.data.models.TransactionType
import com.example.androidpay.data.models.UserData
import com.example.androidpay.databinding.FragmentMainBinding

import com.example.androidpay.fragments.SharedViewModel
import com.example.androidpay.fragments.main.adapter.transaction.TransactionAdapter
import com.example.androidpay.fragments.main.adapter.user.ItemClickListener
import com.example.androidpay.fragments.main.adapter.user.UserAdapter
import com.example.androidpay.utils.hideKeyboard
import com.example.androidpay.viewmodels.TransactionViewModel
import com.example.androidpay.viewmodels.UserViewModel


class MainFragment : Fragment(), ItemClickListener {

    private val mUserAdapter : UserAdapter by lazy { UserAdapter(requireContext(),this) }
    private val mTransactionAdapter:TransactionAdapter by lazy {TransactionAdapter()}
    private val mUserViewModel:UserViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTransactionViewModel:TransactionViewModel by viewModels()

    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        //observe live data
        mUserViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            data -> mSharedViewModel.checkIfUserDatabaseEmpty(data)
            mUserAdapter.setData(data)
        })
        mTransactionViewModel.getTransactionWithUserData.observe(viewLifecycleOwner, Observer {
            data -> mTransactionAdapter.setData(data)
            if(!data.isNullOrEmpty()){
                binding.lastTransactionDetails.text =  data.last().transactionDescription
                binding.transactionAmountTextView.text = data.last().transactionAmount.toString()

                if(data.last().transactionType == TransactionType.SEND){
                    binding.lastTransactionRequest.visibility = View.GONE
                }else{
                    binding.lastTransactionPay.visibility = View.GONE
                }
            }
        })

        (activity  as AppCompatActivity).setSupportActionBar(binding.mainToolbar)

        setUpRecyclerView()


        //Set menu
        setHasOptionsMenu(true)

        //hide soft keyboard
        hideKeyboard(requireActivity())

        binding.sendButton.setOnClickListener {
            Toast.makeText(context,"Select User",Toast.LENGTH_LONG).show()
            binding.expandRecycleViewImage.callOnClick()
        }

        binding.requestButton.setOnClickListener{
            Toast.makeText(context,"Select User",Toast.LENGTH_LONG).show()
            binding.expandRecycleViewImage.callOnClick()
        }

        return binding.root
    }


    private fun setUpRecyclerView() {
        val recyclerViewForUser = binding.recyclerViewForUser


        recyclerViewForUser.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerViewForUser.adapter = mUserAdapter

        binding.expandRecycleViewImage.setOnClickListener {
            binding.recyclerViewForUser.visibility = View.VISIBLE
            binding.selectedItemImage.visibility = View.GONE
            binding.closeRecycleViewImage.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        binding.closeRecycleViewImage.setOnClickListener{
            binding.expandRecycleViewImage.visibility = View.VISIBLE
            binding.recyclerViewForUser.visibility = View.GONE
            it.visibility = View.GONE
        }

        //        recyclerView.addItemDecoration(OverlapDecoration())
        recyclerViewForUser.setHasFixedSize(true)

        val recycleViewForTransactionHistory = binding.recyclerViewForTransactionHistory
        recycleViewForTransactionHistory.adapter = mTransactionAdapter
        recycleViewForTransactionHistory.layoutManager = LinearLayoutManager(activity)
        recycleViewForTransactionHistory.setHasFixedSize(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_avtar_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.user_avtar -> navigateToCreateUser(mUserViewModel.getCurrentUser())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToCreateUser(currentUserData: UserData) {
        val action = MainFragmentDirections.actionMainFragmentToLoginFragment(currentUserData)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class OverlapDecoration : ItemDecoration() {
        private val vertOverlap = -40

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val itemPosition = parent.getChildAdapterPosition(view)

            outRect.set(0, 0, vertOverlap, 0)

        }

        companion object {
            private const val vertOverlap = -40
        }
    }

    override fun itemViewListClicked(position: Int) {
        binding.selectedItemImage.visibility = View.GONE
        val options = RequestOptions().placeholder(R.drawable.vector)
            .error(R.drawable.vector)

        Glide.with(requireContext()).setDefaultRequestOptions(options)
            .load(mUserAdapter.dataList[position].image_url).apply(RequestOptions.circleCropTransform()).
            into(binding.selectedItemImage)

        binding.selectedItemImage.visibility = View.VISIBLE
        binding.recyclerViewForUser.visibility = View.GONE
        binding.expandRecycleViewImage.visibility = View.VISIBLE
        binding.closeRecycleViewImage.visibility = View.GONE

        Toast.makeText(context,mUserAdapter.dataList[position].title,Toast.LENGTH_LONG).show()

        binding.sendButton.setOnClickListener {
            navigateToDetails(position)
        }

        binding.requestButton.setOnClickListener{
            navigateToRequest(position)
        }
    }

    private fun navigateToRequest(position: Int) {
        if(binding.amountEditText.text.isNotEmpty()){
            val action = MainFragmentDirections.actionMainFragmentToRequestFragment(
                binding.amountEditText.text.toString(),
                TransactionType.REQUEST,
                mUserAdapter.dataList[0],
                mUserAdapter.dataList[position]
            )
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(),"Please Enter Amount",Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToDetails(position: Int){
        if(binding.amountEditText.text.isNotEmpty()){
            val action = MainFragmentDirections.actionMainFragmentToPaymentSendFragment(
                binding.amountEditText.text.toString(),
                TransactionType.SEND,
                mUserAdapter.dataList[0],
                mUserAdapter.dataList[position]
            )
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(),"Please Enter Amount",Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onPrepareOptionsMenu(menu: Menu) {
        if(mUserViewModel.getCurrentUser()  != null){
            mSharedViewModel.inflateImageToMenu(mUserViewModel.getCurrentUser().image_url,menu)
        }
        super.onPrepareOptionsMenu(menu)
    }

}