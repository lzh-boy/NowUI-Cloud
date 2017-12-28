package com.nowui.cloud.base.code.controller.admin;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.base.code.entity.Code;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.util.FileUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nowui.cloud.base.code.service.CodeService;
import com.nowui.cloud.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author marcus
 * @since 2017-12-20
 */
@Api(value = "代码生成", description = "代码生成接口管理")
@RestController
public class CodeController extends BaseController {

    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "数据库表列表")
    @RequestMapping(value = "/code/admin/table/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> tableList(@RequestBody Code body) {
        validateRequest(body, Code.TABLE_SCHEMA, Code.TABLE_NAME);

        List<Code> resultList = codeService.tableSchemaList(body.getTableSchema(), body.getTableName());

        validateResponse(Code.TABLE_SCHEMA, Code.TABLE_NAME, Code.ENGINE, Code.TABLE_COMMENT, Code.SYSTEM_CREATE_TIME);

        return renderJson(resultList.size(), resultList);
    }

    @ApiOperation(value = "数据库表字段列表")
    @RequestMapping(value = "/code/admin/table/field/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> fieldlLst(@RequestBody Code body) {
        validateRequest(body, Code.TABLE_SCHEMA, Code.TABLE_NAME);

        List<Code> resultList = codeService.tableNameList(body.getTableSchema(), body.getTableName());

        validateResponse(Code.COLUMN_NAME, Code.COLUMN_KEY, Code.CHARACTER_MAXIMUM_LENGTH, Code.COLUMN_TYPE, Code.DATA_TYPE, Code.COLUMN_COMMENT);

        return renderJson(resultList);
    }

    @ApiOperation(value = "数据库表映射代码生成")
    @RequestMapping(value = "/code/admin/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> generate(@RequestBody Code body) {
        validateRequest(body, Code.TABLE_NAME, Code.COLUMN_LIST);

        try {
            String path = CodeController.class.getResource("/").toURI().getPath() + Constant.PUBLISH;
            String packagePath = path + "/" + body.getPackageName();
            String entityPath = packagePath + "/entity";

            FileUtil.createPath(path);
            FileUtil.createPath(packagePath);
            FileUtil.createPath(entityPath);

            List<Code> codeList = JSONArray.parseArray(body.getColumnList(), Code.class);

            Map<String, Object> templateMap = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);

            for (Code code : codeList) {

            }

//            write(templateMap,"sql.template", entityPath + "/sql/" + first_upper_model_name_without_underline + ".sql");

        } catch (URISyntaxException e) {
            e.printStackTrace();

            throw new RuntimeException("代码生成发生错误");
//        } catch (IOException e) {
//            e.printStackTrace();

//            throw new RuntimeException("代码生成发生错误");
        }

        return renderJson(true);
    }


    private void write(Map<String, Object> templateMap, String templateName, String filePath) {
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("template");
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template template = gt.getTemplate(templateName);

            template.binding(templateMap);

            StringWriter writer = new StringWriter();
            template.renderTo(writer);

            OutputStreamWriter outWriter = new OutputStreamWriter(
                    new FileOutputStream(filePath, false), "UTF-8");

            Writer out = new BufferedWriter(outWriter);
            out.write(writer.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
