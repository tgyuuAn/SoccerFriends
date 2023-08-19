package com.tgyuu.domain.team.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddNewMemberUseCase @Inject constructor() {
    operator suspend fun invoke(
        newMemberName: String,
        newMemberBackNumber: Int,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ): Flow<Result<Unit>> = flow {
        emit(Result.success(Unit))
    }
}