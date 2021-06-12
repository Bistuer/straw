package cn.tedu.straw.portal.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/portal/user")
public class UserController {

//    public String minNumbers(int[] nums) {
//        String[] strs = new String[nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            strs[i] = String.valueOf(nums[i]);
//        }
//        Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
//        StringBuilder res = new StringBuilder();
//        for (String s : strs) {
//            res.append(s);
//        }
//
//        return res.toString();
//
//    }
}
