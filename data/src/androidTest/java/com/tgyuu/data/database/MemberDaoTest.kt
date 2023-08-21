package com.tgyuu.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.tgyuu.data.database.member.MemberDao
import com.tgyuu.data.database.member.MemberEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemberDaoTest {

    lateinit var database: SoccerFriendsDatabase
    private lateinit var dao: MemberDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, SoccerFriendsDatabase::class.java).build()
        dao = database.getMemberDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun 새로운_선수를_등록한다() = runTest {
        //given
        val memberEntity = MemberEntity(
            name = "Tgyuu",
            image = "",
            position = "GK",
            number = 1,
            isBenchWarmer = false
        )

        //when
        dao.insertMember(memberEntity)

        //then
        val expected = MemberEntity(
            id = 1,
            name = "Tgyuu",
            image = "",
            position = "GK",
            number = 1,
            isBenchWarmer = false
        )
        val actual: List<MemberEntity> = dao.getAllMembers().first()
        assertThat(actual).contains(expected)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun 선수의_이름을_바꾼다() = runTest {
        //given
        val memberEntity = MemberEntity(
            name = "Tgyuu",
            image = "",
            position = "GK",
            number = 1,
            isBenchWarmer = false
        )

        dao.insertMember(memberEntity)

        //when
        val newMemberEntity = MemberEntity(
            id = 1,
            name = "Uuygt",
            image = "",
            position = "GK",
            number = 1,
            isBenchWarmer = false
        )

        dao.updateMember(newMemberEntity)

        //then
        val expected = newMemberEntity
        val actual: List<MemberEntity> = dao.getAllMembers().first()
        assertThat(actual).contains(expected)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun 선수를_ID로_찾는다() = runTest {
        //given
        val memberEntity1 = MemberEntity(
            name = "Tgyuu",
            image = "",
            position = "GK",
            number = 1,
            isBenchWarmer = false
        )
        val memberEntity2 = MemberEntity(
            name = "Uuygt",
            image = "",
            position = "GK",
            number = 2,
            isBenchWarmer = false
        )

        dao.insertMember(memberEntity1)
        dao.insertMember(memberEntity2)

        //when


        //then
        val expected = MemberEntity(
            id = 2,
            name = "Uuygt",
            image = "",
            position = "GK",
            number = 2,
            isBenchWarmer = false
        )
        val actual: MemberEntity = dao.getMemberById(2).first()
        assertThat(actual).isEqualTo(expected)
    }
}