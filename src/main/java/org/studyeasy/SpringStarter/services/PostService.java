package org.studyeasy.SpringStarter.services;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.models.Post;
import org.studyeasy.SpringStarter.repositories.PostRepository;
@Service
public class PostService {
    @Autowired
    private PostRepository postrepository;
    public Optional<Post> getById(Long id){
        return postrepository.findById(id);
    }
    public List<Post> getAll()
    {
        return postrepository.findAll();
    }
    public Page<Post> getAll(int offset,int pageSize,String field)
    {
        return postrepository.findAll(PageRequest.of(offset, pageSize).withSort(Direction.ASC, field));
    }
    public void delete(Post post)
    {
        postrepository.delete(post);
    }
    public Post save(Post post)
    {
          if(post.getId()==null)
          {
            post.setCreatedAt(LocalDateTime.now());
          }
          post.setUpdatedAt(LocalDateTime.now());
          return postrepository.save(post);
    }
}  
