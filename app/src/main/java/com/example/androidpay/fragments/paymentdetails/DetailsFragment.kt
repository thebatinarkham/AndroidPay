package com.example.androidpay.fragments.paymentdetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidpay.R
import com.example.androidpay.data.models.TransactionType
import com.example.androidpay.databinding.FragmentDetailrequestBinding
import com.example.androidpay.databinding.FragmentDetailsendBinding
import com.example.androidpay.viewmodels.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_send.view.*

class DetailsFragment : Fragment() {
    private lateinit var _binding: ViewDataBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private var _bindingSend:FragmentDetailsendBinding?  = null
    private var _bindingRequest: FragmentDetailrequestBinding? = null
    private val bindingSend get() = _bindingSend!!
    private val bindingRequest get() = _bindingRequest!!
    private val mTransactionViewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutForView(inflater,container)

        (activity  as AppCompatActivity).setSupportActionBar(view.root.detailsToolbar)

        //Set menu
        setHasOptionsMenu(true)

        return if(args.transactionWithUserData.transactionType == TransactionType.SEND){
            bindingSend.root
        }else {
            bindingRequest.root
        }
    }

    private fun layoutForView(inflater: LayoutInflater, container: ViewGroup?) :ViewDataBinding{
        if(args.transactionWithUserData.transactionType == TransactionType.SEND){
            _bindingSend = FragmentDetailsendBinding.inflate(inflater,container,false)
            _bindingSend?.args = args
            return _bindingSend as @NonNull FragmentDetailsendBinding

        }else {
            _bindingRequest = FragmentDetailrequestBinding.inflate(inflater,container,false)
            _bindingRequest?.args = args
            return _bindingRequest as @NonNull FragmentDetailrequestBinding
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteTransaction -> deleteTransaction()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTransaction() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){ _,_ ->
            mTransactionViewModel.searchTransactionById(args.transactionWithUserData.transactionId)
                .observe(this, Observer {
                        it?.let {
                            mTransactionViewModel.deleteItem(it)
                            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
                            Toast.makeText(requireContext(),"Transaction Deleted", Toast.LENGTH_LONG).show()
                        } })}
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete Current Transaction.. ?" )
        builder.setMessage("Are you sure you want to remove transaction?")
        builder.create().show()

//      Toast.makeText(requireContext(),findNavController().currentDestination.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingSend = null
        _bindingRequest = null
    }


}


