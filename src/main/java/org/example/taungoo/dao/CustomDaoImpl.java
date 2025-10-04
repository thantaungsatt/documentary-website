package org.example.taungoo.dao;

import jakarta.persistence.EntityManager;
import org.example.taungoo.dto.PostDto;
import org.example.taungoo.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomDaoImpl implements  CustomDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Post> findRecentPost() {
        return entityManager.createQuery("""
  select p from Post p
  order by p.createdAt desc
  """, Post.class).setFirstResult(1)
                .setMaxResults(6)
                .getResultList();
  }
}
