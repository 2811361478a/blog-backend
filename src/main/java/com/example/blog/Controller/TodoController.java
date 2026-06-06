package com.example.blog.Controller;

import com.example.blog.Repository.TodoRepository;
import com.example.blog.Result;
import com.example.blog.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Todo")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;
    @PostMapping
    public Result<Todo> create(@RequestBody Todo todo){
        return Result.success(todoRepository.save(todo));
    }
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable Long id){
        Todo todo=todoRepository.findById(id).orElse(null);
        if (todo==null){
            return Result.error("任务不存在");
        }
        todoRepository.deleteById(id);
        return Result.success("删除成功");
    }
    @PutMapping("/{id}/state")
    public Result<Todo> update(@PathVariable Long id,@RequestParam String state){
        Todo td=todoRepository.findById(id).orElse(null);
        if (td==null){
            return Result.error("任务不存在");
        }
        td.setState(state);
        return Result.success(todoRepository.save(td));
    }
    @GetMapping("/user/{userId}")
    public Result<List<Todo>> getAll(@PathVariable Long userId){
        return Result.success(todoRepository.findByUserId(userId));
    }
}
