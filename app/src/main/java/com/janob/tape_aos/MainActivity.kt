package com.janob.tape_aos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.janob.tape_aos.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

private const val TAG1 = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //해쉬키 값 추출
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //더미 데이터
        inputDummyAlbum()
        inputDummyReply()
        inputDummySong()//양희연
        inputDummySongs()//박성현

        initBottomNavigation()

        SongRepository.initialize(this)
        IncludedSongRepository.initialize(this)
        TapeRepository.initialize(this)

    }

    private fun inputDummyAlbum(){
        val database = TapeDatabase.Instance(this)
        val tapeDao = database.tapeDao()
        tapeDao.deleteAll()

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
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
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
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
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
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
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
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        songDao.insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
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
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                1)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                1)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                2)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                2)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Thirsty",
                "aepsa",
                R.drawable.album_3,
                3)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                3)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                3)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                4)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                4)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Thirsty",
                "aepsa",
                R.drawable.album_3,
                4)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                5)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                6)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                7)
        )
        songDB.IncludedSongDao().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                8)
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