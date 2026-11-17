package notrustore.domain.usecase

import javax.inject.Inject
import notrustore.domain.model.AppInfo
import notrustore.domain.repository.AppRepository

class GetAppUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(id: String): AppInfo = repository.getApp(id)
}
