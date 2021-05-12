package ni.edu.uca.moviles2.comicviewer.retrofit

import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity
import ni.edu.uca.moviles2.comicviewer.utils.EntityMapper
import java.util.*
import javax.inject.Inject

class ComicNetworkMapper
constructor(): EntityMapper<ComicNetworkEntity, ComicEntity> {
    override fun mapFromEntity(entity: ComicNetworkEntity): ComicEntity {
        return ComicEntity(
            id = entity.id,
            title = entity.title,
            img = entity.img,
            alt = entity.alt,
            safeTitle = entity.safeTitle,
            transcript = entity.transcript,
            link = entity.link,
            num = entity.num,
            day = entity.day,
            month = entity.month,
            year = entity.year,
            dateAdded = Date().time
        )
    }

    override fun mapToEntity(domainModel: ComicEntity): ComicNetworkEntity {
        return ComicNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            img = domainModel.img,
            alt = domainModel.alt,
            safeTitle = domainModel.safeTitle,
            transcript = domainModel.transcript,
            link = domainModel.link,
            num = domainModel.num,
            day = domainModel.day,
            month = domainModel.month,
            year = domainModel.year
        )
    }

}