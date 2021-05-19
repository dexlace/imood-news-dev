package com.dexlace.admin.controller;


import com.dexlace.admin.service.CategoryService;
import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.admin.CategoryMngControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.model.bo.SaveCategoryBO;
import com.dexlace.model.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.dexlace.api.service.BaseService.REDIS_ALL_CATEGORY;

@RestController
public class CategoryMngController extends BaseController implements CategoryMngControllerApi {

    final static Logger logger = LoggerFactory.getLogger(CategoryMngController.class);

    @Autowired
    private CategoryService categoryService;

    @Override
    public GraceIMOODJSONResult saveOrUpdateCategory(@Valid SaveCategoryBO saveCategoryBO,
                                                     BindingResult result) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        Category newCat = new Category();
        BeanUtils.copyProperties(saveCategoryBO, newCat);
        // id为空新增，不为空修改
        if (saveCategoryBO.getId() == null) {
            // 查询新增的分类名称不能重复存在
            boolean isExist = categoryService.queryCatIsExist(newCat.getName(), null);
            if (!isExist) {
                // 新增到数据库
                categoryService.createCategory(newCat);
            } else {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.CATEGORY_EXIST_ERROR);
            }
        } else {
            // 查询修改的分类名称不能重复存在
            boolean isExist = categoryService.queryCatIsExist(newCat.getName(), saveCategoryBO.getOldName());
            if (!isExist) {
                // 修改到数据库
                categoryService.modifyCategory(newCat);
            } else {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.CATEGORY_EXIST_ERROR);
            }
        }

        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult getCatList() {
        List<Category> categoryList = categoryService.queryCategoryList();
        return GraceIMOODJSONResult.ok(categoryList);
    }

    @Override
    public GraceIMOODJSONResult getCats() {
        // 先从redis中查询，如果有，则返回，如果没有，则查询数据库库后先放缓存，放返回
        String allCatJson = redis.get(REDIS_ALL_CATEGORY);

        List<Category> categoryList = null;
        if (StringUtils.isBlank(allCatJson)) {
            categoryList = categoryService.queryCategoryList();
            redis.set(REDIS_ALL_CATEGORY, JsonUtils.objectToJson(categoryList));
        } else {
            categoryList = JsonUtils.jsonToList(allCatJson, Category.class);
        }

        return GraceIMOODJSONResult.ok(categoryList);
    }
}
