package com.jenson.tool.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class SuperController<Vo, Bo, SearchBo extends SuperSearchBo, Service extends SuperService<Vo, Bo, SearchBo>> {

    @Autowired
    private Service superService;

    @PostMapping("/page_all")
    Result<Page<Vo>> pageAll(@RequestBody SearchBo searchBo) {
        return Result.suc(superService.pageAll(searchBo));
    }

    @PostMapping("/list_all")
    Result<List<Vo>> listAll(@RequestBody SearchBo searchBo) {
        return Result.suc(superService.listAll(searchBo));
    }

    @GetMapping("/get")
    Result<Vo> get(@RequestParam("id") Integer id) {
        return Result.suc(superService.get(id));
    }

    @PostMapping("/edit")
    Result<Vo> edit(@RequestBody Bo bo) {
        return Result.suc(superService.edit(bo));
    }

    @PostMapping("/edit_all")
    Result<List<Vo>> edit(@RequestBody List<Bo> boList) {
        return Result.suc(superService.edit(boList));
    }

    @GetMapping("/del")
    Result<Vo> del(@RequestParam("id") Integer id) {
        return Result.suc(superService.del(id));
    }

    @PostMapping("/del")
    Result<Vo> del(@RequestBody SearchBo searchBo) {
        return Result.suc(superService.del(searchBo));
    }

}
