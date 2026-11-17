package notrustore.domain.usecase

import javax.inject.Inject
import notrustore.domain.repository.AppRepository

class LoadRatingsInParallelUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(ids: List<String>): Map<String, Float> =
        repository.loadRatingsInParallel(ids)
}
