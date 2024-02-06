package com.janob.tape_aos


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentNotifBinding

const val CHANNEL_ID = "Channel Tape"
private const val CHANNEL_NAME = "Tape_AOS"
private const val CHANNEL_DESCRIPTION = "Tape의 Push Notification"


class NotifFragment : Fragment(){

    lateinit var binding: FragmentNotifBinding
    private lateinit var viewModel: NotifViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NotifViewModel::class.java)

        /*val Notiflist = mutableListOf<Alarm>().apply{
            add(Alarm(Alarm.notif_1, "", "", false,true))
            add(Alarm(Alarm.notif_3, "music_play", "님이 회원님의 테이프에 댓글을 작성했습니다.", false, false))
            add(Alarm(Alarm.notif_2, "", "music_play 님이 회원님을 팔로우 합니다.", false, false))
            add(Alarm(Alarm.notif_3, "loveMusic", "님이 회원님의 테이프에 댓글을 작성했습니다.", false, false))

        }

          binding.notif.setOnClickListener {
            Log.d("message", "알림")
            notif()
        }*/

        val notifAdapter = NotifRVAdapter()
        binding.notifRv.adapter=notifAdapter
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.notifData.observe(viewLifecycleOwner){ notifData ->
            notif(notifData)
            notifAdapter.submitList(notifData)
        }

        return binding.root
    }

    /*
        fun getNotifSuccess(){

            notiflist?.let {
                val notifRVAdapter = NotifRVAdapter(it)
                binding.notifRv.adapter = notifRVAdapter
            }
        }

        override fun onStart() {
            super.onStart()
            getNotif()
        }

*/

    fun notif(notifData: List<NotifData>?){  //알림표시
        notifData?.let { data ->
            data.forEach { item ->
                createnotif(item.alarmId, item.alarmContent)
            }
        }
    }

    private fun createnotif(alarmid: Int?, alarmcontent : String?){
        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 26 버전 이상
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                importance
            ).apply {
                // 채널에 다양한 정보 설정
                description = CHANNEL_DESCRIPTION
                /*setShowBadge(true) // 앱아이콘 배지 표시
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)  //알림 기본 소리
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes) //알림 소리
                enableVibration(true) //진동 여부*/
            }

            // 채널을 NotificationManager에 등록
            val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
        }else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(requireContext())
        }

        val intent = Intent(context, NotifFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        Log.d("message", alarmcontent.toString())

        builder.run {
            setSmallIcon(R.drawable.btn_rounded_red)
            setWhen(System.currentTimeMillis())
            setContentTitle("Tape_AOS")
            setContentText(alarmcontent)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(alarmid!!, builder.build())
    }


}
