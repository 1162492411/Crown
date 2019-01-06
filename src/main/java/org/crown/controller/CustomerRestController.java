/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.common.mybatisplus.LambdaQueryWrapperChain;
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.StatusEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.dto.UserDTO;
import org.crown.model.entity.Customer;
import org.crown.model.entity.User;
import org.crown.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.crown.framework.controller.SuperController;

import java.util.Objects;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty;

/**
 * <p>
 * 客户信息表 前端控制器
 * </p>
 *
 * @author Caratacus
 */
@Api(tags = {"Customer"}, description = "客户信息表相关接口")
@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class CustomerRestController extends SuperController {

    @Autowired
    private ICustomerService customerService;

    @Resources(auth = AuthTypeEnum.LOGIN)
    @ApiOperation("查询所有客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "需要检查的账号", paramType = "query"),
            @ApiImplicitParam(name = "identifier", value = "需要检查的账号", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "需要检查的账号", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "需要检查的账号", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<Customer>> page(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "identifier", required = false) String identifier,@RequestParam(value = "address", required = false) String address,@RequestParam(value = "mobile", required = false) String mobile) {
        LambdaQueryWrapperChain<Customer> queryWrapperChain = customerService.query()
                .like(!isEmpty(name), Customer::getName, name)
                .like(!isEmpty(address), Customer::getAddress, address)
                .like(!isEmpty(identifier), Customer::getIdentifier, identifier)
                .like(!isEmpty(mobile), Customer::getMobile, mobile);
        IPage<Customer> page = queryWrapperChain.page(this.getPage());
        return success(page);
    }



}
