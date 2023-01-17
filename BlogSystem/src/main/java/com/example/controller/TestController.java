package com.example.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dao.ArticleRepository;
import com.example.dao.PhotoRepository;
import com.example.dao.TargetRepository;
import com.example.model.Article;
import com.example.model.Photo;
import com.example.model.Target;


@Controller
public class TestController {
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private TargetRepository targetRepository;
	@Autowired
	private PhotoRepository photoRepository;

	@GetMapping("/test")
	@ResponseBody
	public String Hello() {
		return "Hello World";
	}
	
	@GetMapping("/getArticle/{id}")
	@ResponseBody
	public ResponseEntity getArticle(@PathVariable Integer id) {
		System.out.println("getArticle");
		
		return ResponseEntity.status(HttpStatus.OK).body(articleRepository.findById(id));
	}
	
	@GetMapping("/getTarget/{id}")
	@ResponseBody
	public ResponseEntity getTarget(@PathVariable Integer id) {
		System.out.println("getTarget");
		
		return ResponseEntity.status(HttpStatus.OK).body(targetRepository.findById(id));
	}
	
	@GetMapping("/addArticle/{title}/{content}")
	@ResponseBody
	public String addArticle(@PathVariable String title,@PathVariable String content) {
		System.out.println("addArticle");
		
		Set<Target> tags=new LinkedHashSet<Target>();
		
		//tags.add(targetRepository.findById(4).get());
		//tags.add(targetRepository.findById(7).get());
		
		Target tag=new Target();
		tag.setTname("學校");
		targetRepository.save(tag);
		tags.add(tag);
		
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());
		Article article=new Article();
		article.setTitle(title);
		article.setCreatetime(ts);
		article.setContent(content);
		article.setTargets(tags);
		articleRepository.save(article);
		return "新增文章成功";
	}
	
	@GetMapping("/getPhoto/{id}")
	@ResponseBody
	public ResponseEntity getPhoto(@PathVariable Integer id) {
		System.out.println("getPhoto");
		
		return ResponseEntity.status(HttpStatus.OK).body(photoRepository.findById(id));
	}
	
	@GetMapping("/deleteArticle/{id}")
	@ResponseBody
	public String deleteArticle(@PathVariable Integer id) {
		System.out.println("getPhoto");
		articleRepository.deleteById(id);
		return "刪除id編號:"+id+" 文章";
	}
	
	//使前端網頁可以透過href取得圖片位置
    @RequestMapping(value = "/getimg/{pid}/{pname}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Integer pid,@PathVariable String pname) throws IOException {
    	try {
    		Photo photo=photoRepository.findById(pid).get();
    		FileSystemResource imgFile = new FileSystemResource("C:/Users/USER/Desktop/images/"+photo.getPanme()+".png");
    		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes);
    	}catch (NoSuchElementException e) {
    		return null;
    	}
    }
	
}
