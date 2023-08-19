package com.tgyuu.domain.team.usecase

import javax.inject.Inject

class AddNewMemberUseCase @Inject constructor() {
    operator suspend fun invoke(
        newMemberName: String,
        newMemberBackNumber: Int,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ): Result<Unit> {
        return Result.success(Unit)
    }
}