package com.adi.githubsearch.domain.interactor

import com.adi.githubsearch.domain.usecase.SearchUserUseCase
import javax.inject.Inject

class UserInteractor @Inject constructor(
    val searchUserUseCase: SearchUserUseCase
)