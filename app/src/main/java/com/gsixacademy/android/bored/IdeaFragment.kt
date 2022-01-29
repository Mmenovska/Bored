package com.gsixacademy.android.bored

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gsixacademy.android.bored.api.BoredAPI
import com.gsixacademy.android.bored.api.ServiceBuilder
import com.gsixacademy.android.bored.models.ActivityResponse
import kotlinx.android.synthetic.main.activity_idea.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IdeaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_idea,container,false)
        val request = ServiceBuilder.buildService(BoredAPI::class.java)
        val call = request.getRandomActivity()
        call.enqueue(object : Callback <ActivityResponse>{
            override fun onResponse(
                call: Call<ActivityResponse>,
                response: Response<ActivityResponse>
            ) {
                if (response.isSuccessful){
                    val activityResponse = response.body()
                    if (activityResponse != null){
                        text_activity.text = activityResponse.activity
                        text_accessibility.text = activityResponse.accessibility.toString()
                        text_type.text = activityResponse.type
                        text_participants.text = activityResponse.participants.toString()
                        text_price.text = activityResponse.price.toString()
                        if (!activityResponse.link.isNullOrEmpty()){
                            text_link.visibility = View.VISIBLE
                            tvLink.visibility = View.VISIBLE
                            text_link.text = activityResponse.link
                        } else {
                            text_link.visibility = View.GONE
                            tvLink.visibility = View.GONE
                        }
                        text_link.setOnClickListener {
                            var realUrl = if (activityResponse.link!!.startsWith("http"))activityResponse.link else "https://${activityResponse.link}"
                            var urlIntent = Intent(Intent.ACTION_VIEW,Uri.parse(realUrl))
                            startActivity(urlIntent)

                        }

                        btnNext.setOnClickListener {
                            call.clone().enqueue(object : Callback<ActivityResponse>{
                                override fun onResponse(
                                    call: Call<ActivityResponse>,
                                    response: Response<ActivityResponse>
                                ) {
                                    if (response.isSuccessful){
                                        val newActivity = response.body()
                                        if (newActivity != null){
                                            text_activity.text = newActivity.activity
                                            text_accessibility.text = newActivity.accessibility.toString()
                                            text_type.text = newActivity.type
                                            text_participants.text = newActivity.participants.toString()
                                            text_price.text = newActivity.price.toString()
                                            if (!newActivity.link.isNullOrEmpty()){
                                                tvLink.visibility = View.VISIBLE
                                                text_link.visibility = View.VISIBLE
                                                text_link.text = newActivity.link
                                            } else {
                                                tvLink.visibility = View.GONE
                                                text_link.visibility = View.GONE
                                            }
                                            text_link.setOnClickListener {
                                                val realUrl = if (newActivity.link!!.startsWith("http"))newActivity.link else "https:// ${newActivity.link}"
                                                val urlIntent = Intent (Intent.ACTION_VIEW, Uri.parse(realUrl))
                                                startActivity(urlIntent)

                                            }
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ActivityResponse>, t: Throwable) {
                                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                                }

                            })
                        }



                    }
                }

            }

            override fun onFailure(call: Call<ActivityResponse>, t: Throwable) {
                Toast.makeText(activity,"Error!", Toast.LENGTH_SHORT).show()
            }

        })
        return view

    }






}