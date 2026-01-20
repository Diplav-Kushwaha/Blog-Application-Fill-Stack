package com.diplav.blog.mapper;

import com.diplav.blog.entity.Post;
import com.diplav.blog.enumeration.PostStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Named("calculatePosts")
    default Integer calculatePosts(List<Post> posts){
        if(posts==null){
            return 0;
        }
        return (int)posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}
