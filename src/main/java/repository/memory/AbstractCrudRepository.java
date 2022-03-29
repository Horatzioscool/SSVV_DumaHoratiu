package repository.memory;

import repository.CrudRepository;
import repository.HasID;
import repository.RepositoryException;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCrudRepository <ID, E extends HasID<ID>> implements CrudRepository<ID, E> {
    private final Map<ID, E> entities;

    protected AbstractCrudRepository(){
        this.entities = new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        return this.entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return this.entities.values();
    }

    @Override
    public E save(E entity) {
        E el = this.findOne(entity.getID());
        if (el == null){
            this.entities.put(entity.getID(), entity);
            return null;
        }
        else throw new RepositoryException(String.format("Entity with id %s already in repository", entity.getID()));
    }

    @Override
    public E delete(ID id) {
        return this.entities.remove(id);
    }

    @Override
    public E update(E entity) {
        if (this.entities.get(entity.getID()) == null) {
            return null;
        }
        return this.entities.replace(entity.getID(), entity);
    }
}
