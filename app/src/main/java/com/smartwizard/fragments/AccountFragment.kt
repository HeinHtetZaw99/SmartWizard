package com.smartwizard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smartwizard.R
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment()
{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(profileIv)
            .asBitmap()
            .load(R.drawable.dummy_user_photo)
            .apply(RequestOptions.circleCropTransform())
            .into(profileIv)
    }


}
