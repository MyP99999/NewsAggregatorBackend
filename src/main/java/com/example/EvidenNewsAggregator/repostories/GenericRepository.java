package com.example.EvidenNewsAggregator.repostories;

public interface GenericRepository<T>
{
    public void add(T t);

    public int deleteById(Integer t);

    public Iterable<T> findAll();

    public T findById(Integer id);

    public int update(T t);

}
