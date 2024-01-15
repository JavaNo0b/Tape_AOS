package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var tapeAlbumData : List<TapeAlbum>
    lateinit var tapeReplyData : List<Reply>
    lateinit var songData : List<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //더미 데이터
        inputDummyAlbum()
        inputDummyReply()
        inputDummySong()

        initBottomNavigation()


        startActivity(Intent(this, OnboardActivity::class.java))
    }

    private fun inputDummySong(){
        val tapeDB = TapeDatabase.Instance(this)
        songData = tapeDB.songDao().getAll()

        if(songData.isNotEmpty()) return

        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
        tapeDB.songDao().insert(
            Song(R.drawable.albumcover_5,
                "우리 서로 사랑하지는 말자",
                "기리보이",
                "기리보이 EP [영화같게]"
            )
        )
    }
    private fun inputDummyAlbum(){
        val tapeDB = TapeDatabase.Instance(this)
        tapeAlbumData = tapeDB.albumDao().getAll()

        if(tapeAlbumData.isNotEmpty()) return

        tapeDB.albumDao().insert(
            TapeAlbum("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("와르르",
                "Colde",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("와르르",
                "Colde",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("Broken Melodies",
                "NCT DREAM",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("Thirsty",
                "aepsa",
                "K_pop_lover",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )
        tapeDB.albumDao().insert(
            TapeAlbum("와르르",
                "Colde",
                "music_play",
                R.drawable.albumcover_5,
                R.drawable.albumcover_5)
        )

    }
    private fun inputDummyReply(){
        val tapeDB = TapeDatabase.Instance(this)
        tapeReplyData = tapeDB.replyDao().getAll()

        if(tapeReplyData.isNotEmpty())
            return

        tapeDB.replyDao().insert(
            Reply(0,
                "너무 좋아요ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ가사 너무 아련...")
        )
        tapeDB.replyDao().insert(
            Reply(1,
                "노래 너무 좋아요❤️")
        )
        tapeDB.replyDao().insert(
            Reply(2,
                "이 조합 미쳤다,,완벽한 겨울노래")
        )
        tapeDB.replyDao().insert(
            Reply(3,
                "목소리 너무 신비스럽게 이쁘네")
        )
        tapeDB.replyDao().insert(
            Reply(4,
                "들을 때마다 더 좋아지냐 마음이 따뜻해지네")
        )
        tapeDB.replyDao().insert(
            Reply(5,
                "안볼수가 없는 조합")
        )
        tapeDB.replyDao().insert(
            Reply(6,
                "우와... 겨울 분위기 대박 노래 너무 좋아요ㅠ")
        )
        tapeDB.replyDao().insert(
            Reply(7,
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