package com.suime.library.rest.admin;

import com.confucian.framework.web.AbstractRestController;

/**
 * Created by ice on 23/12/2015.
 *
 * @author ice
 */
//@Api(description = "后台管理，用户接口", tags = "student")
//@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU, RoleType.WENKU_ADMIN}, logical = Logical.OR)
//@RestController
//@RequestMapping(path = "/student")
public class StudentRestController extends AbstractRestController {
//
//    /**
//     * studentService
//     */
//    @Autowired
//    private StudentService studentService;
//
//    /**
//     * 根据cellphone和name模糊搜素
//     *
//     * @param cellphone
//     * @param studentNickName
//     * @param sortField
//     * @param sort
//     * @param curPage
//     * @param pageSize
//     * @return
//     */
//    @ApiOperation(value = "根据条件获取用户，条件为空时获取所有，text为用户昵称")
//    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
//    @RequiresAuthentication
//    @RequestMapping(path = "/list", method = {RequestMethod.GET})
//    public Object search(@RequestParam(name = "text", required = false) String studentNickName,
//                         @RequestParam(name = "sortField", required = false) String sortField,
//                         @RequestParam(name = "sort", defaultValue = "DESC") String sort,
//                         @RequestParam(name = "page", defaultValue = "1") Integer curPage,
//                         @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
//                         @RequestParam(name = "cellphone", required = false) String cellphone) {
//        CommonResultBean resultBean = new CommonResultBean();
//        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
//        SearchAdminBean searchAdminBean = new SearchAdminBean();
//        searchAdminBean.setCellphone(cellphone);
//        searchAdminBean.setName(studentNickName);
//        searchAdminBean.setSort(OrderType.valueOf(sort));
//        searchAdminBean.setSortField(sortField);
//        resultBean.setBody(this.studentService.searchByItem(searchAdminBean, curPage, pageSize));
//        return resultBean;
//    }
}
