package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var tapeData : List<Tape>
    lateinit var tapeReplyData : List<Reply>
    lateinit var songData : List<Song>
    lateinit var includedSongData : List<IncludedSong>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //더미 데이터
        inputDummyAlbum()
        inputDummyReply()
        inputDummySong()//양희연
        inputDummySongs()//박성현

        initBottomNavigation()


        startActivity(Intent(this, OnboardActivity::class.java))
    }

    private fun inputDummyAlbum(){
        val tapeDB = TapeDatabase.Instance(this)
        tapeData = tapeDB.tapeDao().getAll()

        if(tapeData.isNotEmpty()) return

        tapeDB.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_1,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.album_2,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.album_3,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_4,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.album_1,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.album_2,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.album_3,
                R.drawable.albumcover_5)
        )
        tapeDB.tapeDao().insert(
            Tape("와르르",
                "Colde",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
    }

    //양희연
    private fun inputDummySong(){
        val tapeDB = TapeDatabase.Instance(this)
        songData = tapeDB.songDao().getAll()

        if(songData.isNotEmpty()) return

        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]",
                0,0
            )
        )
        tapeDB.songDao().insert(
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
        includedSongData = songDB.songDaos().getSongs()
        if(includedSongData.isNotEmpty())
            return
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                1)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                2)
        )
        songDB.songDaos().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                2)
        )
        songDB.songDaos().insert(
            IncludedSong("Thirsty",
                "aepsa",
                R.drawable.album_3,
                3)
        )
        songDB.songDaos().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                3)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                3)
        )
        songDB.songDaos().insert(
            IncludedSong("와르르",
                "Colde",
                R.drawable.album_2,
                4)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                4)
        )
        songDB.songDaos().insert(
            IncludedSong("Thirsty",
                "aepsa",
                R.drawable.album_3,
                4)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                5)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                6)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                7)
        )
        songDB.songDaos().insert(
            IncludedSong("Broken Melodies",
                "NCT DREAM",
                R.drawable.album_1,
                8)
        )

    }
    private fun inputDummyReply(){
        val tapeDB = TapeDatabase.Instance(this)
        tapeReplyData = tapeDB.replyDao().getAll()

        if(tapeReplyData.isNotEmpty())
            return

        tapeDB.replyDao().insert(
            Reply(1,
                "너무 좋아요ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ가사 너무 아련...")
        )
        tapeDB.replyDao().insert(
            Reply(2,
                "노래 너무 좋아요❤️")
        )
        tapeDB.replyDao().insert(
            Reply(3,
                "이 조합 미쳤다,,완벽한 겨울노래")
        )
        tapeDB.replyDao().insert(
            Reply(4,
                "목소리 너무 신비스럽게 이쁘네")
        )
        tapeDB.replyDao().insert(
            Reply(5,
                "들을 때마다 더 좋아지냐 마음이 따뜻해지네")
        )
        tapeDB.replyDao().insert(
            Reply(6,
                "안볼수가 없는 조합")
        )
        tapeDB.replyDao().insert(
            Reply(7,
                "우와... 겨울 분위기 대박 노래 너무 좋아요ㅠ")
        )
        tapeDB.replyDao().insert(
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

                R.id.musictalk_nav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fm, MusictalkFragment())
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