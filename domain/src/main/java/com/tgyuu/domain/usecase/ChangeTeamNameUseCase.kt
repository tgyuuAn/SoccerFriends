package com.tgyuu.domain.usecase

import javax.inject.Inject

class ChangeTeamNameUseCase @Inject constructor() {
    operator suspend fun invoke(teamName : String){
        return Unit
    }
}