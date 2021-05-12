package ni.edu.uca.moviles2.comicviewer.utils


//Sirve como interface de los mappers
interface EntityMapper <Entity, DomainModel>{
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}