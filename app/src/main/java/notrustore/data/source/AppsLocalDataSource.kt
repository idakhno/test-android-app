package notrustore.data.source

import javax.inject.Inject
import notrustore.data.dto.AppInfoDto

class AppsLocalDataSource @Inject constructor() {
    fun getApps(): List<AppInfoDto> = sampleAppDtos
}
