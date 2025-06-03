package com.fresherlancer.app.util;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtil {

    public static  <T , V> void  populateOneToManyCollection(Set<T> masterEntities, Collection<V> selectedIds,
                                                                                   Function<T,V> idFunction, Function<V,T> objectFunction) {
        if (!CollectionUtils.isEmpty(masterEntities)) {
            if (CollectionUtils.isEmpty(selectedIds)) {
                masterEntities.clear();
            } else {
                masterEntities.removeIf((abstractEntity) -> {
                    boolean itemToRemove = true;
                    V existingId = idFunction.apply(abstractEntity);
                    if(existingId !=null && selectedIds.contains(existingId)) {
                        selectedIds.remove(existingId);
                        itemToRemove = false;
                    }
                    return itemToRemove;
                });
            }
        }
        if(!CollectionUtils.isEmpty(selectedIds)) {
            selectedIds.forEach((selectedId) -> {
                T newAbstractEntity = objectFunction.apply(selectedId);
                masterEntities.add(newAbstractEntity);
            });
        }
    }

    public static  <T , V> void  populateOneToManyCollectionForObjects(
            Set<T> masterEntities,
            Collection<V> selectedObjects,
            Function<T,V> compareFunction,
            BiConsumer<T,V> editFunction,
            Function<V,T> addFunction) {
        if (!CollectionUtils.isEmpty(masterEntities)) {
            if (CollectionUtils.isEmpty(selectedObjects)) {
                masterEntities.clear();
            } else {
                masterEntities.removeIf((abstractEntity) -> {
                    boolean itemToRemove = true;
                    V existingObject = compareFunction.apply(abstractEntity);
                    if(selectedObjects.contains(existingObject)) {
                        editFunction.accept(abstractEntity,existingObject);
                        selectedObjects.remove(existingObject);
                        itemToRemove = false;
                    }
                    return itemToRemove;
                });
            }
        }
        if(!CollectionUtils.isEmpty(selectedObjects)) {
            selectedObjects.forEach((selectedObject) -> {
                T newAbstractEntity = addFunction.apply(selectedObject);
                masterEntities.add(newAbstractEntity);
            });
        }
    }


    public static  <T , V> void  populateOneToManyCollectionForObjectsList(
            List<T> masterEntities,
            Collection<V> selectedObjects,
            Function<T,V> compareFunction,
            BiConsumer<T,V> editFunction,
            Function<V,T> addFunction) {
        if (!CollectionUtils.isEmpty(masterEntities)) {
            if (CollectionUtils.isEmpty(selectedObjects)) {
                masterEntities.clear();
            } else {
                masterEntities.removeIf((abstractEntity) -> {
                    boolean itemToRemove = true;
                    V existingObject = compareFunction.apply(abstractEntity);
                    if(selectedObjects.contains(existingObject)) {
                        editFunction.accept(abstractEntity,existingObject);
                        selectedObjects.remove(existingObject);
                        itemToRemove = false;
                    }
                    return itemToRemove;
                });
            }
        }
        if(!CollectionUtils.isEmpty(selectedObjects)) {
            selectedObjects.forEach((selectedObject) -> {
                T newAbstractEntity = addFunction.apply(selectedObject);
                masterEntities.add(newAbstractEntity);
            });
        }
    }

    public static  <T , V> void  populateOneToManyCollectionForObjectsWithCreateConsumer(
            Set<T> masterEntities,
            Collection<V> selectedObjects,
            Function<T,V> compareFunction,
            BiConsumer<T,V> editFunction,
            Consumer<Collection<V>> customAddFunction) {
        if (!CollectionUtils.isEmpty(masterEntities)) {
            if (CollectionUtils.isEmpty(selectedObjects)) {
                masterEntities.clear();
            } else {
                masterEntities.removeIf((abstractEntity) -> {
                    boolean itemToRemove = true;
                    V existingObject = compareFunction.apply(abstractEntity);
                    if(selectedObjects.contains(existingObject)) {
                        editFunction.accept(abstractEntity,existingObject);
                        selectedObjects.remove(existingObject);
                        itemToRemove = false;
                    }
                    return itemToRemove;
                });
            }
        }
        if(!CollectionUtils.isEmpty(selectedObjects)) {
            customAddFunction.accept(selectedObjects);
        }
    }

    public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
        return iterable == null ? Collections.<T>emptyList() : iterable;
    }
}
