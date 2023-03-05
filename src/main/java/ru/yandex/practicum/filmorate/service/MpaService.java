package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDb;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
public class MpaService {
    final MpaDb mpaDbStorage;

    @Autowired
    public MpaService(MpaDb mpaDbStorage){
        this.mpaDbStorage = mpaDbStorage;
    }

    /**Получение всех MPA.*/
    public List<Mpa> getAll(){
        return mpaDbStorage.findAll();
    }

    /**Получение MPA по id.*/
    public Mpa getById(Integer id){
        return new Mpa(id, mpaDbStorage.findById(id));
    }
}