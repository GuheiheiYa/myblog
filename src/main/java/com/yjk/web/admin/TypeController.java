package com.yjk.web.admin;


import com.yjk.pojo.Type;
import com.yjk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping(path = "/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }


    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            result.rejectValue("name","nameError","请勿添加重复的类型");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","操作失败！");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            result.rejectValue("name","nameError","请勿添加重复的类型");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.updateType(id,type);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","更新失败！");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }



    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/types";
    }



}
