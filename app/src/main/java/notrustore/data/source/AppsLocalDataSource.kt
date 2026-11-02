package notrustore.data.source

import notrustore.data.dto.AppInfoDto

class AppsLocalDataSource {
    fun getApps(): List<AppInfoDto> = sampleAppDtos
}
