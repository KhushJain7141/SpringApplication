package org.studyeasy.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.studyeasy.SpringStarter.models.Account;
import org.studyeasy.SpringStarter.models.Post;
import org.studyeasy.SpringStarter.services.AccountService;
import org.studyeasy.SpringStarter.services.PostService;

import jakarta.validation.Valid;


@EnableMethodSecurity
@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            String authUser = (principal != null) ? principal.getName() : "email";
            model.addAttribute("isOwner", authUser.equals(post.getAccount().getEmail()));

            return "post_views/post"; // Ensure this view exists
        } else {
            return "redirect:/error"; // Handle not found appropriately
        }
    }

    @GetMapping("/posts/add")
    public String addPostForm(Model model, Principal principal) {
        String authUser = (principal != null) ? principal.getName() : "email";
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);

        if (optionalAccount.isPresent()) {
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add"; // Ensure this view exists
        } else {
            return "redirect:/"; // Redirect if account is not found
        }
    }

    @PostMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@ModelAttribute Post post,BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors())
        {
            return "post_views/post_add";
        }
        String authUser = (principal != null) ? principal.getName() : "email";
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0) {
            return "redirect:/?error"; // Redirect if email doesn't match
        }
        postService.save(post);
        return "redirect:/posts/" + post.getId(); // Redirect to the newly created post
    }
    @GetMapping("posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
 public String getPostForEdit(@PathVariable long id, Model model)
 {
    Optional <Post> optionalPost = postService.getById(id);
    if(optionalPost.isPresent())
    {
        Post post = optionalPost.get();
        model.addAttribute("post",post);
        return "post_views/post_edit";
    }else{
        return"404";
    }
    
      
 }
 @PostMapping("posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatedPost(@Valid @ModelAttribute Post post,BindingResult bindingResult,@PathVariable long id)
    {
        if(bindingResult.hasErrors())
        {
            return "post_views/post_add";
        }
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent())
        {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            postService.save(existingPost);
        }
        return "redirect:/posts/"+post.getId();
    }
    @GetMapping("posts/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable long id){
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent())
        {
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/";
        }
        else{
            return "redirect:/?error";
        }

    }
}
