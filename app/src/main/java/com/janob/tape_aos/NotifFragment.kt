package com.janob.tape_aos


import NotifRVAdapter
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.FragmentNotifBinding

const val CHANNEL_ID = "Channel Tape"
private const val CHANNEL_NAME = "Tape_AOS"
private const val CHANNEL_DESCRIPTION = "Tape의 Push Notification"


class NotifFragment : Fragment(){

    lateinit var binding: FragmentNotifBinding
    //private lateinit var viewModel: NotifViewModel
    lateinit var notifAdapter: NotifRVAdapter
    lateinit var recyclerView: RecyclerView

    //뷰모델
    private val viewModel: NotifViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifBinding.inflate(inflater, container, false)

        Log.d("message", "hi")
        viewModel.viewmodel(getJwt().toString())

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



        /*viewModel.fetchAlarmAll().observe(viewLifecycleOwner, Observer{ AlarmResultDTO ->

            Log.d("message", "hi")
            Log.d("message", AlarmResultDTO.toString())
            AlarmResultDTO.data?.let { alarmInnerDTO ->
                notif(alarmInnerDTO)
                notifAdapter.submitList(alarmInnerDTO)
                Log.d("message", "hi")
                Log.d("message", alarmInnerDTO.toString())
            }
            Log.d("message", AlarmResultDTO.toString())
        })*/
/*

        notifAdapter = NotifRVAdapter()
        binding.notifRv.adapter=notifAdapter
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
*/

        notifAdapter = NotifRVAdapter(emptyList())
        binding.notifRv.adapter=notifAdapter
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        notifAdapter.setMyItemClickListener(object  : NotifRVAdapter.MyItemClickListener{
            override fun onItemClick(item: AlarmInnerDTO) {
                if(item.alarmType=="좋아요"){    //좋아요 누른 테이프로 가기
                    moveAlbumFragment(item.tapeId)
                }
                else if(item.alarmType=="댓글"){  //댓글 입력한 테이프로 가기
                    moveAlbumFragment(item.tapeId)
                }
                else{
                    moveOtherprofileFragment(item.receiverNickname)
                }
            }

        })



        return binding.root
    }

    private fun getJwt(): String?{

        val spf = context?.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)!!
        val token = spf.getString("jwt", null)
        val bToken = "Bearer $token"
        return bToken
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("message", "hi")
        //viewModel.fetchAlarmAll()
        viewModel.NotifLiveData.observe(viewLifecycleOwner, Observer {
            alarmResultDTO ->
            alarmResultDTO?.data?.let { data ->
                Log.d("message", alarmResultDTO.data.toString())
                notif(alarmResultDTO.data)
                notifAdapter = NotifRVAdapter(alarmResultDTO.data)
                Log.d("message", alarmResultDTO.data.toString())

            }
        })

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

    fun notif(notifData: List<AlarmInnerDTO>?){  //알림표시
        notifData?.let { data ->
            data.forEach { item ->
                createnotif(item.tapeId, item.alarmType, item.receiverNickname)
                Log.d("message", "hi")
            }
        }
    }

    private fun createnotif(tapeId: Int, alarmType : String, receiverNickname: String){
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

        val notifcontent: String = Notifcontent(receiverNickname, alarmType)
        val intent = Intent(requireContext(), NotifFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)


        builder.run {
            setSmallIcon(R.drawable.btn_rounded_red)
            setWhen(System.currentTimeMillis())
            setContentTitle("Tape_AOS")
            setContentText(notifcontent)
            setContentIntent(pendingIntent)
        }

        val albumid = 0
        notificationManager.notify(0, builder.build())
        //albumid+1
    }


    private fun Notifcontent(receiverNickname: String, alarmType: String) : String{
        return when (alarmType){
            "좋아요" -> receiverNickname+"님이 회원님의 Tape를 좋아합니다."
            "팔로우" -> receiverNickname+"님이 회원님을 팔로우 합니다."
            "댓글" -> receiverNickname+"님이 회원님의 테이프에 댓글을 작성했습니다."
            else -> throw IllegalArgumentException("없는 알림 메시지")
        }
    }

    private fun moveAlbumFragment(tapeId: Int){  //tape_id 갖고 테이프로 이동
//        (context as MainActivity).supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fm, AlbumFragment().apply {
//                arguments = Bundle().apply {
//                    val gson = Gson()
//                    val notifJson = gson.toJson(tapeId)
//                    putString("notif", notifJson)
//                }
//            })
//            .commitAllowingStateLoss()
//

        val bundle = Bundle().apply {
            putInt("tape_id", tapeId)
        }
        val albumFragment = AlbumFragment().apply {
            arguments = bundle
        }
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, albumFragment)
            .addToBackStack(null)   //뒤로가기 버튼 클릭시 notif
            .commit()
    }


    private fun moveOtherprofileFragment(receiverNickname: String){
        /*(context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, OtherprofileFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val notifJson = gson.toJson(receiverNickname)
                    putString("notif", notifJson)
                }
            })
            .commitAllowingStateLoss()*/
    }
}

/*
class NotifAdapter(var dataList: List<AlarmInnerDTO>):RecyclerView.Adapter<NotifViewHolder>(){

    interface MyItemClickListener{ fun onItemClick(item: AlarmInnerDTO) }


    private lateinit var ItemClickListener: MyItemClickListener

    fun setMyItemClickListener(Listener: MyItemClickListener){
        ItemClickListener = Listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val binding = ItemNotif2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        val context = parent.context
        return NotifViewHolder(binding,context)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.itemView.setOnClickListener{
            ItemClickListener.onItemClick(dataList[position])
        }
    }


}


class NotifViewHolder(val binding: ItemNotif2Binding, val context: Context):RecyclerView.ViewHolder(binding.root){

    lateinit var data : AlarmInnerDTO

    fun bind(Data: AlarmInnerDTO){

        this.data = Data
        binding.itemNotif2TextTv1.text = data.receiverNickname
        binding.itemNotif2TextTv2.text = Notifcontent2(data.alarmType)

    }

    private fun Notifcontent2(alarmType: String) : String{
        return when (alarmType){
            "좋아요" -> " 님이 회원님의 Tape를 좋아합니다."
            "팔로우" -> " 님이 회원님을 팔로우 합니다."
            "댓글" -> " 님이 회원님의 테이프에 댓글을 작성했습니다."
            else -> throw IllegalArgumentException("없는 알림 메시지")
        }
    }
}
*/