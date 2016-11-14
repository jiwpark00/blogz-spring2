package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		// To get the author, we need to get from the currently logged in session
		HttpSession newSession = request.getSession(); // true parameter is not necessary
		User author = getUserFromSession(newSession);
		// getUserFromSession is from AbstractController
		
		if (title == null || title.isEmpty()) {
			model.addAttribute("error", "We need a title for the post");
			return "newpost";
		}
		
		if (body == null || body.isEmpty()) {
			model.addAttribute("title",title);
			model.addAttribute("error", "We need a body for the post");
			return "newpost";
		}
		
		// Assuming we have title and body
		// We want to save the post
		Post post = new Post(title, body, author);
		postDao.save(post); // again, thanks to CrudRepository
		
		// TODO - this redirect should go to the new post's page  		
		return "redirect:/blog/" + author.getUsername() + "/" + post.getUid(); 
		}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		return "blog";
	}
	
}
