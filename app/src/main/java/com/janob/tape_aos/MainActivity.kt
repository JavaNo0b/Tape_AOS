package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityMainBinding

private const val TAG1 = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var tapeData : List<Tape>
    lateinit var tapeReplyData : List<Reply>
    lateinit var songData : List<Song>
    lateinit var includedSongData : List<IncludedSong>
    lateinit var userData : List<User>

    lateinit var myUserData : MyUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //해쉬키 값 추출
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //더미 데이터
        inputDummyAlbum()
        inputDummyReply()
        inputDummySong()//양희연
        inputDummySongs()//박성현
        inputDummyUser()

        initBottomNavigation()

        SongRepository.initialize(this)
        IncludedSongRepository.initialize(this)
        TapeRepository.initialize(this)

    }

    private fun inputDummyAlbum(){

        val database = TapeDatabase.Instance(this)
        tapeData = database.tapeDao().getAll()


        if(tapeData.isNotEmpty()) return
        database.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_1,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.album_2,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.album_3,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_4,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.album_1,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.album_2,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_3,
                R.drawable.albumcover_5)
        )
        database.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
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
        tapeData = tapeDB.tapeDao().getAll()
        val my_user_tapeList = ArrayList<Tape>()
        songData = tapeDB.songDao().getAllList()

        //val imageUriString : String = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F1000000033/ORIGINAL/NONE/image%2Fjpeg/789589731"
        //val setImageUri : Uri? = imageUriString?.let { Uri.parse(it) }
        val userImg = R.drawable.albumcover_5


        if(userData.isNotEmpty())
            return

        var followerList : List<String> = arrayListOf("music_play", "k_pop_lover", "tape_123", "like_song", "_sing__", "dance_music__", "music_best", "listen_music_", "_k_pop__", "pop_song_", "user123")
        var followingList : List<String> = arrayListOf("music_play", "k_pop_lover", "tape_123", "like_song", "_sing__", "dance_music__", "music_best", "listen_music_", "_k_pop__", "pop_song_", "user123")
        var my_user_followingList : List<String> = arrayListOf("myuser_1", "myuser_2", "myuser_3", "myuser_4", "myuser_5", "myuser_6", "myuser_7", "myuser_8", "myuser_9", "myuser_10")
//        tapeDB.userDao().insert(
//            User(1,
//                userImg,
//                "Min_SEO",
//                "잡다한 음악 다 좋아해요♥",
//                followerList,
//                my_user_followingList.toList(),
//                my_user_tapeList.toList(),
//                songData)
//        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "music_play",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "k_pop_lover",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "tape_123",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "like_song",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "_sing__",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "dance_music__",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "music_best",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "listen_music_",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "_k_pop__",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "pop_song_",
                "user comment",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "user123",
                "user123 comment",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_1",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_2",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_3",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_4",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_5",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_6",
                "케이팝 좋아해요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_7",
                "노래 좋아요",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_8",
                "작사, 작곡 공부 중",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_9",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                followingList,
                tapeData,
                songData)
        )
        tapeDB.userDao().insert(
            User(0,
                userImg,
                "myuser_10",
                "user comment",
                followerList,
                followingList,
                tapeData,
                songData)
        )
    }
    private fun inputDummyMyUser(){
        val tapeDB = TapeDatabase.Instance(this)
        myUserData = tapeDB.myUserDao().getAll()

        tapeData = tapeDB.tapeDao().getAll()
        val my_user_tapeList = ArrayList<Tape>()
        songData = tapeDB.songDao().getAllList()

        var followerList : List<String> = arrayListOf("music_play", "k_pop_lover", "tape_123", "like_song", "_sing__", "dance_music__", "music_best", "listen_music_", "_k_pop__", "pop_song_", "user123")
        var followingList : List<String> = arrayListOf("music_play", "k_pop_lover", "tape_123", "like_song", "_sing__", "dance_music__", "music_best", "listen_music_", "_k_pop__", "pop_song_", "user123")
        var my_user_followingList : List<String> = arrayListOf("myuser_1", "myuser_2", "myuser_3", "myuser_4", "myuser_5", "myuser_6", "myuser_7", "myuser_8", "myuser_9", "myuser_10")

        tapeDB.myUserDao().insert(
            MyUser("",
                "Min_SEO",
                "잡다한 음악 다 좋아해요♥",
                followerList,
                my_user_followingList.toList(),
                my_user_tapeList.toList(),
                songData)
        )

    }
    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, TapeFragment())
            .commitAllowingStateLoss()
        binding.mainBottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.tape_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, TapeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.notif_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, NotifFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.post_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, PostFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.search_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.profile_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, ProfileFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}