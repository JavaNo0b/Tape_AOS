package com.janob.tape_aos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentNotifBinding
class NotifFragment : Fragment() {

    lateinit var binding: FragmentNotifBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifBinding.inflate(inflater, container, false)
        val Notiflist = mutableListOf<Alarm>().apply{
            add(Alarm(Alarm.notif_1, "", "", false,true))
            add(Alarm(Alarm.notif_3, "music_play", "님이 회원님의 테이프에 댓글을 작성했습니다.", false, false))
            add(Alarm(Alarm.notif_2, "", "music_play 님이 회원님을 팔로우 합니다.", false, false))
            add(Alarm(Alarm.notif_3, "loveMusic", "님이 회원님의 테이프에 댓글을 작성했습니다.", false, false))

        }
        val notifRVAdapter = NotifRVAdapter(Notiflist)
        binding.notifRv.adapter=notifRVAdapter //리사이클러뷰 어댑터와 연결
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }



}
