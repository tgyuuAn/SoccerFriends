@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tgyuu.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.tgyuu.data.database.team.TeamDao
import com.tgyuu.data.database.team.TeamEntity
import com.tgyuu.presentation.rule.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executors

class TeamDaoTest {

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var database: SoccerFriendsDatabase
    private lateinit var dao: TeamDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, SoccerFriendsDatabase::class.java
        ).build()
        dao = database.getTeamDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun 팀을_추가한다() = runTest {
        //given
        val teamEntity = TeamEntity(
            id = 1,
            name = "Tgyuu",
            image = ""
        )

        //when
        dao.insertTeam(teamEntity)

        //then
        val actual: TeamEntity = dao.getTeam().first()
        val expected = teamEntity
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun 팀의_이름을_바꾼다() = runTest {
        //given
        val teamEntity = TeamEntity(
            id = 1,
            name = "Tgyuu",
            image = ""
        )

        dao.insertTeam(teamEntity)

        //when
        val newTeamEntity = TeamEntity(
            id = 1,
            name = "Uuygt",
            image = ""
        )

        dao.updateTeam(newTeamEntity)

        //then
        val actual: TeamEntity = dao.getTeam().first()
        val expected = newTeamEntity
        Truth.assertThat(actual).isEqualTo(expected)
    }
}