package com.janob.tape_aos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.janob.tape_aos.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

private const val TAG1 = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var tapeData : List<Tape>
    lateinit var tapeReplyData : List<Reply>
    lateinit var songData : List<Song>
    lateinit var includedSongData : List<IncludedSong>
    lateinit var userData : List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //api 연동시 삭제
        //inputDummyAlbum()
        inputDummyReply()
        inputDummySong()//양희연
        inputDummySongs()//박성현
        inputDummyUser()

        initBottomNavigation()

        SongRepository.initialize(this)
        IncludedSongRepository.initialize(this)
        //TapeRepository.initialize(this)



    }



    //양희연
    private fun inputDummySong(){
        val database = TapeDatabase.Instance(this)
        val songDao = database.songDao()

        songDao.deleteAll()

        songDao.insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "NO PAIN",
                "실리카겔",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "Winter Blossom (feat. SAAY)",
                "펀치넬로",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "사랑이었나봐",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "빈집",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "Love Lee",
                "AKMU",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "후라이의 꿈",
                "AKMU",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "후라이의 꿈",
                "악동뮤지션",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "밤편지",
                "아이유",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
    }
    //박성현
    private fun inputDummySongs(){
        val songDB = TapeDatabase.Instance(this)
        val includedSongDao = songDB.IncludedSongDao()

        includedSongDao.deleteAll()

        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3th Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                1,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                2,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Love Part 1",
                "와르르",
                "Colde",
                R.drawable.album_2,
                2,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Love Part 1",
                "와르르",
                "Colde",
                R.drawable.album_2,
                2)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                3,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Love Part 1",
                "와르르",
                "Colde",
                R.drawable.album_2,
                3,
                true)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                3,
                true)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Love Part 1",
                "와르르",
                "Colde",
                R.drawable.album_2,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("MyWorld - The 3rd Mini Album",
                "Thirsty",
                "aepsa",
                R.drawable.album_3,
                4,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                5,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                6,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album",
                "Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                7,
                false)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("ISTJ-The 3rd Album","Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                8,
                false)
        )

    }
    private fun inputDummyReply(){
        val database = TapeDatabase.Instance(this)
        val replyDao = database.replyDao()

        replyDao.deleteAll()

        replyDao.insert(
            Reply(1,
                "너무 좋아요ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ가사 너무 아련...")
        )
        replyDao.insert(
            Reply(2,
                "노래 너무 좋아요❤️")
        )
        replyDao.insert(
            Reply(3,
                "이 조합 미쳤다,,완벽한 겨울노래")
        )
        replyDao.insert(
            Reply(4,
                "목소리 너무 신비스럽게 이쁘네")
        )
        replyDao.insert(
            Reply(5,
                "들을 때마다 더 좋아지냐 마음이 따뜻해지네")
        )
        replyDao.insert(
            Reply(6,
                "안볼수가 없는 조합")
        )
        replyDao.insert(
            Reply(7,
                "우와... 겨울 분위기 대박 노래 너무 좋아요ㅠ")
        )
        replyDao.insert(
            Reply(8,
                "두 분의 목소리 진짜 악기같다... 넘 좋아요❤")
        )

    }
    private fun inputDummyUser(){
        val tapeDB = TapeDatabase.Instance(this)
        userData = tapeDB.userDao().getAll()


        if(userData.isNotEmpty())
            return

        var followerList : List<String> = arrayListOf("music_play", "k_pop_lover", "user1", "user2", "user3", "user4", "user5", "user6", "user7", "user", "user123")
        var followingList : List<String> = arrayListOf("music_play", "k_pop_lover", "user1", "user2", "user3", "user4", "user5", "user6", "user7", "user", "user123")
        tapeDB.userDao().insert(
            User(1,
                R.drawable.user_profile_img,
                "Min_SEO",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "music_play",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "k_pop_lover",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user1",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user2",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user3",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user4",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user5",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user6",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user7",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user",
                "user comment",
                followerList,
                followingList,
                tapeData)
        )
        tapeDB.userDao().insert(
            User(0,
                R.drawable.albumcover_5,
                "user123",
                "user123 comment",
                followerList,
                followingList,
                tapeData)
        )
    }
    private fun initBottomNavigation() {
        val selectedColor = ContextCompat.getColor(this, R.color.navi_selected)
        val unSelectedColor = ContextCompat.getColor(this, R.color.navi_unselected)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, TapeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomTapeIb.setColorFilter(selectedColor)
        binding.mainBottomTapeTv.setTextColor(selectedColor)

        binding.mainBottomTapeLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, TapeFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomTapeIb, binding.mainBottomTapeTv)
        }
        binding.mainBottomNotifLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, NotifFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomNotifIb, binding.mainBottomNotifTv)
        }
        binding.mainBottomPostLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, PostFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            binding.mainBottomPostIb.setImageResource(R.drawable.btm_post_selector_selected)
//            if (binding.mainBottomPostLayout.tag == null){
//                binding.mainBottomPostIb.setImageResource(R.drawable.btm_post_selector_selected)
//                binding.mainBottomPostLayout.tag = 0
//            }
//            else{
//                binding.mainBottomPostIb.setImageResource(R.drawable.btm_post_selector)
//                binding.mainBottomPostLayout.tag = null
//            }
        }
        binding.mainBottomSearchLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, SearchFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomSearchIb, binding.mainBottomSearchTv)
        }
        binding.mainBottomProfileLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, ProfileFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
        }
    }
    private fun setNavigationColor(icon: ImageView, text: TextView){
        val selectedColor = ContextCompat.getColor(this, R.color.navi_selected)
        icon.setColorFilter(selectedColor)
        text.setTextColor(selectedColor)
    }
    private fun setNavigationColorNone(){
        val unSelectedColor = ContextCompat.getColor(this, R.color.navi_unselected)

        binding.mainBottomTapeIb.setColorFilter(unSelectedColor)
        binding.mainBottomTapeTv.setTextColor(unSelectedColor)

        binding.mainBottomNotifIb.setColorFilter(unSelectedColor)
        binding.mainBottomNotifTv.setTextColor(unSelectedColor)

        binding.mainBottomPostIb.setImageResource(R.drawable.btm_post_selector)

        binding.mainBottomSearchIb.setColorFilter(unSelectedColor)
        binding.mainBottomSearchTv.setTextColor(unSelectedColor)
    }
}